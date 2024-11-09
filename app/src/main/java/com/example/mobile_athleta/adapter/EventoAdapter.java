package com.example.mobile_athleta.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_athleta.R;
import com.example.mobile_athleta.models.Evento;
import com.example.mobile_athleta.service.FotoFirebaseImpl;
import com.example.mobile_athleta.service.ValidacaoCadastroImpl;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.EventoViewHolder> {

    private List<Evento> eventoList;
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(Evento evento);
    }

    public EventoAdapter(List<Evento> eventoList, OnItemClickListener onItemClickListener) {
        this.eventoList = eventoList;
        this.onItemClickListener = onItemClickListener;
    }

    public void updateEventoList(List<Evento> newEventoList) {
        this.eventoList.clear();
        this.eventoList.addAll(newEventoList);
        notifyDataSetChanged();
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
        holder.bind(evento, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return eventoList.size();
    }

    public static class EventoViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagem_evento;
        private TextView evento_titulo, evento_descricao, evento_data;

        ValidacaoCadastroImpl validacaoCadastroImpl = new ValidacaoCadastroImpl();
        FotoFirebaseImpl fotoFirebaseImpl = new FotoFirebaseImpl();
        private Context context;

        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);
            imagem_evento = itemView.findViewById(R.id.imagem_evento);
            evento_titulo = itemView.findViewById(R.id.evento_titulo);
            evento_descricao = itemView.findViewById(R.id.evento_descricao);
            evento_data = itemView.findViewById(R.id.titulo_data_evento);
            context = itemView.getContext();
        }
        public void bind(Evento evento, final OnItemClickListener onItemClickListener) {
            fotoFirebaseImpl.recuperarImagem(imagem_evento, evento.getImg());
            evento_titulo.setText(evento.getNome());
            evento_descricao.setText(evento.getDescricao());
            String dateString = String.valueOf(evento.getDtEvento());
            String dataFormatada = validacaoCadastroImpl.converterDataInterface(dateString);
            evento_data.setText(dataFormatada);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(evento);
                }
            });
        }
    }
}