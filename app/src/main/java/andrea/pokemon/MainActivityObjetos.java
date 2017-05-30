package andrea.pokemon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import andrea.pokemon.models.Objetos;
import andrea.pokemon.models.Objetos_Respuesta;
import andrea.pokemon.pokeapi.PokeapiServiceObjetos;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityObjetos extends AppCompatActivity implements View.OnClickListener
{
    private static final String TAG = "POKEDEX";

    private Retrofit retrofit;

    private RecyclerView recyclerView;
    private Lista_Objetos_Adapter listaItemsAdapter;

    private int offset;

    private boolean aptoParaCargar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objetos);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        listaItemsAdapter = new Lista_Objetos_Adapter(this);
        recyclerView.setAdapter(listaItemsAdapter);
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

    private void obtenerDatos(int offset) {
        PokeapiServiceObjetos service = retrofit.create(PokeapiServiceObjetos.class);
        Call<Objetos_Respuesta> itemsRespuestaCall = service.obtenerListaItems(20, offset);

        itemsRespuestaCall.enqueue(new Callback<Objetos_Respuesta>() {
            @Override
            public void onResponse(Call<Objetos_Respuesta> call, Response<Objetos_Respuesta> response) {
                aptoParaCargar = true;
                if (response.isSuccessful()) {

                    Objetos_Respuesta itemsRespuesta = response.body();
                    ArrayList<Objetos> listaItems = itemsRespuesta.getResults();

                    listaItemsAdapter.adicionarListaItems(listaItems);

                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Objetos_Respuesta> call, Throwable t) {
                aptoParaCargar = true;
                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });
    }

    public void onClick(View v)
    {
        Intent intent = new Intent(MainActivityObjetos.this, MainActivity.class);
        startActivity(intent);
    }
}