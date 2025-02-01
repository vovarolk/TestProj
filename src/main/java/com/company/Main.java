package com.company;

import org.apache.commons.cli.*;

public class Main {

    public static void main(String[] args) {
	    //анализ ввода пользователя

        FileStringSorter fs = new FileStringSorter();

        fs.ParseString(args);

        System.out.println(fs.Sort());


    }
}

