package com.company;

public class Main {

    public static void main(String[] args) {
	    //анализ ввода пользователя

        FileStringSorter fs = new FileStringSorter();
        fs.AddNameInputFile("in1.txt");
        fs.AddNameInputFile("in2.txt");
        fs.Sort(false);


    }
}
