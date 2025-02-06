package com.github.vovarolk.Statistic;

/**
 * Класс параметров по которым выводится статистика для типа Integer
 **/

public class StatisticParametrsInt extends StatisticParametrsString {
    public static final String NAME_TYPE = "Integer";

    private float sum;

    public StatisticParametrsInt(String nameFile) {
        super(nameFile);
        sum = 0;
    }

    public float getSum() {
        return sum;
    }

    public void increaseSum(int value) {
        sum += value;
    }

    public float getAvarage() {
        return sum / count;
    }


}
