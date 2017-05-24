package com.populi.testapp.testapplication.internal.network;

import android.util.Log;

import com.populi.testapp.testapplication.BuildConfig;
import com.populi.testapp.testapplication.Constants;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alexander Gavrikov.
 */

public class RestAdapter {
    private static final String TAG = "RestAdapter";
    private static RestAdapter mInstance = new RestAdapter();

    private Retrofit retrofit;

    public static RestAdapter getInstance() {
        return mInstance;
    }

    private RestAdapter() {
    }

    public List<Country> getTours(){
        Retrofit retrofit = getRetrofit();
        Services service = retrofit.create(Services.class);

        try {
            Response<List<Country>> response = service.getTours().execute();

            if (response.code() == 200) {
                // Successful
                return response.body();
            }
            else{
                // Error
                Log.e(TAG, "Http error code in getTours(): " + response.code());
            }
        }
        catch (Exception e) {
            Log.e(TAG, "Unknown error at getTours()", e);
        }
        return null;
    }

    private Retrofit getRetrofit(){

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        clientBuilder.readTimeout(Constants.NETWORK_TIMEOUT, TimeUnit.SECONDS);
        clientBuilder.writeTimeout(Constants.NETWORK_TIMEOUT, TimeUnit.SECONDS);
        clientBuilder.connectTimeout(Constants.NETWORK_TIMEOUT, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            clientBuilder.interceptors().add(new LoggingInterceptor());
        }

        OkHttpClient client = clientBuilder.build();

        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl(Constants.URL)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

        return retrofit;
    }

}
