package model.users;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Classe que compõe um user.
 */
public class User implements IUser, Serializable {
    private String user_id;
    private String user_name;
    private List<String> friends;

    /**
     * Cosntrutor parametrizado de user.
     * @param user_id O id do user.
     * @param user_name O nome do user.
     * @param friends Os amigos do user.
     */
    public User(String user_id, String user_name, List<String> friends) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.friends = friends;
    }

    /**
     * Construtor vazio de user.
     */
    public User () {
        this.user_id = "";
        this.user_name = "";
        this.friends = new ArrayList<>();
    }

    /**
     * Construtor por cópia de user.
     * @param u O user a copiar.
     */
    public User (User u) {
        this.user_id = u.getUser_id();
        this.user_name = u.getUser_name();
        this.friends = u.getFriends();
    }

    /**
     * Obter o user id.
     * @return O id do user.
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * Definir o id de um user.
     * @param user_id O novo id.
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    /**
     * Obter o nome de um user.
     * @return O nome do user.
     */
    public String getUser_name() {
        return user_name;
    }

    /**
     * Definir o nome de um user.
     * @param user_name O novo nome do user.
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    /**
     * Obter a lista de amigos de um user.
     * @return A lista de amigos.
     */
    public List<String> getFriends() {
        return this.friends;
    }

    /**
     * Definir a lista de amigos de um user.
     * @param friends A nova lista dos amigos.
     */
    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    /**
     * Obter uma cópia de uma user.
     * @return A cópia do user.
     */
    public User clone () {
        return new User(this);
    }


    /**
     * Verificar a igualdade de um user e um objeto.
     * @param o O objeto a comparar.
     * @return A validade da igualdade.
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return this.user_id.equals(user.getUser_id()) && this.user_name.equals(user.getUser_name()) && this.friends.equals(user.getFriends());
    }

    /**
     * Converter um user para o formato de string.
     * @return A string com as informações do user.
     */
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("user_id='").append(user_id).append('\'');
        sb.append(", user_name='").append(user_name).append('\'');
        sb.append(", friends='").append(friends).append('\'');
        sb.append('}');
        return sb.toString();
    }

    /**
     * Obter a função de hash para um user.
     * @return A hash function.
     */
    public int hashCode() {
        return Arrays.hashCode(new Object[] {this.user_id, this.user_name});
    }
}
