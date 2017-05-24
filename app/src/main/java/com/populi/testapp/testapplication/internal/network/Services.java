package com.populi.testapp.testapplication.internal.network;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Alexander Gavrikov.
 */
public interface Services {

    @GET("code_challenge/countries_v1.json")
    Call<List<Country>> getTours();
}
