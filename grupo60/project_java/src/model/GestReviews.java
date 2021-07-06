package model;


import controller.ILoader;
import controller.Loader;
import model.business.Business;
import model.business.BusinessCat;
import model.business.IBusinessCat;
import model.estatisticas.IModuloEstatisticas;
import model.estatisticas.ModuloEstatisticas;
import model.reviews.IReviewCat;
import model.reviews.Review;
import model.reviews.ReviewCat;
import model.users.IUserCat;
import model.users.UserCat;

import java.io.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe agregadora de todo o projeto
 */
public class GestReviews implements IGestReviews, Serializable {
        private IBusinessCat catBus;
        private IReviewCat catRev;
        private IUserCat catUsers;
        private IModuloEstatisticas me;


        public GestReviews() {
                this.catBus = new BusinessCat();
                this.catRev = new ReviewCat();
                this.catUsers = new UserCat();
                this.me = new ModuloEstatisticas();
        }


        public GestReviews(GestReviews m) {
                this.catBus = m.getCatBus();
                this.catRev = m.getCatRev();
                this.catUsers = m.getCatUsers();
        }

        public GestReviews(IBusinessCat bCat, IReviewCat rCat, IUserCat uCat, IModuloEstatisticas me) {
                this.catBus = bCat;
                this.catRev = rCat;
                this.catUsers = uCat;
                this.me = new ModuloEstatisticas((ModuloEstatisticas) me);
        }

        public void loadModel(String fileUsers, String fileBus,String fileRevs,int inclui_amigos) throws IOException {
                ILoader loader = new Loader();
                loader.loadUsers(this.catUsers, this.me, fileUsers,inclui_amigos);
                loader.loadBusinesses(this.catBus ,this.me,fileBus);
                loader.loadReviews(this,fileRevs);

        }



        public void saveModel(String fileName) throws IOException, FileNotFoundException {


                File pasta = new File( "save_files/");
                if (!pasta.exists()) pasta.mkdir();

                File file;

                if(fileName.compareTo("p") == 0){
                        file = new File( "save_files/gestReviews.dat");
                }else{
                        file = new File("save_files/" + fileName);
                }

                if (!file.exists()) file.createNewFile();

                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(this);
                oos.flush();
                oos.close();
        }




        public IModuloEstatisticas getEstatisticas() {
                return this.me.clone();
        }

        public IBusinessCat getCatBus() {
                return this.catBus.clone();
        }

        public void setCatBus(IBusinessCat catBus) {
                this.catBus = catBus;
        }

        public IReviewCat getCatRev() {
                return this.catRev.clone();
        }

        public void setCatRev(IReviewCat catRev) {
                this.catRev = catRev;
        }

        public IUserCat getCatUsers() {
                return this.catUsers.clone();
        }

        public void setCatUsers(IUserCat catUsers) {
                this.catUsers = catUsers;
        }

        public void setMe(IModuloEstatisticas me) {
                this.me = me;
        }

        public boolean verificaKeyRevs (String key) {
                return !this.catRev.getCatRev().containsKey(key);
        }

        /*
                1. Lista ordenada alfabeticamente com os identificadores dos negócios nunca avaliados e o seu respetivo total;
                 */

        /**
         * Obter uma lista ordenada alfabéticamente com os identificadores dos negócios nunca avaliados.
         * @return A lista ordenada alfabéticamente dos negocios nunca avaliados.
         */
        public List<String> query1 () {
                List<String> res = new ArrayList<>();
                Set<String> setReviews = new HashSet<>();
                this.catRev.getCatRev().keySet().stream().forEach(c -> setReviews.add(c));
                for (String k : this.catBus.getCatBus().keySet()) {
                        if (!setReviews.contains(k)) {
                                res.add(k);
                        }
                }
                //neste ponto temos o arraylist com os businesses que NAO têm avaliaçao. Agora temos que ordenar alfabeticamente
                Collections.sort(res, String.CASE_INSENSITIVE_ORDER);
                return res;
        }


