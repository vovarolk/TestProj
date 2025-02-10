package com.github.vovarolk.statistic;

import java.util.Objects;

/**Класс параметров по которым выводится статистика для типа Integer**/

public class IntStatistic {
    public static final String TYPE_NAME = "Integer";

    private int count;
    private String maxValue;
    private String minValue;
    private final String fileName;
    private String sum;

    public IntStatistic(String nameFile) {
        count = 0;
        maxValue = "";
        minValue = "";
        this.fileName = nameFile;
        sum = "0";
    }

    public int getCount() {
        return count;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public String getMinValue() {
        return minValue;
    }

    public String getFileName() {
        return fileName;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public void inceremenCount() {
        count++;
    }

    public void increaseMaxValue(String newMaxValue) {
        if(Objects.equals(maxValue, "")) maxValue = newMaxValue;
        if (newMaxValue.length() > maxValue.length()) maxValue = newMaxValue;
        if ((newMaxValue.length() < maxValue.length())|| (newMaxValue.equals(maxValue))) return;
        if ((newMaxValue.length() == maxValue.length()) ){
            char[] charNewMaxValue = newMaxValue.toCharArray();
            char[] charMaxValue = newMaxValue.toCharArray();
            for (int i = 0; i < maxValue.length(); i++) {
                if(Character.getNumericValue(charNewMaxValue[i]) < Character.getNumericValue(charMaxValue[i])) return;
                if(Character.getNumericValue(charNewMaxValue[i]) > Character.getNumericValue(charMaxValue[i])) maxValue = newMaxValue;
            }
        }
    }

    public void decreaseMinValue(String newMinValue) {
        if(Objects.equals(minValue, "")) minValue = newMinValue;
        if ((newMinValue.length() < minValue.length()) || (minValue.equals("")) ) minValue = newMinValue;
        if ((newMinValue.length() > minValue.length())|| (newMinValue.equals(minValue))) return;
        if ((newMinValue.length() == minValue.length()) ){
            char[] charNewMinValue = newMinValue.toCharArray();
            char[] charMinValue = newMinValue.toCharArray();
            for (int i = 0; i < minValue.length(); i++) {
                if(Character.getNumericValue(charNewMinValue[i]) > Character.getNumericValue(charMinValue[i])) return;
                if(Character.getNumericValue(charNewMinValue[i]) < Character.getNumericValue(charMinValue[i])) minValue = newMinValue;
            }
        }
    }

    public String getSum() {
        return sum;
    }

    public void increaseSum(String value) {
        if(!value.startsWith("-") &&!sum.startsWith("-")) {
            sum = columnAddition(sum, value);
        }
        else {
            if ((value.startsWith("-") && sum.startsWith("-")))
                sum = "-" + columnAddition(sum.substring(value.indexOf("-") + 1), value.substring(value.indexOf("-") + 1));
            else {
                if (value.startsWith("-")) {
                    sum = columnSubstraction(sum, value.substring(value.indexOf("-") + 1));
                } else sum = columnSubstraction(value, sum.substring(sum.indexOf("-") + 1));
            }
        }
    }

    private String columnAddition(String value1, String value2){
        String longer;
        String smaller;
        if (value1.length()>value2.length()){
            longer = value1;
            smaller = value2;
        }
        else{
            longer = value2;
            smaller = value1;
        }
        char[] result = new char[longer.length()];
        char[] charValue1 = longer.toCharArray();//v1 - longer
        char[] charValue2 = smaller.toCharArray();
        boolean isHaveTen = false;
        int diffLength = longer.length() - smaller.length();
        for(int i = longer.length()-1; i >= 0; i--){
            int indexValue2 = i-diffLength;
            if (indexValue2 >= 0){
                int sumDigit = Character.getNumericValue(charValue1[i]) + Character.getNumericValue(charValue2[indexValue2]);
                if (isHaveTen) sumDigit ++;
                result[i] = (char)(sumDigit % 10 +48);
                isHaveTen = sumDigit/10 > 0;
            }
            else{
                if(isHaveTen){
                    result[i] = (char)(Character.getNumericValue(charValue1[i]) +1 + 48);
                    isHaveTen = false;
                }
                else result[i] = charValue1[i];
            }
        }
        return new String(result);
    }

    private String columnSubstraction(String value1, String value2){
        String longer;
        String smaller;
        if (value1.length()>=value2.length()){
            longer = value1;
            smaller = value2;
        }
        else{
            longer = value2;
            smaller = value1;
        }
        char[] result = new char[longer.length()+1];
        if(!longer.equals(value1)) result[0] ='-';
        char[] charLonger = longer.toCharArray();//v1 - longer
        char[] charSmaller = smaller.toCharArray();
        boolean isHaveMinusTen = false;
        int diffLength = longer.length() - smaller.length();

        for(int i = longer.length()-1; i >= 0; i--){
            int indexValue2 = i-diffLength;
            if (indexValue2 >= 0){
                int minusDigit = Character.getNumericValue(charLonger[i]) - Character.getNumericValue(charSmaller[indexValue2]);
                if (isHaveMinusTen) minusDigit--;
                isHaveMinusTen = minusDigit < 0;
                if(isHaveMinusTen) minusDigit = 0;
                result[i+1] = (char)(minusDigit +48);
            }
            else{
                if(isHaveMinusTen){
                    result[i+1] = (char)(Character.getNumericValue(charLonger[i]) - 1 + 48);
                    int sumDigit = Character.getNumericValue(charLonger[i]) - 1;
                    if(sumDigit < 0) {
                        result[i+1] = 48;
                        isHaveMinusTen = true;
                    }
                    else
                        isHaveMinusTen = false;
                }
                else result[i+1] = charLonger[i];
            }
        }
        if(result[0]==0) result[0] = '0';
        return removingNonSignificantZeros(new String(result));
    }


    public String getAvarage() {
        if(sum.equals("0")) return"0";
        return  columndivision(sum, count);
    }

    private String columndivision(String divisible, int divider){
        StringBuilder result = new StringBuilder();
        if (divisible.startsWith("-")) result = new StringBuilder("-");
        String buf = "0";
        char[] divisibleSum = divisible.toCharArray();
        for(int i =0; i<divisibleSum.length; i++){
           int div = ((Character.getNumericValue(divisibleSum[i]) + (Integer.parseInt(buf)*10))/ divider );
           int mod =  ((Character.getNumericValue(divisibleSum[i]) + (Integer.parseInt(buf)*10)) % divider );
           if(div>0) {
               result.append(div);
           }
           else if( (!result.toString().equals("")) && (!result.toString().equals("-"))) result.append("0");
            buf = String.valueOf(mod);
        }
        String fraction = String.valueOf(Float.parseFloat(buf) / divider);
        if (Float.parseFloat(buf) !=0){
            result.append(fraction.substring(fraction.indexOf(".")));
        }
        return result.toString();
    }

    private String removingNonSignificantZeros(String str){
        String clearString="";
        for (int i=0; i<str.length(); i++){
                if (str.charAt(i) !='0'){
                    if(str.charAt(i) == '-') clearString+="-";
                    else {
                        clearString += str.substring(i);
                        break;
                    }
                }
            }
        return clearString;
    }


}
