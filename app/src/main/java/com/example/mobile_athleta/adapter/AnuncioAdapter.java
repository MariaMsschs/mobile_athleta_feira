package com.example.mobile_athleta.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_athleta.R;
import com.example.mobile_athleta.models.Produto;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AnuncioAdapter extends RecyclerView.Adapter<AnuncioAdapter.AnuncioViewHolder> {

    private List<Produto> produtoList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Produto produto);
    }


    public AnuncioAdapter(List<Produto> produtoList, OnItemClickListener onItemClickListener) {
        this.produtoList = produtoList;
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
        Produto produto = produtoList.get(position);
        holder.bind(produto, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return produtoList.size();
    }

    public static class AnuncioViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagem_produto;
        private TextView titulo_anuncio, anunciante, preco;

        public AnuncioViewHolder(@NonNull View itemView) {
            super(itemView);
            imagem_produto = itemView.findViewById(R.id.imagem_produto_anuncio);
            titulo_anuncio = itemView.findViewById(R.id.titulo_anuncio);
            anunciante = itemView.findViewById(R.id.autor_anuncio);
            preco = itemView.findViewById(R.id.preco);
        }

        public void bind(final Produto produto, final OnItemClickListener listener) {
            Picasso.get().load(produto.getImage()).into(imagem_produto);
            titulo_anuncio.setText(produto.getTitle());
            anunciante.setText(produto.getDescription());
            String valor = "R$" + String.valueOf(produto.getPrice());
            preco.setText(valor);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(produto);
                }
            });
        }
    }
}
