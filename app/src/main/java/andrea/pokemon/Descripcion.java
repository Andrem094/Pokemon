package andrea.pokemon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import andrea.pokemon.models.Pokemon;
import andrea.pokemon.pokeapi.PokeapiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Descripcion extends AppCompatActivity {
    private TextView codigo;
    private TextView nombreTextView;
    private TextView experiencia;
    private TextView habilidad;
    private TextView tipo;
    private TextView altura;
    private TextView peso;
    private Lista_Pokemon_Adapter listaPokemonAdapter;
    private Retrofit retrofit;
    private static final String TAG="POKEDEX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion);
        nombreTextView = (TextView) findViewById(R.id.nombreTextView);
        experiencia = (TextView)findViewById(R.id.experiencia);
        habilidad= (TextView)findViewById(R.id.habilidad);
        tipo = (TextView)findViewById(R.id.tipo);
        altura= (TextView)findViewById(R.id.altura);
        peso = (TextView)findViewById(R.id.peso);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null){
            int idPoke= (int) bundle.get("id");
            obtenerDatosPokemon(idPoke);
        }


    }

    public void obtenerDatosPokemon(int id) {
        PokeapiService service = retrofit.create(PokeapiService.class);
        Call<Pokemon> pokemon = service.obtenerPokemon(id);

        pokemon.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                if(response.isSuccessful()){
                    Pokemon pokemon = response.body();
                    nombreTextView.setText(pokemon.getName());
                    experiencia.setText(pokemon.getBase_experience());
                    habilidad.setText(pokemon.getAbilities().get(0).getAbility().getName());
                    tipo.setText(pokemon.getTypes().get(0).getType().getName());
                    altura.setText(pokemon.getHeight().toString());
                    peso.setText(pokemon.getWeight().toString());

                }else
                {
                    Log.e(TAG, "onResponse: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                Log.e(TAG," onFailure: "+t.getMessage());
            }
        });
    }
}