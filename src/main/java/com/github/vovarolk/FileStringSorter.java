package com.github.vovarolk;

import com.github.vovarolk.Statistic.*;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/** Класс сортировки **/
public class FileStringSorter {
    private final SortingParameters sortingParametrs;

    IntStatistic intStatistic;
    FloatStatistic floatStatistic;
    StringStatistic stringStatistic;

    public FileStringSorter(SortingParameters parameters) {
        this.sortingParametrs = parameters;
        intStatistic = new IntStatistic(sortingParametrs.getPrefixOutFiles() + SortingParameters.INTEGERS_FILE_DEFAULT_NAME);
        floatStatistic = new FloatStatistic(sortingParametrs.getPrefixOutFiles() + SortingParameters.FLOATS_FILE_DEFAULT_NAME);
        stringStatistic = new StringStatistic(sortingParametrs.getPrefixOutFiles() + SortingParameters.STRINGS_FILE_DEFAULT_NAME);
    }

    public SortingResult sortFiles() {
        ArrayList<BufferedReader> readers = makeInputFileReaders();
        if (!sortingParametrs.isAppend()) {
            fileClear(sortingParametrs.getPrefixOutFiles() + SortingParameters.INTEGERS_FILE_DEFAULT_NAME);
            fileClear(sortingParametrs.getPrefixOutFiles() + SortingParameters.FLOATS_FILE_DEFAULT_NAME);
            fileClear(sortingParametrs.getPrefixOutFiles() + SortingParameters.STRINGS_FILE_DEFAULT_NAME);
        }

        while (readers.size() != 0) {
            ArrayList<Integer> indexFinishReaders = new ArrayList<>();
            for (BufferedReader reader : readers) {
                String line;
                try {
                    if ((line = reader.readLine()) != null) {
                        if (checkStringIsInteger(line)) addNewInteger(line);
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
        return (new SortingResult(intStatistic, floatStatistic, stringStatistic ));
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

    private boolean checkStringIsInteger(String str) {
        Pattern pattern = Pattern.compile("-?\\d+");
        Matcher matcher = pattern.matcher(str);
        return(matcher.matches());
    }

    private boolean checkStringIsFloat(String str) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?(E-?\\d+)?");
        Matcher matcher = pattern.matcher(str);
        return(matcher.matches());
    }

    private void addNewInteger(String str) {
        intStatistic.increaseMaxValue(str);
        intStatistic.decreaseMinValue(str);
        intStatistic.increaseSum(str);
        intStatistic.inceremenCount();
        writeFile(str, (intStatistic.getFileName()));
    }

    private void addNewFloat(String str) {
        floatStatistic.increaseMaxValue(str);
        floatStatistic.decreaseMinValue(str);
        floatStatistic.increaseSum(str);
        floatStatistic.inceremenCount();
        writeFile(str + "\n", (floatStatistic.getFileName()));
    }

    private void addNewString(String str) {
        stringStatistic.increaseMaxValue(str.length());
        stringStatistic.decreaseMinValue(str.length());
        stringStatistic.inceremenCount();
        writeFile(str, (stringStatistic.getNameFile()));
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
