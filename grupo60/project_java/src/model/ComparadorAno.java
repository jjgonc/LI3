package model;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * comparador para query6
 */
public class ComparadorAno implements Comparator<Map.Entry<Integer,List<Map.Entry<String,Integer>>>> {

    public int compare(Map.Entry<Integer,List<Map.Entry<String,Integer>>> g1, Map.Entry<Integer,List<Map.Entry<String,Integer>>> g2) {
        if (g1.getKey() > g2.getKey()) return 1;
        if (g1.getKey() < g2.getKey()) return -1;
        return 0;
    }
}