    /*
    2. Dado um mês e um ano (válidos), determinar o número total global de reviews realizadas e o número total de users distintos que as realizaram;
     */

        /**
         * A partir de um mês e um ano, determinar o número total global de reviews realizadas e o número total de users distintos que as realizaram.
         * @param mes O mês pelo qual queremos procurar.
         * @param ano O ano correspondente.
         * @return O par com o número total de de reviews realizadas e o número total de users distintos.
         */
        public Map.Entry<Integer, Integer> query2 (int mes, int ano) {
                int totalReviews = 0;
                Set<String> usersDistintos = new HashSet<>();
                for (Review r : this.catRev.getCatRev().values()) {
                        if (r.getDate().getYear() == ano && r.getDate().getMonth().getValue() == mes) {
                                totalReviews++;
                                usersDistintos.add(r.getUser_id());
                        }
                }
                return new AbstractMap.SimpleEntry<>(totalReviews, usersDistintos.size());
        }



        /*
        3. Dado um código de utilizador, determinar, para cada mês, quantas reviews fez, quantos negócios distintos avaliou e que nota média atribuiu;
         */


        /**
         * Obter, para cada mês, quantas reviews, quantos negócios distintos e a nota média que um dado utilizador atribuiu
         * @param user_id O utilizador em questão.
         * @return O conjuto das informações mês a mês.
         */
        public Set<Map.Entry<String,String>> query3 (String user_id) {          //construtor de Query3Triplos para criar um novo com as 3 informaçoes
                int n_reviews = 0;
                double sumNota = 0.0;
                double media = 0.0;
                Map<String, Integer> negociosDistintos = new HashMap<>();  //vai ter os negocios distintos para cada mes -> key = BID ; value = random
                Map<String, Query3Triplos> res = new HashMap<>();
                Map<String, Map<String, Integer>> negocios = new HashMap<>();                //para cada mês tem os negocios distintos que avaliou
                for (Map.Entry<String, Review> it : this.catRev.getCatRev().entrySet()) {
                        if (it.getValue().getUser_id().equals(user_id)) {                       //se o user for o mesmo, aumentamos o nº de reviews e somamos na media
                                if (!res.containsKey(it.getValue().getDate().getMonth().name())) n_reviews = 0;
                                else n_reviews = res.get(it.getValue().getDate().getMonth().name()).getN_reviews_user();
                                n_reviews++;

                                if (!res.containsKey(it.getValue().getDate().getMonth().name())) sumNota = 0;
                                else sumNota = res.get(it.getValue().getDate().getMonth().name()).getSoma_reviews();
                                sumNota += it.getValue().getStars();

                                media = sumNota/n_reviews;

                                if (negocios.containsKey(it.getValue().getDate().getMonth().name())) {
                                        negociosDistintos = negocios.get(it.getValue().getDate().getMonth().name());
                                }
                                else {
                                        negociosDistintos = new HashMap<>();
                                }
                                negociosDistintos.put(it.getValue().getBusiness_id(), 0);
                                negocios.put(it.getValue().getDate().getMonth().name(), negociosDistintos);                   //se for um negocio distinto é adicionado ao set. Set nao deixa haver repetidos
                                res.put(it.getValue().getDate().getMonth().name(), new Query3Triplos(n_reviews, negocios.size(), sumNota, media));
                        }
                }

                Set<Map.Entry<String,String>> resultado = new TreeSet<>(new ComparadorMes());
                for (Map.Entry<String,Query3Triplos> it : res.entrySet()){
                        resultado.add(Map.entry(it.getKey(),it.getValue().toString()));
                }
                return resultado;
        }






        /*
        4.Dado o código de um negócio, determinar, mês a mês, quantas vezes foi avaliado, por quantos users diferentes e a média de classificação;
         */

