import controller.ILoader;
import controller.Loader;
import model.*;
import model.business.BusinessCat;
import model.business.IBusinessCat;
import model.estatisticas.IModuloEstatisticas;
import model.estatisticas.ModuloEstatisticas;
import model.reviews.IReviewCat;
import model.reviews.ReviewCat;
import model.users.IUserCat;
import model.users.UserCat;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Classe independente para testes
 */
public class Testes {
    private IBusinessCat catBus;
    private IReviewCat catRev;
    private IUserCat catUsers;
    private IModuloEstatisticas me;
    /**
     * definição cor verde
     */
    public static final String GREEN = "\033[0;32m";
    /**
     * definição cor amarelo
     */
    public static final String YELLOW = "\033[0;33m";
    /**
     * definição cor azul
     */
    public static final String ANSI_BLUE = "\u001B[34m";
    /**
     * reset de cor
     */
    public static final String RESET = "\033[0m";


    /**
     * Método para executar todos os testes de performance do programa desenvolvido.
     * @param args Argumentos.
     */
    public static void main (String [] args){
        Testes t = new Testes();
        System.out.println(GREEN + "Load do Catálogo de Businesses" + RESET);
        t.loadCatalogoBusinesses();
        System.out.println("\n\n" + GREEN + "Load do Catálogo de Users" + RESET);
        t.loadCatalogoUsers();
        System.out.println("\n\n" + GREEN + "Load do Catálogo de Reviews" + RESET);
        t.loadCatalogoRevs();
        System.out.println("\n\n" + GREEN +"Testes de Queries" + RESET);
        t.testeQueries();
    }


    /**
     * Construtor vazio de Testes.
     */
    public Testes () {
        this.catBus = new BusinessCat();
        this.catRev = new ReviewCat();
        this.catUsers = new UserCat();
        this.me = new ModuloEstatisticas();
    }


