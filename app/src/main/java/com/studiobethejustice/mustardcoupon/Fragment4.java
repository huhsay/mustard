package com.studiobethejustice.mustardcoupon;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.studiobethejustice.mustardcoupon.AndroidRequest.DeleteIDRequest;
import com.studiobethejustice.mustardcoupon.AndroidRequest.LoginRequest;
import com.studiobethejustice.mustardcoupon.AndroidRequest.PasswordChangeRequest;
import com.studiobethejustice.mustardcoupon.Store.ClientManagement;
import com.studiobethejustice.mustardcoupon.Store_DBConn.Update;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment4 extends Fragment {

    String userID;
    String userPassword;
    MemberActivity memberActivity;


    public Fragment4() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        memberActivity = (MemberActivity) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    @Override
    //   public void onActivityCreated(Bundle b){
    //       super .onActivityCreated(b);
//
//
//                Response.Listener<String> responseListener = new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            Log.e("aa","2");
//                            JSONObject jsonResponse = new JSONObject(response);
//                            Log.e("aa","3");
//                            boolean success = jsonResponse.getBoolean("success");
//                            Log.e("aa","4");
//                            if (success) {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                                builder.setMessage("비밀번호가 변경되었습니다.")
//                                        .setPositiveButton("확인", null)
//                                        .create()
//                                        .show();
//                            } else {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                                builder.setMessage("로그인에 실패하였습니다.")
//                                        .setNegativeButton("다시시도", null)
//                                        .create()
//                                        .show();
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                };
//
//                PasswordChangeRequest passwordChangeRequest = new PasswordChangeRequest(userID, userPassword, newPassword, responseListener);
//                RequestQueue queue = Volley.newRequestQueue(getActivity());
//                queue.add(passwordChangeRequest);
    // }
    //   });
//
//
    //   }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment4, null);

        final TextView idText;
        final EditText passwordText;
        final EditText newPasswordText;
        Button changeButton;
        final Button cancelButton;
        userID = getArguments().getString("userID");

        idText = (TextView) view.findViewById(R.id.idText);
        idText.setText(userID);
        passwordText = (EditText) view.findViewById(R.id.userPassword);
        newPasswordText = (EditText) view.findViewById(R.id.newPassword);
        changeButton = (Button) view.findViewById(R.id.changeButton);

        changeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();
                String newPassword = newPasswordText.getText().toString();

                if (userPassword.equals("") || newPassword.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("비밀번호를 입력해주세요")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setMessage("비밀번호가 변경되었습니다.")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                                memberActivity.callFragment(1);

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setMessage("비밀번호가 틀렸습니다.")
                                        .setNegativeButton("다시시도", null)
                                        .create()
                                        .show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                //PasswordChangeRequest passwordChangeRequest = new PasswordChangeRequest(userID, userPassword, newPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                //queue.add(passwordChangeRequest);

            }


        });

        //취소버튼
//        cancelButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                memberActivity.callFragment(1);
//            }
//        });


        //회원탈퇴버튼
        TextView deleteButton= (TextView) view.findViewById(R.id.deleteId);
        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder
                        .setMessage("정말로 탈퇴하겠습니까?")
                        .setCancelable(false).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(
                            DialogInterface dialog, int id) {
                        // 다이얼로그를 취소한다
                        dialog.cancel();
                    }
                })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Response.Listener<String> responseListener = new Response.Listener<String>() {

                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonResponse = new JSONObject(response);
                                            boolean success = jsonResponse.getBoolean("success");
                                            if (success) {
                                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                                builder.setMessage("아이디를 삭제하였습니다.")
                                                        .setPositiveButton("확인", null)
                                                        .create()
                                                        .show();
                                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                                getActivity().startActivity(intent);
                                            }

                                        }
                                        catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };

                                DeleteIDRequest deleteIDRequest = new DeleteIDRequest(userID, responseListener);
                                RequestQueue queue = Volley.newRequestQueue(getActivity());
                                queue.add(deleteIDRequest);

                            }
                        });

                AlertDialog alert = alertDialogBuilder.create();
                alert.show();


            }
        });


        return view;
    }
}
