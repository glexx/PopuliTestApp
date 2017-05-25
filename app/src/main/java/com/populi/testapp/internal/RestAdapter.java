package com.populi.testapp.internal;

import android.util.Log;

import com.populi.testapp.internal.rest.LoggingInterceptor;
import com.populi.testapp.internal.rest.Services;
import com.populi.testapp.testapplication.BuildConfig;
import com.populi.testapp.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Alexander Gavrikov.
 */

public class RestAdapter {

    private static final String TAG = "RestAdapter";

    public RestAdapter() {
    }

    public String getTours(){
        Retrofit retrofit = getRetrofit();
        Services service = retrofit.create(Services.class);

        try {
            Response response = service.getTours().execute();

            if (response.code() == 200) {
                // Successful
                return response.body().toString();
            }
            else{
                // Error
                Log.e(TAG, "Http error code in updateTours(): " + response.code());
            }
        }
        catch (Exception e) {
            Log.e(TAG, "Unknown error at updateTours()", e);
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
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

        return retrofit;
    }

}
