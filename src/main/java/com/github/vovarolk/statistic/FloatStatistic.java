package com.github.vovarolk.statistic;

import java.util.Objects;

/**Класс параметров по которым выводится статистика для типа Float**/
public class FloatStatistic {
    public static final String TYPE_NAME = "Float";

    private int count;
    private String sum;
    private String maxValue;
    private String minValue;
    private final String fileName;

    public FloatStatistic(String nameFile) {
        count = 0;
        sum = "";
        maxValue = "";
        minValue = "";
        this.fileName = nameFile;
    }

    public String getSum() {
        return sum;
    }

    public void increaseSum(String value) {
        if(Objects.equals(sum, "")){
            sum=value;
            return;
        }
        int degreeSum = sum.indexOf(".")-1;
        int eSum = 0;
        int eValue = 0;
        if(sum.contains("E")) eSum=Integer.parseInt(sum.substring(sum.indexOf("E")+1));
        degreeSum += eSum;
        int degreeValue = value.indexOf(".")-1;
        if(value.contains("E")) eValue=Integer.parseInt(value.substring(value.indexOf("E")+1));
        degreeValue +=eValue;
        if(Math.abs(degreeValue-degreeSum)>6) {
            if(degreeValue< degreeSum) return;
            else sum=value;
        }
        else{
            float f1, f2;
            String sub1, sub2;
            int indexE1 = sum.indexOf("E");
            int indexE2 = value.indexOf("E");
            if(indexE1 != -1) {
                if (indexE1 <= 6) sub1 = sum.substring(0, indexE1);
                else sub1 = sum.substring(0, 9);
            }
            else{
                if(sum.length()<5) sub1 = sum;
                else sub1 = sum.substring(0, 5);
            }

            if(indexE2 != -1) {
                if (indexE2<=6) sub2 = value.substring(0, indexE2);
                else sub2 = value.substring(0, 5);
            }
            else{
                if(value.length()<6) sub2 = value;
                else sub2 = value.substring(0, 6);
            }
            f1= Float.parseFloat(sub1);
            f2= Float.parseFloat(sub2);
            int eResult;
            if(eSum>eValue){
                f2 *= Math.pow(10, -Math.abs(eValue-eSum));
                eResult = eSum;
            }
            else{
                f1 *= Math.pow(10, -Math.abs(eSum - eValue));
                eResult = eValue;
            }
            float result = f1+f2;
            sum = result+"E" + eResult;
        }

    }

    public String getAvarage() {
        String sub = sum.substring(0, sum.indexOf("E"));
        float mantisSum = Float.parseFloat(sub);
        float avarageM = mantisSum/count;
        String eSum = sum.substring(sum.indexOf("E"));
        return (avarageM + eSum);
    }

    public String getMaxValue() {
        return maxValue;
    }

    public int getCount() {
        return count;
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
        int degreeNewMaxValue = newMaxValue.indexOf(".");
        if(newMaxValue.contains("E")) degreeNewMaxValue+=Integer.parseInt(newMaxValue.substring(newMaxValue.indexOf("E")+1));
        int degreeMaxValue = maxValue.indexOf(".");
        if(maxValue.contains("E")) degreeMaxValue+=Integer.parseInt(maxValue.substring(maxValue.indexOf("E")+1));
        if (degreeNewMaxValue > degreeMaxValue) {
            maxValue = newMaxValue;
            return;
        }
        if (degreeNewMaxValue < degreeMaxValue) return;
        if (degreeNewMaxValue == degreeMaxValue){
            char[] charNewMaxValue = newMaxValue.toCharArray();
            char[] charMaxValue = newMaxValue.toCharArray();
            for (int i = 0; i < maxValue.length(); i++) {
                if((charNewMaxValue[i] == '.')||(charMaxValue[i] == '.')) continue;
                if((charNewMaxValue[i] == 'E')||(charMaxValue[i] != 'E')) maxValue = newMaxValue;
                if((charNewMaxValue[i] != 'E')||(charMaxValue[i] == 'E')) return;
                if(Character.getNumericValue(charNewMaxValue[i]) < Character.getNumericValue(charMaxValue[i])) return;
                if(Character.getNumericValue(charNewMaxValue[i]) > Character.getNumericValue(charMaxValue[i])) maxValue = newMaxValue;
            }
        }
    }

    public void decreaseMinValue(String newMinValue) {
        if(minValue==""){
            minValue = newMinValue;
            return;
        }
        int degreeNewMinValue = newMinValue.indexOf(".");
        if(newMinValue.contains("E"))
            degreeNewMinValue+=Integer.parseInt(newMinValue.substring(newMinValue.indexOf("E")+1));
        int degreeMinValue =minValue.indexOf(".");
        if(minValue.contains("E"))
            degreeMinValue+=Integer.parseInt(minValue.substring(minValue.indexOf("E")+1));
        if (degreeNewMinValue < degreeMinValue) minValue = newMinValue;
        if (degreeNewMinValue > degreeMinValue) return;
        if (degreeNewMinValue == degreeMinValue){
            char[] charNewMaxValue = newMinValue.toCharArray();
            char[] charMaxValue = newMinValue.toCharArray();
            for (int i = 0; i < minValue.length(); i++) {
                if((charNewMaxValue[i] == '.')||(charMaxValue[i] == '.')) continue;
                if((charNewMaxValue[i] == 'E')||(charMaxValue[i] != 'E')) return;
                if((charNewMaxValue[i] != 'E')||(charMaxValue[i] == 'E')) minValue = newMinValue;
                if(Character.getNumericValue(charNewMaxValue[i]) > Character.getNumericValue(charMaxValue[i])) return;
                if(Character.getNumericValue(charNewMaxValue[i]) < Character.getNumericValue(charMaxValue[i])) minValue = newMinValue;
            }
        }    }
}
