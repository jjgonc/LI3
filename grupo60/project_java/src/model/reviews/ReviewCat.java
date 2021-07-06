package model.reviews;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Classe que compõe um catálogo de reviews.
 */
public class ReviewCat implements IReviewCat, Serializable {
    private Map<String, Review> catRev;     //review_id é a key e a propria review o value

    /**
     * Construtor de cópia de uma catálogo de reviews.
     */
    public ReviewCat () {
        this.catRev = new HashMap<>();
    }

    /**
     * Construtor parametrizado de uma catálogo de review.
     * @param catRev O catálogo a passar.
     */
    public ReviewCat(Map<String, Review> catRev) {
        this.catRev = catRev;
    }

    /**
     * Construtor por cópia de um catálogo de reviews.
     * @param r O catálogo a copiar
     */
    public ReviewCat (ReviewCat r) {
        this.catRev = r.getCatRev();
    }

    /**
     * Obter o catálogo das reviews.
     * @return O catálogo.
     */
    public Map<String, Review> getCatRev() {
        Map <String, Review> res = new HashMap<>();
        for (Map.Entry<String, Review> it : this.catRev.entrySet()) {
            res.put(it.getKey(), it.getValue().clone());
        }
        return res;
    }

    /**
     * Definir um catálogo de reviews.
     * @param catR O catálogo a definir.
     */
    public void setCatRev(Map<String, Review> catR) {
        this.catRev = catR;
    }

    /**
     * Criar uma cópia de uma catálogo de reviews.
     * @return A cópia do catálogo.
     */
    public ReviewCat clone () {
        return new ReviewCat(this);
    }

    /**
     * Verificar a igualdade de dois catálogos.
     * @param o O objeto a comparar.
     * @return O valor associado à comparação.
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewCat reviewCat = (ReviewCat) o;
        return this.getCatRev().equals(reviewCat.getCatRev());
    }

    /**
     * Obter um catálogo de reviews em formato de string.
     * @return A string do catálogo.
     */
    public String toString() {
        final StringBuilder sb = new StringBuilder("ReviewCat{");
        sb.append("catRev=").append(catRev);
        sb.append('}');
        return sb.toString();
    }

    /**
     * Remover uma review do catálogo.
     * @param revID O id da review a remover.
     * @return Em caso de sucesso, retorna true.
     */
    public boolean removeRev (String revID) {
        Review r = null;
        r = this.getCatRev().remove(revID);
        return r != null;
    }

    /**
     * Adicionar uma review ao catálogo de reviews.
     * @param r A review a adicionar.
     */
    public void addReview (Review r) {
        this.catRev.put(r.getReview_id(), r.clone());
    }

    /**
     * Adicionar uma review ao catálogo. É usado pelo parser após verificar se uma review é válida.
     * @param rID O id da review.
     * @param userID O id do user.
     * @param busId O id do negócio.
     * @param stars As stars atribuidas.
     * @param useful O valor de useful.
     * @param funny O valor de funny.
     * @param cool O valor de cool.
     * @param date A data da review.
     * @param text O texto da review.
     */
    public void addRevParametros (String rID, String userID, String busId, String stars, String useful, String funny, String cool, String date, String text) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

        double starsD = Double.parseDouble(stars);
        int usefulD = Integer.parseInt(useful);
        int funnyD = Integer.parseInt(funny);
        int coolD = Integer.parseInt(cool);
        Review r = new Review(rID, userID, busId, starsD, usefulD, funnyD, coolD, dateTime, text);
        this.catRev.put(rID, r.clone());
    }


}
