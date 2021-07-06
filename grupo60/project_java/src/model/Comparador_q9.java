package model;

import java.util.Comparator;
import java.util.Map;

/**
 * Comparador para a query9
 */
public class Comparador_q9 implements Comparator<Map.Entry<Double,String>> {

    /**
     * Comparador que ordena por ordem decrescente o valor e, para quantidades iguais, por ordem alfabética dos negócios.
     * @param g1 Um dos pares.
     * @param g2 O outro par.
     * @return O valor associado à ordenação.
     */
    public int compare(Map.Entry<Double, String> g1, Map.Entry<Double, String> g2) {
        if (g1.getKey() > g2.getKey()) return -1;
        else if (g1.getKey() < g2.getKey()) return 1;
        else {
            return g1.getValue().compareTo(g2.getValue());
        }
    }

}