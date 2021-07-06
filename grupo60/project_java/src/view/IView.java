package view;

import java.util.AbstractMap;


/**
 * Inteface para a view.
 */
public interface IView {

    public int menuQueries();

    public int menuOpcoes();

    public int menuLerFicheiro();

    public int leNumero (int limInf,int limSup,String tipo);

    public String leNomeFicheiro (String tipo);

    public int menuEstatisticas();

    public AbstractMap.SimpleEntry<Integer,Integer> leInputsQuery2();

    public String leInputsQuery3();

    public String leInputsQuery4();

    public int leInputsQueryTop();

    public AbstractMap.SimpleEntry<String,Integer> leInputsQuery9();

    public void printResposta(String res);

    public void printUserInvalido (String user);

    public void printBusinessInvalido (String business);

    public void printReviewInvalido (String review);

    public void erroLerFicheiroBinario(int i);

    public String leBinarioFile(int i);

    public void erroLerFicheiros();

    }
