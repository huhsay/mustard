package com.studiobethejustice.mustardcoupon;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.studiobethejustice.mustardcoupon.AndroidRequest.LoginRequest;
import com.studiobethejustice.mustardcoupon.AndroidRequest.LoginStoreRequest;


import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    EditText idText;
    EditText passwordText;
    Button loginButton;
    TextView registerButton;
    String userGroup;
    int userGroupID;
    int radiobtn = 1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //액션바숨기기
        hideActionBar();
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(1);

        idText = (EditText) findViewById(R.id.idText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (TextView) findViewById(R.id.registerButton);

        RadioGroup rg = (RadioGroup) findViewById(R.id.rg);

        //화중 수정

        userGroupID = rg.getCheckedRadioButtonId();
        userGroup = ((RadioButton) findViewById(userGroupID)).getText().toString();

        RadioButton rd1 = (RadioButton) findViewById(R.id.memrber);
        final RadioButton rd2 = (RadioButton) findViewById(R.id.storemember);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton groupButton = (RadioButton) findViewById(checkedId);
                userGroup = groupButton.getText().toString();
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                dialog.setTitle("회원가입으로 이동")
                        .setMessage("가입하실 종류를 선택해주세요")
                        .setPositiveButton("일반회원", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
                                startActivity(intent1);
                            }
                        })
                        .setNegativeButton("사업자 회원", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent2 = new Intent(LoginActivity.this, RegisterStoreActivity.class);
                                startActivity(intent2);
                            }
                        });
                dialog.create();
                dialog.show();


            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.v("aa", "aa1");
                final String userID = idText.getText().toString();
                final String userPassword = passwordText.getText().toString();

                switch (userGroup) {
                    case "일반고객":
                        radiobtn = 1;


                        Intent intent = new Intent(LoginActivity.this, MemberActivity.class);
                        LoginActivity.this.startActivity(intent);


                        /*

                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);

                                    boolean success = jsonResponse.getBoolean("success");
                                    if (success && radiobtn == 1) {
                                        String userID = jsonResponse.getString("userID");
                                        String userPassword = jsonResponse.getString("userPassword");
                                        int userNum = jsonResponse.getInt("memberSeq");
                                        String userN = Integer.toString(userNum);
                                        Intent intent = new Intent(LoginActivity.this, MemberActivity.class);
                                        intent.putExtra("userID", userID);
                                        intent.putExtra("userPassword", userPassword);
                                        intent.putExtra("userNum", userN);

                                        Log.d("Member", "!!!!");
                                        LoginActivity.this.startActivity(intent);


                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                        builder.setMessage("로그인에 실패하였습니다.")
                                                .setNegativeButton("다시시도", null)
                                                .create()
                                                .show();
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };


                        LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                        queue.add(loginRequest);

                        */

                        break;

                    case "사업자":
                        radiobtn = 2;

                        Intent intent2 = new Intent(LoginActivity.this, StoreActivity.class);
                        LoginActivity.this.startActivity(intent2);

                        /*
                        Response.Listener<String> responseListener2 = new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if (success&&radiobtn==2) {
                                        String userID = jsonResponse.getString("userID");
                                        String userPassword = jsonResponse.getString("userPassword");
                                        int userNum = jsonResponse.getInt("memberSeq");
                                        String userN= Integer.toString(userNum);
                                        Intent intent = new Intent(LoginActivity.this,StoreActivity.class);
                                        intent.putExtra("userID", userID);
                                        intent.putExtra("userPassword", userPassword);
                                        intent.putExtra("ID",userN);

                                        LoginActivity.this.startActivity(intent);

                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                        builder.setMessage("로그인에 실패하였습니다.")
                                                .setNegativeButton("다시시도", null)
                                                .create()
                                                .show();
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        //LoginStoreRequest loginStoreRequest = new LoginStoreRequest(userID, userPassword, responseListener2);
                        //queue = Volley.newRequestQueue(LoginActivity.this);
                        //queue.add(loginStoreRequest);
*/
                        break;


                }
            }
        });
    }


//        rd1.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                radiobtn = 1;
//
//                loginButton.setOnClickListener(new View.OnClickListener(){
//
//                    @Override
//                    public void onClick(View v) {
//                        Log.v("aa","aa1");
//                        final String userID = idText.getText().toString();
//                        final String userPassword = passwordText.getText().toString();
//
//                        Response.Listener<String> responseListener = new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                try {
//                                    JSONObject jsonResponse = new JSONObject(response);
//                                    boolean success = jsonResponse.getBoolean("success");
//                                    if (success&&radiobtn==1) {
//                                        String userID = jsonResponse.getString("userID");
//                                        String userPassword = jsonResponse.getString("userPassword");
//                                        int userNum = jsonResponse.getInt("memberSeq");
//                                        String userN= Integer.toString(userNum);
//                                        Intent intent = new Intent(LoginActivity.this, MemberActivity.class);
//                                        intent.putExtra("userID", userID);
//                                        intent.putExtra("userPassword", userPassword);
//                                        intent.putExtra("userNum",userN);
//
//                                        Log.d("Member","!!!!");
//                                        LoginActivity.this.startActivity(intent);
//
//
//                                    } else {
//                                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
//                                        builder.setMessage("로그인에 실패하였습니다.")
//                                                .setNegativeButton("다시시도", null)
//                                                .create()
//                                                .show();
//                                    }
//
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        };
//
//                        LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
//                        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
//                        queue.add(loginRequest);
//                    }
//                });
//            }
//        });
//
//
//        // 화중 수정
//
//        rd2.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                radiobtn = 2;
//
//                loginButton.setOnClickListener(new View.OnClickListener(){
//
//                    @Override
//                    public void onClick(View v) {
//                        final String userID = idText.getText().toString();
//                        final String userPassword = passwordText.getText().toString();
//
//                        Response.Listener<String> responseListener = new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                try {
//                                    JSONObject jsonResponse = new JSONObject(response);
//                                    boolean success = jsonResponse.getBoolean("success");
//                                    if (success&&radiobtn==2) {
//                                        String userID = jsonResponse.getString("userID");
//                                        String userPassword = jsonResponse.getString("userPassword");
//                                        int userNum = jsonResponse.getInt("memberSeq");
//                                        String userN= Integer.toString(userNum);
//                                        Intent intent = new Intent(LoginActivity.this,StoreActivity.class);
//                                        intent.putExtra("userID", userID);
//                                        intent.putExtra("userPassword", userPassword);
//                                        intent.putExtra("ID",userN);
//
//                                        LoginActivity.this.startActivity(intent);
//
//                                    } else {
//                                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
//                                        builder.setMessage("로그인에 실패하였습니다.")
//                                                .setNegativeButton("다시시도", null)
//                                                .create()
//                                                .show();
//                                    }
//
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        };
//
//                        LoginStoreRequest loginRequest = new LoginStoreRequest(userID, userPassword, responseListener);
//                        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
//                        queue.add(loginRequest);
//                    }
//                });
//            }
//        });
//    }

    //액션바 숨기는 메소드

    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.hide();
        }
    }


}
