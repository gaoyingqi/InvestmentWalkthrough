package org.gyq.iw.account;

import java.math.BigDecimal;

/**
 * 买入某只股票的票据
 * Created by gyq on 2016/6/8.
 */
public class Bill {
    private String stockId;

    /**
     * 目前价值
     */
    private BigDecimal worth;
    private BigDecimal currentPrice;
    /**
     * 买入价格
     */
    private BigDecimal buyPrivice;
    private int count;

}
