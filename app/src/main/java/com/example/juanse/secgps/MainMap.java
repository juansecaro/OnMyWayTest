package com.example.juanse.secgps;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Juanse on 17/05/2015.
 */
public class MainMap extends FragmentActivity {

    private static final LatLng CHANDLER = new LatLng(33.455,-112.0668);

    LatLng point1 = new LatLng(33.123123, -127.123123);
    LatLng point2 = new LatLng(33.455,-112.0668);
    LatLng point3 = new LatLng(33.3333,-111.8335);
    LatLng point4 = new LatLng(33.4296,-111.9436);
    LatLng point5 = new LatLng(33.4152,-111.8315);
    LatLng point6 = new LatLng(33.3525,-111.7896);

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmap);


        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mainnmap)).getMap();
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CHANDLER, ZOOM_LEVEL));
        Drawable iconDrawable = getResources().getDrawable(R.drawable.left_arrow);
        Bitmap iconBmp = ((BitmapDrawable) iconDrawable).getBitmap();

        mMap.addMarker(new MarkerOptions()
                .position(point1)
                .icon(BitmapDescriptorFactory.fromBitmap(iconBmp)));

        mMap.addMarker(new MarkerOptions()
                .position(point2)
                .icon(BitmapDescriptorFactory.fromBitmap(iconBmp)));
        mMap.addMarker(new MarkerOptions()
                .position(point3)
                .icon(BitmapDescriptorFactory.fromBitmap(iconBmp)));

    }
}