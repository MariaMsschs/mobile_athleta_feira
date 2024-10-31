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
import com.example.mobile_athleta.models.Evento;
import com.example.mobile_athleta.models.Post;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.EventoViewHolder> {

    private List<Evento> eventoList;

    public EventoAdapter(List<Evento> eventoList) {
        this.eventoList = eventoList;
    }

    public void setListaFiltrada(List<Evento> eventoList) {
        this.eventoList = eventoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EventoAdapter.EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_evento, parent, false);
        return new EventoAdapter.EventoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventoAdapter.EventoViewHolder holder, int position) {
        Evento evento = eventoList.get(position);
        holder.bind(evento);
    }

    @Override
    public int getItemCount() {
        return eventoList.size();
    }

    public static class EventoViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagem_evento;
        private TextView evento_titulo, evento_descricao, evento_data;

        private Context context;

        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);
            imagem_evento = itemView.findViewById(R.id.imagem_evento);
            evento_titulo = itemView.findViewById(R.id.evento_titulo);
            evento_descricao = itemView.findViewById(R.id.evento_descricao);
            evento_data = itemView.findViewById(R.id.titulo_data_evento);
            context = itemView.getContext();
        }
        public void bind(Evento evento) {
            String imagemUrl = evento.getImg();
            Picasso.get()
                    .load(imagemUrl)
                    .into(imagem_evento);
            evento_titulo.setText(evento.getNome());
            evento_descricao.setText(evento.getDescricao());
            evento_data.setText(String.valueOf(evento.getDtEvento()));
        }
    }
}
