package com.company.Statistic;

public class StatisticParametrsFloat{
    private int count;
    private float sum;
    private float maxValue;
    private float minValue;

    public StatisticParametrsFloat(){
        count = 0;
        sum = 0;
        maxValue = Float.MIN_VALUE;
        minValue = Float.MAX_VALUE;
    }

    public float getSum() {
        return sum;
    }

    public void increaseSum(float value){
        sum+=value;
    }

    public float getAvarage() {
        return sum/count;
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



    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    public void setMinValue(float minValue) {
        this.minValue = minValue;
    }

    public void incriminationCount(){
        count ++;
    }

    public void increaseMaxValue(float newMaxValue){
        if (newMaxValue > maxValue) maxValue = newMaxValue;
    }

    public void decreaseMinValue(float newMinValue){
        if (newMinValue < minValue) minValue = newMinValue;
    }


}
