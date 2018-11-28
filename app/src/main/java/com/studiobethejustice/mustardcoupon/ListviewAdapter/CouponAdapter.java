package com.studiobethejustice.mustardcoupon.ListviewAdapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.studiobethejustice.mustardcoupon.ListViewItem.SingerStoreListView;
import com.studiobethejustice.mustardcoupon.ListViewItem.StoreListItem;

import java.util.ArrayList;

/**
 * Created by eunsol on 2017-07-17.
 */

public class CouponAdapter extends BaseAdapter {

    private ArrayList<StoreListItem> listViewItemList=new ArrayList<StoreListItem>();
    private int pos;
    private Context context;


    public CouponAdapter(Context context) {
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

        SingerStoreListView view = new SingerStoreListView(context,position);
        StoreListItem item = listViewItemList.get(position);

        //부분 뷰에 리스트 뷰 아이템 적용
        view.setName(item.getStoreName());
        view.setPhone(item.getStorePhone());

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
    public void addStore(StoreListItem add) {

        StoreListItem item = new StoreListItem(add.getKey(),add.getStoreId(),add.getStoreName(),add.getStorePhone() ,add.getStoreAddress());

        listViewItemList.add(item);
    }

    //리스트 뷰 아이템 완전 삭제
    public void delete(){
        listViewItemList.clear();
    }


    public ArrayList<StoreListItem> getMember() { return listViewItemList; }
}
