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

public class PostSocial extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PostSocial() {
    }
    public static PostSocial newInstance(String param1, String param2) {
        PostSocial fragment = new PostSocial();
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
        View view = inflater.inflate(R.layout.fragment_post_social, container, false);

        RecyclerView recyclerViewPost = view.findViewById(R.id.recycler_post_social);

        postList = new ArrayList<>();

        postList.add(new Post(1, "OASIS", "https://lastfm.freetls.fastly.net/i/u/avatar170s/41049b383497d46303be8310be34fd96", "User2", "https://lastfm.freetls.fastly.net/i/u/avatar170s/295a03bef4538a8704d1689e0d8ed3b9"));
        postList.add(new Post(2, "post3", "https://lastfm.freetls.fastly.net/i/u/avatar170s/071a740e92bb4d4498f9f12f94f90c2f", "User2","https://lastfm.freetls.fastly.net/i/u/avatar170s/295a03bef4538a8704d1689e0d8ed3b9"));
        postList.add(new Post(3, "post1", "https://lastfm.freetls.fastly.net/i/u/avatar170s/47df7c610398db3abd32691b05d24ed7", "User2", "https://lastfm.freetls.fastly.net/i/u/avatar170s/295a03bef4538a8704d1689e0d8ed3b9"));

        recyclerViewPost.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        postAdapter = new PostAdapter(postList);
        recyclerViewPost.setAdapter(postAdapter);

        return view;
    }
}