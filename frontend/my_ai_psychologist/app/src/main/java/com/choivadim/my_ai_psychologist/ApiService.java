package com.choivadim.my_ai_psychologist;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/register")
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);

    @POST("/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("/journal")
    Call<JournalResponse> addJournalEntry(@Header("Authorization") String token, @Body JournalRequest journalRequest);

    @POST("/chat")
    Call<ChatResponse> chatWithAI(@Header("Authorization") String token, @Body ChatRequest chatRequest);
}
