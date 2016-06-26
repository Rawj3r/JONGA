package com.risencolab.rogernkosi.jonga;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by empirestate on 6/25/16.
 */
public interface API {
    @GET("/getContacts.php")
    void getContacts(Callback<String> callback);
}
