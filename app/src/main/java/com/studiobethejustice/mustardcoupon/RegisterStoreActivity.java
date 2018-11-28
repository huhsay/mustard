package com.studiobethejustice.mustardcoupon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.studiobethejustice.mustardcoupon.AndroidRequest.RegisterRequest;
import com.studiobethejustice.mustardcoupon.AndroidRequest.ValidateRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterStoreActivity extends AppCompatActivity {
    private boolean validate = false;
    private AlertDialog dialog;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText idText= (EditText) findViewById(R.id.idText);
        final EditText passwordText= (EditText) findViewById(R.id.passwordText);
        final EditText nameText= (EditText) findViewById(R.id.nameText);
        final Button validateButton = (Button) findViewById(R.id.membervalidate);
        Button registerButton = (Button) findViewById(R.id.registerButton);
        Button cancelButton = (Button)findViewById(R.id.cancelButton);


    }
}
