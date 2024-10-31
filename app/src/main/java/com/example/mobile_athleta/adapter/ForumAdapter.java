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
import com.example.mobile_athleta.models.Forum;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ForumAdapter  extends RecyclerView.Adapter<ForumAdapter.ForumViewHolder> {

    private List<Forum> forumList;

    public ForumAdapter(List<Forum> forumList) {
        this.forumList = forumList;
    }

    public void setListaFiltrada(List<Forum> forumList) {
        this.forumList = forumList;
        notifyDataSetChanged();
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

        private Context context;

        public ForumViewHolder(@NonNull View itemView) {
            super(itemView);
            imagem_perfil = itemView.findViewById(R.id.imagem_comunidade);
            forum_titulo = itemView.findViewById(R.id.forum_titulo);
            forum_descricao = itemView.findViewById(R.id.forum_descricao);
            forum_seguidores = itemView.findViewById(R.id.forum_seguidores);
            context = itemView.getContext();
        }

        public void bind(Forum forum) {
            String imagemUrl = forum.getImagem_perfil();
//            Glide.with(itemView.getContext()).load(imagemUrl).into(imagem_perfil);
            Picasso.get()
                    .load(imagemUrl)
                    .into(imagem_perfil);
            forum_titulo.setText(forum.getNome());
            forum_descricao.setText(forum.getDescricao());
            forum_seguidores.setText(String.valueOf(forum.getSeguidores()));
        }
    }
}
