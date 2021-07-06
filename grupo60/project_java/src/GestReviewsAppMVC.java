import controller.Controller;
import controller.IController;

/**
 * aplicação final
 */
public class GestReviewsAppMVC {

    /**
     * Função que coloca o controlador a correr
     * @param args argumentos
     */
    public static void main (String [] args) {
       IController controller = new Controller();
       controller.run();
    }

}
