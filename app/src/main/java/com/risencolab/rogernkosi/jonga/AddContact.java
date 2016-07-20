package com.risencolab.rogernkosi.jonga;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AddContact extends Fragment implements View.OnClickListener{

    private EditText cname, cnumber;
    private Button btnadd;
    private ProgressDialog progressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_add_contact, container, false);

        cname = (EditText)view.findViewById(R.id.cname);
        cnumber = (EditText)view.findViewById(R.id.cnumber);
        btnadd = (Button)view.findViewById(R.id.btnadd);
        btnadd.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnadd){
            new AddUser().execute();
        }
    }

    class AddUser extends AsyncTask<String,String, JSONObject> {

        final String name = cname.getText().toString();
        final String number = cnumber.getText().toString();

        JSONPArser jsonParser = new JSONPArser();

        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";

        @Override
        protected void onPostExecute(JSONObject jsonObject) {

            super.onPostExecute(jsonObject);
            int success = 0;
            String message = "";

            if (progressDialog != null){
                progressDialog.dismiss();
            }

            if (jsonObject != null){


                try {
                    Toast.makeText(getContext(), jsonObject.getString(TAG_MESSAGE).toString(), Toast.LENGTH_SHORT).show();
                    success = jsonObject.getInt(TAG_SUCCESS);
                    message = jsonObject.getString(TAG_MESSAGE);
                }catch (JSONException je){
                    je.printStackTrace();
                }
            }

            if (success == 1){
                FragmentManager manager = getFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new Contacts());
                fragmentTransaction.commit();
            }else {
                Log.d("Failure", message);
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Adding contact...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            try{
                HashMap<String, String> map = new HashMap<>();
                map.put("type", "addContact");
                map.put("contactPhoneNumber", number);
                map.put("cname", name);

                JSONObject jsonObject = jsonParser.makeHttpRequest(Constant.BASE_URL, "POST", map);

                if (jsonObject != null){
                    Log.e("JSON result", jsonObject.toString());
                    return jsonObject;
                }
            }catch (Exception sex){
                sex.printStackTrace();
            }
            return null;
        }
    }

}
