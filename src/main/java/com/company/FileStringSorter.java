package com.company;

import org.apache.commons.cli.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Класс сортировки строк типа integer, float, string по соответствующим текстовым файлам из одного или нескольких файлов формата *.txt
 * @autor Володя Аникаев
 * @version 0.4
 */

public class FileStringSorter {

    private static final String NameOutFileString  = "strings.txt";
    private static final String NameOutFileInt  = "integers.txt";
    private static final String NameOutFileFloat  = "floats.txt";
    private static final String PatternOutMessage  = "Сортировка прошла успешно\n ";


    private ArrayList<String> nameInputFiles;
    private String prefixOutFiles, pathOutFiles;
    private boolean append, fullStat, shortStat;

    private Pattern patternInt, PatternFloat, PatternFloatMinus, PatternFloatMinusE, PatternFloatE;

    /**
     * Конструктор - создание нового объекта
     */
    public FileStringSorter() {
        nameInputFiles = new ArrayList<>();
        prefixOutFiles = "";
        pathOutFiles ="";
        append = false;
        patternInt = Pattern.compile("-?\\d{1,10}");
        PatternFloatMinusE = Pattern.compile("-?\\d{1,40}(\\.\\d+)?(E-?\\d{1,2})?");
        fullStat = false;
        shortStat = false;
    }

    //гетеры и сетеры

    public boolean getAppend() {return append;}
    public String getPrefixOutFiles() {return prefixOutFiles;}
    public String getPathOutFiles() {return pathOutFiles;}

    public void setAppend(boolean append) {
        this.append = append;
    }

    public void setPrefixOutFiles(String prefixOutFiles) {
        this.prefixOutFiles = prefixOutFiles;
    }

    public void setPathOutFiles(String pathOutFiles) {
        this.pathOutFiles = pathOutFiles+"/";
    }

    public void AddNameInputFile(String nameInputFile){
        nameInputFiles.add(nameInputFile);
    }


    public String Sort(){
        String message = PatternOutMessage;


        if (fullStat && shortStat)  message += "Выбраны два варианта показа статистики, будет показана полная статистика \n";
        message += SortWithStatistic();


        return (message);
    }


