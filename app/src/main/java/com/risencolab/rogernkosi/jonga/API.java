package com.risencolab.rogernkosi.jonga;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by empirestate on 6/25/16.
 */
public interface API {
    @FormUrlEncoded
    @POST("/index.php")
    void getContacts(@FieldMap Map<String, String> map, Callback<String> callback);
}
