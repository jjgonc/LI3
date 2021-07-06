package controller;

import model.*;
import view.IView;
import view.View;

import java.io.IOException;
import java.util.*;

/**
 * classe Controller que conhece o modelo e a view e é a ligação entre estes
 */
public class Controller implements IController {
    private IGestReviews model ;
    private IView view;

    /**
     * Construtor por omissão do Controller
     */
    public Controller(){
        this.model = new GestReviews();
        this.view = new View();
    }

    /**
     * Construtor parametrizado do Controler
     * @param model modelo
     * @param view view
     */
    public Controller (IGestReviews model, IView view){
        this.model = model;
        this.view = view;
    }

    /**
     * faz a atribuição de um novo modelo
     * @param model modelo
     */
    public void setModel(IGestReviews model) {
        this.model = model;
    }


    /**
     * constrói a string de resposta da query1 para enviar à view
     * @param res1 resultado da query1
     * @return resposta da query1 como string
     */
    public String constroiResposta1 (List<String> res1) {
        StringBuilder sb = new StringBuilder();
        sb.append("Lista ordenada alfabeticamente com os identificadores dos negócios nunca avaliados e o seu respetivo total:\n");
        for (String s : res1) {
           sb.append("\t").append(s).append("\n");
        }
        sb.append("Total : ").append(res1.size());
        return sb.toString();
    }

    /**
     * constrói a string de resposta da query2 para enviar à view
     * @param res2 resultado da query2
     * @param mes mes com que foi chamado a query2
     * @param ano ano com que foi chamado a query2
     * @return resposta da query2 como string
     */
    public String constroiResposta2 (Map.Entry<Integer, Integer> res2,int mes,int ano){
        StringBuilder sb = new StringBuilder();
        sb.append("Em " + mes + "-" + ano + " -> Total de reviews: " + res2.getKey() + " | Users distintos: " + res2.getValue());

        return sb.toString();
    }

