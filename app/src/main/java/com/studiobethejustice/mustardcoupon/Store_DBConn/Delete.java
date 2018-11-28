package com.studiobethejustice.mustardcoupon.Store_DBConn;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by eunsol on 2017-07-13.
 */

public class Delete extends AsyncTask<String,Void,String> {


    private String Url;

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