package com.company;

import org.apache.commons.cli.*;

public class Main {

    public static void main(String[] args) {
	    //анализ ввода пользователя


        FileStringSorter fs = new FileStringSorter();
//TODO проверка на два одновременных флага


        fs.ParseString(args);

        /*fs.AddNameInputFile("in1.txt");
        fs.AddNameInputFile("in2.txt");

        fs.setAppend(true);
        fs.setPrefixOutFiles("new_");*/

        System.out.println(fs.Sort());


    }
}
