package com.studiobethejustice.mustardcoupon.Store;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studiobethejustice.mustardcoupon.AndroidRequest.StoreDeleteIDRequest;
import com.studiobethejustice.mustardcoupon.AndroidRequest.StorePasswordChangeRequest;
import com.studiobethejustice.mustardcoupon.LoginActivity;
import com.studiobethejustice.mustardcoupon.R;

/**
 * Created by econo106 on 2017-07-03.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.studiobethejustice.mustardcoupon.StoreActivity;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class Setting extends Fragment {

    String userID;
    String userPassword;
    StoreActivity StoreActivity;


    public Setting() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        StoreActivity = (StoreActivity) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_st_setting, null);

        final TextView idText;
        final EditText passwordText;
        final EditText newPasswordText;
        Button changeButton;
        userID = getArguments().getString("Id");

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
                                StoreActivity.callFragment(1);

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

                // StorePasswordChangeRequest storePasswordChangeRequest = new StorePasswordChangeRequest(userID, userPassword, newPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                // queue.add(storePasswordChangeRequest);

            }


        });

        //회원탈퇴버튼
        TextView deleteButton= (TextView) view.findViewById(R.id.deleteId);
        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("정말로 탈퇴하시겠습니까?")
                        .setPositiveButton("확인", null)
                        .create()
                        .show();

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setMessage("탈퇴하였습니다.")
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

                StoreDeleteIDRequest storeDeleteIDRequest = new StoreDeleteIDRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(storeDeleteIDRequest);
            }
        });

        return view;
    }
}

