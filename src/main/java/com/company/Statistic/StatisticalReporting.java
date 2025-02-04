package com.company.Statistic;

import com.company.SortingParameters;
import com.company.Statistic.StatisticParametrsFloat;
import com.company.Statistic.StatisticParametrsInt;
import com.company.Statistic.StatisticParametrsString;

public class StatisticalReporting {
    StatisticParametrsInt parametrsInt = new StatisticParametrsInt();
    StatisticParametrsFloat parametrsFloat = new StatisticParametrsFloat();
    StatisticParametrsString parametrsString = new StatisticParametrsString();
    SortingParameters sortingParametrs;

    public StatisticalReporting(StatisticParametrsInt parametrsInt,  StatisticParametrsFloat parametrsFloat, StatisticParametrsString parametrsString, SortingParameters sortingParametrs){
        this.parametrsInt = parametrsInt;
        this.parametrsFloat = parametrsFloat;
        this.parametrsString= parametrsString;
        this.sortingParametrs = sortingParametrs;
    }


    public String getFullStatistic(){
        String message = getFullInteger() + "\n";
        message += getFullFloat() + "\n";
        message += getFullString() + "\n";
        return (message);
    }

    public String getShortStatistic(){
        String message = getShortInteger() + "\n";
        message += getShortFloat() + "\n";
        message += getShortString() + "\n";
        return (message);

    }

    private String getFullInteger(){
        return( getShortInteger() + " \n Наибольший - " + parametrsInt.getMaxValue()
                + " \n наименьший - " + parametrsInt.getMinValue() + " \n среднее значение - " + parametrsInt.getAvarage()
                + " \n сумма элементов - " + parametrsInt.getSum());

    }

    private String getShortInteger(){
        return( "В файле " + sortingParametrs.getPrefixOutFiles() + sortingParametrs.INTEGERS_FILE_DEFAULT_NAME +
                " добавлено " + parametrsInt.getCount() + " элементов типа Integer. ");
    }



    private String getFullFloat(){
        return( getShortFloat() + "\n Наибольший - " + parametrsFloat.getMaxValue()
                + "\n наименьший - " + parametrsFloat.getMinValue() + " \n среднее значение - " + parametrsFloat.getAvarage()
                + "\n сумма элементов - " + parametrsFloat.getSum());

    }

    private String getShortFloat(){
        return( "В файле " + sortingParametrs.getPrefixOutFiles() + sortingParametrs.FLOATS_FILE_DEFAULT_NAME +
                " добавлено " + parametrsFloat.getCount() + " элементов типа Float. ");
    }

    private String getFullString(){
        return( getShortString() + "\n Наибольшая длина строки - " + parametrsString.getMaxValue()
                + " \n наименьшая длина строки - " + parametrsString.getMinValue());

    }

    private String getShortString(){
        return( "В файле " + sortingParametrs.getPrefixOutFiles() + sortingParametrs.STRINGS_FILE_DEFAULT_NAME +
                " добавлено " + parametrsString.getCount() + " элементов типа String. ");
    }

}

