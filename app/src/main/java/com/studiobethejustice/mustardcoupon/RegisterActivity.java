package com.studiobethejustice.mustardcoupon;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class RegisterActivity extends AppCompatActivity {
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

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String userId = idText.getText().toString();
                String userPassword = passwordText.getText().toString();
                String userName = nameText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response){
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("회원 등록에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("회원 등록에 실패했습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }
                        }
                        catch(JSONException e){
                            e.printStackTrace();
                         }
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(userId, userPassword, userName, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });


        validateButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                userID = idText.getText().toString();
                if(validate){
                    return;
                }

                if(userID.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("아이디는 빈칸이 될 수 없습니다.")
                           .setNegativeButton("확인", null)
                           .create()
                           .show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try{
                            Log.e("22", response);
                            JSONObject jsonResponse = new JSONObject(response);
                            Log.e("22", response);
                            boolean success = jsonResponse.getBoolean("success");
                            Log.e("22", response);
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("사용할 수 있는 아이디 입니다")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                                idText.setEnabled(false);
                                validate=true;
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("사용할 수 없는 아이디 입니다")
                                        .setNegativeButton("확인", null)
                                        .create()
                                        .show();
                            }

                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };

                //ValidateRequest validateRequest = new ValidateRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                //queue.add(validateRequest);
            }
        });


        cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent2);
                finish();
            }
        });

    }
}
