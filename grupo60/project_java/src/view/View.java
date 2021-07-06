package view;

import java.util.*;

/**
 * Classe da view. Componente do MVC.
 */
public class View implements IView {
    private Scanner ins;

    /**
     * Definir a cor original.
     */
    public static final String RESET = "\033[0m";
    
    /**
     * Definir a cor verde.
     */
    public static final String GREEN = "\033[0;32m";
    
    /**
     * Definir a cor vermelha.
     */
    public static final String RED = "\033[0;31m";
    
    /**
     * Definir a cor amarela.
     */
    public static final String YELLOW = "\033[0;33m";
    
    /**
     * Definir a cor verde a Bold.
     */
    public static final String GREEN_BOLD = "\033[1;32m";
    
    /**
     * Definir a cor ciano.
     */
    public static final String CYAN_BOLD = "\033[1;36m";

    public View(){
        this.ins = new Scanner(System.in);
    }

    public int menuQueries(){
        System.out.println("\nConsultas interativas");
        System.out.println(GREEN + "1 - Lista ordenada alfabeticamente com os identificadores dos negócios nunca avaliados e o seu respetivo total" + RESET);
        System.out.println(GREEN + "2 - Dado um mês e um ano (válidos), determinar o número total global de reviews realizadas e o número total de users distintos que as realizaram" + RESET);
        System.out.println(GREEN + "3 - Dado um código de utilizador, determinar, para cada mês, quantas reviews fez, quantos negócios distintos avaliou e que nota média atribuiu" + RESET);
        System.out.println(GREEN + "4 - Dado o código de um negócio, determinar, mês a mês, quantas vezes foi avaliado, por quantos users diferentes e a média de classificação" + RESET);
        System.out.println(GREEN + "5 - Dado o código de um utilizador determinar a lista de nomes de negócios que mais avaliou (e quantos), ordenada por ordem decrescente de quantidade e, para quantidades iguais, por ordem alfabética dos negócios" + RESET);
        System.out.println(GREEN + "6 - Determinar o conjunto dos X negócios mais avaliados (com mais reviews) em cada ano, indicando o número total de distintos utilizadores que o avaliaram (X é um inteiro dado pelo utilizador)" + RESET);
        System.out.println(GREEN + "7 - Determinar, para cada cidade, a lista dos três mais famosos negócios em termos de número de reviews" + RESET);
        System.out.println(GREEN + "8 - Determinar os códigos dos X utilizadores (sendo X dado pelo utilizador) que avaliaram mais negócios diferentes, indicando quantos, sendo o critério de ordenação a ordem decrescente do número de negócios" + RESET);
        System.out.println(GREEN + "9 - Dado o código de um negócio, determinar o conjunto dos X users que mais o avaliaram e, para cada um, qual o valor médio de classificação" + RESET);
        System.out.println(GREEN + "10 - Determinar para cada estado, cidade a cidade, a média de classificação de cada negócio" + RESET);
        System.out.println(GREEN + "11 - Voltar Atrás" + RESET);
        int sel = leNumero(1,11,"Opção");
        return sel;
    }

    public int menuOpcoes(){

        System.out.println(GREEN + "\n1 - Leitura e validação de dados de memória secundária, a partir de ficheiros de texto , e população das estruturas de dados em memória central; " + RESET);
        System.out.println(GREEN + "2 - Gravação da estrutura de dados em ficheiro de objetos" + RESET);
        System.out.println(GREEN + "3 - Consulta interativa (queries) sobre as estruturas de dados" + RESET);
        System.out.println(GREEN + "4 - Consulta das Estatísticas"+ RESET);
        System.out.println(GREEN + "5 - Quit" + RESET);

        int sel = leNumero(1,5,"Opção");
        return sel;
    }

    public int menuLerFicheiro(){

        System.out.println(GREEN + "1- Ler ficheiros de texto" + RESET );
        System.out.println(GREEN + "2- Ler ficheiros binários" + RESET);


        int sel = leNumero(1,2,"Opção");
        return sel;
    }

    public int menuEstatisticas(){
        System.out.println(GREEN + "1- Estatisticas 1" + RESET);
        System.out.println(GREEN + "2- Estatisticas 2" + RESET);

        int sel = leNumero(1,2,"Opção");
        return sel;
    }



    public class ComandoInvalidoException extends Exception {
        public ComandoInvalidoException(){
            super();
        }

        public ComandoInvalidoException(String s){
            super(s);
        }
    }

    public int validaComandoNum (String s) throws ComandoInvalidoException{
        for (char c : s.toCharArray()){
            if (c >= '0' && c <= '9');
            else throw new ComandoInvalidoException(s);
        }
        if(s.length() == 0) throw new ComandoInvalidoException(s);
        return Integer.parseInt(s);
    }

