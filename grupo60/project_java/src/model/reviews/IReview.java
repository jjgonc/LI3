package model.reviews;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Inteface para review.
 */
public interface IReview {
    public String getReview_id();

    public void setReview_id(String review_id);

    public String getUser_id();

    public void setUser_id(String user_id);

    public String getBusiness_id();

    public void setBusiness_id(String business_id);

    public double getStars();

    public void setStars(double stars);

    public int getUseful();

    public void setUseful(int useful);

    public int getFunny();

    public void setFunny(int funny);

    public int getCool();

    public void setCool(int cool);

    public LocalDateTime getDate();

    public void setDate(LocalDateTime date);

    public String getText();

    public void setText(String text);
}
