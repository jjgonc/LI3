package model;

/**
 * Classe que permite usar triplos na query3
 */
public class Query3Triplos {
    private int n_reviews_user;
    private int n_negocios_distintos;
    private double soma_reviews;
    private double notaMedia;


    public Query3Triplos () {
        this.n_reviews_user = 0;
        this.n_negocios_distintos = 0;
        this.soma_reviews = 0.0;
        this.notaMedia = 0.0;
    }

    public Query3Triplos(Query3Triplos query3Triplos) {
        this.n_reviews_user = query3Triplos.getN_reviews_user();
        this.n_negocios_distintos = query3Triplos.getN_negocios_distintos();
        this.soma_reviews = query3Triplos.getSoma_reviews();
        this.notaMedia = query3Triplos.getNotaMedia();
    }

    public Query3Triplos (int n_reviews_user, int n_negocios_distintos, double soma, double notaMedia) {
        this.n_reviews_user = n_reviews_user;
        this.n_negocios_distintos = n_negocios_distintos;
        this.soma_reviews = soma;
        this.notaMedia = notaMedia;
    }

    public int getN_reviews_user() {
        return n_reviews_user;
    }

    public void setN_reviews_user(int n_reviews_user) {
        this.n_reviews_user = n_reviews_user;
    }

    public int getN_negocios_distintos() {
        return n_negocios_distintos;
    }

    public void setN_negocios_distintos(int n_negocios_distintos) {
        this.n_negocios_distintos = n_negocios_distintos;
    }

    public double getNotaMedia() {
        return notaMedia;
    }

    public void setNotaMedia(double notaMedia) {
        this.notaMedia = notaMedia;
    }

    public double getSoma_reviews() {
        return soma_reviews;
    }

    public void setSoma_reviews(double soma_reviews) {
        this.soma_reviews = soma_reviews;
    }

    public Query3Triplos clone () {
        return new Query3Triplos(this);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("\tNº de reviews que o user fez = ").append(n_reviews_user);
        sb.append(", Nº de negocios distintos = ").append(n_negocios_distintos);
        sb.append(", Nota média = ");
        String aux = String.format("%.2f",notaMedia);
        sb.append(aux);
        return sb.toString();
    }
}

