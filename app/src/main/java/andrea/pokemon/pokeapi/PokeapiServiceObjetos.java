package andrea.pokemon.pokeapi;

import andrea.pokemon.models.Objetos_Respuesta;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by andrea on 29/05/17.
 */

public interface PokeapiServiceObjetos {
    @GET("item")
    Call<Objetos_Respuesta> obtenerListaItems(@Query("limit") int limit, @Query("offset") int offset);
}
