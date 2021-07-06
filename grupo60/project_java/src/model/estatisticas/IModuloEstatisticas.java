package model.estatisticas;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Interface para o módulo de estatísticas.
 */
public interface IModuloEstatisticas {

    public List<Integer> devolve12zeros();

    public List<Double> devolve12zerosdouble();

    //getters
    public int getUsersComReviews();

    public int getTotalNegociosAvaliados();

    public Double getMediaGlobal();

    public int getNumeroReviewsErrados();

    public int getNumeroTotalNegocios();

    public int getReviewsComZeroImpacto();

    public int getTotalNegociosNaoAvaliados();

    public int getTotalUsers();

    public int getUsersSemAvaliacoes();

    public int getUsersSemReviews();

    public List<Double> getClassificacaoPorMes();

    public List<Integer> getAvaliadoresPorMes();

    public List<Integer> getReviewsPorMes();

    public List<String> getNomesFicheiros();


    //setters


    public void setUsersComReviews(int usersComReviews);

    public void setTotalNegociosAvaliados(int totalNegociosAvaliados);

    public void setAvaliadoresPorMes(List<Integer> avaliadoresPorMes);

    public void setClassificacaoPorMes(List<Double> classificacaoPorMes);

    public void setMediaGlobal(Double mediaGlobal);

    public void setNomesFicheiros(List<String> nomesFicheiros);

    public void setNumeroReviewsErrados(int numeroReviewsErrados);

    public void setNumeroTotalNegocios(int numeroTotalNegocios);

    public void setReviewsComZeroImpacto(int reviewsComZeroImpacto);

    public void setReviewsPorMes(List<Integer> reviewsPorMes);

    public void setTotalNegociosNaoAvaliados(int totalNegociosNaoAvaliados);

    public void setTotalUsers(int totalUsers);

    public void setUsersSemAvaliacoes(int usersSemAvaliacoes);

    public void setUsersSemReviews(int usersSemReviews);

    public void addReviewErrado();

    public void addNegocioAvaliado();

    public void addUserReviews();

    public void  addReviewZeroImpacto();

    public void addReviewMes(int mes);

    public void addClassMes(int mes, double stars);

    public void calculaMedias();

    public void usersAvaliamPorMes(List<Set<String>>idPorMes);

    public void setUsersSemReviews();

    public void setTotalNegociosNaoAvaliados();

    public void addUser();

    public  void addBus();

    public IModuloEstatisticas clone();

    public String toString();

    public String toString2();

    }
