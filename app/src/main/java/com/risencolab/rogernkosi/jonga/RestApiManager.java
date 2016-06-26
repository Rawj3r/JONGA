package com.risencolab.rogernkosi.jonga;

import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by empirestate on 3/22/16.
 */
public class RestApiManager {

    private API homeApi;

    public API getHomeAPi(){

        if (homeApi == null){
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(String.class, new StringDesirializer());

            homeApi = new RestAdapter.Builder()
                    .setEndpoint(Constant.BASE_URL)
                    .setConverter(new GsonConverter(builder.create()))
                    .build()
                    .create(API.class);
        }
        return homeApi;
    }
}
