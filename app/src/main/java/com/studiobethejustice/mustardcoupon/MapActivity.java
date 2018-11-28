package com.studiobethejustice.mustardcoupon;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.studiobethejustice.mustardcoupon.Member.MycouponDialog;
import com.studiobethejustice.mustardcoupon.NMapResource.NMapPOIflagType;
import com.studiobethejustice.mustardcoupon.NMapResource.NMapViewerResourceProvider;

/**
 * Created by eunsol on 2017-07-15.
 */

public class MapActivity extends NMapActivity implements NMapView.OnMapStateChangeListener{

    private NMapView mMapView;
    private final String CLIENT_ID="oRzjmXJ1AsOpz0nIIi3t";

    NMapController nMapController = null;
    LinearLayout MapContainer;

    NMapViewerResourceProvider mMapViewerResourceProvider = null;
    NMapOverlayManager mOverlayManager;
    private NMapOverlayManager.OnCalloutOverlayListener onCalloutOverlayListener;

    private double latitude;
    private double longitude;
    private double mylat;
    private double mylong;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
           setContentView(R.layout.activity_map);


        Log.d("!!","!!1!!!!");
        MapContainer = (LinearLayout) findViewById(R.id.MapContainer);

        mMapView=new NMapView(this);
        nMapController = mMapView.getMapController();
        mMapView.setApiKey(CLIENT_ID);
        MapContainer.addView(mMapView);
        mMapView.setClickable(true);
        mMapView.setBuiltInZoomControls(true,null);
        mMapView.setOnMapStateChangeListener(this);

        latitude= MycouponDialog.latitude;
        longitude=MycouponDialog.longitude;

        //com.studiobethejustice.mustardcoupon.GpsInfo gps = new com.studiobethejustice.mustardcoupon.GpsInfo(this);
        //mylat=gps.getLatitude();
        //mylong=gps.getLongitude();

        mMapViewerResourceProvider = new NMapViewerResourceProvider(this);

        mOverlayManager = new NMapOverlayManager(this, mMapView, mMapViewerResourceProvider);




        int markerId = NMapPOIflagType.PIN;
        NMapPOIdata poiData = new NMapPOIdata(2, mMapViewerResourceProvider);
        poiData.beginPOIdata(2);
        poiData.addPOIitem(longitude, latitude ,MycouponDialog.storeName, markerId, 0);    //요기 좌표 입력해주면, 그 좌표가 표시됩니다.
        poiData.addPOIitem(mylong, mylat, "내 위치", markerId, 0);             //요기 좌표 입력해주면, 그 좌표가 표시됩니다.
        poiData.endPOIdata();

        NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);
        poiDataOverlay.showAllPOIdata(0);




    }


    @Override
    public void onMapInitHandler(NMapView nMapView, NMapError nMapError) {

    }



    //지도 레벨 변경 시 호출된다.
    @Override
    public void onZoomLevelChange(NMapView nMapView, int i) {

    }


    //지도 중심 변경시 호출 된다. (중심 좌표 파라미터 전달.)
    @Override
    public void onMapCenterChange(NMapView nMapView, NGeoPoint nGeoPoint) {

    }

    //지도 애니메이션 상태 변경 시 호출출
    @Override    public void onAnimationStateChange(NMapView nMapView, int i, int i1) {

    }

    @Override
    public void onMapCenterChangeFine(NMapView nMapView) {

    }

}
