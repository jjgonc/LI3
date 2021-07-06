package model.business;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface para business.
 */
public interface IBusiness {

    public String getBus_id();

    public void setBus_id(String id) ;

    public String getBus_name();

    public void setBus_name(String name);

    public String getCity();

    public void setCity(String city);

    public String getState();

    public void setState(String state);

    public List<String> getCategories();

    public void setCategories(List<String> categories);
}
