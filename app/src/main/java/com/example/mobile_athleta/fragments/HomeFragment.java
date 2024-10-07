package com.example.mobile_athleta.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mobile_athleta.Login;
import com.example.mobile_athleta.R;
import com.example.mobile_athleta.adapter.EsporteCardAdapter;
import com.example.mobile_athleta.adapter.ForumAdapter;
import com.example.mobile_athleta.adapter.PostAdapter;
import com.example.mobile_athleta.models.Esporte;
import com.example.mobile_athleta.models.Forum;
import com.example.mobile_athleta.models.Post;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters

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
        esportesList.add(new Esporte("Beisebol", "A prática do beisebol envolve duas equipes de nove jogadores...", R.drawable.baseball));
        esportesList.add(new Esporte("Badminton", "Badminton é um esporte de raquete que pode ser jogado...", R.drawable.baseball));

        recyclerViewCards.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
        esportesAdapter = new EsporteCardAdapter(esportesList);
        recyclerViewCards.setAdapter(esportesAdapter);

        //recycler posts

        recyclerViewPosts = view.findViewById(R.id.recycler_posts);

        postList = new ArrayList<>();

        postList.add(new Post(1, "post2", 0, "User2", R.drawable.perfil_pequeno));
        postList.add(new Post(2, "post3", 0, "User2", R.drawable.perfil_pequeno));
        postList.add(new Post(3, "post1", 0, "User2", R.drawable.perfil_pequeno));


        recyclerViewPosts.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        postAdapter = new PostAdapter(postList);
        recyclerViewPosts.setAdapter(postAdapter);

        //recycler forum
        recyclerViewForum = view.findViewById(R.id.recycler_forum);

        forumList = new ArrayList<>();

        forumList.add(new Forum(1, "PingPros", "Comunidade de ping pong.", "user2", R.drawable.comunidadefoto, R.drawable.comunidadefoto, 0));
        forumList.add(new Forum(2, "PingPros", "Comunidade de ping pong.", "user2", R.drawable.comunidadefoto, R.drawable.comunidadefoto, 0));
        forumList.add(new Forum(3, "PingPros", "Comunidade de ping pong.", "user2", R.drawable.comunidadefoto, R.drawable.comunidadefoto, 0));


        recyclerViewForum.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        forumAdapter = new ForumAdapter(forumList);
        recyclerViewForum.setAdapter(forumAdapter);

        return view;
    }
}