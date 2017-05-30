package andrea.pokemon.models;

import java.util.ArrayList;

/**
 * Created by andrea on 29/05/17.
 */

public class Pokemon {


    private Integer id;
    private String name;
    private String weight;
    private String height;
    private String url;
    private String base_experience;

    public String getBase_experience() {return base_experience;}

    public void setBase_experience(String base_experience) {this.base_experience = base_experience;}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public Integer getNumber() {

        String[] urlPartes = url.split("/");
        return Integer.parseInt(urlPartes[urlPartes.length - 1]);

    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setNumber(Integer number)
    {
        this.number = number;
    }

    private Integer number;

    private ArrayList<Habilidad> abilities;
    public ArrayList<Habilidad> getAbilities() {return abilities;}

    public void setAbilities(ArrayList<Habilidad> abilities) {this.abilities = abilities;}

    private ArrayList<Tipo> types;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public ArrayList<Tipo> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<Tipo> types) {
        this.types = types;
    }


}


