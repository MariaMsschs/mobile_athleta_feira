package com.example.mobile_athleta.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobile_athleta.R;
import com.example.mobile_athleta.adapter.NotificacaoAdapter;
import com.example.mobile_athleta.models.Notificacao;

import java.util.ArrayList;
import java.util.List;

public class NotificacaoFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public NotificacaoFragment() {
        // Required empty public constructor
    }
    public static NotificacaoFragment newInstance(String param1, String param2) {
        NotificacaoFragment fragment = new NotificacaoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    RecyclerView recyclerViewNotificacao;
    private List<Notificacao> notificacaoList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notificacao, container, false);
        recyclerViewNotificacao = view.findViewById(R.id.recyler_notificacao);
        notificacaoList = new ArrayList<>();
        notificacaoList.add(new Notificacao(0,0,"A","10/10/2020"));
        notificacaoList.add(new Notificacao(1,0,"A","10/10/2020"));

        recyclerViewNotificacao.setLayoutManager(new LinearLayoutManager(getContext()));
        NotificacaoAdapter notificacaoAdapter = new NotificacaoAdapter(notificacaoList);
        recyclerViewNotificacao.setAdapter(notificacaoAdapter);
        return view;
    }
}