    /**
     * constrói a string de resposta da query3 para enviar à view
     * @param res3 resultado do query3
     * @return resposta da query3 como string
     */
    public String constroiResposta3(Set<Map.Entry<String, String>> res3){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> it : res3) {
            sb.append("mes: ").append(it.getKey()).append("\n");
            sb.append("\t").append(it.getValue()).append("\n");
        }
        return sb.toString();
    }

    /**
     * constrói a string de resposta da query4 para enviar à view
     * @param res4 resultado do query4
     * @return resposta da query4 como string
     */
    public String constroiResposta4(Set<Map.Entry<String,String>> res4){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String,String> it : res4){
            sb.append("mes: ").append(it.getKey()).append("\n");
            sb.append(it.getValue());
        }
        return sb.toString();
    }

    /**
     * constrói a string de resposta da query5 para enviar à view
     * @param res5 resultado do query5
     * @return resposta da query5 como string
     */
    public String constroiResposta5(Set<AbstractMap.SimpleEntry<String,Integer>> res5){
        StringBuilder sb = new StringBuilder();
        if (res5.size() == 0) sb.append("Este user não avaliou nenhum negócio\n");
        else {
            sb.append("A lista de nomes de negócios que mais avaliou:\n");
            for (AbstractMap.SimpleEntry<String,Integer> it : res5){
                sb.append("\tBusiness Name: ").append(it.getKey()).append(" | Quantidade :").append(it.getValue()).append("\n");
            }
        }
        return sb.toString();
    }


    /**
     * constrói a string de resposta da query6 para enviar à view
     * @param res6 resultado do query6
     * @param top top com que é chamado a query6
     * @return resposta da query6 como string
     */
    public String constroiResposta6( Set<Map.Entry<Integer,List<Map.Entry<String,Integer>>>> res6, int top) {
        StringBuilder sb = new StringBuilder();
        sb.append("Conjunto dos ").append(top).append(" negócios mais avaliados (com mais reviews) em cada ano\n");
        for (Map.Entry<Integer,List<Map.Entry<String,Integer>>> it : res6 ){
            sb.append("Ano: ").append(it.getKey()).append("\n");
            int i = 1;
            for (Map.Entry<String,Integer> list: it.getValue()){
                sb.append("\t").append(i).append(": BusinessID '").append(list.getKey()).append(" | Nº de reviews = ")
                        .append(list.getValue()).append("\n");
                i++;
            }
        }
        return sb.toString();
    }

    /**
     * constrói a string de resposta da query7 para enviar à view
     * @param res resultado do query7
     * @return resposta da query7 como string
     */
    public String constroiResposta7(Map<String, List<String>> res){
        StringBuilder sb = new StringBuilder();
        sb.append("A lista dos três mais famosos negócios em termos de número de reviews para cada cidade:");
        for (Map.Entry<String,List<String>> it : res.entrySet()){
            sb.append("Cidade: ").append(it.getKey()).append("\n");
            int i = 1;
            for (String busName : it.getValue()){
                sb.append("\t").append(i).append("-> ").append(busName).append("\n");
                i++;
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * constrói a string de resposta da query8 para enviar à view
     * @param res resultado do query8
     * @param x top com que é chamado a query8
     * @return resposta da query8 como string
     */
    public String constroiResposta8 (Set<Map.Entry<String,Integer>> res,int x){
        StringBuilder sb = new StringBuilder();
        sb.append("Top ").append(x).append(" utilizadores que avaliaram mais negócios diferentes :\n");
        int i = 1;
        for (Map.Entry<String,Integer> it : res){
            sb.append("\t").append(i).append("-> ").append(it.getKey()).append("  |  Avaliações de negócios diferentes: ")
                    .append(it.getValue()).append("\n");
            i++;
        }
        return sb.toString();
    }

    /**
     * constrói a string de resposta da query9 para enviar à view
     * @param res9 resultado do query9
     * @param top top com que é chamado a query9
     * @param bus_id businessID com que é chamado a query9
     * @return resposta da query9 como string
     */
    public String constroiResposta9 (List<Map.Entry<Double, String>> res9,int top,String bus_id){
        StringBuilder sb = new StringBuilder();
        if (res9.size() == 0) sb.append("Nenhum user avaliou este negócio '").append(bus_id).append("'\n");
        else {
            int i = 1;
            sb.append("Top ").append(top).append(" users que mais avaliaram o busID: '")
                    .append(bus_id).append("' e, para cada um, qual o valor médio de classificação\n");
            for (Map.Entry<Double, String> it : res9) {
                sb.append("\t").append(i).append(": UserID: '").append(it.getValue()).append("' -> valor médio classificação: ")
                        .append(it.getKey()).append("\n");
                i++;
            }
        }
        return sb.toString();
    }

    /**
     * constrói a string de resposta da query10 para enviar à view
     * @param res10 resultado do query10
     * @return resposta da query10 como string
     */
    public String constroiResposta10 (Map<String,Map<String,Map<String,Double>>> res10){
        StringBuilder sb = new StringBuilder();
        sb.append("Para cada estado, cidade a cidade, a média de classificação de cada negócio:\n");
        for (Map.Entry<String,Map<String,Map<String,Double>>> it : res10.entrySet()){
            sb.append("Estado: ").append(it.getKey()).append("\n");
            for (Map.Entry<String,Map<String,Double>> it2 : it.getValue().entrySet()){
                sb.append("\tCidade: ").append(it2.getKey()).append("\n");
                for(Map.Entry<String,Double> it3 : it2.getValue().entrySet() ){
                    String med = String.format("%.2f",it3.getValue());
                    sb.append("\t\t BusinessID: '").append(it3.getKey()).append("' | Média de Classificação_:").append(med).append("\n");
                }
            }
        }
        return sb.toString();
    }

    /**
     * metodo que lê ficheiros de texto
     */
    public void leFicheiros () {
        String file_user = view.leNomeFicheiro("users");
        view.printResposta("Deseja incluir os amigos se sim(1) ou não (2)");
        Integer incluir_amigos = view.leNumero(1,2,"Número");
        String file_bus = view.leNomeFicheiro("businesses");
        String file_reviews = view.leNomeFicheiro("reviews");
        if (file_user.equals("p")) file_user = "input_files/users_full.csv";
        if (file_bus.equals("p")) file_bus = "input_files/business_full.csv";
        if (file_reviews.equals("p")) file_reviews = "input_files/reviews_1M.csv";
        try {
            model.loadModel(file_user,file_bus,file_reviews,incluir_amigos);
            List<String> nome_ficheiros = new ArrayList<>();
            nome_ficheiros.add(file_user);
            nome_ficheiros.add(file_bus);
            nome_ficheiros.add(file_reviews);
            model.setNomeFicheirosE(nome_ficheiros);
        } catch (IOException e) {
            view.erroLerFicheiros();
            leFicheiros();
        }
    }

    /**
     * lê ficheiros binários
     */
    public void leFicheirosBin(){
        ILoader loader = new Loader();
        String fileName = view.leBinarioFile(1);
        try {
            IGestReviews m  =  loader.binaryLoader(fileName,model);
            this.model = m;
            List<String> nome_ficheiros = new ArrayList<>();
            nome_ficheiros.add(fileName);
            this.model.setNomeFicheirosE(nome_ficheiros);
        } catch (IOException e) {
            view.erroLerFicheiroBinario(1);
            leFicheirosBin();
        } catch (ClassNotFoundException e) {
            view.erroLerFicheiroBinario(1);
            leFicheirosBin();
        }
    }


    /**
     * método que corre o controler e efectua a comunicação entre a view e o model
     */
    public void run() {
        boolean quit = false;
        int query,opcao;

        opcao = view.menuLerFicheiro();
        switch (opcao){
            case 1:
                leFicheiros();
                break;


            case 2:
                leFicheirosBin();
                break;
        }


        while(!quit){

            opcao = view.menuOpcoes();

            switch (opcao) {
                case 1:
                    int opcaoF = view.menuLerFicheiro();

                    switch (opcaoF){
                        case 1:
                            leFicheiros();
                            break;


                        case 2:
                            leFicheirosBin();
                            break;
                    }
                    break;

                case 2:

                    String fileName = view.leBinarioFile(2);
                    try {
                        this.model.saveModel(fileName);
                    } catch (IOException e) {
                        view.erroLerFicheiroBinario(2);
                    }
                    break;

                case 3:
                    query = view.menuQueries();

                    switch (query) {
                        case 1:
                            Chrono.start();
                            List<String> res1 = model.query1();
                            view.printResposta(constroiResposta1(res1));
                            Chrono.printElapsedTime();
                            break;

                        case 2:
                            AbstractMap.SimpleEntry<Integer,Integer> in2 = view.leInputsQuery2();
                            Chrono.start();
                            Map.Entry<Integer, Integer> res2 = model.query2(in2.getKey(),in2.getValue());
                            view.printResposta(constroiResposta2(res2, in2.getKey(), in2.getValue()));
                            Chrono.printElapsedTime();
                            break;

                        case 3:
                            String user_id = view.leInputsQuery3();
                            if (!this.model.validaUserID(user_id)){
                                this.view.printUserInvalido(user_id);
                            }
                            else {
                                Chrono.start();
                                Set<Map.Entry<String, String>> res3 = model.query3(user_id);
                                view.printResposta(constroiResposta3(res3));
                                Chrono.printElapsedTime();
                            }
                            break;

                        case 4:
                            String bus_id = view.leInputsQuery4();
                            if (!this.model.validaBusinessID(bus_id)){
                                this.view.printBusinessInvalido(bus_id);
                            }
                            else {
                                Chrono.start();
                                Set<Map.Entry<String,String>> res4 = model.query4(bus_id);
                                view.printResposta(constroiResposta4(res4));
                                Chrono.printElapsedTime();
                            }
                            break;

                        case 5:
                            String user_id5 = view.leInputsQuery3();
                            if (!this.model.validaUserID(user_id5)){
                                this.view.printUserInvalido(user_id5);
                            }
                            else {
                                Chrono.start();
                                Set<AbstractMap.SimpleEntry<String,Integer>> res5 = model.query5(user_id5);
                                view.printResposta(constroiResposta5(res5));
                                Chrono.printElapsedTime();
                            }
                            break;

                        case 6:
                            int top = view.leInputsQueryTop();
                            Chrono.start();
                            Set<Map.Entry<Integer,List<Map.Entry<String,Integer>>>> res6 = model.query6(top);
                            view.printResposta(constroiResposta6(res6,top));
                            Chrono.printElapsedTime();
                            break;

                        case 7:
                            Chrono.start();
                            Map<String,List<String>> res7 = model.query7();
                            view.printResposta(constroiResposta7(res7));
                            Chrono.printElapsedTime();
                            break;

                        case 8:
                            int x = view.leInputsQueryTop();
                            Chrono.start();
                            Set<Map.Entry<String,Integer>> res8 = model.query8(x);
                            view.printResposta(constroiResposta8(res8,x));
                            Chrono.printElapsedTime();
                            break;

                        case 9:
                            AbstractMap.SimpleEntry<String,Integer> in9 = view.leInputsQuery9();
                            if (!this.model.validaBusinessID(in9.getKey())){
                                this.view.printBusinessInvalido(in9.getKey());
                            }
                            else {
                                Chrono.start();
                                List<Map.Entry<Double, String>> res9 = model.query9(in9.getKey(),in9.getValue());
                                view.printResposta(constroiResposta9(res9,in9.getValue(),in9.getKey()));
                                Chrono.printElapsedTime();
                            }
                            break;

                        case 10:
                            Chrono.start();
                            Map<String,Map<String,Map<String,Double>>> res10 = model.query10();
                            view.printResposta(constroiResposta10(res10));
                            Chrono.printElapsedTime();
                            break;

                        case 11:
                            break;
                    }
                    break;

                case 4:
                    int sel = view.menuEstatisticas();
                    if (sel == 1) view.printResposta(model.getEstatisticas().toString());
                    else view.printResposta(model.getEstatisticas().toString2());
                    break;

                case 5:
                    quit = true;
                    break;
            }
        }
    }


}
