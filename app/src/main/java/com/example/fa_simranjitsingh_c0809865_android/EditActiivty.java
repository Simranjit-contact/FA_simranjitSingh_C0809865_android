package com.example.fa_simranjitsingh_c0809865_android;
//Created by Simranjit Singh C0809865 on 16-02-2022

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class EditActiivty extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private GoogleMap mMap;
    String lat,lng,id;
    Marker mCurrLocationMarker;
    DbHelper dbHelper;
    AddPlaceModel addPlaceModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_actiivty);
        dbHelper=new DbHelper(EditActiivty.this);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            id=bundle.getString("ID");
            lat=bundle.getString("LAT");
            lng=bundle.getString("LNG");
            addPlaceModel=bundle.getParcelable("OBJ");

        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMarkerDragListener(this);

        //Place current location marker
        LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(getAddress(Double.parseDouble(lat),Double.parseDouble(lng)));
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        markerOptions.draggable(true);
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
    }

    private String getAddress(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.e("Current Address", strReturnedAddress.toString());
            } else {
                Log.e("Current Address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(EditActiivty.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return strAdd;
    }

    @Override
    public void onMarkerDrag(@NonNull Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(@NonNull Marker marker) {

        //mMap.clear();
        MarkerOptions markerOptions = new MarkerOptions().position(marker.getPosition()).title("I am here!").draggable(true);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 11));
        mMap.addMarker(markerOptions);

        dbHelper.update(addPlaceModel,getAddress(marker.getPosition().latitude, marker.getPosition().longitude),String.valueOf(marker.getPosition().latitude),String.valueOf(marker.getPosition().longitude),"false");
        Toast.makeText(EditActiivty.this, "Location Updated Successfully!", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onMarkerDragStart(@NonNull Marker marker) {

    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(EditActiivty.this, com.example.fa_simranjitsingh_c0809865_android.ShowPlaces.class));
        finish();
    }
}