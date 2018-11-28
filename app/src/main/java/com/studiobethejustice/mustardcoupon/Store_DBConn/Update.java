package com.studiobethejustice.mustardcoupon.Store_DBConn;

import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewDebug;

import com.studiobethejustice.mustardcoupon.ListViewItem.ClientManageItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by eunsol on 2017-07-11.
 */

public class Update extends AsyncTask<String,Void,String>    {

    private ClientManageItem updateMember;
    private String Url;
    private static final String TAG_Response="response";
    private static final String TAG_ClientID="client";
    private static final String TAG_Coupon="coupon";
    private static final String TAG_Name="name";
    private static final String TAG_pw="pw";
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        Url= strings[0];
        BufferedReader br = null;


        try {

            URL url = new URL(Url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            StringBuilder sb = new StringBuilder();
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String json;
            while((json = br.readLine()) != null) {
                sb.append(json+"\n");
            }



            return sb.toString().trim();

        }catch (Exception e) {
            return null;
        }

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);


    }
}
