package com.company.Statistic;

public class StatisticParametrsInt extends StatisticParametrsString {
    private int sum;

    public StatisticParametrsInt(){
        super();
        sum = 0;
    }

    public int getSum() {
        return sum;
    }

    public void increaseSum(int value){
        sum+=value;
    }

    public float getAvarage() {
        return sum/count;
    }


}
