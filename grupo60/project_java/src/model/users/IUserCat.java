package model.users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Interface para o catálogo de users.
 */
public interface IUserCat {

    public Map<String, User> getCatUser();

    public void setCatUser(Map<String, User> catUser);

    public boolean removeUser (String userId);

    public void addUser (User u);

    public void addUserParametros (String uID, String uName, String friends,int inclui_amigos);

    public UserCat clone ();

    public boolean equals(Object o);

    public String toString();
}
