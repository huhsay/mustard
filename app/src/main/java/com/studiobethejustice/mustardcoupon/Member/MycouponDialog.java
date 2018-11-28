package com.studiobethejustice.mustardcoupon.Member;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.studiobethejustice.mustardcoupon.MapActivity;
import com.studiobethejustice.mustardcoupon.MemberActivity;
import com.studiobethejustice.mustardcoupon.R;

import java.io.IOException;
import java.util.List;

/**
 * Created by eunsol on 2017-07-17.
 */

public class MycouponDialog extends DialogFragment {

    private  String stroePhone;
    private String storeAddress;
    public static String storeName;
    private String coupon;
    public static double latitude=0;
    public static double longitude=0;

    //  final Geocoder geocoder = new Geocoder(getActivity());
    // List<Address> list =null;
    public MycouponDialog() {
        // Required empty public constructor
    }

    public MycouponDialog (String Name, String Phone, String address, String coupon){

        storeName=Name;
        stroePhone=Phone;
        storeAddress=address;
        this.coupon=coupon;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.customstoredialog,null);
        builder.setView(view);
        builder.setTitle(storeName);

        final Button go_map = (Button) view.findViewById(R.id.go_map);
        final TextView tv_phone = (TextView) view.findViewById(R.id.tx_storephone);
        final TextView tv_address = (TextView) view.findViewById(R.id.tx_storeaddress);
        final TextView tv_coupon = (TextView) view.findViewById(R.id.tx_coupon);


        tv_phone.setText(stroePhone);
        tv_address.setText(storeAddress);
        tv_coupon.setText(coupon);



        go_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getPoint(storeAddress);
                Intent intent= new Intent(MemberActivity.context, MapActivity.class);
                MemberActivity.context.startActivity(intent);
            }
        });


        return builder.create();
    }

    public static void getPoint(String address){

        Geocoder geocoder = new Geocoder(MemberActivity.context);
        List<Address> addr= null;

        try{
            addr=geocoder.getFromLocationName(address,5);
        } catch(IOException e){
            e.printStackTrace();
        }

        if(addr!=null){
            for(int i=0;i<addr.size();i++){
                Address lating = addr.get(i);

                latitude=lating.getLatitude();
                longitude=lating.getLongitude();
            }
        }

    }
}
