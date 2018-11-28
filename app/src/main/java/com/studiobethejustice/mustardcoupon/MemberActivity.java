package com.studiobethejustice.mustardcoupon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.studiobethejustice.mustardcoupon.Member.Mycoupon;
import com.studiobethejustice.mustardcoupon.Member.SearchStore;

public class MemberActivity extends AppCompatActivity implements View.OnClickListener{

    private final int FRAGMENT1 = 1;
    private final int FRAGMENT2 = 2;
    private final int FRAGMENT3 = 3;

    String userID;
    String userPassword;
    String userNum;

    private Button bt_tab1, bt_tab3, bt_tab4;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        userID=intent.getStringExtra("userID");
        userPassword=intent.getStringExtra("userPassword");
        userNum= intent.getStringExtra("userNum");
        Log.d("Member","!");

        // 위젯에 대한 참조
        bt_tab1 = (Button)findViewById(R.id.bt_tab1);
        bt_tab3 = (Button)findViewById(R.id.bt_tab3);
        bt_tab4 = (Button)findViewById(R.id.bt_tab4);

        // 탭 버튼에 대한 리스너 연결
        bt_tab1.setOnClickListener(this);
        bt_tab3.setOnClickListener(this);
        bt_tab4.setOnClickListener(this);

        // 임의로 액티비티 호출 시점에 어느 프레그먼트를 프레임레이아웃에 띄울 것인지를 정함

        context=MemberActivity.this;
        callFragment(FRAGMENT1);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_tab1 :
                // '버튼1' 클릭 시 '프래그먼트1' 호출

                callFragment(FRAGMENT1);
                break;

            case R.id.bt_tab3 :
                // '버튼2' 클릭 시 '프래그먼트2' 호출
                callFragment(FRAGMENT2);
                break;

            case R.id.bt_tab4 :
                // '버튼2' 클릭 시 '프래그먼트2' 호출
                callFragment(FRAGMENT3);
                break;
        }
    }

    public void callFragment(int frament_no){

        // 프래그먼트 사용을 위해
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        Bundle bundle= new Bundle();
        bundle.putString("userID", userID);
        bundle.putString("userPassword", userPassword);
        bundle.putString("userNum",userNum);

        switch (frament_no){
            case 1:

                // '프래그먼트1' 호출
                Mycoupon mycoupon = new Mycoupon();
                transaction.replace(R.id.fragment_container, mycoupon);
                transaction.commit();
                mycoupon.setArguments(bundle);

                break;

            case 2:
                // '프래그먼트2' 호출
                SearchStore searchStore = new SearchStore();

                searchStore.setArguments(bundle);
                transaction.replace(R.id.fragment_container,searchStore);
                transaction.commit();
                break;

            case 3:
                // '프래그먼트2' 호출
                Fragment4 fragment4 = new Fragment4();
                fragment4.setArguments(bundle);

                transaction.replace(R.id.fragment_container, fragment4);
                transaction.commit();
                break;


        }
    }

    private  long lastTimeBackPressed;

    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis() - lastTimeBackPressed < 1500){
            finish();
            return;
        }
        lastTimeBackPressed = System.currentTimeMillis();
    }
}