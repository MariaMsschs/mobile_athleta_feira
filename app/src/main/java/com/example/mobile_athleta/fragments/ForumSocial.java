package com.example.mobile_athleta.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobile_athleta.R;
import com.example.mobile_athleta.adapter.ForumAdapter;
import com.example.mobile_athleta.models.Forum;

import java.util.ArrayList;
import java.util.List;

public class ForumSocial extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public ForumSocial() {
    }

    public static ForumSocial newInstance(String param1, String param2) {
        ForumSocial fragment = new ForumSocial();
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

    private ForumAdapter forumAdapter;
    private List<Forum> forumList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum_social, container, false);

        RecyclerView recyclerViewForum = view.findViewById(R.id.recycler_forum_social);

        forumList = new ArrayList<>();

        forumList.add(new Forum(1, "PingPros", "Comunidade de ping pong.", "user2","https://lastfm.freetls.fastly.net/i/u/avatar170s/c009cbba6eb44dfb9ba4081f30bfe46b", "https://lastfm.freetls.fastly.net/i/u/avatar170s/3dad5639665ad1b040cfb4071e95fb7a", 0));
        forumList.add(new Forum(2, "PingPros", "Comunidade de ping pong.", "user2", "https://lastfm.freetls.fastly.net/i/u/avatar170s/3dad5639665ad1b040cfb4071e95fb7a", "https://lastfm.freetls.fastly.net/i/u/avatar170s/c009cbba6eb44dfb9ba4081f30bfe46b", 0));
        forumList.add(new Forum(3, "PingPros", "Comunidade de ping pong.", "user2", "https://lastfm.freetls.fastly.net/i/u/avatar170s/c009cbba6eb44dfb9ba4081f30bfe46b", "https://lastfm.freetls.fastly.net/i/u/avatar170s/3dad5639665ad1b040cfb4071e95fb7a", 0));

        recyclerViewForum.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        forumAdapter = new ForumAdapter(forumList);
        recyclerViewForum.setAdapter(forumAdapter);

        return view;
    }
}