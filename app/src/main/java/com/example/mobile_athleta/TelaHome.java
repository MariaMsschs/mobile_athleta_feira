package com.example.mobile_athleta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mobile_athleta.adapter.EsporteCardAdapter;
import com.example.mobile_athleta.adapter.ForumAdapter;
import com.example.mobile_athleta.adapter.PostAdapter;
import com.example.mobile_athleta.models.Esporte;
import com.example.mobile_athleta.models.Forum;
import com.example.mobile_athleta.models.Post;

import java.util.ArrayList;
import java.util.List;

public class TelaHome extends AppCompatActivity {

    private RecyclerView recyclerViewCards, recyclerViewPosts, recyclerViewForum;
    private EsporteCardAdapter esportesAdapter;
    private PostAdapter postAdapter;
    private ForumAdapter forumAdapter;
    private List<Esporte> esportesList;
    private List<Post> postList;

    private List<Forum> forumList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_home);

        //recycler cards
        recyclerViewCards = findViewById(R.id.cards_recycler);

        esportesList = new ArrayList<>();
        esportesList.add(new Esporte("Beisebol", "A prática do beisebol envolve duas equipes de nove jogadores...", R.drawable.baseball));
        esportesList.add(new Esporte("Badminton", "Badminton é um esporte de raquete que pode ser jogado...", R.drawable.baseball));

        recyclerViewCards.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        esportesAdapter = new EsporteCardAdapter(esportesList);
        recyclerViewCards.setAdapter(esportesAdapter);

        //recycler posts

        recyclerViewPosts = findViewById(R.id.recycler_posts);

        postList = new ArrayList<>();

        postList.add(new Post(1, "post2", 0, "User2", R.drawable.perfil_pequeno));
        postList.add(new Post(2, "post3", 0, "User2", R.drawable.perfil_pequeno));
        postList.add(new Post(3, "post1", 0, "User2", R.drawable.perfil_pequeno));


        recyclerViewPosts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        postAdapter = new PostAdapter(postList);
        recyclerViewPosts.setAdapter(postAdapter);

        //recycler forum
        recyclerViewForum = findViewById(R.id.recycler_forum);

        forumList = new ArrayList<>();

        forumList.add(new Forum(1, "PingPros", "Comunidade de ping pong.", "user2", R.drawable.comunidadefoto, R.drawable.comunidadefoto, 0));
        forumList.add(new Forum(2, "PingPros", "Comunidade de ping pong.", "user2", R.drawable.comunidadefoto, R.drawable.comunidadefoto, 0));
        forumList.add(new Forum(3, "PingPros", "Comunidade de ping pong.", "user2", R.drawable.comunidadefoto, R.drawable.comunidadefoto, 0));


        recyclerViewForum.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        forumAdapter = new ForumAdapter(forumList);
        recyclerViewForum.setAdapter(forumAdapter);
    }
}