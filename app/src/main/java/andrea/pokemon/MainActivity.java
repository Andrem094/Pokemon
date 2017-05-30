package andrea.pokemon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import andrea.pokemon.models.Pokemon;
import andrea.pokemon.models.Pokemon_Respuesta;
import andrea.pokemon.pokeapi.PokeapiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class MainActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private static final String TAG = "POKEDEX";
    private RecyclerView recyclerView;
    private Lista_Pokemon_Adapter listaPokemonAdapter;
    private int offset;
    private boolean aptoParaCargar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        listaPokemonAdapter = new Lista_Pokemon_Adapter(this);
        recyclerView.setAdapter(listaPokemonAdapter);

        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (aptoParaCargar) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            Log.i(TAG, " Llegamos al final.");

                            aptoParaCargar = false;
                            offset += 20;
                            obtenerDatos(offset);
                        }
                    }
                }
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        aptoParaCargar = true;
        offset = 0;
        obtenerDatos(offset);
    }

    public void obtenerDatos(int offset) {
        PokeapiService service = retrofit.create(PokeapiService.class);
        Call<Pokemon_Respuesta> pokemonRespuestaCall = service.obtenerListaPokemon(20, offset);

        pokemonRespuestaCall.enqueue(new Callback<Pokemon_Respuesta>() {
            @Override
            public void onResponse(Call<Pokemon_Respuesta> call, Response<Pokemon_Respuesta> response) {
                aptoParaCargar = true;
                if (response.isSuccessful()) {
                    Pokemon_Respuesta pokemonRespuesta = response.body();
                    ArrayList<Pokemon> listaPokemon = pokemonRespuesta.getResults();

                    listaPokemonAdapter.adicionarListaPokemon(listaPokemon);
                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Pokemon_Respuesta> call, Throwable t) {
                aptoParaCargar = true;
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void onClick(View v)
    {
        Intent intent = new Intent(MainActivity.this, MainActivityObjetos.class);
        startActivity(intent);
    }

}
