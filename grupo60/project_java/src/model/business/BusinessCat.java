package model.business;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe que compõe um catálogo de businesses.
 */
public class BusinessCat implements IBusinessCat, Serializable {
    private Map<String, Business> catBus;   //bus_id é a key e o próprio business o value

    /**
     * Construtor vazio do Catálogo de businesses.
     */
    public BusinessCat () {
        this.catBus = new HashMap<>();
    }

    /**
     * Construtor por cópia de um catálogo de Business.
     * @param b O business a copiar.
     */
    public BusinessCat (BusinessCat b) {
        this.catBus = b.getCatBus();
    }

    /**
     * Construtor parametrizado de um catálogo de businesses.
     * @param novo novo catalogo de businesses
     */
    public BusinessCat (Map<String, Business> novo) {
        this.catBus = novo;
    }

    /**
     * Obter o catálogo de businesses.
     * @return O catálogo de businesses.
     */
    public Map<String, Business> getCatBus() {
        Map <String, Business> res = new HashMap<>();
        for (Map.Entry<String, Business> it : this.catBus.entrySet()) {
            res.put(it.getKey(), it.getValue().clone());
        }
        return res;
    }

    /**
     * Definir um catálogo de businesses.
     * @param catB O novo catálogo.
     */
    public void setCatBus(Map<String, Business> catB) {
        this.catBus = catB;
    }

    /**
     * Obter uma cópia de um catálogo de businesses.
     * @return O novo catálogo.
     */
    public BusinessCat clone () {
        return new BusinessCat(this);
    }

    /**
     * Verificar a igualdade entre dois catáogos de businesses.
     * @param o O objeto a comparar.
     * @return O valor associado à comparação.
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessCat that = (BusinessCat) o;
        return this.getCatBus().equals(that.getCatBus());
    }


    /**
     * Passar para o formato de String um catálogo de businesses.
     * @return A string do catálogo.
     */
    public String toString() {
        final StringBuilder sb = new StringBuilder("BusinessCat{");
        sb.append("catBus=").append(catBus);
        sb.append('}');
        return sb.toString();
    }

    /**
     * Remover um negócio do catálogo.
     * @param busID O Business id a remover.
     * @return Caso tenha removido com sucesso, retorna true.
     */
    public boolean removeBus (String busID) {
        Business b = null;
        b = this.catBus.remove(busID);
        if (b == null) {
            return false;
        }
        return true;
    }

    /**
     * Adicionar um negócio ao catálogo.
     * @param b O negócio a adicionar.
     */
    public void addBusiness (Business b) {
        this.catBus.put(b.getBus_id(), b.clone());
    }

    /**
     * Adicionar um negócio ao catálogo. É usado pelo parser após verificar se um negócio é válido.
     * @param bID O id do negócio.
     * @param bName O nome do negócio.
     * @param city A cidade do negócio.
     * @param state O estado de negócio.
     * @param cat O conjunto das categorias em que se insere.
     */
    public void addBusParametros (String bID, String bName, String city, String state, String cat) {    //recebe o ultimo campo como uma string! (tal como tiramos do ultimo campo de cada linha)
        String [] categorias = cat.split(",");
        List<String> res = new ArrayList<>();
        for (String s : categorias) {
            res.add(s);
        }
        Business b = new Business(bID, bName, city, state, res);
        this.catBus.put(bID, b.clone());
    }
}
