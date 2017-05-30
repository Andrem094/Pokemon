package andrea.pokemon.models;

/**
 * Created by andrea on 29/05/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Habilidad {

    @SerializedName("is_hidden")
    @Expose
    private Boolean isHidden;
    @SerializedName("slot")
    @Expose
    private Integer slot;
    @SerializedName("ability")
    @Expose
    private Habilidad_Pokemon ability;

    public Boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public Habilidad_Pokemon getAbility() {
        return ability;
    }

    public void setAbility(Habilidad_Pokemon ability) {
        this.ability = ability;
    }
}
