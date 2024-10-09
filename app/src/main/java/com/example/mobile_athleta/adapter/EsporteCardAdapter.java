package com.example.mobile_athleta.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        private ImageView sportImage;
        private TextView sportTitle;
        private TextView sportDescription;

        public EsporteViewHolder(@NonNull View itemView) {
            super(itemView);
            sportImage = itemView.findViewById(R.id.imagem_produto_anuncio);
            sportTitle = itemView.findViewById(R.id.forum_titulo);
            sportDescription = itemView.findViewById(R.id.forum_descricao);
        }

        public void bind(Esporte esporte) {
            sportTitle.setText(esporte.getTitle());
            sportDescription.setText(esporte.getDescription());
            sportImage.setImageResource(esporte.getImageResource());
        }
    }
}