package model.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Interface para o catálogo de businesses.
 */
public interface IBusinessCat {

    public Map<String, Business> getCatBus();

    public void setCatBus(Map<String, Business> catB);

    public boolean removeBus (String busID);

    public void addBusiness (Business b);

    //recebe o ultimo campo como uma string! (tal como tiramos do ultimo campo de cada linha)
    public void addBusParametros (String bID, String bName, String city, String state, String cat);

    public BusinessCat clone ();

    public boolean equals(Object o);

    public String toString();
}
