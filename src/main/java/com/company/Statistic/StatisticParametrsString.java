package com.company.Statistic;

public class StatisticParametrsString {
    protected int count;
    protected int maxValue;
    protected int minValue;

    public StatisticParametrsString(){
        count = 0;
        maxValue = Integer.MIN_VALUE;
        minValue = Integer.MAX_VALUE;
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



    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public void incriminationCount(){
        count ++;
    }

    public void increaseMaxValue(int newMaxValue){
        if (newMaxValue > maxValue) maxValue = newMaxValue;
    }

    public void decreaseMinValue(int newMinValue){
        if (newMinValue < minValue) minValue = newMinValue;
    }
}
