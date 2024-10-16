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
import com.example.mobile_athleta.models.Esporte;

import java.util.List;

public class EsporteCardAdapter extends RecyclerView.Adapter<EsporteCardAdapter.EsporteViewHolder> {

    private List<Esporte> esportesList;

    public EsporteCardAdapter(List<Esporte> esportesList) {
        this.esportesList = esportesList;
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
        holder.bind(esporte);
    }

    @Override
    public int getItemCount() {
        return esportesList.size();
    }

    public static class EsporteViewHolder extends RecyclerView.ViewHolder {
        private ImageView esporteImagem;
        private TextView esporteTitulo;
        private TextView esporteDescricao;

        private Context context;

        public EsporteViewHolder(@NonNull View itemView) {
            super(itemView);
            esporteImagem = itemView.findViewById(R.id.imagem_produto_anuncio);
            esporteTitulo = itemView.findViewById(R.id.esporte_titulo);
            esporteDescricao = itemView.findViewById(R.id.esporte_descricao);
            context = itemView.getContext();
        }

        public void bind(Esporte esporte) {
            esporteTitulo.setText(esporte.getTitle());
            esporteDescricao.setText(esporte.getDescription());
            String imageUrl = esporte.getImagem();
            Glide.with(itemView.getContext()).load(imageUrl).into(esporteImagem);
        }
    }
}