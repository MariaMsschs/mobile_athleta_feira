package com.example.mobile_athleta.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobile_athleta.R;
import com.example.mobile_athleta.models.Anuncio;
import com.example.mobile_athleta.service.FotoFirebaseImpl;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AnuncioAdapter extends RecyclerView.Adapter<AnuncioAdapter.AnuncioViewHolder> {

    private List<Anuncio> anuncioList;
    private OnItemClickListener onItemClickListener;

    public void setListaFiltrada(List<Anuncio> anuncioList) {
        this.anuncioList = anuncioList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Anuncio anuncio);
    }


    public AnuncioAdapter(List<Anuncio> anuncioList, OnItemClickListener onItemClickListener) {
        this.anuncioList = anuncioList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public AnuncioAdapter.AnuncioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_anuncio, parent, false);
        return new AnuncioAdapter.AnuncioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnuncioAdapter.AnuncioViewHolder holder, int position) {
        Anuncio anuncio = anuncioList.get(position);
        holder.bind(anuncio, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return anuncioList.size();
    }

    public static class AnuncioViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagem_produto;
        private TextView titulo_anuncio, anunciante, preco;

        FotoFirebaseImpl fotoFirebaseImpl = new FotoFirebaseImpl();

        public AnuncioViewHolder(@NonNull View itemView) {
            super(itemView);
            imagem_produto = itemView.findViewById(R.id.imagem_produto_anuncio);
            titulo_anuncio = itemView.findViewById(R.id.titulo_anuncio);
            anunciante = itemView.findViewById(R.id.autor_anuncio);
            preco = itemView.findViewById(R.id.preco);
        }

        public void bind(final Anuncio anuncio, final OnItemClickListener listener) {
            fotoFirebaseImpl.recuperarImagem(imagem_produto, anuncio.getImagem());
            Picasso.get().load(anuncio.getImagem()).into(imagem_produto);
            titulo_anuncio.setText(anuncio.getNome());
            anunciante.setText(anuncio.getDescricao());
            String valor = "R$" + String.valueOf(anuncio.getPreco());
            preco.setText(valor);

            itemView.setOnClickListener(v -> listener.onItemClick(anuncio));
        }
    }
}
