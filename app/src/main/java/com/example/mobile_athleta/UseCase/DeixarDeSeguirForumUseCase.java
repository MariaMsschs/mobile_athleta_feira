package com.example.mobile_athleta.UseCase;

import com.example.mobile_athleta.service.RelacionamentoForum;

public class DeixarDeSeguirForumUseCase {
    public interface ForumCallback {
        void onSeguirForumSuccess(boolean response);
        void onSeguirForumError(String message);
    }

    public void deixarSeguir(String token, Long id, ForumCallback callback) {

    }

}
