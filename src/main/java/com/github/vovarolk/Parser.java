package com.github.vovarolk;

import org.apache.commons.cli.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**Класс парсера параметров статистики**/
public class Parser {

    private final SortingParameters parameters;

    public Parser(String[] processingString) {
        parameters = parameterParsingFromString(processingString);
    }

    public SortingParameters getParameters() {
        return parameters;
    }

    private SortingParameters parameterParsingFromString(String[] parsedStrings) {
        String prefixOutFiles = "";
        String pathOutFiles = "";

        boolean append = false;
        boolean fullStat = false;
        boolean shortStat = false;

        Options options = new Options();

        options.addOption(makeOption("p", true, true, 1, "Prefix"));
        options.addOption(makeOption("o", true, true, 1, "Path"));
        options.addOption(makeOption("a", false, true, 0, "Append"));
        options.addOption(makeOption("s", false, true, 0, "ShortSt"));
        options.addOption(makeOption("f", false, true, 0, "FullSt"));

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine commandLine = parser.parse(options, parsedStrings);

            if (commandLine.hasOption("p")) {
                String[] arg = commandLine.getOptionValues("p");
                if (arg != null) prefixOutFiles = prefixAnalysis(arg[0]);
            }

            if (commandLine.hasOption("o")) {
                String[] arg = commandLine.getOptionValues("o");
                if (arg != null) pathOutFiles = pathAnalyzes(arg[0]) + "/";
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

        List<String> inputFilesName;
        inputFilesName = parseFilesName(parsedStrings);
        return new SortingParameters(inputFilesName, prefixOutFiles, pathOutFiles, append, fullStat, shortStat);
    }

    private List<String> parseFilesName(String[] str) {
        ArrayList<String> files = new ArrayList<>();
        Pattern pattern = Pattern.compile(".+\\.txt");
        for (String substring : str) {
            Matcher matcher = pattern.matcher(substring);
            if (matcher.matches()) {
                if (fileExistsCheck(substring)) files.add(substring);
                else {
                    System.err.println("Ошибка имени входного файла. Файла " + substring + "не существует");
                    return parseFilesName(forcedReadInputFiles());
                }
            }

        }

        if (files.isEmpty()) {
            System.err.println("Ошибка чтения имен файлов. Не указаны имена входных файлов. Для работы программы требуется хотя бы одно имя файла,");
            return parseFilesName(forcedReadInputFiles());
        }

        return files;
    }

    public boolean fileExistsCheck(String nameFile) {
        File file = new File(nameFile);
        return file.exists();
    }

    private Option makeOption(String opt, boolean hasArg, boolean optionalArg, int countArg, String description) {
        Option option = new Option(opt, hasArg, description);
        option.setArgs(countArg);
        option.setOptionalArg(optionalArg);
        option.setArgName(("Arg_" + description));//
        return option;
    }

    private String[] forcedReadInputFiles() {
        Scanner in = new Scanner(System.in);
        System.out.println("Пожалуйста, введите имена файлов в формате \" FileName.txt \", в названии файла не должно содержаться спецсимволов. ");
        System.out.println("Для завершения работы программы введите \" exit \" ");
        System.out.println();
        String inputString = in.nextLine();
        String[] newStr = inputString.split(" ");
        for (String temp : newStr) {
            if (temp.contains("exit")) System.exit(0);
        }
        return (newStr);
    }


    private boolean serviceCharacterCheck(String str) {
        if (str.contains("/")) return true;
        if (str.contains("\\")) return true;
        if (str.contains("|")) return true;
        if (str.contains(":")) return true;
        if (str.contains("*")) return true;
        if (str.contains("\"")) return true;
        if (str.contains("<")) return true;
        return str.contains(">");
    }

    private String prefixAnalysis(String prefix) {
        while (serviceCharacterCheck(prefix)) {
            System.err.println("Ошибка в префиксе выходных файлов! Префикс не должен содержать спецсимволы (/, \\, |, :, *, \", <, >)");
            prefix = readPrefixOutFiles();
        }
        return prefix;
    }

    private String readPrefixOutFiles() {
        System.out.println("Введите префикс выходных файлов. Префикс не должен содержать спецсимволы (/, \\, |, :, *, \", <, >). ");
        System.out.println("Для завершения работы программы введите \" exit \" ");
        System.out.println();
        Scanner in = new Scanner(System.in);
        String inputString = in.nextLine();
        if (inputString.contains("exit")) System.exit(0);
        return (inputString);
    }

    private String pathAnalyzes(String path) {
        File f = new File(path);
        while (!f.isDirectory()) {
            path = readPathOutFiles();
            f = new File(path);
        }
        return (path);
    }

    private String readPathOutFiles() {
        System.out.println("Введите путь выходных файлов.  ");
        System.out.println("Для завершения работы программы введите \" exit \" ");
        System.out.println();
        Scanner in = new Scanner(System.in);
        String inputString = in.nextLine();
        if (inputString.contains("exit")) System.exit(0);
        return (inputString);
    }

}
