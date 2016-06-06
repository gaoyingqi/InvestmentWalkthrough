package org.gyq.iw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gyq on 2016/6/5.
 */
public class InvestmentWalkthrough {
    private static final Logger LOGGER = LoggerFactory.getLogger("main");

    private static String filePath = "C:\\Users\\gyq\\Desktop\\上证指数.csv";

    public static void main(String[] args) {
        List<OneLine> lineList = new ArrayList<OneLine>(10000);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int lineNumber = 1;
            String line = reader.readLine();
            while (line != null) {
                String[] split = line.split(",");
                if (split.length != 5) {
                    LOGGER.error(lineNumber + "line is Incorrect");
                } else {
                    lineList.add(new OneLine(split[0], split[1], split[2], split[3], split[4]));
                }
                line = reader.readLine();
                lineNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("read lines " + lineList.size());

        AccountMoney money = new AccountMoney(new BigDecimal(10000), "ltt");
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
            if (up.getClose_upper().compareTo(new BigDecimal(0)) == 1 && line.getDiff_dea().compareTo(new BigDecimal(0)) == -1) {
                line.setClose_upper_sell_flag(true);
            }
            // close_upper + > -
            if (up.getClose_lower().compareTo(new BigDecimal(0)) == -1 && line.getClose_lower().compareTo(new BigDecimal(0)) == 1) {
                line.setClose_lower_buy_flag(true);
            }
        }

        for (int i = 1; i < lineList.size(); i++) {
            OneLine line = lineList.get(i);
            if (line.getDiff_buy_flag()) {
                boolean buy = false;
                if (i - 6 >= 0) {
                    List<OneLine> subList = lineList.subList(i - 5, i);
                    for (OneLine upLine : subList) {
                        if (upLine.getClose_lower_buy_flag()) {
                            buy = true;
                        }
                    }
                    if (buy) {
                        if (money.buy(line.getPrice())) {
                            LOGGER.info("line {}  buy date {}  buy price {}", i, line.getDate(), line.getPrice());
                        }
                    }
                }
            }
            if (line.getDiff_sell_flag()) {
                if (money.sell(line.getPrice())) {
                    LOGGER.info("line {}  sell date sell price {}", i, line.getDate(), line.getPrice());
                }
            }
        }
        LOGGER.info("has money " + money.getMoney().toPlainString());
    }
}
