package model;

import model.business.IBusinessCat;
import model.estatisticas.IModuloEstatisticas;
import model.reviews.IReviewCat;
import model.users.IUserCat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Inteface para GestReviews
 */
public interface IGestReviews {

    public IBusinessCat getCatBus();

    public void setCatBus(IBusinessCat catBus);

    public IReviewCat getCatRev();

    public void setCatRev(IReviewCat catRev);

    public IUserCat getCatUsers();

    public void setCatUsers(IUserCat catUsers);

    public List<String> query1 ();

    public Map.Entry<Integer, Integer> query2 (int mes, int ano);

    public Set<Map.Entry<String, String>> query3 (String user_id);

    public Set<Map.Entry<String,String>> query4 (String businessId);

    public Set<AbstractMap.SimpleEntry<String,Integer>> query5 (String user_id);

    public Set<Map.Entry<Integer,List<Map.Entry<String,Integer>>>> query6(int x);

    public Map<String,List<String>> query7 ();

    public Set<Map.Entry<String,Integer>> query8(int x);

    public List<Map.Entry<Double, String>> query9 (String businessId, int x) ;

    Map<String,Map<String,Map<String,Double>>> query10();

    public void loadModel(String fileUsers, String fileBus,String fileRevs,int incluir_amigos) throws IOException;

    public IModuloEstatisticas getEstatisticas();

    public void setMe(IModuloEstatisticas me);

    public boolean validaUserID (String userID);

    public boolean validaBusinessID (String businessID);

    public boolean validaReviewID (String reviewID);

    public void saveModel(String fileName) throws IOException, FileNotFoundException;

    public void setNomeFicheirosE (List<String> nomeFicheiros);


}
