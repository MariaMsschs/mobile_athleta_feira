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
import com.example.mobile_athleta.models.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> postList;

    public PostAdapter(List<Post> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_post, parent, false);
        return new PostAdapter.PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        private ImageView usuarioPerfil, imagem;
        private TextView usuarioNome, legenda;

        private Context context;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            usuarioNome = itemView.findViewById(R.id.usuario);
            legenda = itemView.findViewById(R.id.legenda);
            imagem = itemView.findViewById(R.id.imagem_post);
            usuarioPerfil = itemView.findViewById(R.id.foto_perfil);
            context = itemView.getContext();
        }

        public void bind(Post post) {
            usuarioNome.setText(post.getUsuario());
            legenda.setText(post.getLegenda());

            String imagemPostUrl = post.getImagem();
            Glide.with(itemView.getContext()).load(imagemPostUrl).into(imagem);

            String imagemPerfilUrl = post.getUsuarioPerfil();
            Glide.with(itemView.getContext()).load(imagemPerfilUrl).into(usuarioPerfil);
        }
    }
}
