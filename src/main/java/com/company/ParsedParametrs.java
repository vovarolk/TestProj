package com.company;

import org.apache.commons.cli.*;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParsedParametrs {
    private String prefixOutFiles, pathOutFiles;
    private boolean append, fullStat, shortStat;
    private ArrayList<String> nameInputFiles = new ArrayList<>();
    public ParsedParametrs(){
        prefixOutFiles = "";
        pathOutFiles ="";
        append = false;
        fullStat = false;
        shortStat = false;
    }

    public ParsedParametrs(String[] parsedStrings){
        prefixOutFiles = "";
        pathOutFiles ="";
        append = false;
        fullStat = false;
        shortStat = false;
        ParseString(parsedStrings);
    }

    public boolean isAppend() {
        return append;
    }

    public boolean isFullStat() {
        return fullStat;
    }

    public boolean isShortStat() {
        return shortStat;
    }

    public String getPathOutFiles() {
        return pathOutFiles;
    }

    public String getPrefixOutFiles() {
        return prefixOutFiles;
    }

    public void ParseString(String[] parsedStrings){
        Options options = new Options();

        options.addOption(MakeOption("p", true, true, 1, "Prefix"));


        /*
        Option optionPath = new Option("o", true, "Path");
        optionPath.setArgs(1); // число аргументов в опции
        optionPath.setOptionalArg(true);// являются ли аргументы необязательными для ввода, по умолчанию аргументы обязательны для ввода, так что эту строчку можно было опутить
        optionPath.setArgName("Path");//
        options.addOption(optionPath);
        Option optionAppend = new Option("a", false, "Append");
        optionAppend.setArgs(0); // число аргументов в опции
        optionAppend.setOptionalArg(true);// являются ли аргументы необязательными для ввода, по умолчанию аргументы обязательны для ввода, так что эту строчку можно было опутить
        optionAppend.setArgName("Append");//
        options.addOption(optionAppend);



        Option optionShortSt = new Option("s", false, "ShortSt");
        optionShortSt.setArgs(0); // число аргументов в опции
        optionShortSt.setOptionalArg(true);// являются ли аргументы необязательными для ввода, по умолчанию аргументы обязательны для ввода, так что эту строчку можно было опутить
        optionShortSt.setArgName("ShortSt");//
        options.addOption(optionShortSt);



        Option optionFullSt = new Option("f", false, "FullSt");
        optionFullSt.setArgs(0); // число аргументов в опции
        optionFullSt.setOptionalArg(true);// являются ли аргументы необязательными для ввода, по умолчанию аргументы обязательны для ввода, так что эту строчку можно было опутить
        optionFullSt.setArgName("FullSt");//
        options.addOption(optionFullSt);
        */
        options.addOption(MakeOption("o", true, true, 1, "Path"));
        options.addOption(MakeOption("a", false, true, 0, "Append"));
        options.addOption(MakeOption("s", false, true, 0, "ShortSt"));
        options.addOption(MakeOption("f", false, true, 0, "FullSt"));

        CommandLineParser Parser = new PosixParser();// создаем Posix парсер
        try {
            CommandLine commandLine = Parser.parse(options, parsedStrings);// парсим командную строку

            if (commandLine.hasOption("p")) {
                String[] arg = commandLine.getOptionValues("p");
                pathOutFiles = arg[0];
            }

            if (commandLine.hasOption("o")) {
                String[] arg = commandLine.getOptionValues("p");
                prefixOutFiles = arg[0];
            }

            if (commandLine.hasOption("a")) {
                append = true;
            }
            if (commandLine.hasOption("s")) {
                shortStat = true;
            }
            if (commandLine.hasOption("f")) {
                fullStat = true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    public ArrayList<String> ParseFilesName(String str){
        ArrayList<String> files = new ArrayList<>();
        String[] temp = str.split(" ");
        Pattern pattern = Pattern.compile(".+\\.txt");
        for (String t : temp){
            Matcher matcher = pattern.matcher(t);
            if(matcher.matches())
                files.add(t);
        }


        return files;
    }

    private Option MakeOption(String opt, boolean hasArg, boolean OptionalArg, int countArg, String description){
        Option option = new Option(opt, hasArg, description);//конструктор опции
        option.setArgs(countArg); // число аргументов в опции
        option.setOptionalArg(OptionalArg);// являются ли аргументы необязательными для ввода, по умолчанию аргументы обязательны для ввода, так что эту строчку можно было опутить
        option.setArgName(("Arg_" + description));//
        return option;
    }

}
