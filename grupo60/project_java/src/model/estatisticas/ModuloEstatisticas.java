package model.estatisticas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Classe que gerencia as estatisticas
 */
public class ModuloEstatisticas implements IModuloEstatisticas, Serializable {

    private List<String> nomesFicheiros;
    private int numeroReviewsErrados;
    private int numeroTotalNegocios;
    private int totalNegociosAvaliados;
    private int totalNegociosNaoAvaliados;
    private int totalUsers;
    private int usersComReviews;
    private int usersSemAvaliacoes; //users sem reviews + users com reviews mas com campos de avaliação vazios(0)
    private int usersSemReviews;
    private int reviewsComZeroImpacto;
    private List<Integer> reviewsPorMes;
    private List<Double> classificacaoPorMes;
    private Double mediaGlobal;
    private List<Integer> avaliadoresPorMes;


    /**
     * Construtor vazio modulo estatisticas
     */

    public ModuloEstatisticas(){

        this.nomesFicheiros = new ArrayList<>();
        this.numeroReviewsErrados = 0;
        this.numeroTotalNegocios = 0;
        this.totalNegociosAvaliados = 0;
        this.totalNegociosNaoAvaliados = 0;
        this.totalUsers = 0;
        this.usersComReviews = 0;
        this.usersSemAvaliacoes = 0;
        this.usersSemReviews = 0;
        this.reviewsComZeroImpacto = 0;
        this.reviewsPorMes = devolve12zeros();
        this.classificacaoPorMes = devolve12zerosdouble();
        this.mediaGlobal = 0.0;
        this.avaliadoresPorMes = devolve12zeros();

    }

    /**
     * Construtor por objeto do modulo estatisticas
     */

    public ModuloEstatisticas(ModuloEstatisticas me){
        this.nomesFicheiros = me.getNomesFicheiros();
        this.numeroReviewsErrados = me.getNumeroReviewsErrados();
        this.numeroTotalNegocios = me.getNumeroTotalNegocios();
        this.totalNegociosAvaliados = me.getTotalNegociosAvaliados();
        this.totalNegociosNaoAvaliados = me.getTotalNegociosNaoAvaliados();
        this.totalUsers = me.getTotalUsers();
        this.usersComReviews = me.getUsersComReviews();
        this.usersSemAvaliacoes = me.getUsersSemAvaliacoes();
        this.usersSemReviews = me.getUsersSemReviews();
        this.reviewsComZeroImpacto = me.getReviewsComZeroImpacto();
        this.reviewsPorMes = me.getReviewsPorMes();
        this.classificacaoPorMes = me.getClassificacaoPorMes();
        this.mediaGlobal = me.getMediaGlobal();
        this.avaliadoresPorMes = me.getAvaliadoresPorMes();

    }

    /**
     Função auxiliar que cria a estrutura correta para as listas de interios do modulo estatisticas
     */
    public List<Integer> devolve12zeros(){
        List<Integer> ints = new ArrayList<>();
        ints.add(0);
        ints.add(0);
        ints.add(0);
        ints.add(0);
        ints.add(0);
        ints.add(0);
        ints.add(0);
        ints.add(0);
        ints.add(0);
        ints.add(0);
        ints.add(0);
        ints.add(0);
        return ints;
    }

    /**
     Função auxiliar que cria a estrutura correta para as listas de doubles do modulo estatisticas
     */
    public List<Double> devolve12zerosdouble(){
        List<Double> doubs = new ArrayList<>();
        doubs.add(0.0);
        doubs.add(0.0);
        doubs.add(0.0);
        doubs.add(0.0);
        doubs.add(0.0);
        doubs.add(0.0);
        doubs.add(0.0);
        doubs.add(0.0);
        doubs.add(0.0);
        doubs.add(0.0);
        doubs.add(0.0);
        doubs.add(0.0);
        return doubs;
    }


    //getters

    /**
     *
     * @return Users com reviews
     */

    public int getUsersComReviews() {
        return this.usersComReviews;
    }

    /**
     *
     * @return Total de nogeciosAvaliados
     */

    public int getTotalNegociosAvaliados() {
        return totalNegociosAvaliados;
    }

    /**
     *
     * @return Media global
     */
    public Double getMediaGlobal() {
        return mediaGlobal;
    }

    /**
     *
     * @return Numero reviews errados
     */
    public int getNumeroReviewsErrados() {
        return numeroReviewsErrados;
    }

    /**
     *
     * @return Numero Total de Negocios
     */
    public int getNumeroTotalNegocios() {
        return numeroTotalNegocios;
    }

    /**
     *
     * @return Reviews com Zero Impacto
     */

    public int getReviewsComZeroImpacto() {
        return reviewsComZeroImpacto;
    }

