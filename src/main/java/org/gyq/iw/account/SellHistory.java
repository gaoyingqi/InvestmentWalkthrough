package org.gyq.iw.account;

import java.math.BigDecimal;

/**
 * Created by gyq on 2016/6/11.
 */
public class SellHistory extends TradingHistory {

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

}
