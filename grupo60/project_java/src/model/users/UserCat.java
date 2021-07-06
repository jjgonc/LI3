package model.users;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Classe que compõe um catálogo de users.
 */
public class UserCat implements IUserCat, Serializable {
    private Map<String, User> catUser;  //userId é a key e o proprio user o value

    /**
     * Construtor vazio de um catálogo de user.
     */
    public UserCat () {
        this.catUser = new HashMap<>();
    }

    /**
     * Construtor parametrizado de uma catálogo de user.
     * @param novo O catálogo novo.
     */
    public UserCat (Map <String, User> novo) {
        this.catUser = novo;
    }

    /**
     * Construtor por cópia de uma catálogo de users.
     * @param u O catálogo.
     */
    public UserCat (UserCat u) {
        this.catUser = u.getCatUser();
    }


    /**
     * Obter um catálogo de users.
     * @return O catálogo de users.
     */
    public Map<String, User> getCatUser() {
        Map <String, User> res = new HashMap<>();
        for (Map.Entry<String, User> it : this.catUser.entrySet()) {
            res.put(it.getKey(), it.getValue().clone());
        }
        return res;
    }

    /**
     * Definir um novo catálogo de users.
     * @param catUser O novo catálogo de users.
     */
    public void setCatUser(Map<String, User> catUser) {
        this.catUser = catUser;
    }


    /**
     * Obter um clone de uma catálgo de users.
     * @return O clone do catálogo.
     */
    public UserCat clone () {
        return new UserCat(this);
    }

    /**
     * Verificar a igualdade com um objeto.
     * @param o O objeto a verificar.
     * @return A validade da igualdade.
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCat userCat = (UserCat) o;
        return this.catUser.equals(userCat.getCatUser());
    }

    /**
     * Passar um catálogo de users para o formato de string.
     * @return A string do catálogo.
     */
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserCat{");
        sb.append("catUser=").append(catUser);
        sb.append('}');
        return sb.toString();
    }

    /**
     * Remover um user do catálogo.
     * @param userId O user id do user a remover.
     * @return Em caso de sucesso, retorna true.
     */
    public boolean removeUser (String userId) {
        User u = null;
        u = this.catUser.remove(userId);
        return (u != null);
    }

    /**
     * Adicionar um novo user ao catálogo.
     * @param u O user a adicionar.
     */
    public void addUser (User u) {
        this.catUser.put(u.getUser_id(), u.clone());
    }

    //recebe o ultimo campo como uma string! (tal como tiramos do ultimo campo de cada linha)

    /**
     * Adicionar um user ao catálogo. É usado pelo parser após verificar se um negócio é válido.
     * @param uID O user id.
     * @param uName O nome do user.
     * @param friends Os amigos do user.
     */
    public void addUserParametros (String uID, String uName, String friends,int inclui_amigos) {
        User u;
        if (inclui_amigos == 1){
            String [] categorias = friends.split(",");
            List<String> res = new ArrayList<>();
            for (String s : categorias) {
                res.add(s);
            }
            u = new User(uID, uName, res);
        }
        else {
            u = new User(uID, uName, null);
        }
        this.catUser.put(uID,u.clone());
    }
}
