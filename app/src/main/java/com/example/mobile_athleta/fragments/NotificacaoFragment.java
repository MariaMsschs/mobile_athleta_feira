package com.example.mobile_athleta.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobile_athleta.R;
import com.example.mobile_athleta.UseCase.ListarNotificacoesUseCase;
import com.example.mobile_athleta.adapter.NotificacaoAdapter;
import com.example.mobile_athleta.models.Notificacao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificacaoFragment extends Fragment {


    public NotificacaoFragment() {
    }
    public static NotificacaoFragment newInstance() {
        NotificacaoFragment fragment = new NotificacaoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    RecyclerView recyclerViewNotificacao;
    private List<Notificacao> notificacaoList;
    private ListarNotificacoesUseCase listarNotificacoesUseCase = new ListarNotificacoesUseCase();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notificacao, container, false);
        recyclerViewNotificacao = view.findViewById(R.id.recyler_notificacao);
        notificacaoList = new ArrayList<>();
        Long id = view.getContext().getSharedPreferences("login", Context.MODE_PRIVATE).getLong("idUsuario", 0);
        String token = view.getContext().getSharedPreferences("login", Context.MODE_PRIVATE).getString("token", "");
        recyclerViewNotificacao.setLayoutManager(new LinearLayoutManager(getContext()));
        NotificacaoAdapter notificacaoAdapter = new NotificacaoAdapter(notificacaoList);
        recyclerViewNotificacao.setAdapter(notificacaoAdapter);

        listarNotificacoesUseCase.listarNotificacoes(token, id, new ListarNotificacoesUseCase.OnListarNotificacoesCallback() {
            @Override
            public void onListarNotificacoesSuccess(List<Notificacao> notificacaoList) {
                notificacaoAdapter.updateNotificacoesList(notificacaoList);
            }

            @Override
            public void onListarNotificacoesError(String message) {

            }
        });
        return view;
    }
}