    public int leNumero (int limInf,int limSup,String tipo) {
        int res = 0;
        while (res == 0) {
            String comando = this.ins.nextLine();
            try {
                res = validaComandoNum(comando);
                if (res >= limInf && res <= limSup) ;
                else {
                    System.out.println(RED + "Este número '"+ res + "' não está dentro dos parametros. Por favor insira um número entre "+limInf + " e " + limSup + RESET);
                    res = 0;
                }
            } catch (ComandoInvalidoException e) {
                System.out.println(RED + tipo +" '" + e.getMessage() + "' é inválido! Tente novamente." + RESET);
            }
        }
        return res;
    }

    public String leNomeFicheiro (String tipo){
        System.out.println(YELLOW + "Insira o nome do ficheiro de texto para carregar o catalogo dos " + tipo + ". Se pretender ler o ficheiro predefinido insira " + CYAN_BOLD + "p" + RESET);
        return this.ins.nextLine();
    }


    public AbstractMap.SimpleEntry<Integer,Integer> leInputsQuery2(){
        System.out.println(YELLOW + "Insira o mês(número) e o ano (no formato 'mes/ano') " + RESET);
        String input = this.ins.nextLine();
        if(input.length() == 0) {
            System.out.println(RED + "Tente Novamente" + RESET);
            return leInputsQuery2();
        }
        String[] splited = input.split("/");
        try {
            validaComandoNum(splited[0]);
        }
        catch (ComandoInvalidoException e) {
            System.out.println("Introduza o mês em formato númerico");
            return leInputsQuery2();
        }


        if(!(splited.length == 2)){
            System.out.println(RED + "Argumentos a mais/menos. Tente Novamente" + RESET);
            return leInputsQuery2();
        }
        else if (Integer.parseInt(splited[0]) < 1 || Integer.parseInt(splited[0]) > 12){
            System.out.println(RED + "Mês Invalido. Tente Novamente" + RESET);
            return leInputsQuery2();
        }
        else return  (new AbstractMap.SimpleEntry<>(Integer.parseInt(splited[0]),Integer.parseInt(splited[1])));
    }

    public String leInputsQuery3(){
        System.out.println(YELLOW + "Introduza o número de utilizador(user_id)" + RESET);
        String res = this.ins.nextLine();
        return res;
    }

    public String leInputsQuery4(){
        System.out.println(YELLOW + "Introduza o número de business(business_id)" + RESET);
        String res = this.ins.nextLine();
        return res;
    }

    public int leInputsQueryTop(){
        System.out.println(YELLOW + "Insira um número(x) para obter o top x"+ RESET);
        String comando = this.ins.nextLine();
        int x = 0;
        try {
            x = validaComandoNum(comando);
        } catch (ComandoInvalidoException e) {
            System.out.println(RED + "O que inseriu '" + e.getMessage() + "' é inválido! Tente novamente." + RESET);
        }
        return x;
    }

    public AbstractMap.SimpleEntry<String,Integer> leInputsQuery9(){
        System.out.println(YELLOW + "Insira o código do negócio e um inteiro para o top X (no formato 'business_id,x') " + RESET);
        String input = this.ins.nextLine();
        if(input.length() == 0) {
            System.out.println(RED + "Tente Novamente" + RESET);
            return leInputsQuery9();
        }
        String[] splited = input.split(",");
        if(!(splited.length == 2)){
            System.out.println(RED + "Argumentos a mais/menos. Tente Novamente" + RESET);
            return leInputsQuery9();
        }
        else return  (new AbstractMap.SimpleEntry<>(splited[0],Integer.parseInt(splited[1])));
    }

    public void printResposta(String res) {
        System.out.println(res);
    }

    public void printUserInvalido (String user){
        System.out.println(RED + "O userID que inseriu '" + user + "' não existe no catalogo dos users" + RESET);
    }

    public void printBusinessInvalido (String business){
        System.out.println(RED + "O businessID que inseriu '" + business + "' não existe no catalogo dos businesses" + RESET);
    }

    public void printReviewInvalido (String review){
        System.out.println(RED + "O reviewID que inseriu '" + review + "' não existe no catalogo dos reviews" + RESET);
    }

    public void erroLerFicheiroBinario(int i){
        if (i == 1) System.out.println("Erro ao ler ficheiro binário");
        else System.out.println("Erro ao gravar ficheiro binário");
    }

    public String leBinarioFile(int i){
        if(i == 1)System.out.println("Insira o nome do ficheiro para carregar os dados em binário. Se pretender ler o ficheiro predefinido insira p");
        else System.out.println("Insira o nome do ficheiro para guardar os dados em binário. Se pretender gravar o ficheiro predefinido insira p");
        return this.ins.nextLine();
    }

    public void erroLerFicheiros(){
        System.out.println("Erro ao ler ficheiro");

    }


}
