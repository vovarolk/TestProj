package com.github.vovarolk;

import com.github.vovarolk.Statistic.StatisticParametrsFloat;
import com.github.vovarolk.Statistic.StatisticParametrsInt;
import com.github.vovarolk.Statistic.StatisticParametrsString;
import com.github.vovarolk.Statistic.StatisticalReporting;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Pattern;


/**
 * Класс сортировки строк типа integer, float, string по соответствующим текстовым файлам из одного или нескольких файлов формата *.txt
 *
 * @version 0.4
 * @autor Володя Аникаев
 */

public class FileStringSorter {

    private static final String SORT_FINISHED_MESSAGE = "Сортировка прошла успешно!" + System.lineSeparator();

    private final SortingParameters sortingParametrs;

    StatisticParametrsInt parametrsInt;
    StatisticParametrsFloat parametrsFloat;
    StatisticParametrsString parametrsString;


    private Pattern patternInt, PatternFloat, PatternFloatMinus, PatternFloatMinusE, PatternFloatE;

    /**
     * Конструктор - создание нового объекта
     */

    public FileStringSorter(SortingParameters parameters) {
        this.sortingParametrs = parameters;
        parametrsInt = new StatisticParametrsInt(sortingParametrs.getPrefixOutFiles() + SortingParameters.INTEGERS_FILE_DEFAULT_NAME);
        parametrsFloat = new StatisticParametrsFloat(sortingParametrs.getPrefixOutFiles() + SortingParameters.FLOATS_FILE_DEFAULT_NAME);
        parametrsString = new StatisticParametrsString(sortingParametrs.getPrefixOutFiles() + SortingParameters.STRINGS_FILE_DEFAULT_NAME);
    }

    //гетеры и сетеры


    public String sortWithMassage() {
        sortFiles();

        StatisticalReporting report = new StatisticalReporting(parametrsInt, parametrsFloat, parametrsString, sortingParametrs);
        String message = "" + SORT_FINISHED_MESSAGE;
        if (sortingParametrs.isFullStat() && sortingParametrs.isShortStat())
            message += "Выбраны два варианта показа статистики, будет показана полная статистика" + System.lineSeparator();
        if (sortingParametrs.isFullStat()) message += report.getFullStatistic();
        else if (sortingParametrs.isShortStat()) message += report.getShortStatistic();

        return (message);
    }


    private void sortFiles() {
        ArrayList<BufferedReader> readers = makeInputFileReaders();

        if (!sortingParametrs.isAppend()) {
            fileClear(sortingParametrs.getPrefixOutFiles() + SortingParameters.INTEGERS_FILE_DEFAULT_NAME);
            fileClear(sortingParametrs.getPrefixOutFiles() + SortingParameters.FLOATS_FILE_DEFAULT_NAME);
            fileClear(sortingParametrs.getPrefixOutFiles() + SortingParameters.STRINGS_FILE_DEFAULT_NAME);
        }

        while (readers.size() != 0) {
            ArrayList<Integer> indexFinishReaders = new ArrayList<Integer>();
            for (BufferedReader reader : readers) {
                String line;
                try {
                    if ((line = reader.readLine()) != null) {
                        if (checkStringIsInt(line)) addNewInt(line);
                        else if (checkStringIsFloat(line)) addNewFloat(line);
                        else addNewString(line);
                    } else {
                        indexFinishReaders.add(readers.indexOf(reader));
                    }
                } catch (IOException e) {
                    System.err.println("Ошибка чтения из файла");
                    e.printStackTrace();
                }
            }
            for (int i : indexFinishReaders) {
                readers.remove(i);
            }
        }
    }

    private ArrayList<BufferedReader> makeInputFileReaders() {
        ArrayList<BufferedReader> readers = new ArrayList<>();
        for (String nameInputFile : sortingParametrs.getInputFilesName()) {
            try {
                readers.add(Files.newBufferedReader(Paths.get(nameInputFile)));
            } catch (NoSuchFileException e) {
                System.err.println("Ошибка открытия файла " + nameInputFile + " Такого файла не существует. Пожалуйста, проверьте правильность написания имени файла и его наличие.");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return readers;
    }

    private boolean checkStringIsInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return (false);
        }
    }

    private boolean checkStringIsFloat(String str) {
        try {
            return (!Float.isInfinite(Float.parseFloat(str)));

        } catch (NumberFormatException e) {
            return (false);
        }
    }

    private void addNewInt(String str) {
        int newInt = Integer.parseInt(str);
        parametrsInt.increaseMaxValue(newInt);
        parametrsInt.decreaseMinValue(newInt);
        parametrsInt.increaseSum(newInt);
        parametrsInt.incriminationCount();
        writeFile(str, (parametrsInt.getNameFile()));
    }

    private void addNewFloat(String str) {
        float newFloat = Float.parseFloat(str);
        parametrsFloat.increaseMaxValue(newFloat);
        parametrsFloat.decreaseMinValue(newFloat);
        parametrsFloat.increaseSum(newFloat);
        parametrsFloat.incriminationCount();
        writeFile(str + "\n", (parametrsFloat.getNameFile()));
    }

    private void addNewString(String str) {
        parametrsString.increaseMaxValue(str.length());
        parametrsString.decreaseMinValue(str.length());
        parametrsString.incriminationCount();
        writeFile(str, (parametrsString.getNameFile()));
    }


    private void writeFile(String information, String nameFile) {
        try {
            FileWriter writer = new FileWriter((sortingParametrs.getPathOutFiles() + nameFile), true);
            writer.write(information + System.lineSeparator());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fileClear(String nameFile) {
        try {
            FileWriter writer = new FileWriter((sortingParametrs.getPathOutFiles() + nameFile), false);
            writer.write("");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
