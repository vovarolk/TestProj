package com.github.vovarolk;

import com.github.vovarolk.Statistic.StatisticalReporting;

public class Main {
    private static final String SORT_FINISHED_MESSAGE = "Сортировка прошла успешно!" + System.lineSeparator();

    public static void main(String[] args) {
        Parser parser = new Parser(args);
        SortingParameters parameters = parser.getParameters();

        FileStringSorter fs = new FileStringSorter(parameters);
        StatisticalReporting report = new StatisticalReporting(fs.sortFiles());
        String message = "" + SORT_FINISHED_MESSAGE;
        if (parameters.isFullStat() && parameters.isShortStat())
            message += "Выбраны два варианта показа статистики, будет показана полная статистика" + System.lineSeparator();
        if (parameters.isFullStat())
            message += report.getFullStatistic();
        else
            if (parameters.isShortStat()) message += report.getShortStatistic();
        System.out.println(message);
    }
}

