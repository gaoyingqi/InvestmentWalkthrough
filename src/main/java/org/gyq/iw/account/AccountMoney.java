package org.gyq.iw.account;

import org.gyq.iw.util.BigDecimalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * 一个金融账户
 * Created by gyq on 2016/6/5.
 */
public class AccountMoney {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountMoney.class.getName());
    private BigDecimal money;
    private BigDecimal lastBuyMoney;
    private BigDecimal lastSellMoney;
    private boolean canBuy = true;
    private String accountId;
    private NumberFormat numberFormat = NumberFormat.getNumberInstance();


    public AccountMoney(BigDecimal money, String accountId) {
        this.money = money.setScale(2, BigDecimal.ROUND_CEILING);
        this.accountId = accountId;
        numberFormat.setMinimumFractionDigits(0);//设置小数点后面允许多少位
        numberFormat.setGroupingUsed(true);//设置分组如：,
    }

    /**
     * 全部买入某个价格
     *
     * @param price
     */
    public boolean buy(BigDecimal price) {
        if (canBuy) {
            lastBuyMoney = price;
            lastSellMoney = null;
            canBuy = false;
            LOGGER.debug("{} buy by price {}", accountId, price.toPlainString());
            return true;
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
    public boolean sell(BigDecimal price) {
        if (canBuy) {
            LOGGER.debug("{} if empty , by price {}", accountId, price.toPlainString());
            return false;
        } else {
            lastSellMoney = price;
            canBuy = true;
            LOGGER.debug("{} sell by price {}", accountId, price.toPlainString());
            calculatingRevenue();
            return true;
        }
    }

    /**
     * 全部卖出某个价格
     *
     * @param price   卖出价格
     * @param percent 卖出百分比，相对于账户持有股票金额   （0,1]
     */
    public boolean sell(BigDecimal price, BigDecimal percent) {
        if (percent == null || BigDecimalUtil.lessThan(percent, 0) || BigDecimalUtil.greaterThan(percent, 1)) {
            throw new IllegalArgumentException("percent must be in (0,1]");
        }
        if (canBuy) {
            LOGGER.debug("{} if empty , by price {}", accountId, price.toPlainString());
            return false;
        } else {
            lastSellMoney = price;
            canBuy = true;
            LOGGER.debug("{} sell by price {}", accountId, price.toPlainString());
            calculatingRevenue();
            return true;
        }
    }

    /**
     * 计算收入
     */
    private void calculatingRevenue() {
        BigDecimal growthRate = lastSellMoney.divide(lastBuyMoney, BigDecimal.ROUND_CEILING, 6);
        BigDecimal multiply = money.multiply(growthRate);
        LOGGER.info("{} money change from {} to {} growthRate is {} {}", accountId, numberFormat.format(money), numberFormat.format(multiply), numberFormat.format(growthRate.multiply(new BigDecimal(100))), growthRate.doubleValue() > 1);
        money = multiply;
    }

    public BigDecimal getMoney() {
        return money;
    }
}
