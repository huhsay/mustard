package com.studiobethejustice.mustardcoupon.ListViewItem;

/**
 * Created by eunsol on 2017-07-17.
 */

public class StoreListItem {

    String Key;
    String StoreName;
    String StorePhone;
    String StoreId;
    String StoreAddress;

    public StoreListItem(String Key,String Id,String Name,String Phone,String Address){
        this.Key=Key;
        StoreId=Id;
        StoreName=Name;
        StorePhone=Phone;
        StoreAddress=Address;
    }

    public void setStoreName(String Name) { StoreName =Name; }
    public void setStorePhone(String Phone) {StorePhone=Phone; }
    public void setStoreId(String Id) {StoreId= Id; }
    public void setStoreAddress(String address) {StoreAddress= address; }

    public String getKey() {return  Key;}
    public String getStoreName() {return StoreName; }
    public String getStorePhone() {return StorePhone; }
    public String getStoreId() {return StoreId; }
    public String getStoreAddress() {return StoreAddress; }

}
