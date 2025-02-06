package com.github.vovarolk;

import java.util.ArrayList;

/**
 * Класс параметров необходимых для сортировки текстовых файлов.
 **/
public class SortingParameters {

    public static final String STRINGS_FILE_DEFAULT_NAME = "strings.txt";
    public static final String INTEGERS_FILE_DEFAULT_NAME = "integers.txt";
    public static final String FLOATS_FILE_DEFAULT_NAME = "floats.txt";

    private final ArrayList<String> inputFilesName;
    private final String prefixOutFiles;
    private final String pathOutFiles;
    private final boolean append;
    private final boolean fullStat;
    private final boolean shortStat;

    public SortingParameters() {
        inputFilesName = new ArrayList<>();
        prefixOutFiles = "";
        pathOutFiles = "";
        append = false;
        fullStat = false;
        shortStat = false;
    }

    public SortingParameters(ArrayList<String> inputFilesName, String prefixOutFiles, String pathOutFiles, boolean append, boolean fullStat, boolean shortStat) {
        this.inputFilesName = inputFilesName;
        this.prefixOutFiles = prefixOutFiles;
        this.pathOutFiles = pathOutFiles;
        this.append = append;
        this.fullStat = fullStat;
        this.shortStat = shortStat;
    }


    public boolean isAppend() {
        return append;
    }

    public String getPrefixOutFiles() {
        return prefixOutFiles;
    }

    public String getPathOutFiles() {
        return pathOutFiles;
    }

    public boolean isShortStat() {
        return shortStat;
    }

    public boolean isFullStat() {
        return fullStat;
    }

    public ArrayList<String> getInputFilesName() {
        return inputFilesName;
    }
}