    private String SortWithStatistic(){
        String SortMessage = "";
        int countString = 0, countFloat=0, countInt=0;
        ArrayList<BufferedReader> readers = new ArrayList<>();


        for(String nameInputFile : nameInputFiles){
            try {
                readers.add(Files.newBufferedReader(Paths.get(nameInputFile)));
            } catch (NoSuchFileException e) {
                System.out.println("Ошибка открытия файла "+ nameInputFile + " Такого файла не существует. Пожалуйста, проверьте правильность написания имени файла и его наличие.");
                nameInputFiles = ParseFilesName(ReadFilesName());
                return(SortWithStatistic());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String bufferString ="", bufferInt = "", bufferFloat="";
        int i =0;

        int maxInt=Integer.MIN_VALUE, minInt=Integer.MAX_VALUE, sumInt=0, maxStringLength = Integer.MIN_VALUE, minStringLength = Integer.MAX_VALUE;
        float maxFloat = Float.MIN_VALUE, minFloat=Float.MAX_VALUE, sumFloat=0, averageFloat=0, averageInt;

        while (readers.size()!=0){
            String line;
            try {
                if( (line = readers.get(i).readLine()) != null){
                    Matcher matcher = patternInt.matcher(line);
                    if(matcher.matches()&& (Long.parseLong(line) < Integer.MAX_VALUE)){
                        countInt++;
                        bufferInt+=line +"\n";
                        if(fullStat) {
                            if (Integer.parseInt(line) > maxInt) maxInt = Integer.parseInt(line);
                            if (Integer.parseInt(line) < minInt) minInt = Integer.parseInt(line);
                            sumInt += Integer.parseInt(line);
                        }
                    }
                    else {
                        matcher = PatternFloatMinusE.matcher(line);//патерн не учитывает 1.528535047E-25 -0.001
                        if(matcher.matches() && (!Float.isInfinite(Float.parseFloat(line)))) {
                            countFloat++;
                            bufferFloat+=line +"\n";
                            float test = Float.parseFloat(line);
                            if(fullStat) {
                                if (Float.parseFloat(line) > maxFloat) maxFloat = Float.parseFloat(line);
                                if (Float.parseFloat(line) < minFloat) minFloat = Float.parseFloat(line);
                                sumFloat += Float.parseFloat(line);
                            }
                        }
                        else {
                            if(fullStat) {
                                if (line.length() < minStringLength) minStringLength = line.length();
                                if (line.length() > maxStringLength) maxStringLength = line.length();
                            }
                            countString++;
                            bufferString+=line +"\n";

                        }
                    }

                }
                else{
                    readers.remove(i);
                    i--;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            if(i!=(readers.size()-1)) i++; else i=0;

        }



        if (bufferInt != ""){
            WriteFile(bufferInt, (pathOutFiles + prefixOutFiles + NameOutFileInt));
            if(shortStat|| fullStat) {
                SortMessage += "В файле " + prefixOutFiles + NameOutFileInt + " добавлено " + countInt
                        + " элементов типа Integer";
                if (fullStat) {
                    averageInt = sumInt / countInt;
                    SortMessage += " ,наибольший - " + maxInt
                            + " наименьший - " + minInt + " среднее значение - " + averageInt
                            + " сумма элементов - " + sumInt + "\n";
                }
            }
        }

        if (bufferFloat != ""){
            WriteFile(bufferFloat, (pathOutFiles + prefixOutFiles + NameOutFileFloat));
            if(shortStat|| fullStat) {
                SortMessage += "В файле " + prefixOutFiles + NameOutFileFloat + " добавлено "  +
                        countFloat + " элементов типа Float";
                if (fullStat) {
                    averageFloat = sumFloat / countFloat;

                    SortMessage += ", наибольший - " + maxFloat + " наименьший - " + minFloat
                            + " среднее значение - " + averageFloat + " сумма элементов - " + sumFloat + "\n";
                }
            }
        }

        if (bufferString != "") {

            WriteFile(bufferString, (pathOutFiles + prefixOutFiles + NameOutFileString));
            if(shortStat|| fullStat) {
                SortMessage +=  "В файле " + prefixOutFiles + NameOutFileString + " добавлено " + countString +
                        " элементов типа String";
                if (fullStat) {
                    SortMessage +=", длина самой длинной строки - " + maxStringLength
                            + " длина самой короткой строки - " + minStringLength + "\n";
                }
            }
        }
        if (!(shortStat|| fullStat)) SortMessage += "Не выбран вариант показа статистики, поэтому статистика не отображается.";

        return SortMessage;

    }

    private void WriteFile(String information, String nameFile){
            try {
                FileWriter writer = new FileWriter(nameFile, append);
                writer.write(information);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

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
                if (arg == null) prefixOutFiles = "";
                else prefixOutFiles = PrefixAnalysis(arg[0]);
            }

            if (commandLine.hasOption("o")) {
                String[] arg = commandLine.getOptionValues("o");
                if (arg == null) pathOutFiles = "";
                else pathOutFiles = PathAnalyzes(arg[0]) + "/";
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


        nameInputFiles = ParseFilesName(parsedStrings);

    }

    public ArrayList<String> ParseFilesName(String[] str){
        ArrayList<String> files = new ArrayList<>();
        Pattern pattern = Pattern.compile(".+\\.txt");
        for (String t : str){
            Matcher matcher = pattern.matcher(t);
            if(matcher.matches())
                files.add(t);
        }

        if(files.isEmpty()) {
            System.out.println("Ошибка чтения имен  файлов. Не указаны имена входных файлов. Для работы программы требуется хотяя бы одно имя файла,");
            return ParseFilesName(ReadFilesName());
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

    private String[] ReadFilesName(){
        Scanner in = new Scanner(System.in);
        System.out.println("Введите имена файлов в формате \" FileName.txt \", в названии файла не должно содержаться спецсимволов. ");
        System.out.println("Для завершения работы программы введите \" exit \" ");
        System.out.println("");
        String inputString = in.nextLine();
        //String inputString = "in1.txt in2.txt" ;
        String[] splitStr = inputString.split(" ");
        for (String temp : splitStr){
            if(temp.contains("exit")) System.exit(0);


        }
        return(splitStr);
    }

    private boolean WildcardCheck (String str){
        if (str.contains("/")) return true;
        if (str.contains("\\")) return true;
        if (str.contains("|")) return true;
        if (str.contains(":")) return true;
        if (str.contains("*")) return true;
        if (str.contains("\"")) return true;
        if (str.contains("<")) return true;
        if (str.contains(">")) return true;
        //
        return false;
    }

    private String PrefixAnalysis(String prefix){

        if(WildcardCheck(prefix)){
            System.out.println("Ошибка в префиксе выходных файлов! Префикс не должен содержать спецсимволы (/, \\, |, :, *, \", <, >)");
            return (PrefixAnalysis(ReadPrefixOutFiles()));
        }
        else return prefix;
    }

    private String ReadPrefixOutFiles(){
        Scanner in = new Scanner(System.in);
        System.out.println("Введите префикс выходных файлов. Префикс не должен содержать спецсимволы (/, \\, |, :, *, \", <, >). ");
        System.out.println("Для завершения работы программы введите \" exit \" ");
        System.out.println("");
        String inputString = in.nextLine();
        //String inputString = "in1.txt in2.txt" ;
        if(inputString.contains("exit")) System.exit(0);

        return(inputString);
    }

    private String PathAnalyzes(String path){
            File f = new File(path);
            if (!f.exists()) {
                System.out.println("Ошибка директории. Данный путь не существует.");
                return (PathAnalyzes(ReadPathOutFiles()));
            } else if (!f.isDirectory()) {
                System.out.println("Ошибка директории. Путь к файлу является файлом.");
                return (PathAnalyzes(ReadPathOutFiles()));
            }

        return (path);
    }

    private String ReadPathOutFiles(){
        Scanner in = new Scanner(System.in);
        System.out.println("Введите путь выходных файлов.  ");
        System.out.println("Для завершения работы программы введите \" exit \" ");
        System.out.println("");
        String inputString = in.nextLine();
        if(inputString.contains("exit")) System.exit(0);

        return(inputString);
    }

}
