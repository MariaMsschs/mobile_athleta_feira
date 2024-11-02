package com.example.mobile_athleta.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mobile_athleta.R;
public class SocialFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public SocialFragment() {
    }

    public static SocialFragment newInstance(String param1, String param2) {
        SocialFragment fragment = new SocialFragment();
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

    TextView tabParaVoce, tabForuns, tabEventos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_social, container, false);

        if (savedInstanceState == null) {
            carregarFragment(new PostSocial());
        }

        tabParaVoce = view.findViewById(R.id.tab_voce);
        tabForuns = view.findViewById(R.id.tab_foruns);
        tabEventos = view.findViewById(R.id.tab_eventos);
        View indicator = view.findViewById(R.id.indicator);

        tabParaVoce.post(() -> {
            int width = tabParaVoce.getWidth();
            ViewGroup.LayoutParams params = indicator.getLayoutParams();
            params.width = width;
            indicator.setLayoutParams(params);
        });

        tabParaVoce.setOnClickListener(v -> {
            animateIndicator(indicator, tabParaVoce);
            resetTabColors();
            tabParaVoce.setTextColor(getResources().getColor(R.color.black));
            carregarFragment(new PostSocial());
        });

        tabForuns.setOnClickListener(v -> {
            animateIndicator(indicator, tabForuns);
            resetTabColors();
            tabForuns.setTextColor(getResources().getColor(R.color.black));
            carregarFragment(new ForumSocial());
        });

        tabEventos.setOnClickListener(v -> {
            animateIndicator(indicator, tabEventos);
            resetTabColors();
            tabEventos.setTextColor(getResources().getColor(R.color.black));
            carregarFragment(new EventoSocial());
        });

        return view;
    }

    private void animateIndicator(View indicator, TextView tab) {
        indicator.animate().x(tab.getX()).setDuration(250);
        ViewGroup.LayoutParams params = indicator.getLayoutParams();
        params.width = tab.getWidth();
        indicator.setLayoutParams(params);
    }

    private void resetTabColors() {
        tabParaVoce.setTextColor(getResources().getColor(R.color.gray));
        tabForuns.setTextColor(getResources().getColor(R.color.gray));
        tabEventos.setTextColor(getResources().getColor(R.color.gray));
    }

    private void carregarFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.frame_conteudo_social, fragment)
                .commit();
    }
}