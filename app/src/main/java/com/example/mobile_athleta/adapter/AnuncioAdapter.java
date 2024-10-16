package com.example.mobile_athleta.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobile_athleta.R;
import com.example.mobile_athleta.models.Forum;
import com.example.mobile_athleta.models.Produto;

import java.util.List;

public class AnuncioAdapter extends RecyclerView.Adapter<AnuncioAdapter.AnuncioViewHolder> {

    private List<Produto> produtoList;

    public AnuncioAdapter(List<Produto> produtoList) {
        this.produtoList = produtoList;
    }

    @NonNull
    @Override
    public AnuncioAdapter.AnuncioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_anuncio, parent, false);
        return new AnuncioAdapter.AnuncioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnuncioAdapter.AnuncioViewHolder holder, int position) {
        Produto produto = produtoList.get(position);
        holder.bind(produto);
    }

    @Override
    public int getItemCount() {
        return produtoList.size();
    }

    public static class AnuncioViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagem_produto;
        private TextView titulo_anuncio, anunciante, preco;

        private Context context;

        public AnuncioViewHolder(@NonNull View itemView) {
            super(itemView);
            imagem_produto = itemView.findViewById(R.id.imagem_produto_anuncio);
            titulo_anuncio = itemView.findViewById(R.id.titulo_anuncio);
            anunciante = itemView.findViewById(R.id.autor_anuncio);
            preco = itemView.findViewById(R.id.preco);
            context = itemView.getContext();
        }

        public void bind(Produto produto) {
            String imageUrl = produto.getImage();
            Glide.with(itemView.getContext()).load(imageUrl).into(imagem_produto);
            titulo_anuncio.setText(produto.getTitle());
            anunciante.setText(produto.getDescription());
            preco.setText(String.valueOf(produto.getPrice()));
        }
    }
}
