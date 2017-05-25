package com.populi.testapp.internal.rest;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Alexander Gavrikov.
 */
public interface Services {

    @GET("code_challenge/countries_v1.json")
    Call<String> getTours();
}