        /**
         * Obter, mês a mês, o número de vezes que um dado negócio foi avaliado, por quantos utilizadores diferentes e a média das classificações atribuidas.
         * @param businessId O id do negócio.
         * @return As informações prentendidas, exibidas para cada mês.
         */
        public Set<Map.Entry<String,String>> query4 (String businessId) {
                int n_reviews = 0;
                int n_users_diferentes = 0;
                double somaClassificacao = 0.0;
                double mediaClassificacao = 0.0;
                Map<String, Integer> usersDiferentes = new HashMap<>();         //key é o mes e value é o nº de users diferentes
                Map<String, Query4Triplos> res = new HashMap<>();

                for (Review r : this.catRev.getCatRev().values()) {                                                     //para cada review
                        if (r.getBusiness_id().equals(businessId) ) {                                                   //verificar se temos o mesmo business_id
                                if (!res.containsKey(r.getDate().getMonth().name())) n_reviews = 0;
                                else n_reviews = res.get(r.getDate().getMonth().name()).getN_vezes_avaliado();          //ir buscar o nº de reviews daquele mes (mes é a key)
                                n_reviews++;                                                                            //aumentar o nº de reviews daquele mes

                                if (!res.containsKey(r.getDate().getMonth().name())) somaClassificacao = 0;
                                else somaClassificacao = res.get(r.getDate().getMonth().name()).getSoma_classificacao();//ir buscar a soma das classificacoes
                                somaClassificacao += r.getStars();                                                      //adicionar as stars ao somatorio

                                if (!usersDiferentes.containsKey(r.getUser_id())) n_users_diferentes = 0;
                                else n_users_diferentes = usersDiferentes.get(r.getUser_id());                          //ir buscar o nº de users diferentes naquele mes
                                n_users_diferentes++;                                                                   //incrementar esse nº
                                usersDiferentes.put(r.getDate().getMonth().name(), n_users_diferentes);                 //voltar a por no map, mas com o nº de diferentes atualizado

                                mediaClassificacao = somaClassificacao/n_reviews;                              //calcular a nova media
                                res.put(r.getDate().getMonth().name(), new Query4Triplos(n_reviews, n_users_diferentes, somaClassificacao, mediaClassificacao));        //atualizar/adicionar no map
                        }
                }
                Set<Map.Entry<String,String>> resultado = new TreeSet<>(new ComparadorMes());
                for (Map.Entry<String,Query4Triplos> it : res.entrySet()){
                        resultado.add(Map.entry(it.getKey(),it.getValue().toString()));
                }
                return resultado;
        }

        /*

         5 - Dado o código de um utilizador determinar a lista de nomes de negócios que mais avaliou (e quantos), ordenada por ordem decrescente de quantidade e, para
         quantidades iguais, por ordem alfabética dos negócios;

         */

        class q5_comparator implements Comparator<Map.Entry<String,Integer>> {
                public int compare(Map.Entry<String,Integer> a, Map.Entry<String,Integer> b)
                {
                        int res = 0;
                        if(a.getValue() < b.getValue()) res = 1;
                        else {
                                if(a.getValue() > b.getValue()) res = -1;
                                else if (a.getValue() ==  b.getValue()){
                                        res = a.getKey().compareTo(b.getKey());
                                }
                                }

                        return  res;
                }
        }


        public Set<AbstractMap.SimpleEntry<String,Integer>> query5(String user_id){

                Map<String,Integer> businessIdPorReviews = new HashMap<>();
                Map<String,Review> catReviews = this.getCatRev().getCatRev();

                for(Review r : catReviews.values()){
                        if(r.getUser_id().compareTo(user_id) == 0){
                                if(businessIdPorReviews.containsKey(r.getBusiness_id())){
                                        int nValor = businessIdPorReviews.get(r.getBusiness_id());
                                        businessIdPorReviews.put(r.getBusiness_id(),nValor + 1);
                                }else{
                                        businessIdPorReviews.put(r.getBusiness_id(),1);
                                }
                        }
                }

                Set<AbstractMap.SimpleEntry<String,Integer>> res = new TreeSet<>(new q5_comparator());

                for(Map.Entry e : businessIdPorReviews.entrySet()){
                        String busId = (String)e.getKey();
                        String busName = this.getCatBus().getCatBus().get(busId).getBus_name();
                        AbstractMap.SimpleEntry<String,Integer> entry = new AbstractMap.SimpleEntry<String,Integer>(busName,(Integer) e.getValue());
                        res.add(entry);

                }

                return  res;
        }




