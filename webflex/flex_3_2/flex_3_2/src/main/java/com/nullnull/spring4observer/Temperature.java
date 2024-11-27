package com.nullnull.spring4observer;

/**
 * @author nullnull
 * @since 2023/7/13
 */
public class Temperature {


    /**
     * 温度值
     */
    private final double value;

    public Temperature(double temperature) {
        this.value = temperature;
    }

    public double getValue() {
        return value;
    }


}
