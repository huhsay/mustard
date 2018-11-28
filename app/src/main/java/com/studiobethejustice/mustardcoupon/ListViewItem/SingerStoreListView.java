package com.studiobethejustice.mustardcoupon.ListViewItem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studiobethejustice.mustardcoupon.Member.SearchStore;
import com.studiobethejustice.mustardcoupon.MemberActivity;
import com.studiobethejustice.mustardcoupon.R;
import com.studiobethejustice.mustardcoupon.Store.ClientManagement;
import com.studiobethejustice.mustardcoupon.StoreActivity;
import com.studiobethejustice.mustardcoupon.Store_DBConn.Update;

/**
 * Created by eunsol on 2017-07-17.
 */

public class SingerStoreListView extends LinearLayout {

    private TextView tv_Name;
    private TextView tv_Phone;
    private Button Sign;

    private int pos;


    public SingerStoreListView(Context context, int position){
        super(context);
        init(context);

        pos = position; //리스트뷰의 몇번째 버튼을 클릭했는지

    }

    //부분 아이템 뷰 생성
    public SingerStoreListView(Context context, AttributeSet attrs){
        super(context,attrs);
        init(context);
    }

    public void init(Context context){

        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.store_listview,this,true);

        tv_Name= (TextView) findViewById(R.id.search_stName);
        tv_Phone = (TextView) findViewById(R.id.search_stPhone);

        Sign = (Button) findViewById(R.id.sign_in);

        Sign.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder delDialogBuilder = new AlertDialog.Builder(MemberActivity.context);

                delDialogBuilder.setTitle("쿠폰 발급");
                delDialogBuilder
                        .setMessage( SearchStore.adapter.getMember().get(pos).getStoreName()+"의 쿠폰을 발급하시겠습니까?")
                        .setCancelable(false).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(
                            DialogInterface dialog, int id) {
                        // 다이얼로그를 취소한다
                        dialog.cancel();

                    }
                })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {

                                Update db_Change = new Update();
                                Log.d("!!","!!"+SearchStore.userNum+SearchStore.listMember.get(pos).getKey());
                                db_Change.execute("http://168.131.35.106/main_member/signIn.php?Num=" + SearchStore.userNum + "&SNum=" + SearchStore.listMember.get(pos).getKey());

                                SearchStore.listMember.remove(pos);
                                SearchStore.adapter.getMember().remove(pos);
                                SearchStore.adapter.notifyDataSetChanged();
                            }
                        });

                AlertDialog signin = delDialogBuilder.create();
                signin.show();
            }
        });

    }

    public void setName(String name){
        tv_Name.setText(name);
    }

    public void setPhone(String stamp){
        tv_Phone.setText(stamp);
    }


}
