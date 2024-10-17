package com.example.mobile_athleta.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobile_athleta.R;
import com.example.mobile_athleta.adapter.EventoAdapter;
import com.example.mobile_athleta.adapter.ForumAdapter;
import com.example.mobile_athleta.models.Evento;
import com.example.mobile_athleta.models.Forum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventoPerfil extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public EventoPerfil() {
    }

    public static EventoPerfil newInstance(String param1, String param2) {
        EventoPerfil fragment = new EventoPerfil();
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

    private EventoAdapter eventoAdapter;
    private List<Evento> eventoList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_evento_perfil, container, false);

        RecyclerView recyclerViewForum = view.findViewById(R.id.recycler_evento_perfil);

        String dateString = "2008-02-06";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;

        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        eventoList = new ArrayList<>();
        eventoList.add(new Evento(2, "PingPros", "Comunidade de ping pong.", date, "https://static.wikia.nocookie.net/disney/images/e/e5/Profile_-_Marie.jpg/revision/latest?cb=20240215032542&path-prefix=pt-br"));
        eventoList.add(new Evento(3, "PingPros", "Comunidade de ping pong.", date, "https://i.scdn.co/image/ab6761610000e5eb0522e98a6f0cf1ddbee9a74f"));


        recyclerViewForum.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        eventoAdapter = new EventoAdapter(eventoList);
        recyclerViewForum.setAdapter(eventoAdapter);

       return view;
    }
}