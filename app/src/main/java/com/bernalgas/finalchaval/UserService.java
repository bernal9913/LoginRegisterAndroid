package com.bernalgas.finalchaval;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("users")
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);

    @POST("log_user")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
}
