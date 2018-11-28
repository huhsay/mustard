package com.studiobethejustice.mustardcoupon.ListviewAdapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.studiobethejustice.mustardcoupon.ListViewItem.ClientManageItem;
import com.studiobethejustice.mustardcoupon.ListViewItem.SingerClientListView;



import java.util.ArrayList;

/**
 * Created by econo106 on 2017-07-07.
 */

public class ClientManageAdapter extends BaseAdapter {


    private ArrayList<ClientManageItem> listViewItemList=new ArrayList<ClientManageItem>();
    private int pos;
    private Context context;
    private String userNum;

    public ClientManageAdapter (Context context,String num) {
        this.userNum=num;
        this.context=context;
        Log.d("!!","!!");
    }

    //필수 선언 (데이터의 갯수를 리턴)
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    //리스트 뷰 화면에 출력될 뷰를 리턴
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        pos = position;
        final Context context = parent.getContext();

        SingerClientListView view = new SingerClientListView(context,position,userNum);
        ClientManageItem item = listViewItemList.get(position);

        //부분 뷰에 리스트 뷰 아이템 적용
        view.setName(item.getName());
        view.setStamp(item.getStamp());
        view.setCoupon(item.getCoupon());

        return view;
    }

    //지정한 위치에 있는 (row) 아이템 ID 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }


    //데이터 추가 함수 (나중에 타입 변경하기)
    public void addClient(ClientManageItem add) {

        ClientManageItem item = new ClientManageItem(add.getKey(),add.getId(),add.getCoupon(),add.getStamp() ,add.getName(),add.getPw()) ;

        listViewItemList.add(item);
    }

    //리스트 뷰 아이템 완전 삭제
    public void delete(){
        listViewItemList.clear();
    }


    public ArrayList<ClientManageItem> getMember() { return listViewItemList; }


}
