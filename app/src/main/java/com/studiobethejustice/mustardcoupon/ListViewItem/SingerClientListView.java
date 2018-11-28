package com.studiobethejustice.mustardcoupon.ListViewItem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.studiobethejustice.mustardcoupon.R;
import com.studiobethejustice.mustardcoupon.Store.ClientManagement;
import com.studiobethejustice.mustardcoupon.StoreActivity;
import com.studiobethejustice.mustardcoupon.Store_DBConn.Update;

/**
 * Created by eunsol on 2017-07-12.
 */

public class SingerClientListView extends LinearLayout {

    private TextView tv_name;
    private TextView tv_coupon;
    private TextView tv_stamp;
    private Button Use;
    private Button Del;
    private Button Stamp;
    private int pos;
    private String userNum;


    public SingerClientListView(Context context,int position,String num){
        super(context);
        init(context);

        pos = position; //리스트뷰의 몇번째 버튼을 클릭했는지
        userNum = num; //현재 상점의 고유번호를 가져온다. (get 으로 보내기 위해)
    }

    //부분 아이템 뷰 생성
    public SingerClientListView(Context context, AttributeSet attrs){
        super(context,attrs);
        init(context);
    }

    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.client_listview,this,true);

        tv_name = (TextView) findViewById(R.id.content1);
        tv_stamp = (TextView) findViewById(R.id.content2);
        tv_coupon = (TextView) findViewById(R.id.content3);
        Stamp= (Button) findViewById(R.id.btn_1);
        Use = (Button) findViewById(R.id.btn_2);
        Del = (Button) findViewById(R.id.btn_3);

        Stamp.setOnClickListener(listener);
        Use.setOnClickListener(listener);
        Del.setOnClickListener(listener);

    }

    public void setName(String name){
        tv_name.setText(name);
    }

    public void setStamp(String stamp){
        tv_stamp.setText(stamp);
    }

    public void setCoupon(String coupon){
        tv_coupon.setText(coupon);
    }

    OnClickListener listener = new OnClickListener() {

        @Override
        public void onClick(View view) {

            switch (view.getId()){

                //쿠폰 발행 이벤트버튼
                case R.id.btn_1:
                    // 쿠폰 발행을 위한 다이얼로그를 띄운다.
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StoreActivity.context);

                    alertDialogBuilder.setTitle("쿠폰 발행");
                    alertDialogBuilder
                            .setMessage("쿠폰을 발행 하시겠습니까?")
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

                                    String Id= ClientManagement.adapter.getMember().get(pos).getKey();
                                    Log.d("!!","!"+Id+","+userNum);
                                    Update db_Change =new Update();

                                    //디비 업데이트
                                    db_Change.execute("http://168.131.35.106/main_store/updateCoupon.php?MID="+Id+"&SID="+userNum);

                                    int num =Integer.parseInt(ClientManagement.adapter.getMember().get(pos).getStamp());

                                    //스탬프와 쿠폰의 갯수를 조절하고 바뀐 데이터를 리스트뷰 데이터에 적용시킨다.
                                    if(num>=9){
                                        num = 0;
                                        String coupon = Integer.toString(Integer.parseInt(ClientManagement.adapter.getMember().get(pos).getCoupon()) +1);
                                        ClientManagement.adapter.getMember().get(pos).setCoupon(coupon);
                                        ClientManagement.listMember.get(pos).setCoupon(coupon);
                                    }else{
                                        num++;
                                    }

                                    String n=Integer.toString(num);
                                    ClientManagement.adapter.getMember().get(pos).setStramp(n);
                                    ClientManagement.listMember.get(pos).setStramp(n);
                                    //어댑터의 내용이 변하면 업데이트
                                    ClientManagement.adapter.notifyDataSetChanged();
                                }
                            });

                    AlertDialog alert = alertDialogBuilder.create();
                    alert.show();


                    break;

                //쿠폰 사용 이벤트 버튼
                case R.id.btn_2:

                    //커스텀 대화상자 생성
                    Context mContext = StoreActivity.context;
                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View layout = inflater.inflate(R.layout.customdialog,(ViewGroup)findViewById(R.id.layout_root));
                    final AlertDialog.Builder customDailog = new AlertDialog.Builder(StoreActivity.context);
                    customDailog.setTitle("회원 확인번호를 입력하세요");
                    customDailog.setView(layout);
                    //final EditText ePw = (EditText)layout.findViewById(R.id.pw_edit);
/*
                    customDailog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            if(ClientManagement.adapter.getMember().get(pos).getPw().equals(ePw.getText().toString())) {

                                String Id = ClientManagement.adapter.getMember().get(pos).getKey();
                                Log.d("!!", "!" + Id + "," + userNum);
                                Update db_Change = new Update();

                                //디비 업데이트
                                db_Change.execute("http://168.131.35.106/main_store/useCoupon.php?MID=" + Id + "&SID=" + userNum);
                                int num = Integer.parseInt(ClientManagement.adapter.getMember().get(pos).getCoupon());

                                num += 1;
                                String coupon = Integer.toString(Integer.parseInt(ClientManagement.adapter.getMember().get(pos).getCoupon()) -1);
                                ClientManagement.adapter.getMember().get(pos).setCoupon(coupon);
                                ClientManagement.listMember.get(pos).setCoupon(coupon);

                                //어댑터의 내용이 변하면 업데이트
                                ClientManagement.adapter.notifyDataSetChanged();

                                Toast.makeText(StoreActivity.context,"쿠폰을 사용합니다!",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(StoreActivity.context,"인증번호가 틀립니다/",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    */
                    customDailog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    if( Integer.parseInt(ClientManagement.adapter.getMember().get(pos).getCoupon()) >=1) {
                        AlertDialog custom = customDailog.create();
                        custom.show();


                    }else{
                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(StoreActivity.context);
                        builder.setMessage("쿠폰 갯수가 부족합니다..")
                                .setNegativeButton("다시시도", null)
                                .create()
                                .show();
                    }

                    break;

                case R.id.btn_3:

                    AlertDialog.Builder delDialogBuilder = new AlertDialog.Builder(StoreActivity.context);

                    delDialogBuilder.setTitle("회원 삭제");
                    delDialogBuilder
                            .setMessage("회원을 삭제하시면 정보가 모두 사라집니다 계속 하시겠습니까?")
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

                                    String Id = ClientManagement.adapter.getMember().get(pos).getKey();
                                    Update db_Change = new Update();

                                    db_Change.execute("http://168.131.35.106/main_store/deleteUser.php?MID=" + Id + "&SID=" + userNum);

                                    ClientManagement.adapter.getMember().remove(pos);
                                    ClientManagement.listMember.remove(pos);
                                    ClientManagement.adapter.notifyDataSetChanged();

                                }
                            });

                    AlertDialog delete = delDialogBuilder.create();
                    delete.show();

                    break;

            }
        }
    };

}
