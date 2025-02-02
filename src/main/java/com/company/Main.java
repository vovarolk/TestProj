package com.company;

import org.apache.commons.cli.*;

public class Main {

    public static void main(String[] args) {
	    //анализ ввода пользователя

        float g = (float) 1E+25;
        System.out.println(g);
        FileStringSorter fs = new FileStringSorter();

        fs.ParseString(args);

        System.out.println(fs.Sort());


    }
}