        /*
             6 Determinar o conjunto dos X negócios mais avaliados (com mais reviews) em cada ano, indicando o número total de distintos utilizadores que o avaliaram (X é um inteiro dado pelo utilizador);
         */


        class q6_comparator implements Comparator<Map.Entry<String,Integer>> {
                public int compare(Map.Entry<String,Integer> a, Map.Entry<String,Integer> b)
                {
                        return -a.getValue().compareTo(b.getValue());
                }
        }


        public List<Map.Entry<String,Integer>> xNegociosComMaisReviews(int x, Map<String, List<String>> usersReviwedBus){

                List<Map.Entry<String,Integer>> res = new ArrayList<>();

                Set<Map.Entry<String,Integer>> auxRetirarMaisReviews = new TreeSet<>(new q6_comparator());

                for(Map.Entry e : usersReviwedBus.entrySet()){
                        List<String> users = (List<String>) e.getValue();
                        Map.Entry nEntry = new AbstractMap.SimpleEntry(e.getKey(), users.size());
                        auxRetirarMaisReviews.add(nEntry);
                }


                for(Map.Entry e : auxRetirarMaisReviews){
                        if(x < 1) break;
                        List<String> todosUsers = usersReviwedBus.get(e.getKey());
                        Set<String> usersSemRepeticoes = new TreeSet<>();
                        for(String s : todosUsers){
                                usersSemRepeticoes.add(s);
                        }

                       Map.Entry nova = new AbstractMap.SimpleEntry(e.getKey(),usersSemRepeticoes.size());
                        res.add(nova);
                        x--;
                }

                return res;
        }


        public Set<Map.Entry<Integer,List<Map.Entry<String,Integer>>>> query6(int x) {

                Map<String, Review> revs = this.getCatRev().getCatRev();

                //Criação de uma estrutura auxiliar que guarda os users por ano que fizeram reviews a todos os business

                Map<Integer, Map<String, List<String>>> busAnoUsers = new HashMap<>();


                for (Review rev : revs.values()) {
                        if (busAnoUsers.containsKey(rev.getDate().getYear())) {

                                Map<String, List<String>> anoOcorrencias = busAnoUsers.get(rev.getDate().getYear());
                                if (anoOcorrencias.containsKey(rev.getBusiness_id())) {
                                        List<String> users = anoOcorrencias.get(rev.getBusiness_id());
                                        users.add(rev.getUser_id());
                                        anoOcorrencias.put(rev.getBusiness_id(),users);
                                        busAnoUsers.put(rev.getDate().getYear(), anoOcorrencias);
                                } else {
                                        List<String> novoAno = new ArrayList<>();
                                        novoAno.add(rev.getUser_id());
                                        anoOcorrencias.put(rev.getBusiness_id(), novoAno);
                                        busAnoUsers.put(rev.getDate().getYear(), anoOcorrencias);
                                }

                        } else {
                                Map<String, List<String>> novasOCorrencias = new HashMap<>();
                                List<String> users = new ArrayList<>();
                                users.add(rev.getUser_id());
                                novasOCorrencias.put(rev.getBusiness_id(), users);
                                busAnoUsers.put(rev.getDate().getYear(), novasOCorrencias);
                        }
                }


                //Retira da estrutura auxiliar os businesses a mais (ou seja aqueles com menos Reviews)
                Set<Map.Entry<Integer,List<Map.Entry<String,Integer>>>> res = new TreeSet<>(new ComparadorAno());

                for(Map.Entry e : busAnoUsers.entrySet()){

                        Map<String, List<String>> map = (Map<String, List<String>>) e.getValue();
                        res.add(Map.entry((int)e.getKey(),xNegociosComMaisReviews(x,map)));
                }

                return res;
        }


