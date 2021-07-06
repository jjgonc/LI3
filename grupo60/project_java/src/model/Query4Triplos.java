package model;

import java.util.Objects;

/**
 * Classe que permite usar triplos na query4
 */
public class Query4Triplos {
    private int n_vezes_avaliado;
    private int n_users_diferentes;
    private double soma_classificacao;
    private double media_classificacao;


    public Query4Triplos () {
        this.n_vezes_avaliado = 0;
        this.n_users_diferentes = 0;
        this.soma_classificacao = 0.0;
        this.media_classificacao = 0.0;
    }

    public Query4Triplos (int n_vezes_avaliado,double soma_classificacao,double media_classificacao){
        this.n_vezes_avaliado = n_vezes_avaliado;
        this.soma_classificacao = soma_classificacao;
        this.media_classificacao = media_classificacao;
    }

    public Query4Triplos(int n_vezes_avaliado, int n_users_diferentes, double soma_classificacao, double media_classificacao) {
        this.n_vezes_avaliado = n_vezes_avaliado;
        this.n_users_diferentes = n_users_diferentes;
        this.soma_classificacao = soma_classificacao;
        this.media_classificacao = media_classificacao;
    }

    public Query4Triplos (Query4Triplos q) {
        this.n_users_diferentes = q.getN_users_diferentes();
        this.n_vezes_avaliado = q.getN_vezes_avaliado();
        this.soma_classificacao = q.getSoma_classificacao();
        this.media_classificacao = q.getMedia_classificacao();
    }

    public int getN_vezes_avaliado() {
        return n_vezes_avaliado;
    }

    public void setN_vezes_avaliado(int n_vezes_avaliado) {
        this.n_vezes_avaliado = n_vezes_avaliado;
    }

    public int getN_users_diferentes() {
        return n_users_diferentes;
    }

    public void setN_users_diferentes(int n_users_diferentes) {
        this.n_users_diferentes = n_users_diferentes;
    }

    public double getSoma_classificacao() {
        return soma_classificacao;
    }

    public void setSoma_classificacao(double soma_classificacao) {
        this.soma_classificacao = soma_classificacao;
    }

    public double getMedia_classificacao() {
        return media_classificacao;
    }

    public void setMedia_classificacao(double media_classificacao) {
        this.media_classificacao = media_classificacao;
    }

    public Query4Triplos clone () {
        return new Query4Triplos(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Query4Triplos that = (Query4Triplos) o;
        return n_vezes_avaliado == that.n_vezes_avaliado && n_users_diferentes == that.n_users_diferentes && Double.compare(that.soma_classificacao, soma_classificacao) == 0 && Double.compare(that.media_classificacao, media_classificacao) == 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("\tNº de vezes avaliado = ").append(n_vezes_avaliado);
        sb.append("  Nº de users diferentes = ").append(n_users_diferentes);
        sb.append(", Soma classificacao = ").append(soma_classificacao);
        sb.append(", Media classificacao = ").append(media_classificacao);
        sb.append("\n");
        return sb.toString();
    }
}
