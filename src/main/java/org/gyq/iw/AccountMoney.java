package org.gyq.iw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * Created by gyq on 2016/6/5.
 */
public class AccountMoney {
    private final static Logger LOGGER = LoggerFactory.getLogger(AccountMoney.class.getName());
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
            // LOGGER.info(accountId + " buy by price " + price.toPlainString());
            return true;
        } else {
            // LOGGER.info(accountId + " if full , by price " + price.toPlainString());
            return false;
        }
    }

    /**
     * 全部卖出某个价格
     *
     * @param price
     */
    public boolean sell(BigDecimal price) {
        if (!canBuy) {
            lastSellMoney = price;
            canBuy = true;
            //LOGGER.info(accountId + " sell by price " + price.toPlainString());
            calculatingRevenue();
            return true;
        } else {
            //LOGGER.info(accountId + " if empty , by price " + price.toPlainString());
            return false;
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
