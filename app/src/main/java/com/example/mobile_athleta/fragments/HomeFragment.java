package com.example.mobile_athleta.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.example.mobile_athleta.R;
import com.example.mobile_athleta.adapter.EsporteCardAdapter;
import com.example.mobile_athleta.adapter.ForumAdapter;
import com.example.mobile_athleta.adapter.PostAdapter;
import com.example.mobile_athleta.models.Esporte;
import com.example.mobile_athleta.models.Forum;
import com.example.mobile_athleta.models.Post;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HomeFragment() {
    }

    private RecyclerView recyclerViewCards, recyclerViewPosts, recyclerViewForum;
    private EsporteCardAdapter esportesAdapter;
    private PostAdapter postAdapter;
    private ForumAdapter forumAdapter;
    private List<Esporte> esportesList;
    private List<Post> postList;

    private ImageView logo;
    private List<Forum> forumList;

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //recycler cards
        recyclerViewCards = view.findViewById(R.id.cards_recycler);

        esportesList = new ArrayList<>();
        esportesList.add(new Esporte("Beisebol", "A prática do beisebol envolve duas equipes de nove jogadores...", "https://lastfm.freetls.fastly.net/i/u/770x0/2c99d172718679ddefa26d910452545e.jpg#2c99d172718679ddefa26d910452545e"));
        esportesList.add(new Esporte("Badminton", "Badminton é um esporte de raquete que pode ser jogado...", "https://lastfm.freetls.fastly.net/i/u/770x0/2c99d172718679ddefa26d910452545e.jpg#2c99d172718679ddefa26d910452545e"));

        recyclerViewCards.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
        esportesAdapter = new EsporteCardAdapter(esportesList);
        recyclerViewCards.setAdapter(esportesAdapter);

        //recycler posts

        recyclerViewPosts = view.findViewById(R.id.recycler_posts);

        postList = new ArrayList<>();



        recyclerViewPosts.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        postAdapter = new PostAdapter(postList, new PostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Post post) {
            }
        });

        recyclerViewPosts.setAdapter(postAdapter);

        recyclerViewForum = view.findViewById(R.id.recycler_forum);

        forumList = new ArrayList<>();

        forumList.add(new Forum(1, "PingPros", "Comunidade de ping pong.", "user2","https://lastfm.freetls.fastly.net/i/u/avatar170s/c009cbba6eb44dfb9ba4081f30bfe46b", "https://lastfm.freetls.fastly.net/i/u/avatar170s/3dad5639665ad1b040cfb4071e95fb7a", 0));
        forumList.add(new Forum(2, "PingPros", "Comunidade de ping pong.", "user2", "https://lastfm.freetls.fastly.net/i/u/avatar170s/3dad5639665ad1b040cfb4071e95fb7a", "https://lastfm.freetls.fastly.net/i/u/avatar170s/c009cbba6eb44dfb9ba4081f30bfe46b", 0));
        forumList.add(new Forum(3, "PingPros", "Comunidade de ping pong.", "user2", "https://lastfm.freetls.fastly.net/i/u/avatar170s/c009cbba6eb44dfb9ba4081f30bfe46b", "https://lastfm.freetls.fastly.net/i/u/avatar170s/3dad5639665ad1b040cfb4071e95fb7a", 0));

        recyclerViewForum.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        forumAdapter = new ForumAdapter(forumList);
        recyclerViewForum.setAdapter(forumAdapter);

        return view;
    }
}