    /**
     *
     * @return Total negocios nao avaliados
     */
    public int getTotalNegociosNaoAvaliados() {
        return totalNegociosNaoAvaliados;
    }

    /**
     *
     * @return Total de users
     */
    public int getTotalUsers() {
        return this.totalUsers;
    }

    /**
     *
     * @return Users sem avaliações
     */

    public int getUsersSemAvaliacoes() {
        return usersSemAvaliacoes;
    }

    /**
     *
     * @return Users sem reviews
     */
    public int getUsersSemReviews() {
        return usersSemReviews;
    }

    /**
     *
     * @return Classificação por mês
     */

    public List<Double> getClassificacaoPorMes() {
        return classificacaoPorMes.stream().collect(Collectors.toList());
    }


    /**
     *
     * @return Avaliadores por mes
     */
    public List<Integer> getAvaliadoresPorMes() {

        List<Integer> res = new ArrayList<>();
        res = avaliadoresPorMes.stream().collect(Collectors.toList());
        return res;
    }

    /**
     *
     * @return Reviews por mes
     */
    public List<Integer> getReviewsPorMes() {
        List<Integer> res = new ArrayList<>();
        res = reviewsPorMes.stream().collect(Collectors.toList());
        return res;
    }

    /**
     *
     * @return Nomes ficheiros
     */
    public List<String> getNomesFicheiros() {
        return nomesFicheiros.stream().collect(Collectors.toList());
    }


    //setters

    /**
     *
     * @param usersComReviews users com reviews
     */

    public void setUsersComReviews(int usersComReviews) {
        this.usersComReviews = usersComReviews;
    }

    /**
     *
     * @param totalNegociosAvaliados total negocios avaliados
     */
    public void setTotalNegociosAvaliados(int totalNegociosAvaliados) {
        this.totalNegociosAvaliados = totalNegociosAvaliados;
    }

    /**
     *
     * @param avaliadoresPorMes avaliadores por mes
     */
    public void setAvaliadoresPorMes(List<Integer> avaliadoresPorMes) {
        this.avaliadoresPorMes = avaliadoresPorMes;
    }

    /**
     *
     * @param classificacaoPorMes classificação por mes
     */
    public void setClassificacaoPorMes(List<Double> classificacaoPorMes) {
        this.classificacaoPorMes = classificacaoPorMes;
    }

    /**
     *
     * @param mediaGlobal media global
     */
    public void setMediaGlobal(Double mediaGlobal) {
        this.mediaGlobal = mediaGlobal;
    }

    /**
     *
     * @param nomesFicheiros nomes dos ficheiros
     */
    public void setNomesFicheiros(List<String> nomesFicheiros) {
        this.nomesFicheiros = nomesFicheiros;
    }

    /**
     *
     * @param numeroReviewsErrados  numero total de reviews errados
     */
    public void setNumeroReviewsErrados(int numeroReviewsErrados) {
        this.numeroReviewsErrados = numeroReviewsErrados;
    }

    /**
     *
     * @param numeroTotalNegocios numero total de negocios
     */
    public void setNumeroTotalNegocios(int numeroTotalNegocios) {
        this.numeroTotalNegocios = numeroTotalNegocios;
    }

    /**
     *
     * @param reviewsComZeroImpacto reviews com zero impacto
     */
    public void setReviewsComZeroImpacto(int reviewsComZeroImpacto) {
        this.reviewsComZeroImpacto = reviewsComZeroImpacto;
    }

    /**
     *
     * @param reviewsPorMes reviews por mes
     */
    public void setReviewsPorMes(List<Integer> reviewsPorMes) {
        this.reviewsPorMes = reviewsPorMes;
    }

    /**
     *
     * @param totalNegociosNaoAvaliados total de negocios nao avaliados
     */
    public void setTotalNegociosNaoAvaliados(int totalNegociosNaoAvaliados) {
        this.totalNegociosNaoAvaliados = totalNegociosNaoAvaliados;
    }

