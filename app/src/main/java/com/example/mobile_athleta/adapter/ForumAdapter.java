package com.example.mobile_athleta.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_athleta.R;
import com.example.mobile_athleta.models.Forum;

import java.util.List;

public class ForumAdapter  extends RecyclerView.Adapter<ForumAdapter.ForumViewHolder> {

    private List<Forum> forumList;

    public ForumAdapter(List<Forum> forumList) {
        this.forumList = forumList;
    }

    @NonNull
    @Override
    public ForumAdapter.ForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_comunidade, parent, false);
        return new ForumAdapter.ForumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForumAdapter.ForumViewHolder holder, int position) {
        Forum forum = forumList.get(position);
        holder.bind(forum);
    }

    @Override
    public int getItemCount() {
        return forumList.size();
    }

    public static class ForumViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagem_perfil;
        private TextView forum_titulo, forum_descricao, forum_seguidores;

        public ForumViewHolder(@NonNull View itemView) {
            super(itemView);
            imagem_perfil = itemView.findViewById(R.id.imagem_produto_anuncio);
            forum_titulo = itemView.findViewById(R.id.forum_titulo);
            forum_descricao = itemView.findViewById(R.id.forum_descricao);
            forum_seguidores = itemView.findViewById(R.id.forum_seguidores);
        }

        public void bind(Forum forum) {
            imagem_perfil.setImageResource(forum.getImagem_perfil());
            forum_titulo.setText(forum.getNome());
            forum_descricao.setText(forum.getDescricao());
            forum_seguidores.setText(String.valueOf(forum.getSeguidores()));
        }
    }
}
