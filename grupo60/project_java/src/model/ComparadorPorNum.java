package model;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


/**
 * Comparador para a query8
 */
public class ComparadorPorNum implements Comparator <Map.Entry<String,Integer>>{

    public int compare(Map.Entry<String,Integer> g1, Map.Entry<String,Integer> g2) {
        if (g1.getValue() > g2.getValue()) return -1;
        if (g1.getValue() < g2.getValue()) return 1;
        return 0;
    }

}