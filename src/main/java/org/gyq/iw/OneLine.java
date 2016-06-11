package org.gyq.iw;

import java.math.BigDecimal;

/**
 * Created by gyq on 2016/6/5.
 */
public class OneLine {

    private String date;
    private String zhiShu;
    private BigDecimal price;
    private BigDecimal diff_dea;
    private BigDecimal close_upper;
    private BigDecimal close_lower;
    private Boolean diff_buy_flag;
    private Boolean diff_sell_flag;
    private Boolean close_upper_sell_flag;
    private Boolean close_lower_buy_flag;

    OneLine(String date, String zhiShu, String diff_dea, String close_upper, String close_lower) {
        this.date = date;
        this.zhiShu = zhiShu;
        this.price = new BigDecimal(zhiShu);
        this.diff_dea = new BigDecimal(diff_dea);
        this.close_upper = new BigDecimal(close_upper);
        this.close_lower = new BigDecimal(close_lower);
        diff_buy_flag = false;
        diff_sell_flag = false;
        close_upper_sell_flag = false;
        close_lower_buy_flag = false;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getZhiShu() {
        return zhiShu;
    }

    public void setZhiShu(String zhiShu) {
        this.zhiShu = zhiShu;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiff_dea() {
        return diff_dea;
    }

    public void setDiff_dea(BigDecimal diff_dea) {
        this.diff_dea = diff_dea;
    }

    public BigDecimal getClose_upper() {
        return close_upper;
    }

    public void setClose_upper(BigDecimal close_upper) {
        this.close_upper = close_upper;
    }

    public BigDecimal getClose_lower() {
        return close_lower;
    }

    public void setClose_lower(BigDecimal close_lower) {
        this.close_lower = close_lower;
    }

    @Override
    public String toString() {
        return "OneLine{" +
                "date='" + date + '\'' +
                ", zhiShu='" + zhiShu + '\'' +
                ", price=" + price +
                ", diff_dea=" + diff_dea +
                ", close_upper=" + close_upper +
                ", close_lower=" + close_lower +
                '}';
    }

    public Boolean getDiff_buy_flag() {
        return diff_buy_flag;
    }

    public void setDiff_buy_flag(Boolean diff_buy_flag) {
        this.diff_buy_flag = diff_buy_flag;
    }

    public Boolean getDiff_sell_flag() {
        return diff_sell_flag;
    }

    public void setDiff_sell_flag(Boolean diff_sell_flag) {
        this.diff_sell_flag = diff_sell_flag;
    }

    public Boolean getClose_upper_sell_flag() {
        return close_upper_sell_flag;
    }

    public void setClose_upper_sell_flag(Boolean close_upper_sell_flag) {
        this.close_upper_sell_flag = close_upper_sell_flag;
    }

    public Boolean getClose_lower_buy_flag() {
        return close_lower_buy_flag;
    }

    public void setClose_lower_buy_flag(Boolean close_lower_buy_flag) {
        this.close_lower_buy_flag = close_lower_buy_flag;
    }
}
