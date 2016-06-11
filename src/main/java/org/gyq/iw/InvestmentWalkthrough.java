package org.gyq.iw;

import org.gyq.iw.account.AccountMoney;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by gyq on 2016/6/5.
 */
public class InvestmentWalkthrough {
    private static final Logger LOGGER = LoggerFactory.getLogger("main");

    private static final BigDecimal initialMoney = new BigDecimal(100000000);

    private static String filePath = "E:\\百度云同步盘\\Dropbox\\上证指数.csv";

    public static void main(String[] args) {
        List<OneLine> lineList = new ArrayList<OneLine>(10000);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int lineNumber = 1;
            String line = reader.readLine();
            while (line != null) {
                String[] split = line.split(",");
                if (split.length == 5) {
                    lineList.add(new OneLine(split[0], split[1], split[2], split[3], split[4]));
                } else {
                    LOGGER.error(lineNumber + "line is Incorrect");
                }
                line = reader.readLine();
                lineNumber++;
            }
        } catch (IOException e) {
            LOGGER.error("read file error", e);
        }
        LOGGER.info("read lines " + lineList.size());

        AccountMoney money = new AccountMoney(initialMoney, "ltt");
        for (int i = 1; i < lineList.size(); i++) {
            OneLine up = lineList.get(i - 1);
            OneLine line = lineList.get(i);
            // Diff_dea - > +
            if (up.getDiff_dea().compareTo(new BigDecimal(0)) == -1 && line.getDiff_dea().compareTo(new BigDecimal(0)) == 1) {
                line.setDiff_buy_flag(true);
            }
            // Diff_dea + > -
            if (up.getDiff_dea().compareTo(new BigDecimal(0)) == 1 && line.getDiff_dea().compareTo(new BigDecimal(0)) == -1) {
                line.setDiff_sell_flag(true);
            }
            // close_upper + > -
            if (up.getClose_upper().compareTo(new BigDecimal(0)) == 1 && line.getClose_upper().compareTo(new BigDecimal(0)) == -1) {
                line.setClose_upper_sell_flag(true);
            }
            // close_upper + > -
            if (up.getClose_lower().compareTo(new BigDecimal(0)) == -1 && line.getClose_lower().compareTo(new BigDecimal(0)) == 1) {
                line.setClose_lower_buy_flag(true);
            }
        }

        for (int i = 1; i < lineList.size(); i++) {
            OneLine line = lineList.get(i);
            List<OneLine> subList = Collections.emptyList();
            if (i - 6 >= 0) {
                subList = lineList.subList(i - 5, i);
            }
            if (line.getDiff_buy_flag()) {
                boolean buy = false;
                if (!subList.isEmpty()) {
                    for (OneLine upLine : subList) {
                        if (upLine.getClose_lower_buy_flag()) {
                            buy = true;
                        }
                    }
                    if (buy) {
                        boolean b = money.buy(line.getPrice(), line.getDate(), "1", new BigDecimal(1));
                        LOGGER.info("want buy {}  by price {} and {}", line.getDate(), line.getPrice(), b ? "success" : "fail");
                    }
                }
            }
            if (line.getClose_upper_sell_flag()) {
                boolean b = money.sell(line.getPrice(), new BigDecimal(0.5), line.getDate());
                LOGGER.info("want sell {} 50% by price {} and {} ", line.getDate(), line.getPrice(), b ? "success" : "fail");
            }
            if (line.getDiff_sell_flag()) {
                boolean sell = false;
                if (!subList.isEmpty()) {
                    for (OneLine upLine : subList) {
                        if (upLine.getClose_upper_sell_flag()) {
                            sell = true;
                        }
                    }
                    if (sell) {
                        boolean b = money.sell(line.getPrice(), line.getDate());
                        LOGGER.info("want sell {} 100% by price {} and ", line.getDate(), line.getPrice(), b ? "success" : "fail");
                    }
                }
            }
        }
        OneLine last = lineList.get(lineList.size() - 1);
        money.sell(last.getPrice(), last.getDate());
        LOGGER.info("want sell {} 100% by price {}", last.getDate(), last.getPrice());

        LOGGER.info("initialMoney {} ,now money {} ,up {}", initialMoney.toPlainString(), money.getRemainderMoney().toPlainString(), money.getRemainderMoney().divide(initialMoney, BigDecimal.ROUND_CEILING));
    }
}
