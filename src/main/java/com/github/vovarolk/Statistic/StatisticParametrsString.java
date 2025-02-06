package com.github.vovarolk.Statistic;

/**
 * Класс параметров по которым выводится статистика для типа String
 **/

public class StatisticParametrsString {
    public static final String NAME_TYPE = "String";


    protected int count;
    protected int maxValue;
    protected int minValue;
    protected String nameFile;


    public StatisticParametrsString(String nameFile) {
        count = 0;
        maxValue = Integer.MIN_VALUE;
        minValue = Integer.MAX_VALUE;
        this.nameFile = nameFile;
    }

    public int getCount() {
        return count;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public void incriminationCount() {
        count++;
    }

    public void increaseMaxValue(int newMaxValue) {
        if (newMaxValue > maxValue) maxValue = newMaxValue;
    }

    public void decreaseMinValue(int newMinValue) {
        if (newMinValue < minValue) minValue = newMinValue;
    }
}
