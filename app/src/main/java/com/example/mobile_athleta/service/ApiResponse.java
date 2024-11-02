package com.example.mobile_athleta.service;

import com.example.mobile_athleta.models.Forum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApiResponse {
    private boolean responseSucessfull;
    private String description;
    private List<Object> object;
    private String aditionalInformation;

    public ApiResponse(boolean responseSucessfull, String description, List<Object> object, String aditionalInformation) {
        this.responseSucessfull = responseSucessfull;
        this.description = description;
        this.object = object;
        this.aditionalInformation = aditionalInformation;
    }

    public boolean getResponseSucessfull() {
        return responseSucessfull;
    }

    public String getDescription() {
        return description;
    }

    public List<Object> getObject() {
        return object;
    }

    public String getAditionalInformation() {
        return aditionalInformation;
    }

    public List<Forum> getForuns() {
        List<Forum> foruns = new ArrayList<>();
        for (Object obj : object) {
            if (obj instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) obj;
                Forum forum = new Forum();
                forum.setId((Integer) map.get("id"));
                forum.setNome((String) map.get("nome"));
                forum.setDescricao((String) map.get("descricao"));
                forum.setUsuario((String) map.get("usuario"));
                forum.setImagem_perfil((String) map.get("imagem_perfil"));
                forum.setImagem_fundo((String) map.get("imagem_fundo"));
                forum.setSeguidores((map.get("seguidores") != null) ? (Integer) map.get("seguidores") : 0);

                foruns.add(forum);
            }
        }
        return foruns;
    }


}
