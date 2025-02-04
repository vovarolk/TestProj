package com.company;

import com.company.Statistic.StatisticParametrsFloat;
import com.company.Statistic.StatisticParametrsInt;
import com.company.Statistic.StatisticParametrsString;
import com.company.Statistic.StatisticalReporting;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Класс сортировки строк типа integer, float, string по соответствующим текстовым файлам из одного или нескольких файлов формата *.txt
 * @autor Володя Аникаев
 * @version 0.4
 */

public class FileStringSorter {

    private static final String SORT_FINISHED_MESSAGE = "Сортировка прошла успешно";

    private final SortingParameters parameters;

    StatisticParametrsInt parametrsInt = new StatisticParametrsInt();
    StatisticParametrsFloat parametrsFloat = new StatisticParametrsFloat();
    StatisticParametrsString parametrsString = new StatisticParametrsString();


    private Pattern patternInt, PatternFloat, PatternFloatMinus, PatternFloatMinusE, PatternFloatE;

    /**
     * Конструктор - создание нового объекта
     */
    public FileStringSorter() {
        parameters = new SortingParameters();
        parametrsInt = new StatisticParametrsInt();
        parametrsFloat = new StatisticParametrsFloat();
        parametrsString = new StatisticParametrsString();
    }

    public FileStringSorter(SortingParameters parameters) {
       this.parameters = parameters;
        parametrsInt = new StatisticParametrsInt();
        parametrsFloat = new StatisticParametrsFloat();
        parametrsString = new StatisticParametrsString();
    }

    //гетеры и сетеры


    public String SortWithMassage(){
        sortFiles();
        String message = SORT_FINISHED_MESSAGE;
        StatisticalReporting report = new StatisticalReporting(parametrsInt, parametrsFloat, parametrsString, parameters);

        if (parameters.isFullStat() && parameters.isShortStat())  message += "Выбраны два варианта показа статистики, будет показана полная статистика \n";
        if (parameters.isFullStat()) message +=report.getFullStatistic();
        else if(parameters.isShortStat()) message += report.getShortStatistic();



        return (message);
    }

    //

    private void sortFiles(){//название поменять

        ArrayList<BufferedReader> readers = MakeInputFileReaders();

        if(!parameters.isAppend()){
            fileClear(parameters.getPrefixOutFiles() + parameters.INTEGERS_FILE_DEFAULT_NAME);
            fileClear(parameters.getPrefixOutFiles() + parameters.FLOATS_FILE_DEFAULT_NAME);
            fileClear(parameters.getPrefixOutFiles() + parameters.STRINGS_FILE_DEFAULT_NAME);

        }

        while (readers.size()!=0) {
            ArrayList<Integer> indexFinishReaders = new ArrayList<Integer>();
            for (BufferedReader reader : readers ){
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
            for (int i : indexFinishReaders){
                readers.remove(i);
            }
        }



    }

    private ArrayList<BufferedReader> MakeInputFileReaders(){
        ArrayList<BufferedReader> readers = new ArrayList<>();
        for(String nameInputFile : parameters.getInputFilesName()){
            try {
                readers.add(Files.newBufferedReader(Paths.get(nameInputFile)));
            } catch (NoSuchFileException e) {
                System.err.println("Ошибка открытия файла "+ nameInputFile + " Такого файла не существует. Пожалуйста, проверьте правильность написания имени файла и его наличие.");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return readers;
    }

 private boolean checkStringIsInt(String str){
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e) {
            return (false);
        }
 }

    private boolean checkStringIsFloat(String str){
        try {
            return (!Float.isInfinite(Float.parseFloat(str)));

        } catch(NumberFormatException e) {
            return (false);
        }
    }

 private void addNewInt(String str){
     int newInt = Integer.parseInt(str);
     parametrsInt.increaseMaxValue(newInt);
     parametrsInt.decreaseMinValue(newInt);
     parametrsInt.increaseSum(newInt);
     parametrsInt.incriminationCount();
     WriteFile(str, (parameters.getPrefixOutFiles() + parameters.INTEGERS_FILE_DEFAULT_NAME));
 }

    private void addNewFloat(String str){
        float newInt = Float.parseFloat(str);
        parametrsFloat.increaseMaxValue(newInt);
        parametrsFloat.decreaseMinValue(newInt);
        parametrsFloat.increaseSum(newInt);
        parametrsFloat.incriminationCount();
        WriteFile(str + "\n", (parameters.getPrefixOutFiles() + parameters.FLOATS_FILE_DEFAULT_NAME));
    }

    private void addNewString(String str){
        parametrsString.increaseMaxValue(str.length());
        parametrsFloat.decreaseMinValue(str.length());
        parametrsString.incriminationCount();
        WriteFile(str, (parameters.getPrefixOutFiles() + parameters.FLOATS_FILE_DEFAULT_NAME));
    }


    private void WriteFile(String information, String nameFile){
            try {
                FileWriter writer = new FileWriter((parameters.getPathOutFiles()+nameFile), true);
                writer.write(information);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    private void fileClear( String nameFile){
        try {
            FileWriter writer = new FileWriter((parameters.getPathOutFiles()+nameFile), false);
            writer.write("");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
