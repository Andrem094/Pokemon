package andrea.pokemon.pokeapi;

import andrea.pokemon.models.Pokemon;
import andrea.pokemon.models.Pokemon_Respuesta;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by andrea on 29/05/17.
 */

public interface PokeapiService {

    @GET("pokemon")
    Call<Pokemon_Respuesta> obtenerListaPokemon(@Query("limit") int limit, @Query("offset") int offset);

    @GET("pokemon/{id}/")
    Call<Pokemon> obtenerPokemon(@Path("id")int id);

}
