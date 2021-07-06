package controller;

import model.IGestReviews;
import model.business.Business;
import model.business.IBusinessCat;
import model.estatisticas.IModuloEstatisticas;
import model.reviews.IReviewCat;
import model.reviews.ReviewCat;
import model.users.IUserCat;
import model.users.User;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe responsável pelo carregamento de dados para memoria
 */



public class Loader implements ILoader {


    /**
     * Função que carrega o businesses atravês de um ficheiro de texto e atualiza estatisticas
     * @param model modelo
     * @param me modelo estatisticas
     * @param file nome do ficheiro
     * @throws IOException Avisa o controller sobre um erro de input/output
     * @throws FileNotFoundException Avisa o controller sobre a inxistencia do ficheiro
     */


    public void loadBusinesses (IBusinessCat model,IModuloEstatisticas me, String file) throws IOException, FileNotFoundException{

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String s = br.readLine();
            while ((s = br.readLine()) != null) {
                String c[] = s.split(";");
                if (c.length == 5)
                    model.addBusParametros(c[0], c[1], c[2], c[3], c[4]);
                me.addBus();
                if (c.length == 4)
                    model.addBusParametros(c[0], c[1], c[2], c[3], "");
                me.addBus();
            }
        }
    }

    /**
     * Função que carrega os users atravês de um ficheiro de texto e atualiza estatisticas
     * @param model modelo
     * @param me modulo estatisticas
     * @param file nome do ficheiro
     * @throws IOException Avisa o controller sobre um erro de IO
     * @throws FileNotFoundException Avisa o controller sobre a inxistencia do ficheiro

     */

    public void loadUsers (IUserCat model, IModuloEstatisticas me, String file,int incluir_amigos)  throws IOException, FileNotFoundException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String s = br.readLine();
            while ((s = br.readLine()) != null) {

                String c[] = s.split(";");
                if (c.length == 3) {
                    model.addUserParametros(c[0], c[1], c[2],incluir_amigos);
                    me.addUser();
                }
                if (c.length == 2) {
                    model.addUserParametros(c[0], c[1], "",incluir_amigos);
                    me.addUser();
                }
            }
        }
    }

    /**
     * Devolve o mes de uma data em strings
     * @param  data data em string
     * @return int data em int
     */
    public int getMesData(String data){
        String[] splited = data.split("-");
        return Integer.parseInt(splited[1]);
    }

    /**
     *
     * @return Lista com 12 sets de strinng vazias
     */

    public List<Set<String>> initListaSets(){
        List<Set<String>> listSet = new ArrayList<>();

        for(int i = 0; i < 12; i++) {
            Set<String> set = new TreeSet<>();
            listSet.add(set);
        }
        return  listSet;
    }

    /**
     * Adiciona um user ao set do mes correto
     * @param listSet lista com set users
     * @param mes numero do mes
     * @param nome identificador do user
     */
    public void addUserMes(List<Set<String>> listSet,int mes, String nome){

            Set<String> nomes = listSet.get(mes-1);
            nomes.add(nome);
            listSet.set(mes-1,nomes);
    }

    /**
     * Função que carrega os reviews para o model e atualiza as estatisticas
     * @param m modelo
     * @param file path
     * @throws IOException Avisa o controller sobre um erro de input outpur
     * @throws FileNotFoundException Avisa o controller sobre a inxistencia do ficheiro
     */
    public void loadReviews(IGestReviews m, String file)  throws IOException, FileNotFoundException{

        IModuloEstatisticas me = m.getEstatisticas();

        IReviewCat rev = new ReviewCat();
        Set<String> businessSet = new TreeSet<>();
        Set<String> usersSet = new TreeSet<>();
        List<Set<String>> usersPorMes = initListaSets();

        Map<String, User> usCat = m.getCatUsers().getCatUser();
        Map<String, Business> busCat = m.getCatBus().getCatBus();

        Set<String> usersNaoAvaliaramSet = usCat.keySet().stream().collect(Collectors.toSet());

        BufferedReader br = new BufferedReader(new FileReader(file));
        String linha = br.readLine();
        while ((linha = br.readLine()) != null) {
            String[] splited = linha.split(";");
            if (splited.length == 9 && usCat.containsKey(splited[1]) && busCat.containsKey(splited[2])) {
                int mes = getMesData(splited[7]);
                double stars = Double.parseDouble(splited[3]);

                rev.addRevParametros(splited[0], splited[1], splited[2], splited[3], splited[4], splited[5], splited[6], splited[7], splited[8]);

                if (businessSet.add(splited[2])) me.addNegocioAvaliado();

                if (usersSet.add(splited[1])) me.addUserReviews();

                if(!splited[4].equals("0") || !splited[5].equals("0") || !splited[6].equals("0")){
                    usersNaoAvaliaramSet.remove(splited[1]);
                    addUserMes(usersPorMes, mes, splited[1]);
                }

                if (splited[4].equals("0") && splited[5].equals("0") && splited[6].equals("0"))
                    me.addReviewZeroImpacto();

                me.addReviewMes(mes);

                me.addClassMes(mes, stars);

                me.setTotalNegociosNaoAvaliados();

            } else me.addReviewErrado();
        }

        m.setCatRev(rev);
        me.setUsersSemAvaliacoes(usersNaoAvaliaramSet.size());
        me.setUsersSemReviews();
        me.calculaMedias();
        me.usersAvaliamPorMes(usersPorMes);
        br.close();

        m.setMe(me);

    }

    /**
     * Função que carrega os dados atravês de um ficheiro binário
     * @param fileName nome do ficheiro
     * @param model modelo
     * @return modelo
     * @throws IOException Avisa o controller sobre um erro de input/output
     * @throws ClassNotFoundException Avisa o controller sobre a inxistencia do ficheiro
     */

    public IGestReviews binaryLoader(String fileName, IGestReviews model) throws IOException, ClassNotFoundException {

        File toRead;

        if(fileName.compareTo("p") == 0){

            toRead = new File("save_files/" + "gestReviews.dat");

        }else{
            toRead = new File("save_files/" + fileName);
        }

        FileInputStream bf = new FileInputStream(toRead);
        ObjectInputStream ois = new ObjectInputStream(bf);
        IGestReviews m = (IGestReviews) ois.readObject();
        ois.close();

        return m;

    }

}
