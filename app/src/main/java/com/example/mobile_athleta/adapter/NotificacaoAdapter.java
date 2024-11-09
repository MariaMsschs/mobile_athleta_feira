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
import com.example.mobile_athleta.service.ValidacaoCadastroImpl;

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

    public void updateNotificacoesList(List<Notificacao> newPostList) {
        this.notificacaoList.addAll(newPostList);
        notifyDataSetChanged();
    }

    public static class NotificacaoViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagemNotificacao;
        private TextView descricao_notificacao, data_notificacao;
        ValidacaoCadastroImpl validacaoCadastroImpl = new ValidacaoCadastroImpl();

        public NotificacaoViewHolder(@NonNull View itemView) {
            super(itemView);
            imagemNotificacao = itemView.findViewById(R.id.imagem_notificacao);
            descricao_notificacao = itemView.findViewById(R.id.descricao_notificacao);
            data_notificacao = itemView.findViewById(R.id.data_notificacao);
        }

        public void bind(Notificacao notificacao) {
            if(notificacao.getConteudo().contains("O organizador")){
                imagemNotificacao.setImageResource(R.drawable.social);
            }
            descricao_notificacao.setText(notificacao.getConteudo());
            String data = notificacao.getDtNotificacao().toString();
            String dataFormatada = validacaoCadastroImpl.converterDataFormatoLongo(data);
            data_notificacao.setText(dataFormatada);
        }
    }
}
