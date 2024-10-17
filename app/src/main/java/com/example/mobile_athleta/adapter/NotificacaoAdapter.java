package com.example.mobile_athleta.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_athleta.R;
import com.example.mobile_athleta.models.Notificacao;

import java.util.List;

public class NotificacaoAdapter extends RecyclerView.Adapter<NotificacaoAdapter.NotificacaoViewHolder> {

    private List<Notificacao> notificacaoList;

    public NotificacaoAdapter(List<Notificacao> notificacaoList) {
        this.notificacaoList = notificacaoList;
    }

    @NonNull
    @Override
    public NotificacaoAdapter.NotificacaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_notificacao, parent, false);
        return new NotificacaoAdapter.NotificacaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificacaoAdapter.NotificacaoViewHolder holder, int position) {
        Notificacao notificacao = notificacaoList.get(position);
        holder.bind(notificacao);
    }

    @Override
    public int getItemCount() {
        return notificacaoList.size();
    }

    public static class NotificacaoViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagemNotificacao;
        private TextView descricao_notificacao, data_notificacao;

        public NotificacaoViewHolder(@NonNull View itemView) {
            super(itemView);
            imagemNotificacao = itemView.findViewById(R.id.imagem_notificacao);
            descricao_notificacao = itemView.findViewById(R.id.descricao_notificacao);
            data_notificacao = itemView.findViewById(R.id.data_notificacao);
        }

        public void bind(Notificacao notificacao) {
            imagemNotificacao.setImageResource(notificacao.getImagemNotificacao());
            descricao_notificacao.setText(notificacao.getDescricao());
            data_notificacao.setText(notificacao.getData());
        }
    }
}
