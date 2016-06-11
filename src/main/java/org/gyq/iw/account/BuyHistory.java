package org.gyq.iw.account;

import java.math.BigDecimal;

/**
 * Created by gyq on 2016/6/11.
 */
public class BuyHistory extends TradingHistory {
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
    private int buyCount;

    private BigDecimal newAveragPrice;
}
