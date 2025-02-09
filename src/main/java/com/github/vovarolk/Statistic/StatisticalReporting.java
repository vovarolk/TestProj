package com.github.vovarolk.Statistic;

/**
 * Класс статистики по сортировке файлов. Включает в себя полную и сокращенную статистику.
 **/
public class StatisticalReporting {

    private static final String SHORT_STATISTIC_MESSAGE = "В файле \"%s\" добавлено %d элементов типа %ss." + System.lineSeparator();
    private static final String FULL_STATISTIC_NUMBER_SUPPLEMENT = " наибольший - %s," + System.lineSeparator() + " наименьший - %s," + System.lineSeparator() + " среднее значение - %s," + System.lineSeparator() + " сумма элементов - %s" + System.lineSeparator();
    private static final String FULL_STATISTIC_STRING_SUPPLEMENT = " длина наименьшей строки - %s," + System.lineSeparator() + " длина наибольшей строки - %s," + System.lineSeparator();

    SortingResult sortingResult;

    public StatisticalReporting(SortingResult sortingResult) {
        this.sortingResult = sortingResult;
    }

    public String getFullStatistic() {
        String message = getFullStatisticInteger() + "\n";
        message += getFullStatisticFloat() + "\n";
        message += getFullStatisticString() + "\n";
        return (message);
    }

    public String getShortStatistic() {
        String message = getShortTypeStatistic(sortingResult.getIntStatistic().getFileName(), sortingResult.getIntStatistic().getCount(), IntStatistic.TYPE_NAME);
        message += getShortTypeStatistic(sortingResult.getFloatStatistic().getFileName(), sortingResult.getFloatStatistic().getCount(), FloatStatistic.TYPE_NAME);
        message += getShortTypeStatistic(sortingResult.getStringStatistic().getNameFile(), sortingResult.getStringStatistic().getCount(), StringStatistic.TYPE_NAME);
        return (message);
    }

    private String getShortTypeStatistic(String nameOutFile, int countLinesType, String nameType) {
        return ( String.format(SHORT_STATISTIC_MESSAGE, nameOutFile, countLinesType, nameType));
    }

    private String getFullStatisticInteger() {
        if(sortingResult.getIntStatistic().getCount()  == 0)
            return getShortTypeStatistic(sortingResult.getIntStatistic().getFileName(), sortingResult.getIntStatistic().getCount(), IntStatistic.TYPE_NAME);
        String shortStatistic = getShortTypeStatistic(sortingResult.getIntStatistic().getFileName(), sortingResult.getIntStatistic().getCount(), IntStatistic.TYPE_NAME);
        String supplement = String.format(FULL_STATISTIC_NUMBER_SUPPLEMENT, sortingResult.getIntStatistic().getMaxValue(), sortingResult.getIntStatistic().getMinValue(), sortingResult.getIntStatistic().getAvarage(), sortingResult.getIntStatistic().getSum());
        return (shortStatistic + supplement);
    }

    private String getFullStatisticFloat() {
        if(sortingResult.getFloatStatistic().getCount()  == 0)
            return getShortTypeStatistic(sortingResult.getFloatStatistic().getFileName(), sortingResult.getFloatStatistic().getCount(), FloatStatistic.TYPE_NAME);
        String shortStatistic = getShortTypeStatistic(sortingResult.getFloatStatistic().getFileName(), sortingResult.getFloatStatistic().getCount(), FloatStatistic.TYPE_NAME);
        String supplement = String.format(FULL_STATISTIC_NUMBER_SUPPLEMENT, sortingResult.getFloatStatistic().getMaxValue(), sortingResult.getFloatStatistic().getMinValue(), sortingResult.getFloatStatistic().getAvarage(), sortingResult.getFloatStatistic().getSum());
        return (shortStatistic + supplement);
    }


    private String getFullStatisticString() {
        if(sortingResult.getStringStatistic().getCount()  == 0)
            return getShortTypeStatistic(sortingResult.getStringStatistic().getNameFile(), sortingResult.getStringStatistic().getCount(), StringStatistic.TYPE_NAME);
        String shortStatistic = getShortTypeStatistic(sortingResult.getStringStatistic().getNameFile(), sortingResult.getStringStatistic().getCount(), StringStatistic.TYPE_NAME);
        String supplement = String.format(FULL_STATISTIC_STRING_SUPPLEMENT, sortingResult.getStringStatistic().getMaxValue(), sortingResult.getStringStatistic().getMinValue());
        return (shortStatistic + supplement);
    }

}

