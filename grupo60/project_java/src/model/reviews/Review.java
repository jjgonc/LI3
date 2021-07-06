package model.reviews;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Classe que compõe uma review.
 */
public class Review implements IReview, Serializable {
    private String review_id;
    private String rev_user_id;
    private String rev_business_id;
    private double stars;
    private int useful;
    private int funny;
    private int cool;
    private LocalDateTime date;
    private String text;


    /**
     * Construtor vazio de Review.
     */
    public Review() {
        this.review_id = "";
        this.rev_user_id = "";
        this.rev_business_id = "";
        this.stars = 0.0;
        this.useful = 0;
        this.funny = 0;
        this.cool = 0;
        this.date = LocalDateTime.now();
        this.text = "";
    }

    /**
     * Construtor parametrizado de Reviews.
     * @param review_id O review id.
     * @param user_id O user id.
     * @param business_id O business id.
     * @param stars O número de stars associado.
     * @param useful O valor atribuido a useful.
     * @param funny O valor valor atribuido a funny.
     * @param cool O valor atribuido a cool.
     * @param date A data da review.
     * @param text O texto da review.
     */
    public Review(String review_id, String user_id, String business_id, double stars, int useful, int funny, int cool, LocalDateTime date, String text) {
        this.review_id = review_id;
        this.rev_user_id = user_id;
        this.rev_business_id = business_id;
        this.stars = stars;
        this.useful = useful;
        this.funny = funny;
        this.cool = cool;
        this.date = date;
        this.text = text;
    }

    /**
     * Constutor por cópia de Review.
     * @param r A review.
     */
    public Review (Review r) {
        this.review_id = r.getReview_id();
        this.rev_user_id = r.getUser_id();
        this.review_id = r.getReview_id();
        this.rev_business_id = r.getBusiness_id();
        this.stars = r.getStars();
        this.useful = r.getUseful();
        this.funny = r.getFunny();
        this.cool = r.getCool();
        this.date = r.getDate();
        this.text = r.getText();
    }


    /**
     * Obter o id de uma review.
     * @return O id da review.
     */
    public String getReview_id() {
        return this.review_id;
    }

    /**
     * O setter do id de uma review.
     * @param review_id O novo id da review.
     */
    public void setReview_id(String review_id) {
        this.review_id = review_id;
    }

    /**
     * Obter o id do user que fez a review.
     * @return O id do user.
     */
    public String getUser_id() {
        return this.rev_user_id;
    }

    /**
     * Definir o id do user que fez a review.
     * @param user_id O id do user.
     */
    public void setUser_id(String user_id) {
        this.rev_user_id = user_id;
    }

    /**
     * Obter o id do business que se fez a review.
     * @return O id do business.
     */
    public String getBusiness_id() {
        return this.rev_business_id;
    }

    /**
     * Definir o id do business da review.
     * @param business_id O novo id do business.
     */
    public void setBusiness_id(String business_id) {
        this.rev_business_id = business_id;
    }

    /**
     * Obter o número de stars que se classificou a review.
     * @return O número de stars.
     */
    public double getStars() {
        return this.stars;
    }

    /**
     * Definir o número de stars de uma review.
     * @param stars O número a atribuir.
     */
    public void setStars(double stars) {
        this.stars = stars;
    }

    /**
     * Obter a classificação de useful.
     * @return A classificação.
     */
    public int getUseful() {
        return this.useful;
    }

    /**
     * Definir o valor de useful numa review.
     * @param useful O novo valor.
     */
    public void setUseful(int useful) {
        this.useful = useful;
    }

    /**
     * Obter o valor de funny numa review.
     * @return O valor.
     */
    public int getFunny() {
        return this.funny;
    }

    /**
     * Definir o valor de funny numa review.
     * @param funny O novo valor.
     */
    public void setFunny(int funny) {
        this.funny = funny;
    }

    /**
     * Obter o valor de cool numa review.
     * @return O valor.
     */
    public int getCool() {
        return this.cool;
    }

    /**
     * Definir o valor de funny numa review.
     * @param cool O novo valor.
     */
    public void setCool(int cool) {
        this.cool = cool;
    }

    /**
     * Obter a data de uma review.
     * @return A data da review.
     */
    public LocalDateTime getDate() {
        return this.date;
    }

    /**
     * Definir a data de uma review.
     * @param date A data da review.
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Obter o texto associado à review.
     * @return O texto associado.
     */
    public String getText() {
        return this.text;
    }

    /**
     * Definir o texto para uma review.
     * @param text O texto a associar à review.
     */
    public void setText(String text) {
        this.text = text;
    }


    /**
     * Verificar a igualdade de reviews.
     * @param o O objeto a comparar.
     * @return O valor da comparação.
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review rev = (Review) o;
        return this.review_id.equals(rev.getReview_id())
                && this.rev_user_id.equals(rev.getUser_id())
                && this.rev_business_id.equals(rev.getBusiness_id())
                && this.stars == rev.getStars()
                && this.useful == rev.getUseful()
                && this.funny == rev.getFunny()
                && this.cool == rev.getCool()
                && this.date.equals(rev.getDate())
                && this.text.equals(rev.getText());
    }

    /**
     * Obter uma cópia da review.
     * @return A cópia da review.
     */
    public Review clone () {
        return new Review(this);
    }

    /**
     * Passar uma review para string.
     * @return A review em formato de string.
     */
    public String toString() {
        final StringBuilder sb = new StringBuilder("Review{");
        sb.append("review_id='").append(review_id).append('\'');
        sb.append(", user_id='").append(rev_user_id).append('\'');
        sb.append(", business_id='").append(rev_business_id).append('\'');
        sb.append(", stars=").append(stars);
        sb.append(", useful=").append(useful);
        sb.append(", funny=").append(funny);
        sb.append(", cool=").append(cool);
        sb.append(", date=").append(date);
        sb.append(", text='").append(text).append('\'');
        sb.append('}');
        return sb.toString();
    }

    /**
     * O método de gerar a função de hash.
     * @return O novo hash.
     */
    public int hashCode() {

        return Arrays.hashCode(new Object[] {this.review_id, this.rev_user_id, this.rev_business_id});
    }

}
