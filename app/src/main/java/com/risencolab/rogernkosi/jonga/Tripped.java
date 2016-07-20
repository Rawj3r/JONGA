package com.risencolab.rogernkosi.jonga;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
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
public class Tripped extends Fragment implements View.OnClickListener{

    private Button makeCall, falseAlarm;

    public static final String TEL = "09876545678";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_tripped, container, false);
        makeCall = (Button)view.findViewById(R.id.call_police);
        falseAlarm = (Button)view.findViewById(R.id.false_alarm);
        falseAlarm.setOnClickListener(this);
        makeCall.setOnClickListener(this);

        // add PhoneStateListener
        PhoneCallListener phoneListener = new PhoneCallListener();
        TelephonyManager telephonyManager = (TelephonyManager)getContext().getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        return view;
    }

    //monitor phone call activities
    private class PhoneCallListener extends PhoneStateListener {

        private boolean isPhoneCalling = false;

        String LOG_TAG = "LOGGING 123";

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {
                // phone ringing
                Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                // active
                Log.i(LOG_TAG, "OFFHOOK");

                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                // run when class initial and phone call ended,
                // need detect flag from CALL_STATE_OFFHOOK
                Log.i(LOG_TAG, "IDLE");

                if (isPhoneCalling) {

                    Log.i(LOG_TAG, "restart app");

                    // restart app
                    Intent i = getContext().getPackageManager()
                            .getLaunchIntentForPackage(
                                    getContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                    isPhoneCalling = false;
                }

            }
        }
    }

    private void call() {
        String number = "10111";
        Uri number1 = Uri.parse("tel:" + number);
        Intent dial = new Intent(Intent.ACTION_DIAL);
        dial.setData(number1);
        startActivity(dial);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.call_police){
            call();
        }else if (id == R.id.false_alarm){
            new UpdateUnit().execute("2");
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
            map.put("status", params[0]);

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
