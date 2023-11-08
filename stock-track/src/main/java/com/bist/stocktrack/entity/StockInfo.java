package com.bist.stocktrack.entity;

import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockInfo implements Serializable {

    private double rate;
    private double lastprice;
    private String lastpricestr;
    private double hacim;
    private String hacimstr;
    private double min;
    private String minstr;
    private double max;
    private String maxstr;
    private String time;
    private String text;
    private String code;

    @Override
    public String toString() {
        return "StockInfo{" +
                "rate=" + rate +
                ", lastprice=" + lastprice +
                ", lastpricestr='" + lastpricestr + '\'' +
                ", hacim=" + hacim +
                ", hacimstr='" + hacimstr + '\'' +
                ", min=" + min +
                ", minstr='" + minstr + '\'' +
                ", max=" + max +
                ", maxstr='" + maxstr + '\'' +
                ", time='" + time + '\'' +
                ", text='" + text + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
