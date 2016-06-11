package org.gyq.iw.account;

import org.gyq.iw.util.BigDecimalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * 一个金融账户
 * Created by gyq on 2016/6/5.
 */
public class AccountMoney {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountMoney.class.getName());
    private BigDecimal remainderMoney;
    private String accountId;
    private NumberFormat numberFormat = NumberFormat.getNumberInstance();

    private List<Bill> bills = new ArrayList<>(10);
    private List<TradingHistory> histories = new ArrayList<>(100);

    public AccountMoney(BigDecimal money, String accountId) {
        this.remainderMoney = money.setScale(4, BigDecimal.ROUND_CEILING);
        this.accountId = accountId;
        numberFormat.setMinimumFractionDigits(0);//设置小数点后面允许多少位
        numberFormat.setGroupingUsed(true);//设置分组如：,
    }

    /**
     * 全部买入某个价格
     *
     * @param price   买入价格
     * @param time    买入时间
     * @param stockId 股票id
     * @param percent 闲鱼资金百分比
     */
    public boolean buy(BigDecimal price, String time, String stockId, BigDecimal percent) {
        requireNonNull(price);
        requireNonNull(time);
        requireNonNull(stockId);
        requireNonNull(percent);
        if (BigDecimalUtil.lessThan(percent, 0) && BigDecimalUtil.greaterThan(percent, 1)) {
            throw new IllegalArgumentException("percent need [0-1]");
        }
        if (hasMoney()) {
            BigDecimal buyMoney = remainderMoney.multiply(percent);
            int count = BigDecimalUtil.divide(buyMoney, price).intValue();
            if (count > 0) {
                Bill bill = new Bill(price, count, time, stockId);
                bills.add(bill);
                this.remainderMoney = this.remainderMoney.subtract(price.multiply(new BigDecimal(count)));
                LOGGER.debug("success buy by price {} with remainderMoney {} count {}", numberFormat.format(price), numberFormat.format(price.multiply(new BigDecimal(count))), count);
                return true;
            } else {
                LOGGER.debug("want to buy by price , but remain remainderMoney {}", remainderMoney);
                return false;
            }
        } else {
            LOGGER.debug("{} if full , by price {}", accountId, price.toPlainString());
            return false;
        }
    }

    /**
     * 全部卖出某个价格
     *
     * @param price
     */
    public boolean sell(BigDecimal price, String time) {
        return this.sell(price, new BigDecimal(1), time);
    }

    /**
     * 全部卖出某个价格
     *
     * @param price   卖出价格
     * @param percent 卖出百分比，相对于账户持有股票金额   （0,1]
     */
    public boolean sell(BigDecimal price, BigDecimal percent, String time) {

        requireNonNull(price);
        requireNonNull(percent);
        requireNonNull(time);

        if (BigDecimalUtil.lessThan(percent, 0) || BigDecimalUtil.greaterThan(percent, 1)) {
            throw new IllegalArgumentException("percent must be in (0,1]");
        }
        if (hasBill()) {
            for (Bill bill : bills) {
                TradingHistory tradingHistory = bill.sell(percent, time, price);
                histories.add(tradingHistory);
                this.remainderMoney = this.remainderMoney.add(tradingHistory.getSellPrice().multiply(new BigDecimal(tradingHistory.getSellCount())));
                LOGGER.debug("sell {} by price {} count {}, earn remainderMoney {}", tradingHistory.getStockId(), this.numberFormat.format(tradingHistory.getSellPrice()), tradingHistory.getSellCount(), tradingHistory.getEarnMoney());
            }
            clearEmptyBill();
            return true;
        }
        return false;
    }

    public BigDecimal getRemainderMoney() {
        return remainderMoney;
    }

    /**
     * 是否有闲余资金
     *
     * @return 有，没有
     */
    private boolean hasMoney() {
        return BigDecimalUtil.greaterThan(this.remainderMoney, 0);
    }

    /**
     * 是否持仓
     *
     * @return 是，没
     */
    private Boolean hasBill() {
        return !bills.isEmpty();
    }

    private void clearEmptyBill() {
        if (hasBill()) {
            for (Iterator<Bill> billIterator = bills.iterator(); billIterator.hasNext(); ) {
                Bill bill = billIterator.next();
                if (bill.isEmpty()) {
                    billIterator.remove();
                }
            }
        }
    }
}
