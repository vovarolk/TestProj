package com.github.vovarolk.Statistic;

/**
 * Класс параметров по которым выводится статистика для типа Float
 **/
public class StatisticParametrsFloat {
    public static final String NAME_TYPE = "Float";

    private int count;
    private float sum;
    private float maxValue;
    private float minValue;
    protected String nameFile;


    public StatisticParametrsFloat(String nameFile) {
        count = 0;
        sum = 0;
        maxValue = Float.MIN_VALUE;
        minValue = Float.MAX_VALUE;
        this.nameFile = nameFile;
    }

    public float getSum() {
        return sum;
    }

    public void increaseSum(float value) {
        sum += value;
    }

    public float getAvarage() {
        return sum / count;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public int getCount() {
        return count;
    }


    public float getMinValue() {
        return minValue;
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    public void setMinValue(float minValue) {
        this.minValue = minValue;
    }

    public void incriminationCount() {
        count++;
    }

    public void increaseMaxValue(float newMaxValue) {
        if (newMaxValue > maxValue) maxValue = newMaxValue;
    }

    public void decreaseMinValue(float newMinValue) {
        if (newMinValue < minValue) minValue = newMinValue;
    }


}