    /**
     * Método para carregar o catálogo de users.
     */
    public void loadCatalogoUsers () {
        Runtime runtime = Runtime.getRuntime();
        long usedMemoryBefore = (long) ((double) (runtime.totalMemory() - runtime.freeMemory()) / (double)(1024 * 1024));

        ILoader loader = new Loader();
        Chrono.start();
        try {
            loader.loadUsers(this.catUsers,this.me,"input_files/users_full.csv",0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Chrono.printElapsedTime();

        long usedMemoryAfter = (long) ((double) (runtime.totalMemory() - runtime.freeMemory()) / (double)(1024 * 1024));
        System.out.println("Incremento no uso de memória: " + (usedMemoryAfter-usedMemoryBefore) + " MB");
    }

    /**
     * Método para carregar o catálogo de Reviews.
     */
    public void loadCatalogoRevs () {
        Runtime runtime = Runtime.getRuntime();
        long usedMemoryBefore = (long) ((double) (runtime.totalMemory() - runtime.freeMemory()) / (double)(1024 * 1024));
        ILoader loader = new Loader();
        IGestReviews model = new GestReviews(this.catBus, this.catRev, this.catUsers, this.me);
        Chrono.start();
        try {
            loader.loadReviews(model,"input_files/reviews_1M.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Chrono.printElapsedTime();

        long usedMemoryAfter = (long) ((double) (runtime.totalMemory() - runtime.freeMemory()) / (double)(1024 * 1024));
        System.out.println("Incremento no uso de memória: " + (usedMemoryAfter-usedMemoryBefore) + " MB");
    }


    /**
     * Método para carregar o catálogo de businesses.
     */
    public void loadCatalogoBusinesses () {
        Runtime runtime = Runtime.getRuntime();
        long usedMemoryBefore = (long) ((double) (runtime.totalMemory() - runtime.freeMemory()) / (double)(1024 * 1024));

        ILoader loader = new Loader();
        Chrono.start();
        try {
            loader.loadBusinesses(this.catBus ,this.me,"input_files/business_full.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Chrono.printElapsedTime();

        long usedMemoryAfter = (long) ((double) (runtime.totalMemory() - runtime.freeMemory()) / (double)(1024 * 1024));
        System.out.println("Incremento no uso de memória: " + (usedMemoryAfter-usedMemoryBefore) + " MB");
    }

    /**
     * Método para dar print de um separador entre testes.
     */
    public void printSeparador () {
        System.out.println("------------------------------------------------------------");
    }

    /**
     * Método para testar todas as queries. Notar que para tal, são carregados os catálogos novamente.
     */
    public void testeQueries () {

        System.out.println(ANSI_BLUE + "Calculating queries..." + RESET);

        ILoader loader = new Loader();

        try {
            loader.loadBusinesses(this.catBus ,this.me,"input_files/business_full.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        IGestReviews model = new GestReviews(this.catBus, this.catRev, this.catUsers, this.me);
        try {
            loader.loadReviews(model,"input_files/reviews_1M.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            loader.loadUsers(this.catUsers,this.me,"input_files/users_full.csv",0);
        } catch (IOException e) {
            e.printStackTrace();
        }


        //query 1
        Runtime runtime = Runtime.getRuntime();
        long usedMemoryBefore = (long) ((double) (runtime.totalMemory() - runtime.freeMemory()) / (double)(1024 * 1024));

        System.out.println(YELLOW + "[Query 1]" + RESET);
        System.out.println("A testar a query 1");
        Chrono.start();
        List<String> res1 = model.query1();
        double time1 = Chrono.stop();
        System.out.println("Tempo de execução: " +time1);

        long usedMemoryAfter = (long) ((double) (runtime.totalMemory() - runtime.freeMemory()) / (double)(1024 * 1024));
        System.out.println("Incremento no uso de memória: " + (usedMemoryAfter-usedMemoryBefore) + " MB");

        printSeparador();

        //query 2
        usedMemoryBefore = (long) ((double) (runtime.totalMemory() - runtime.freeMemory()) / (double)(1024 * 1024));

        System.out.println(YELLOW + "[Query 2]" + RESET);
        System.out.println("A testar a query 2 para 03-2009");
        Chrono.start();
        Map.Entry<Integer, Integer> res2 = model.query2(3,2009);
        double time2 = Chrono.stop();
        System.out.println("Tempo de execução: " +time2);

        usedMemoryAfter = (long) ((double) (runtime.totalMemory() - runtime.freeMemory()) / (double)(1024 * 1024));
        System.out.println("Incremento no uso de memória: " + (usedMemoryAfter-usedMemoryBefore) + " MB");

        printSeparador();

        //query 3
        usedMemoryBefore = (long) ((double) (runtime.totalMemory() - runtime.freeMemory()) / (double)(1024 * 1024));

        System.out.println(YELLOW + "[Query 3]" + RESET);
        System.out.println("A testar a query 3 para o user id = eLAYHxHUutiXswy-CfeiUw");
        Chrono.start();
        Set<Map.Entry<String,String>> res3 = model.query3("eLAYHxHUutiXswy-CfeiUw");
        double time3 = Chrono.stop();
        System.out.println("Tempo de execução: " +time3);

        usedMemoryAfter = (long) ((double) (runtime.totalMemory() - runtime.freeMemory()) / (double)(1024 * 1024));
        System.out.println("Incremento no uso de memória: " + (usedMemoryAfter-usedMemoryBefore) + " MB");

        printSeparador();

        //query 4
        usedMemoryBefore = (long) ((double) (runtime.totalMemory() - runtime.freeMemory()) / (double)(1024 * 1024));

        System.out.println(YELLOW + "[Query 4]" + RESET);
        System.out.println("A testar a query 4 para o business id = jFYIsSb7r1QeESVUnXPHBw");
        Chrono.start();
        Set<Map.Entry<String,String>> res4 = model.query4("jFYIsSb7r1QeESVUnXPHBw");
        double time4 = Chrono.stop();
        System.out.println("Tempo de execução: " +time3);

        usedMemoryAfter = (long) ((double) (runtime.totalMemory() - runtime.freeMemory()) / (double)(1024 * 1024));
        System.out.println("Incremento no uso de memória: " + (usedMemoryAfter-usedMemoryBefore) + " MB");

        printSeparador();

        //query 5
        usedMemoryBefore = (long) ((double) (runtime.totalMemory() - runtime.freeMemory()) / (double)(1024 * 1024));

        System.out.println(YELLOW + "[Query 5]" + RESET);
        System.out.println("A testar a query 5 para o user id = eLAYHxHUutiXswy-CfeiUw");
        Chrono.start();
        Set<AbstractMap.SimpleEntry<String,Integer>> res5 = model.query5("eLAYHxHUutiXswy-CfeiUw");
        double time5 = Chrono.stop();
        System.out.println("Tempo de execução: " +time5);

        usedMemoryAfter = (long) ((double) (runtime.totalMemory() - runtime.freeMemory()) / (double)(1024 * 1024));
        System.out.println("Incremento no uso de memória: " + (usedMemoryAfter-usedMemoryBefore) + " MB");

        printSeparador();

        //query 6
        usedMemoryBefore = (long) ((double) (runtime.totalMemory() - runtime.freeMemory()) / (double)(1024 * 1024));

        System.out.println(YELLOW + "[Query 6]" + RESET);
        System.out.println("A testar a query 6 para o top 5");
        Chrono.start();
        Set<Map.Entry<Integer,List<Map.Entry<String,Integer>>>> res6 = model.query6(5);
        double time6 = Chrono.stop();
        System.out.println("Tempo de execução: " +time6);

        usedMemoryAfter = (long) ((double) (runtime.totalMemory() - runtime.freeMemory()) / (double)(1024 * 1024));
        System.out.println("Incremento no uso de memória: " + (usedMemoryAfter-usedMemoryBefore) + " MB");

        printSeparador();

        //query 7
        usedMemoryBefore = (long) ((double) (runtime.totalMemory() - runtime.freeMemory()) / (double)(1024 * 1024));

        System.out.println(YELLOW + "[Query 7]" + RESET);
        System.out.println("A testar a query 7");
        Chrono.start();
        Map<String,List<String>> res7 = model.query7();
        double time7 = Chrono.stop();
        System.out.println("Tempo de execução: " +time7);

        usedMemoryAfter = (long) ((double) (runtime.totalMemory() - runtime.freeMemory()) / (double)(1024 * 1024));
        System.out.println("Incremento no uso de memória: " + (usedMemoryAfter-usedMemoryBefore) + " MB");

        printSeparador();

        //query 8
        usedMemoryBefore = (long) ((double) (runtime.totalMemory() - runtime.freeMemory()) / (double)(1024 * 1024));

        System.out.println(YELLOW + "[Query 8]" + RESET);
        System.out.println("A testar a query 8 para 10 utilizadores");
        Chrono.start();
        Set<Map.Entry<String,Integer>> res8 = model.query8(10);
        double time8 = Chrono.stop();
        System.out.println("Tempo de execução: " +time8);

        usedMemoryAfter = (long) ((double) (runtime.totalMemory() - runtime.freeMemory()) / (double)(1024 * 1024));
        System.out.println("Incremento no uso de memória: " + (usedMemoryAfter-usedMemoryBefore) + " MB");

        printSeparador();

        //query 9
        usedMemoryBefore = (long) ((double) (runtime.totalMemory() - runtime.freeMemory()) / (double)(1024 * 1024));

        System.out.println(YELLOW + "[Query 9]" + RESET);
        System.out.println("A testar a query 9 para 5 utilizadores e business id = jFYIsSb7r1QeESVUnXPHBw");
        Chrono.start();
        List<Map.Entry<Double, String>> res9 = model.query9("jFYIsSb7r1QeESVUnXPHBw", 5);
        double time9 = Chrono.stop();
        System.out.println("Tempo de execução: " +time9);

        usedMemoryAfter = (long) ((double) (runtime.totalMemory() - runtime.freeMemory()) / (double)(1024 * 1024));
        System.out.println("Incremento no uso de memória: " + (usedMemoryAfter-usedMemoryBefore) + " MB");

        printSeparador();

        //query 10
        usedMemoryBefore = (long) ((double) (runtime.totalMemory() - runtime.freeMemory()) / (double)(1024 * 1024));

        System.out.println(YELLOW + "[Query 10]" + RESET);
        System.out.println("A testar a query 10");
        Chrono.start();
        Map<String,Map<String,Map<String,Double>>> res10 = model.query10();
        double time10 = Chrono.stop();
        System.out.println("Tempo de execução: " +time10);

        usedMemoryAfter = (long) ((double) (runtime.totalMemory() - runtime.freeMemory()) / (double)(1024 * 1024));
        System.out.println("Incremento no uso de memória: " + (usedMemoryAfter-usedMemoryBefore) + " MB");

        printSeparador();
    }


}
