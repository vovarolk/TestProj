package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class FileStringSorter {

    // cons названия файлов, сообщение вывода,

    private static final String NameOutFileString  = "strings.txt";
    private static final String NameOutFileInt  = "integers.txt";
    private static final String NameOutFileFlout  = "floats.txt";
    private static final String PatternOutMessage  = "Сортировка прошла успешно, были ";


    private ArrayList<String> nameInputFiles;
    private String prefixOutFile, pathOutFile;//Нужны гет и сет
    private boolean rewrite; //Нужны гет и сет

    private Pattern patternInt, PatternFloat;

    public FileStringSorter() {
        nameInputFiles = new ArrayList<>();
        prefixOutFile = "";
        pathOutFile ="";
        rewrite = true;
        patternInt = Pattern.compile("\\d+");
        PatternFloat = Pattern.compile("\\d+(\\.\\d+)?");

    }

    public void AddNameInputFile(String nameInputFile){
        nameInputFiles.add(nameInputFile);
    }

    //создать конструкторы с параметрами

    public void Sort(boolean fullStatistic){//распределение по сортировки в зависимости от флага
        String message;
        if (fullStatistic)  message = SortWithFullStatistic();
        else  message = SortWithNotFullStatistic();
        System.out.println(message);
    }

    private String SortWithNotFullStatistic(){//сортировка и сборка сообщения с крвткой статистикой
        String SortMessage = "";

        //Создание ридеров файлов
        ArrayList<BufferedReader> readers = new ArrayList<>();


        for(String nameInputFile : nameInputFiles){
            try {
                readers.add(Files.newBufferedReader(Paths.get(nameInputFile)));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        int countFile = nameInputFiles.size();
        int countReadFile = 0;
        int i =0;

        while (readers.size()!=0){
            String line;
            try {
                if( (line = readers.get(i).readLine()) != null){
                    System.out.println(line);
                    //сортируем
                }
                else{
                    readers.remove(i);
                    i--;
                    countReadFile++;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            if(i!=(readers.size()-1)) i++; else i=0;

        }

      /*  while(countReadFile != countFile){ //что если сделать цикл основанный на размере arraylist удаляя постепенно сами ридеры
            String line;
            try {
                if( (line = readers.get(i).readLine()) != null){
                    System.out.println(line);
                    //сортируем
                 }
                else{
                    readers.remove(i);
                    countReadFile++;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            ;//ограничение на считывание нуля

            if(i!=(countFile-1)) i++; else i=0;

        }*/


        //Надо читать по одной строке из каждого файла ?????что делать если один файл закончится раньше


        return SortMessage;
    }

    private String SortWithFullStatistic(){ //сортировка и сборка сообщения с полной статистикой
        String SortMessage = "";


        return SortMessage;

    }

}
