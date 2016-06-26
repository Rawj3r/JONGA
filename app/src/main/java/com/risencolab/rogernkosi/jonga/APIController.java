package com.risencolab.rogernkosi.jonga;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by empirestate on 6/25/16.
 */
public class APIController {

    private final String TAG = APIController.class.getSimpleName();
    private HomeCallBackListener listener;
    private RestApiManager restApiManager;

    public APIController(HomeCallBackListener listener) {
        this.listener = listener;
        restApiManager = new RestApiManager();
    }

    public void fetchContacts(){
        HashMap <String, String> map = new HashMap<>();
        map.put("type", "contacts");
        restApiManager.getHomeAPi().getContacts(map, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Log.e(TAG, "JSON :: " + s);

                try {
                    JSONArray array = new JSONArray(s);
                    for (int i = 0; i < array.length(); i++){
                        JSONObject jsonObject = array.getJSONObject(i);
                        ContactsModel contactsModel = new ContactsModel.CBuilder()
                                .setCcname(jsonObject.getString("contactName"))
                                .setCcnumber(jsonObject.getString("contactPhoneNumber"))
                                .build();
                        listener.onFetchProgress(contactsModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listener.onFetchComplete();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "Error :: " + error.getMessage());
                listener.onFetchComplete();
            }
        });
    }

    public interface HomeCallBackListener{
        void onFetchStart();
        void onFetchProgress(ContactsModel model);
        void onFetchProgress(List<ContactsModel> modelList);
        void onFetchComplete();
        void onFetchFailed();
    }
}

