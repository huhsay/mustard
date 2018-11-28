package com.studiobethejustice.mustardcoupon.ListViewItem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.studiobethejustice.mustardcoupon.MapActivity;
import com.studiobethejustice.mustardcoupon.Member.Mycoupon;
import com.studiobethejustice.mustardcoupon.Member.SearchStore;
import com.studiobethejustice.mustardcoupon.MemberActivity;
import com.studiobethejustice.mustardcoupon.R;
import com.studiobethejustice.mustardcoupon.StoreActivity;
import com.studiobethejustice.mustardcoupon.Store_DBConn.Update;

/**
 * Created by eunsol on 2017-07-14.
 */

public class SingerCouponListView extends LinearLayout {

    private TextView tv_Store;
    private TextView tv_Coupon;
    private TextView tv_Stamp;
    private LinearLayout layout;
    private Button Del;

    private int pos;


    public SingerCouponListView(Context context, int position) {
        super(context);
        init(context);

        pos = position; //리스트뷰의 몇번째 버튼을 클릭했는지

    }

    //부분 아이템 뷰 생성
    public SingerCouponListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.mycoupon_listview, this, true);
        layout = (LinearLayout) findViewById(R.id.mycoupon_background);
        tv_Store = (TextView) findViewById(R.id.tb_store);
        tv_Coupon = (TextView) findViewById(R.id.tb_coupon);

        Del = (Button) findViewById(R.id.go_use);

        Del.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder delDialogBuilder = new AlertDialog.Builder(MemberActivity.context);

                delDialogBuilder.setTitle("쿠폰 삭제");
                /*
                delDialogBuilder
                        .setMessage(Mycoupon.adapter.getMember().get(pos).getStoreName() + "의 쿠폰을 삭제하시겠습니까?")
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

                                db_Change.execute("http://168.131.35.106/main_member/deleteCoupon.php?userID=" + SearchStore.userNum + "&storeID=" + Mycoupon.adapter.getMember().get(pos).getId());

                                Mycoupon.adapter.getMember().remove(pos);

                                Mycoupon.adapter.notifyDataSetChanged();

                                Toast.makeText(MemberActivity.context, "삭제를 완료했습니다!", Toast.LENGTH_SHORT).show();
                            }
                        });
*/
                AlertDialog del = delDialogBuilder.create();
                del.show();
            }
        });
    }

    public void setName(String name) {
        tv_Store.setText(name);
    }

    public void setCoupon(String coupon) {
        tv_Coupon.setText(coupon);
    }

    public void setBackground(int count) {
        switch (count) {
            case 0:
                layout.setBackgroundResource(R.drawable.c);
                break;
            case 1:
                layout.setBackgroundResource(R.drawable.c1);
                break;
            case 2:
                layout.setBackgroundResource(R.drawable.c2);
                break;
            case 3:
                layout.setBackgroundResource(R.drawable.c3);
                break;
            case 4:
                layout.setBackgroundResource(R.drawable.c4);
                break;
            case 5:
                layout.setBackgroundResource(R.drawable.c5);
                break;
            case 6:
                layout.setBackgroundResource(R.drawable.c6);
                break;
            case 7:
                layout.setBackgroundResource(R.drawable.c7);
                break;
            case 8:
                layout.setBackgroundResource(R.drawable.c8);
                break;
            case 9:
                layout.setBackgroundResource(R.drawable.c9);
                break;
            case 10:
                layout.setBackgroundResource(R.drawable.c10);
                break;


        }
    }


}
