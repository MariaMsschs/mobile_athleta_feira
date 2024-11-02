package com.example.mobile_athleta.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_athleta.R;
import com.example.mobile_athleta.UseCase.ListarComentariosUseCase;
import com.example.mobile_athleta.models.Comentario;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.ComentarioViewHolder> {

    private List<Comentario> comentariosList;

    private OnItemClickListener onItemClickListener;

    public ComentarioAdapter(List<Comentario> comentariosList, OnItemClickListener onItemClickListener){
        this.comentariosList = comentariosList;
        this.onItemClickListener = onItemClickListener;
    }

    public void updateComentariosList(List<Comentario> newComentariosList) {
        this.comentariosList.addAll(newComentariosList);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ComentarioAdapter.ComentarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_comentario, parent, false);
        return new ComentarioAdapter.ComentarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComentarioAdapter.ComentarioViewHolder holder, int position) {
        Comentario comentario = comentariosList.get(position);
        holder.bind(comentario, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return comentariosList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Comentario comentario);
    }

    public static class ComentarioViewHolder extends RecyclerView.ViewHolder {
        private TextView comentario, usuario;
        private ImageButton usuariofoto;

        public ComentarioViewHolder(@NonNull View itemView) {
            super(itemView);
            this.comentario = itemView.findViewById(R.id.comentario);
            this.usuario = itemView.findViewById(R.id.usuario);
            this.usuariofoto = itemView.findViewById(R.id.foto_perfil);
        }

        public void bind(Comentario comentario, final OnItemClickListener listener) {
            usuario.setText(comentario.getUsername());
            String usuarioPerfil = comentario.getUserFoto();

            String imagemPerfilUrl = comentario.getUserFoto();
            Picasso.get()
                    .load(imagemPerfilUrl)
                    .into(usuariofoto);
        }
    }
}
