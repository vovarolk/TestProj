package com.github.vovarolk.Statistic;

import com.github.vovarolk.SortingParameters;

/**
 * Класс статистики по сортировке файлов. Включает в себя полную и сокращенную статистику.
 **/
public class StatisticalReporting {

    private static final String SHORT_STATISTIC_MESSAGE = "В файле \"%s\" добавлено %d элементов типа %s." + System.lineSeparator();
    private static final String FULL_STATISTIC_NUMBER_SUPPLEMENT = " наибольший - %s," + System.lineSeparator() + " наименьший - %s," + System.lineSeparator() + " среднее значение - %f," + System.lineSeparator() + " сумма элементов - %s" + System.lineSeparator();
    private static final String FULL_STATISTIC_STRING_SUPPLEMENT = " длина наименьшей строки - %s," + System.lineSeparator() + " длина наибольшей строки - %s," + System.lineSeparator();


    StatisticParametrsInt parametrsInt;
    StatisticParametrsFloat parametrsFloat;
    StatisticParametrsString parametrsString;
    SortingParameters sortingParametrs;

    public StatisticalReporting(StatisticParametrsInt parametrsInt, StatisticParametrsFloat parametrsFloat, StatisticParametrsString parametrsString, SortingParameters sortingParametrs) {
        this.parametrsInt = parametrsInt;
        this.parametrsFloat = parametrsFloat;
        this.parametrsString = parametrsString;
        this.sortingParametrs = sortingParametrs;
    }


    public String getFullStatistic() {
        String message = getFullStatisticInteger() + "\n";
        message += getFullStatisticFloat() + "\n";
        message += getFullStatisticString() + "\n";
        return (message);
    }

    public String getShortStatistic() {
        String message = getShortTypeStatistic(parametrsInt.getNameFile(), parametrsInt.getCount(), StatisticParametrsInt.NAME_TYPE);
        message += getShortTypeStatistic(parametrsFloat.getNameFile(), parametrsFloat.getCount(), StatisticParametrsFloat.NAME_TYPE);
        message += getShortTypeStatistic(parametrsString.getNameFile(), parametrsString.getCount(), StatisticParametrsString.NAME_TYPE);
        return (message);
    }

    private String getFullStatisticInteger() {
        String shortStatistic = getShortTypeStatistic(parametrsInt.getNameFile(), parametrsInt.getCount(), StatisticParametrsInt.NAME_TYPE);
        String supplement = String.format(FULL_STATISTIC_NUMBER_SUPPLEMENT, parametrsInt.getMaxValue(), parametrsInt.getMinValue(), parametrsInt.getAvarage(), parametrsInt.getSum());
        return (shortStatistic + supplement);
    }

    private String getShortTypeStatistic(String nameOutFile, int countLinesType, String nameType) {
        String str = String.format(SHORT_STATISTIC_MESSAGE, nameOutFile, countLinesType, nameType);
        return (str);
    }


    private String getFullStatisticFloat() {
        String shortStatistic = getShortTypeStatistic(parametrsFloat.getNameFile(), parametrsFloat.getCount(), StatisticParametrsFloat.NAME_TYPE);
        String supplement = String.format(FULL_STATISTIC_NUMBER_SUPPLEMENT, parametrsFloat.getMaxValue(), parametrsFloat.getMinValue(), parametrsFloat.getAvarage(), parametrsFloat.getSum());
        return (shortStatistic + supplement);
    }


    private String getFullStatisticString() {
        String shortStatistic = getShortTypeStatistic(parametrsString.getNameFile(), parametrsString.getCount(), StatisticParametrsString.NAME_TYPE);
        String supplement = String.format(FULL_STATISTIC_STRING_SUPPLEMENT, parametrsString.getMaxValue(), parametrsString.getMinValue());
        return (shortStatistic + supplement);
    }

}

