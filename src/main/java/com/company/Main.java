package com.company;

import com.company.Statistic.StatisticParametrsInt;
import org.apache.commons.cli.*;

import java.io.UnsupportedEncodingException;

public class Main {

    public static void main(String[] args)  {
	    //анализ ввода пользователя
        ParserSortingParameters parser = new ParserSortingParameters(args);
        SortingParameters parameters = parser.getParameters();


        FileStringSorter fs = new FileStringSorter(parameters);


        System.out.println(fs.SortWithMassage());


        //TODO сделать чистый код (разбить атомарно функции, добавить класс, сделать все чиаемым)


        //TODO класс статистики сортировка файлоа


    }
}

