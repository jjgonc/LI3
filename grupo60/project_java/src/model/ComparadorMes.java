package model;

import java.util.Comparator;
import java.util.Map;

/**
 * Comparador que comprara o mes como strings
 */
public class ComparadorMes implements Comparator<Map.Entry<String,String>> {

    public int mestoInt (String mes){
        mes.toUpperCase();
        int x = 0;
        if (mes.equals("JANUARY")) x = 1;
        if (mes.equals("FEBRUARY")) x = 2;
        if (mes.equals("MARCH")) x = 3;
        if (mes.equals("APRIL")) x = 4;
        if (mes.equals("MAY")) x = 5;
        if (mes.equals("JUNE")) x = 6;
        if (mes.equals("JULY")) x = 7;
        if (mes.equals("AUGUST")) x = 8;
        if (mes.equals("SEPTEMBER")) x = 9;
        if (mes.equals("OCTOBER")) x = 10;
        if (mes.equals("NOVEMBER")) x = 11;
        if (mes.equals("DECEMBER")) x = 12;
        return x;
    }

    public int compare(Map.Entry<String,String> g1, Map.Entry<String,String> g2) {
        if (mestoInt(g1.getKey()) > mestoInt(g2.getKey())) return 1;
        if (mestoInt(g1.getKey()) < mestoInt(g2.getKey())) return -1;
        return 0;
    }

}