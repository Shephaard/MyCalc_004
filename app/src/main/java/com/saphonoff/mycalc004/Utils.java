package com.saphonoff.mycalc004;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public final class Utils {
    private Utils() {}

    public static boolean isOperator(char c) {
        return (c == '÷' ||
                c == '×' ||
                c == '-' ||
                c == '+' );

    }

    // -----------------------------------

    public static boolean hasDot(String s) {
        int length = getLastNumber(s).length();
        for (int i=length-1; i >= 0 ; i--)
            if ( getLastNumber(s).charAt(i) == '.' )
                return true;
        return false;
    }

    public static String getLastNumber(String s) {
        return s.substring(getLastOperatorIndex(s)+1);
    }

    public static int getLastOperatorIndex(String s){
        int length = s.length();
        for (int i=length-1; i >= 0 ; i--)
            if ( Utils.isOperator(s.charAt(i)) )
                return i;
        return -1;
    }

    // -----------------------------------

    public static String calculate(String s) {
        double finalResult = 0;

        String[] resultArray = regexCheck("(\\+|\\-)?[0-9\\.]+([\u00D7\u00F7]\\-?[0-9\\.]+)*", s);
        double[] valuesOfArray = new double[resultArray.length];

        for (int i=0; i<resultArray.length; i++) {
            valuesOfArray[i] = parseFactors(resultArray[i]);
            finalResult += valuesOfArray[i];
        }
        finalResult = round(finalResult);
        if (finalResult % 1 == 0) return (int)finalResult+"";
        return finalResult+"";
    }
    // method's code from http://stackoverflow.com/questions/6020384/create-array-of-regex-matches
    public static String[] regexCheck(String regex, String sToCheck){
        List<String> allMatches = new ArrayList<String>();
        Matcher m = Pattern.compile(regex).matcher(sToCheck);
        while (m.find())
            allMatches.add(m.group());

        return allMatches.toArray(new String[0]);
    }

    public static double parseFactors(String s) {
        String[] elements = s.split("\u00D7|\u00F7");
        String[] operators = s.split("[0-9\\+\\-\\.]+");

        double result = Double.parseDouble(elements[0]);
        for (int i=1; i<operators.length; i++) {
            if ( operators[i].equals("÷") ) result /= Double.parseDouble(elements[i]);
            if ( operators[i].equals("×") ) result *= Double.parseDouble(elements[i]);
        }
        return result;
    }

    public static double round(double value){
        int million = 1000000;

        value *= million;
        long tmp = Math.round(value);

        return (double) tmp / million;
    }
}
