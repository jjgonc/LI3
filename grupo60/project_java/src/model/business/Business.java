package model.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe que compõe um Business.
 */
public class Business  implements IBusiness, Comparable, Serializable {
    private String bus_id;
    private String bus_name;
    private String city;
    private String state;
    private List<String> categories;

    /**
     * Construtor vazio de Business.
     */
    public Business () {
        this.bus_id = "";
        this.bus_name = "";
        this.city = "";
        this.state = "";
        this.categories = new ArrayList<>();
    }

    /**
     * Construtor parametrizado de Business.
     * @param id O business id.
     * @param name O nome do negócio.
     * @param city A cidade do negócio.
     * @param state O estado em que se localiza o negócio.
     * @param categories As categorias em que se insere o negócio.
     */
    public Business (String id, String name, String city, String state, List<String> categories) {
        this.bus_id = id;
        this.bus_name = name;
        this.city = city;
        this.state = state;
        this.categories = categories;
    }

    /**
     * Construtor por cópia de business.
     * @param b O business a "copiar".
     */
    public Business (Business b) {
        this.bus_id = b.getBus_id();
        this.bus_name = b.getBus_name();
        this.city = b.getCity();
        this.state = b.getState();
        this.categories = b.getCategories();
    }

    /**
     * Getter de um business id.
     * @return O business id.
     */
    public String getBus_id() {
        return this.bus_id;
    }

    /**
     * Setter de um business id.
     * @param id O novo id.
     */
    public void setBus_id(String id) {
        this.bus_id = id;
    }

    /**
     * Getter do nome de um negócio.
     * @return O nome do negócio.
     */
    public String getBus_name() {
        return this.bus_name;
    }

    /**
     * Setter do nome do negócio.
     * @param name O nome a atrubuir.
     */
    public void setBus_name(String name) {
        this.bus_name = name;
    }

    /**
     * Getter da cidade do negócio.
     * @return A cidade.
     */
    public String getCity() {
        return this.city;
    }

    /**
     * Setter da cidade do negócio.
     * @param city A cidade nova.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Getter do estado do negócio.
     * @return O estado do negócio.
     */
    public String getState() {
        return this.state;
    }

    /**
     * Setter de estado de um negócio.
     * @param state O novo estado onde se localiza o negócio.
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Getter das categorias em que o negócio se insere.
     * @return A lista das categorias.
     */
    public List<String> getCategories() {
        List<String> res = new ArrayList<>();
        for (String s : categories) {
            res.add(s);
        }
        return res;
    }

    /**
     * Setter das categorias do negócio.
     * @param categories A lista das categorias em que o negócio se insere.
     */
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    /**
     * Método que compara dois objetos.
     * @param o O objeto com o qual queremos comparar.
     * @return O valor da comparação.
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Business business = (Business) o;
        return this.bus_id.equals(business.getBus_id())
                && this.bus_name.equals(business.getBus_name())
                && this.city.equals(business.getCity())
                && this.state.equals(business.getState())
                && this.categories.equals(business.getCategories());
    }

    /**
     * Método de definir a ordem para comparações.
     * @param o O objeto a comparar.
     * @return O valor assciado á comparação
     */
    public  int compareTo (Object o){
        Business b = (Business) o;
        return this.bus_id.compareTo(b.bus_id);

    }


    /**
     * Obter um objeto cópia.
     * @return O novo objeto.
     */
    public Business clone () {
        return new Business(this);
    }

    /**
     * Obter um hash code para uso em funções de hash.
     * @return O hash code.
     */
    public int hashCode() {
        return Arrays.hashCode(new Object[] {this.bus_id, this.bus_name, this.state, this.city,this.categories});
    }

    /**
     * Obter as informações de um negócio em formato String.
     * @return A string com as informações.
     */
    public String toString() {
        final StringBuilder sb = new StringBuilder("Business{");
        sb.append("id='").append(bus_id).append('\'');
        sb.append(", name='").append(bus_name).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append(", categories=").append(categories);
        sb.append('}');
        return sb.toString();
    }
}
