package com.github.vovarolk;

import com.github.vovarolk.statistic.*;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/** Класс сортировки **/
public class FileStringSorter {
    private final SortingParameters sortingParameters;

    private IntStatistic intStatistic;
    private FloatStatistic floatStatistic;
    private StringStatistic stringStatistic;

    public FileStringSorter(SortingParameters parameters) {
        this.sortingParameters = parameters;
        intStatistic = new IntStatistic(sortingParameters.getPrefixOutFiles() + SortingParameters.INTEGERS_FILE_DEFAULT_NAME);
        floatStatistic = new FloatStatistic(sortingParameters.getPrefixOutFiles() + SortingParameters.FLOATS_FILE_DEFAULT_NAME);
        stringStatistic = new StringStatistic(sortingParameters.getPrefixOutFiles() + SortingParameters.STRINGS_FILE_DEFAULT_NAME);
    }

    public SortingResult sortFiles() {
        List<BufferedReader> readers = makeInputFileReaders();
        if (!sortingParameters.isAppend()) {
            fileClear(sortingParameters.getPrefixOutFiles() + SortingParameters.INTEGERS_FILE_DEFAULT_NAME);
            fileClear(sortingParameters.getPrefixOutFiles() + SortingParameters.FLOATS_FILE_DEFAULT_NAME);
            fileClear(sortingParameters.getPrefixOutFiles() + SortingParameters.STRINGS_FILE_DEFAULT_NAME);
        }

        for (BufferedReader reader : readers) {
            try {
                String line;
                while (true) {
                    line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    if (checkStringIsInteger(line)){
                        addNewInteger(line);
                    } else if (checkStringIsFloat(line)) {
                        addNewFloat(line);
                    } else {
                        addNewString(line);
                    }
                }
            } catch (IOException e) {
                System.err.println("Ошибка чтения из файла");
                e.printStackTrace();
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return (new SortingResult(intStatistic, floatStatistic, stringStatistic ));
    }

    private List<BufferedReader> makeInputFileReaders() {
        ArrayList<BufferedReader> readers = new ArrayList<>();
        for (String nameInputFile : sortingParameters.getInputFilesName()) {
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
            FileWriter writer = new FileWriter((sortingParameters.getPathOutFiles() + nameFile), true);
            writer.write(information + System.lineSeparator());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fileClear(String nameFile) {
        try {
            FileWriter writer = new FileWriter((sortingParameters.getPathOutFiles() + nameFile), false);
            writer.write("");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
