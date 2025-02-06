package com.github.vovarolk;

public class Main {

    public static void main(String[] args) {
        ParserSortingParameters parser = new ParserSortingParameters(args);
        SortingParameters parameters = parser.getParameters();

        FileStringSorter fs = new FileStringSorter(parameters);

        System.out.println(fs.sortWithMassage());
    }
}

