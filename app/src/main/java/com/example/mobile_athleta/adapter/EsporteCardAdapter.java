package com.example.mobile_athleta.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobile_athleta.R;
import com.example.mobile_athleta.models.Anuncio;
import com.example.mobile_athleta.models.Esporte;
import com.example.mobile_athleta.service.FotoFirebaseImpl;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EsporteCardAdapter extends RecyclerView.Adapter<EsporteCardAdapter.EsporteViewHolder> {

    private List<Esporte> esportesList;
    private OnItemClickListener onItemClickListener;

    public void setListaFiltrada(List<Esporte> esportesList) {
        this.esportesList = esportesList;
        notifyDataSetChanged();
    }


    public EsporteCardAdapter(List<Esporte> esportesList, OnItemClickListener onItemClickListener) {
        this.esportesList = esportesList;
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(Esporte esporte);
    }

    @NonNull
    @Override
    public EsporteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_cards, parent, false);
        return new EsporteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EsporteViewHolder holder, int position) {
        Esporte esporte = esportesList.get(position);
        holder.bind(esporte, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return esportesList.size();
    }

    public static class EsporteViewHolder extends RecyclerView.ViewHolder {
        private ImageView esporteImagem;
        private TextView esporteTitulo;
        private TextView esporteDescricao;
        FotoFirebaseImpl fotoFirebaseImpl = new FotoFirebaseImpl();

        public EsporteViewHolder(@NonNull View itemView) {
            super(itemView);
            esporteImagem = itemView.findViewById(R.id.imagem_esporte);
            esporteTitulo = itemView.findViewById(R.id.esporte_titulo);
            esporteDescricao = itemView.findViewById(R.id.esporte_descricao);
        }

        public void bind(final Esporte esporte, final OnItemClickListener listener) {
            esporteTitulo.setText(esporte.getNome());
            esporteDescricao.setText(esporte.getDescricao());
            fotoFirebaseImpl.recuperarImagem(esporteImagem, esporte.getImagem());

            itemView.setOnClickListener(v -> listener.onItemClick(esporte));
        }
    }
}