    /**
     *
     * @param totalUsers total de users
     */
    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }

    /**
     *
     * @param usersSemAvaliacoes numero de users sem avaliações
     */
    public void setUsersSemAvaliacoes(int usersSemAvaliacoes) {
        this.usersSemAvaliacoes = usersSemAvaliacoes;
    }

    /**
     *
     * @param usersSemReviews numero de users sem reviews
     */
    public void setUsersSemReviews(int usersSemReviews) {
        this.usersSemReviews = usersSemReviews;
    }

    /**
     * Aumenta o contaor de reviews errados nas estatisticas
     */
    public void addReviewErrado(){
        this.numeroReviewsErrados++;
    }

    /**
     * Aumenta o contador de negocios avaliados nas estatisticas
     */
    public void addNegocioAvaliado(){
        this.totalNegociosAvaliados++;
    }

    /**
     * Aumenta o contador de users com revies nas estatisticas
     */
    public void addUserReviews(){
        this.usersComReviews++;
    }

    /**
     * Aumenta o contador de reviews com zero impacto nas estatisticas
     */
    public void addReviewZeroImpacto(){
        this.reviewsComZeroImpacto++;
    }

    /**
     * Aumenta o contador de reviews de determinado mes
     * @param mes mes
     */
    public void addReviewMes(int mes){

        int valor = this.reviewsPorMes.get(mes - 1) + 1;
        this.reviewsPorMes.set(mes-1,valor);


    }

    /**
     * Adiciona o numero de estrelas de um review à soma de classificaçao de um mes
     * @param mes mes
     * @param stars estrelas
     */
    public void addClassMes(int mes, double stars){

        double valor = this.classificacaoPorMes.get(mes - 1) + stars;
        this.classificacaoPorMes.set(mes-1,valor);


    }

    /**
     * Calcula as medias mensais e anuais das estatisticas
     */
    public void calculaMedias(){
        for(int i = 0; i < 12; i++){

            double media = this.classificacaoPorMes.get(i);
            media = media / (double) this.reviewsPorMes.get(i);
            this.classificacaoPorMes.set(i,media);

        }

        double sum = 0.0;

        for(int i = 0; i < 12 ; i++) sum = sum + this.classificacaoPorMes.get(i);

        sum = sum/ (double) 12.0;

        this.mediaGlobal = sum;
    }

    /**
     * Calcula o numero de users que avaliam por mes de acordo com o conjunto do numero de users que avaliaram
     * @param idPorMes
     */
    public void usersAvaliamPorMes(List<Set<String>>idPorMes){
        int i = 0;
        for(Set<String> s : idPorMes){
            int tamanho = s.size();
            this.avaliadoresPorMes.set(i,tamanho);
            i++;
        }

    }

    /**
     * Calcula os users sem reviews com base na soma total de users e no numero de users com reviews
     */
    public void setUsersSemReviews(){
        this.usersSemReviews = this.totalUsers - this.usersComReviews;
    }

    /**
     * Calcula o total de negocios nao avaliados  com base na soma total de negocios e no numero de negocios avaliados
     */
    public void setTotalNegociosNaoAvaliados(){
        this.totalNegociosNaoAvaliados = this.numeroTotalNegocios - this.totalNegociosAvaliados;
    }

    /**
     * Adiciona um user à contagem  nas estatisticas
     */
    public void addUser(){
        this.totalUsers++;
    }

    /**
     * Adiciona um business à contagem nas estatisticas
     */

    public  void addBus(){
        this.numeroTotalNegocios++;
    }

    /**
     * Converte o modulo estatisticas em string para mostrar a opção 1
     * @return String
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Estatisticas:\n")
          .append("\tnomes Ficheiros = \n");
        int i = 1;
        for (String s : nomesFicheiros){
            if (i==1) sb.append("\t\t Nome Ficheiro Users: '").append(s).append("'\n");
            if (i==2) sb.append("\t\t Nome Ficheiro Businesses: '").append(s).append("'\n");
            if (i==3) sb.append("\t\t Nome Ficheiro Reviews: '").append(s).append("'\n");
            i++;
        }
        sb.append("\tnúmero Reviews Errados = ").append(numeroReviewsErrados).append("\n")
          .append("\tnúmero Total Negócios = ").append(numeroTotalNegocios).append("\n")
          .append("\ttotal Negócios Avaliados = ").append(totalNegociosAvaliados).append("\n")
          .append("\ttotal Negócios Não Avaliados = ").append(totalNegociosNaoAvaliados).append("\n")
          .append("\ttotal Users = ").append(totalUsers).append("\n")
          .append("\tusers Com Reviews = ").append(usersComReviews).append("\n")
          .append("\tusers Sem Avaliacões = ").append(usersSemAvaliacoes).append("\n")
          .append("\tusers Sem Reviews = ").append(usersSemReviews).append("\n")
          .append("\treviews Com Zero Impacto = ").append(reviewsComZeroImpacto).append("\n");

        return sb.toString();
    }

    /**
     * Converte o modulo estatisticas em string para mostrar a opção 2
     * @return String
     */
    public String toString2(){
        StringBuilder sb = new StringBuilder();
        sb.append("reviews Por Mes = ").append(reviewsPorMes).append("\n")
          .append("classificacao Por Mes = ").append(classificacaoPorMes).append("\n")
          .append("media Global = ").append(mediaGlobal).append("\n")
          .append("avaliadores Por Mes = ").append(avaliadoresPorMes).append("\n");

        return sb.toString();
    }

    /**
     *
     * @return copia de um Modulo Estatisticas
     */
    public IModuloEstatisticas clone(){
        return new ModuloEstatisticas(this);
    }
}




