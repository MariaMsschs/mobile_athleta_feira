package com.example.mobile_athleta.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_athleta.R;
import com.example.mobile_athleta.TelaComentarios;
import com.example.mobile_athleta.UseCase.CurtidaUseCase;
import com.example.mobile_athleta.UseCase.ListarComentariosUseCase;
import com.example.mobile_athleta.models.Comentario;
import com.example.mobile_athleta.models.Post;
import com.example.mobile_athleta.service.FotoFirebaseImpl;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> postList;
    private OnItemClickListener onItemClickListener;
    private CurtidaUseCase curtidaUseCase = new CurtidaUseCase();
    FotoFirebaseImpl fotoFirebaseImpl = new FotoFirebaseImpl();

    ImageButton curtida;

    public PostAdapter(List<Post> postList, OnItemClickListener onItemClickListener) {
        this.postList = postList != null ? postList : new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public PostAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_post, parent, false);
        curtida = view.findViewById(R.id.curtir);
        return new PostAdapter.PostViewHolder(view);
    }

    public void updatePostList(List<Post> newPostList) {
        this.postList.addAll(newPostList);
        notifyDataSetChanged();
    }

    public void curtir(Post post, boolean curtido) {
        if (curtido) {
            int position = postList.indexOf(post);
            if (position >= 0) {
                notifyItemChanged(position);
            }
        }
    }


    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.bind(post, onItemClickListener);
        holder.curtir.setImageResource(post.isLiked() ? R.drawable.like_preenchido : R.drawable.like);
        if (post.getImagem() != null && !post.getImagem().isEmpty()) {
            holder.imagem.setVisibility(View.VISIBLE);
        } else {
            holder.imagem.setVisibility(View.GONE);
        }

        String username = holder.context.getSharedPreferences("login", Context.MODE_PRIVATE).getString("username","");
        holder.curtir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curtidaUseCase.interacaoCurtida(post, new CurtidaUseCase.VerificarCallback() {
                    @Override
                    public void onVerificarSuccess(Post updatedPost, String message) {
                        if (message.equals("Curtido")) {
                            holder.curtir.setImageResource(R.drawable.like_preenchido);
                        } else {
                            holder.curtir.setImageResource(R.drawable.like);
                        }
                    }

                    @Override
                    public void onVerificarFailure(String errorMessage) {
                        Toast.makeText(holder.context, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }, username);
            }
        });

        holder.comentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("idPost", post.getId());
                Intent intent = new Intent(v.getContext(), TelaComentarios.class);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });

    }

    public interface OnItemClickListener {
        void onItemClick(Post post);
        void onFotoClick(Post post);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        private ImageView usuarioPerfil, imagem;
        private TextView usuarioNome, legenda;
        private ImageButton curtir,comentar;
        FotoFirebaseImpl fotoFirebaseImpl = new FotoFirebaseImpl();
        private Context context;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            usuarioNome = itemView.findViewById(R.id.usuario);
            legenda = itemView.findViewById(R.id.legenda);
            imagem = itemView.findViewById(R.id.imagem_post);
            usuarioPerfil = itemView.findViewById(R.id.foto_perfil);
            curtir = itemView.findViewById(R.id.curtir);
            comentar = itemView.findViewById(R.id.comentar);
            context = itemView.getContext();
        }

        public void bind(Post post, final OnItemClickListener listener) {
            usuarioNome.setText(post.getUsername());
            legenda.setText(post.getLegenda());

            fotoFirebaseImpl.recuperarImagem(imagem, post.getImagem());

            fotoFirebaseImpl.recuperarImagem(usuarioPerfil, post.getUserFoto());

            curtir.setOnClickListener(v -> listener.onItemClick(post));

            comentar.setOnClickListener(v -> listener.onItemClick(post));

            usuarioPerfil.setOnClickListener(v -> listener.onFotoClick(post));
        }
    }
}
