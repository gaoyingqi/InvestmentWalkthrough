package org.gyq.iw.account;

import java.math.BigDecimal;

/**
 * 交易历史
 * Created by gyq on 2016/6/8.
 */
public class TradingHistory {
    /**
     * 股票id
     */
    private String stockId;

    /**
     * 买入价格
     */
    private BigDecimal buyPrice;
    /**
     * 买入时间
     */
    private String buyTime;
    /**
     * 股票数
     */
    private int sellCount;

    /**
     * 卖出价格
     */
    private BigDecimal sellPrice;

    /**
     * 卖出时间
     */
    private String sellTime;

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public int getSellCount() {
        return sellCount;
    }

    public void setSellCount(int sellCount) {
        this.sellCount = sellCount;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getSellTime() {
        return sellTime;
    }

    public void setSellTime(String sellTime) {
        this.sellTime = sellTime;
    }

    public BigDecimal getEarnMoney() {
        return sellPrice.subtract(buyPrice).multiply(new BigDecimal(sellCount));
    }
}
