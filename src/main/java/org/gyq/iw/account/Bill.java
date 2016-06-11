package org.gyq.iw.account;

import org.gyq.iw.util.BigDecimalUtil;

import java.math.BigDecimal;

/**
 * 买入某只股票的票据
 * Created by gyq on 2016/6/8.
 */
public class Bill {
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
    private int count;

    /**
     * 已卖光
     */
    private boolean isEmpty;

    public Bill(BigDecimal buyPrice, int count, String buyTime, String stockId) {
        this.buyPrice = buyPrice;
        this.count = count;
        this.buyTime = buyTime;
        this.stockId = stockId;
        if (count > 0) {
            isEmpty = false;
        } else {
            isEmpty = true;
        }
    }

    public TradingHistory sell(BigDecimal percent, String sellTime, BigDecimal sellPrice) {
        if (BigDecimalUtil.greaterThan(percent, 1) || BigDecimalUtil.lessThan(percent, 0)) {
            throw new IllegalArgumentException("percent is not in 0,1");
        }
        if (!isEmpty) {
            int sellCount = percent.multiply(new BigDecimal(count)).intValue();
            if (sellCount <= 0) {
                sellCount = count;
            }

            TradingHistory tradingHistory = new TradingHistory();
            tradingHistory.setStockId(stockId);
            tradingHistory.setBuyPrice(buyPrice);
            tradingHistory.setBuyTime(buyTime);
            tradingHistory.setSellCount(sellCount);
            tradingHistory.setSellPrice(sellPrice);
            tradingHistory.setSellTime(sellTime);
            this.count = count - sellCount;
            if (this.count <= 0) {
                isEmpty = true;
            }
            return tradingHistory;
        }
        throw new RuntimeException(stockId + "  " + buyTime + " is empty");
    }

    public boolean isEmpty() {
        return isEmpty;
    }
}