        /*
        7. Determinar, para cada cidade, a lista dos três mais famosos negócios em termos de número de reviews;
         */
        /**
         * Determinar, para cada cidade, a lista dos três mais famosos negócios em termos de número de reviews.
         * @return O conjuto das informações cidade a cidade.
         */
        public Map<String,List<String>> query7 (){
                Map< String , Map<Business,Integer> > businessesPorCidade = new HashMap<>();

                Map<String,Business> catalogoBus = this.catBus.getCatBus();
                for (Review rev : this.catRev.getCatRev().values()){
                        Business bus = catalogoBus.get(rev.getBusiness_id());
                        // se ja tiver a cidade
                        if (businessesPorCidade.containsKey(bus.getCity())){
                                Map<Business,Integer> busCidade = businessesPorCidade.get(bus.getCity());
                                int count = busCidade.containsKey(bus) ? busCidade.get(bus) : 0;
                                busCidade.put(bus,count + 1);
                        }
                        // se nao tiver a cidade no map
                        else {
                                Map<Business,Integer> novo = new HashMap<>();
                                novo.put(bus,1);
                                businessesPorCidade.put(bus.getCity(),novo);
                        }
                }
                // neste momento tenho um hash map que a cada cidade corresponde outro hashMao com todos os negocios daquela cidade
                // que a cada negocio corresponde o número de reviews

                Map<String,List<String>> top3porCidade = new HashMap<>();
                for (Map.Entry<String,Map<Business,Integer>> businessesCidade : businessesPorCidade.entrySet()){
                        String cidade = businessesCidade.getKey();
                        Map<Business,Integer> businessesDaCidade = businessesCidade.getValue();
                        int top1 = 0, top2 =0, top3 = 0;
                        Business b1 = null, b2 = null, b3 = null;
                        for (Map.Entry<Business,Integer> s : businessesDaCidade.entrySet()){
                                int nReviews = s.getValue();
                                Business b = s.getKey();
                                if (nReviews > top1){
                                        top3 = top2; b3 = b2;
                                        top2 = top1; b2 = b1;
                                        top1 = nReviews; b1 = b;
                                } else {
                                        if (nReviews > top2){
                                                top3 = top2; b3 = b2;
                                                top2 = nReviews; b2 = b;
                                        }else {
                                                if (nReviews > top3){
                                                        top3 = nReviews; b3 = b;
                                                }
                                        }
                                }
                        }
                        List<String> top3Bus = new ArrayList<>();
                        if (top2 != 0) top3Bus.add(b2.getBus_name());
                        if (top3 != 0) top3Bus.add(b3.getBus_name());
                        top3porCidade.put(cidade,top3Bus);
                }
                return top3porCidade;
        }

          /*
        8. Determinar os códigos dos X utilizadores (sendo X dado pelo utilizador) que avaliaram mais negócios diferentes,
         indicando quantos, sendo o critério de ordenação a ordem decrescente do número de negócios;
         */

        /**
         *  Determinar os códigos dos X utilizadores  que avaliaram mais negócios diferentes indicando quantos, sendo o critério de ordenação a ordem decrescente do número de negócios.
         * @param x top x
         * @return Lista de utilizadores com mais reviews a negócios diferentes.
         */
        public  Set<Map.Entry<String,Integer>> query8(int x) {
                Map<String, Set<String>> busReviewedPorUser = new HashMap<>();
                for (Review rev : this.catRev.getCatRev().values()) {
                        // se ja tiver o user no meu hashmap
                        if (busReviewedPorUser.containsKey(rev.getUser_id())) {
                                Set<String> businesses = busReviewedPorUser.get(rev.getUser_id());
                                businesses.add(rev.getBusiness_id());
                        } else {
                                Set<String> aux = new TreeSet<>();
                                aux.add(rev.getBusiness_id());
                                busReviewedPorUser.put(rev.getUser_id(), aux);
                        }
                }

                Map<String, Integer> NReviewedPorUser = new HashMap<>();
                for (Map.Entry<String, Set<String>> it : busReviewedPorUser.entrySet()) {
                        NReviewedPorUser.put(it.getKey(), it.getValue().size());
                }

                // neste momento tenho para cada user tenho o numero de businesses diferentes aos quais deu review
                Set<Map.Entry<String, Integer>> nReviewedPorUser = new TreeSet<>(new ComparadorPorNum());
                for (Map.Entry<String, Integer> it : NReviewedPorUser.entrySet()) {
                        nReviewedPorUser.add(it);
                }
                // neste momento tenho um set ordenado

                // tinha assim mas o collect mete no set tudo desordenado
                // nReviewedPorUser = nReviewedPorUser.stream().limit(x).collect(Collectors.toSet());

                Set<Map.Entry<String, Integer>> res = new TreeSet<>(new ComparadorPorNum());
                int i = 0;
                for (Map.Entry<String, Integer> it : nReviewedPorUser){
                        if (i < x) {
                                res.add(it);
                                i++;
                        }
                }
                return res;
        }

