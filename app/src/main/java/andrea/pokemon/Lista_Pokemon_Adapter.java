package andrea.pokemon;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import andrea.pokemon.models.Pokemon;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by andrea on 29/05/17.
 */

public class Lista_Pokemon_Adapter  extends RecyclerView.Adapter<Lista_Pokemon_Adapter.ViewHolder> {

    private ArrayList<Pokemon> dataset;
    private Context context;
    private Retrofit retrofit;
    private static final String TAG="POKEDEX";
    public Lista_Pokemon_Adapter(Context context) {
        this.context = context;
        dataset = new ArrayList<>();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Pokemon p = dataset.get(position);
        holder.nombreTextView.setText(p.getName());
        Glide.with(context)
                .load("http://pokeapi.co/media/sprites/pokemon/"+p.getNumber()+".png")
                .centerCrop()
                .crossFade()
                .placeholder(R.drawable.pokebola_pokeball)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.fotoImageView);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        holder.fotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context,Descripcion.class);
                intent.putExtra("id",p.getNumber());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void adicionarListaPokemon(ArrayList<Pokemon> listaPokemon) {
        dataset.addAll(listaPokemon);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView fotoImageView;
        private TextView nombreTextView;
        public ViewHolder(View itemView) {
            super(itemView);

            fotoImageView = (ImageView) itemView.findViewById(R.id.fotoImageView);
            nombreTextView = (TextView) itemView.findViewById(R.id.nombreTextView);

        }

    }

}

