package com.studiobethejustice.mustardcoupon.ListViewItem;

import android.util.Log;

/**
 * Created by econo106 on 2017-07-07.
 */

public class ClientManageItem {

    private String key;
    private String clinetId;
    private String stamp;
    private String coupon;
    private String userName;
    private String pw;

    public ClientManageItem(String num,String ID,String Stamp,String Coupon,String UserName,String Pw) {

        key=num;
        clinetId=ID;
        stamp=Stamp;
        coupon=Coupon;
        this.userName=UserName;
        this.pw=Pw;
    }

    public void setClinetId(String ID) { this.clinetId = ID; }
    public void setStramp(String num) {this.stamp=num;  }
    public void setCoupon(String num) {this.coupon=num; }

    public void SetClientManageItem(String ID,String Stamp,String Coupon,String UserName,String Pw){

        this.clinetId = ID;
        this.stamp = Stamp;
        this.coupon =Coupon;
        this.userName = UserName;
        this.pw = Pw;

    }

    public String getId() {return clinetId;}
    public String getStamp() {return stamp;}
    public String getCoupon() {return coupon;}
    public String getName() {return userName;}
    public String getPw() {return pw;}
    public String getKey() {return key;}
}
