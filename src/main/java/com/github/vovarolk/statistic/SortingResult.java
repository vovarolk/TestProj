package com.github.vovarolk.statistic;

/**Класс результатов сортировки данных**/
public class SortingResult {

    IntStatistic intStatistic;
    FloatStatistic floatStatistic;
    StringStatistic stringStatistic;

    public SortingResult(IntStatistic parametrsInt, FloatStatistic parametrsFloat, StringStatistic parametrsString) {
        this.intStatistic = parametrsInt;
        this.floatStatistic = parametrsFloat;
        this.stringStatistic = parametrsString;
    }

    public FloatStatistic getFloatStatistic() {
        return floatStatistic;
    }

    public IntStatistic getIntStatistic() {
        return intStatistic;
    }

    public StringStatistic getStringStatistic() {
        return stringStatistic;
    }
}