        /*
        9.Dado o código de um negócio, determinar o conjunto dos X users que mais o avaliaram e, para cada um, qual o valor médio de
        classificação  (ordenação cf. 5);
         */

        /**
         * Para um dado negócio, determinar o conjunto dos X users que mais o avaliaram e, para cada um, qual o valor médio de classificação.
         * @param businessId O business Id a procurar.
         * @param x O número de negócios a apresentar.
         * @return Lista com os pares com o valor médio da avaliação e o id do user que fez a avaliação.
         */
        public List<Map.Entry<Double, String>> query9 (String businessId, int x) {
                int nAvaliacoes = 0;
                double somaAvaliacoes = 0.0;
                Map<String, Map.Entry<Integer, Double>> avaliacoes_by_user = new TreeMap<>();          //key é o UserId; Value é um par de (total_reviews, soma_reviews)
                for (Review r : this.catRev.getCatRev().values()) {
                        if (r.getBusiness_id().equals(businessId)) {
                                if (avaliacoes_by_user.containsKey(r.getUser_id())) {
                                        nAvaliacoes = avaliacoes_by_user.get(r.getUser_id()).getKey();
                                }
                                else nAvaliacoes = 0;
                                nAvaliacoes++;

                                if (avaliacoes_by_user.containsKey(r.getUser_id())) {
                                        somaAvaliacoes = avaliacoes_by_user.get(r.getUser_id()).getValue();
                                }
                                else somaAvaliacoes = 0.0;
                                somaAvaliacoes += r.getStars();
                                avaliacoes_by_user.put(r.getUser_id(), new AbstractMap.SimpleEntry<>(nAvaliacoes, somaAvaliacoes));
                        }
                }

                //passar para um novo map e calcular a media (sendo que cada value tem o nº de elementos e a soma)

                Set<Map.Entry<Double, String>> res = new TreeSet<>(new Comparador_q9());
                double media = 0.0;
                for (Map.Entry <String, Map.Entry<Integer, Double>> it : avaliacoes_by_user.entrySet()) {
                        media = it.getValue().getValue() / it.getValue().getKey();
                        res.add(new AbstractMap.SimpleEntry<>(media, it.getKey()));
                }

                //passar os N maiores para uma lista de pares
                List<Map.Entry<Double, String>> ans = new ArrayList<>();
                double lastKey;
                ans = res.stream().limit(x).collect(Collectors.toList());
                ans.sort(new Comparador_q9());
                return ans;
        }

        /*
        10. Determinar para cada estado, cidade a cidade, a média de classificação de cada negócio.
         */

