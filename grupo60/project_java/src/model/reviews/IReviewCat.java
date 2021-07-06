package model.reviews;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Interface para o catálogo de reviews.
 */
public interface IReviewCat {

    public Map<String, Review> getCatRev() ;

    public void setCatRev(Map<String, Review> catR);

    public boolean removeRev (String revID);

    public void addReview (Review r);

    public void addRevParametros (String rID, String userID, String busId, String stars, String useful, String funny, String cool, String date, String text);

    public ReviewCat clone ();

    public boolean equals(Object o);

    public String toString();
}
