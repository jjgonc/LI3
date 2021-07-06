package model;

import static java.lang.System.nanoTime;

/**
 * Classe para medir tempos de execução
 */
public class Chrono {

    private static long begin = 0L;
    private static long end = 0L;

    /**
     * Método que inicia a contagem do cronómetro
     */
    public static void start() {
        end = 0L;
        begin = nanoTime();
    }

    /**
     * Método para parar a contagem do tempo do cronómetro.
     * @return O tempo decorrido entre o inicio e o fim do cronómetro.
     */
    public static double stop() {
        end = nanoTime();
        long elapsedTime = end - begin;
        return elapsedTime / 1.0E09;
    }

    /**
     * Obter a contagem do tempo em formato de String. Este método pára o cronometro;
     * @return String com o tempo decorrido.
     */
    public static String getTimeAsString() {
        return "" + stop();
    }

    /**
     * Método que pára o cronómetro e devolve o tempo decorrido em segundos.
     */
    public static void printElapsedTime() {
        System.out.println( "Elapsed Time: " + stop() + " s");
    }

}