        /**
         * Determinar para cada estado, cidade a cidade, a média de classificação de cada negócio.
         * @return Map com a média de classificação de cada negócio, cidade a cidade, estado a estado.
         */
        public Map<String,Map<String,Map<String,Double>>> query10(){
                Map<String,Query4Triplos> mediaPorBusiness = new HashMap<>();
                int n_vezes_avaliado = 0;
                double soma_classificacao = 0.0, media_classificacao = 0.0;
                for (Review rev : this.catRev.getCatRev().values() ){
                        if (mediaPorBusiness.containsKey(rev.getBusiness_id())){
                                n_vezes_avaliado = mediaPorBusiness.get(rev.getBusiness_id()).getN_vezes_avaliado();
                                n_vezes_avaliado++;
                                soma_classificacao = mediaPorBusiness.get(rev.getBusiness_id()).getSoma_classificacao();
                                soma_classificacao += rev.getStars();
                                media_classificacao = soma_classificacao/n_vezes_avaliado;
                        }
                        else {
                                n_vezes_avaliado = 1;
                                soma_classificacao = rev.getStars();
                                media_classificacao = soma_classificacao/n_vezes_avaliado;
                        }
                        Query4Triplos novo = new Query4Triplos(n_vezes_avaliado,soma_classificacao,media_classificacao);
                        mediaPorBusiness.put(rev.getBusiness_id(),novo);
                }

                // neste momento tenho um map que para cada business tem a sua media dde classificaçao
                Map<String,Map<String,Map<String,Double>>> mediaBusCaCPorEstado = new HashMap<>();
                for (Business bus : this.catBus.getCatBus().values()){
                        // se nao tiver o estado
                        if (!mediaBusCaCPorEstado.containsKey(bus.getState())){
                                Map<String,Double> mediaBusinessesdaCidade = new HashMap<>();
                                // se o business nao tiver nenhuma review media = 0
                                if (!mediaPorBusiness.containsKey(bus.getBus_id())){
                                        mediaBusinessesdaCidade.put(bus.getBus_id(),0.0);
                                }
                                else {
                                        mediaBusinessesdaCidade.put(bus.getBus_id(), mediaPorBusiness.get(bus.getBus_id()).getMedia_classificacao());
                                }
                                Map<String,Map<String,Double>> mediaBusinessesCaC = new HashMap<>();
                                mediaBusinessesCaC.put(bus.getCity(),mediaBusinessesdaCidade);
                                mediaBusCaCPorEstado.put(bus.getState(),mediaBusinessesCaC);
                        }
                        // tem o estado
                        else{
                                Map<String,Map<String,Double>> mediaBusinessesCaC = mediaBusCaCPorEstado.get(bus.getState());
                                // se nao tiver a city
                                if (!mediaBusinessesCaC.containsKey(bus.getCity())){
                                        Map<String,Double> mediaBusinessesdaCidade = new HashMap<>();
                                        // se o business nao tiver nenhuma review media = 0
                                        if (!mediaPorBusiness.containsKey(bus.getBus_id())){
                                                mediaBusinessesdaCidade.put(bus.getBus_id(),0.0);
                                        }
                                        else {
                                                mediaBusinessesdaCidade.put(bus.getBus_id(), mediaPorBusiness.get(bus.getBus_id()).getMedia_classificacao());
                                        }
                                        mediaBusinessesCaC.put(bus.getCity(),mediaBusinessesdaCidade);
                                }
                                // tem a city
                                else {
                                        Map<String,Double> mediaBusinessesdaCidade = mediaBusinessesCaC.get(bus.getCity());
                                        // se o business nao tiver nenhuma review media = 0
                                        if (!mediaPorBusiness.containsKey(bus.getBus_id())){
                                                mediaBusinessesdaCidade.put(bus.getBus_id(),0.0);
                                        }
                                        else {
                                                mediaBusinessesdaCidade.put(bus.getBus_id(), mediaPorBusiness.get(bus.getBus_id()).getMedia_classificacao());
                                        }
                                }
                        }

                }
                return mediaBusCaCPorEstado;
        }


        public boolean validaUserID (String userID){
                return this.catUsers.getCatUser().containsKey(userID);
        }

        public boolean validaBusinessID (String businessID){
                return this.catBus.getCatBus().containsKey(businessID);
        }

        public boolean validaReviewID (String reviewID){
                return this.catRev.getCatRev().containsKey(reviewID);
        }

        public void setNomeFicheirosE (List<String> nomeFicheiros){
                this.me.setNomesFicheiros(nomeFicheiros);
        }

}
