package com.github.vovarolk.statistic;

/**Класс параметров по которым выводится статистика для типа String**/

public class StringStatistic {
    public static final String TYPE_NAME = "String";
    private int count;
    private int maxValue;
    private int minValue;
    private final String fileName;

    public StringStatistic(String nameFile) {
        count = 0;
        maxValue = Integer.MIN_VALUE;
        minValue = Integer.MAX_VALUE;
        this.fileName = nameFile;
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
        return fileName;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public void inceremenCount() {
        count++;
    }

    public void increaseMaxValue(int newMaxValue) {
        if (newMaxValue > maxValue) maxValue = newMaxValue;
    }

    public void decreaseMinValue(int newMinValue) {
        if (newMinValue < minValue) minValue = newMinValue;
    }
}
