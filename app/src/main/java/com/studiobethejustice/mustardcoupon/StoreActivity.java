package com.studiobethejustice.mustardcoupon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.studiobethejustice.mustardcoupon.Store.Setting;

/**
 * Created by econo106 on 2017-07-03.
 */

public class StoreActivity extends AppCompatActivity {

    private final int ClientManagement = 1;
    private final int SETTING = 2;
    private String userID;
    private String userPW;
    private String userNum;
    public static Context context;
    Button clientMangement;
    Button setting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        Intent intent=getIntent();
//        userID=intent.getExtras().getString("userID");
//        userPW=intent.getExtras().getString("userPassword");
//        userNum= intent.getExtras().getString("ID");

        clientMangement= (Button) findViewById(R.id.bt_clientM);
        setting= (Button) findViewById(R.id.bt_setting);

        clientMangement.setOnClickListener(click);
        setting.setOnClickListener(click);
        context =StoreActivity.this;
        callFragment(ClientManagement);
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){

                case R.id.bt_clientM:
                    callFragment(ClientManagement);
                    break;

                case R.id.bt_setting:
                    callFragment(SETTING);
                    break;
            }
        }
    };

    public void callFragment(int frament_no){

        // 프래그먼트 사용을 위해
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        //프래그먼트에 데이터 전달
        Bundle bundle = new Bundle();
        bundle.putString("Id", userID);
        bundle.putString("Pw",userPW);
        bundle.putString("Num",userNum);

        switch (frament_no){
            case 1:
                // '프래그먼트1' 호출
               // com.studiobethejustice.mustardcoupon.Store.ClientManagement clientmanagement = new ClientManagement();
               // transaction.replace(R.id.fragment_con, clientmanagement);
                transaction.commit();
                //clientmanagement.setArguments(bundle);

                break;

            case 2:
                Setting setting = new Setting();
                transaction.replace(R.id.fragment_con,setting);
                transaction.commit();
                setting.setArguments(bundle);
                break;
        }
    }
}