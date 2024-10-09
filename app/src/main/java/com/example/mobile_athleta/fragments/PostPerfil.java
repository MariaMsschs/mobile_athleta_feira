package com.example.mobile_athleta.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobile_athleta.R;
import com.example.mobile_athleta.adapter.PostAdapter;
import com.example.mobile_athleta.models.Post;

import java.util.ArrayList;
import java.util.List;

public class PostPerfil extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public PostPerfil() {
    }
    public static PostPerfil newInstance(String param1, String param2) {
        PostPerfil fragment = new PostPerfil();
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

    private PostAdapter postAdapter;
    private List<Post> postList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_perfil, container, false);

        RecyclerView recyclerViewPost = view.findViewById(R.id.recycler_post_perfil);

        postList = new ArrayList<>();

        postList.add(new Post(1, "post2", 0, "User2", R.drawable.perfil_pequeno));
        postList.add(new Post(2, "post3", 0, "User2", R.drawable.perfil_pequeno));
        postList.add(new Post(3, "post1", 0, "User2", R.drawable.perfil_pequeno));

        recyclerViewPost.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        postAdapter = new PostAdapter(postList);
        recyclerViewPost.setAdapter(postAdapter);

        return view;
    }
}