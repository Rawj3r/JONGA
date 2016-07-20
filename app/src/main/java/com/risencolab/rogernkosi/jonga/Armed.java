package com.risencolab.rogernkosi.jonga;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by empirestate on 6/15/16.
 */
public class Armed extends Fragment implements View.OnClickListener{

    Button disarm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_armed, container, false);
        disarm = (Button)view.findViewById(R.id.disarm);
        disarm.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        

        if (v.getId() == R.id.disarm){
            new UpdateUnit().execute();

            Intent i = new Intent(getContext(), FullscreenActivity.class);
            startActivity(i);
        }

    }


    class UpdateUnit extends AsyncTask<String, String, JSONObject> {

        JSONPArser jsonParser = new JSONPArser();

        ProgressDialog progressDialog;

        String URL = "http://jonga.co/jongas/";

        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Getting view...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            HashMap<String, String> map = new HashMap<>();
            map.put("type", "updateStatus");
            map.put("status", "2");

            JSONObject object = jsonParser.makeHttpRequest(URL, "POST", map);

            if (object != null){
                Log.d("JSON result", object.toString());
                return object;
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);

            if (progressDialog != null){
                progressDialog.dismiss();
            }
        }
    }
}
