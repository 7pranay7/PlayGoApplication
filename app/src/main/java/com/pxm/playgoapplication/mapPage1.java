package com.pxm.playgoapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class mapPage1 extends AppCompatActivity implements OnMapReadyCallback {

    boolean isPermissionGranted;
    GoogleMap mGoogleMap;
    private int GPS_REQUEST_CODE=9001;
    EditText locSearch;
    ImageView searchIcon;
    ExtendedFloatingActionButton select;

    String locationName;
    Double latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_page1);

        locSearch=findViewById(R.id.et_search);
        searchIcon=findViewById(R.id.search_icon);
        select=findViewById(R.id.select_location);


        checkMyPermission();

        if(isPermissionGranted){
            if(isGPSenable()) {
                SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);
                supportMapFragment.getMapAsync(this);
            }
        }

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationName = locSearch.getText().toString();
                Geocoder geocoder=new Geocoder(mapPage1.this, Locale.getDefault());
                try {
                    List<Address> addressList = geocoder.getFromLocationName(locationName, 1);
                    if(addressList.size()>0){
                        Address address=addressList.get(0);
                        goToLocation(address.getLatitude(),address.getLongitude());
                        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(address.getLatitude(),address.getLongitude())));

                        Toast.makeText(mapPage1.this, address.getLocality(), Toast.LENGTH_SHORT).show();

                    }

                }catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationName = locSearch.getText().toString();
                Geocoder geocoder=new Geocoder(mapPage1.this, Locale.getDefault());
                try {
                    List<Address> addressList = geocoder.getFromLocationName(locationName, 1);
                    if(addressList.size()>0){
                        Address address=addressList.get(0);
                        goToLocation(address.getLatitude(),address.getLongitude());
                        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(address.getLatitude(),address.getLongitude())));

                        latitude=address.getLatitude();
                        longitude=address.getLongitude();

                        Intent intent=new Intent(mapPage1.this,Hook.class);
                        intent.putExtra("locname",locationName);
                        intent.putExtra("lat",latitude);
                        intent.putExtra("long",longitude);

                        startActivity(intent);

                        Toast.makeText(mapPage1.this, "Location Saved", Toast.LENGTH_SHORT).show();

                    }

                }catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });


    }


    private void goToLocation(double latitude,double longitude){
        LatLng LatLng=new LatLng(latitude,longitude);
        CameraUpdate cameraUpdate= CameraUpdateFactory.newLatLngZoom(LatLng,18);
        mGoogleMap.moveCamera(cameraUpdate);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }


    private boolean isGPSenable(){
        LocationManager locationManager=(LocationManager) getSystemService(LOCATION_SERVICE);

        boolean providerEnable=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(providerEnable){
            return true;
        }
        else{
            AlertDialog alertDialog=new AlertDialog.Builder(this).setTitle("GPS is required").setMessage("Enable GPS?")
                    .setPositiveButton("Yes",((dialogInterface, i) -> {
                        Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent,GPS_REQUEST_CODE);
                    }))
                    .setCancelable(false).show();
        }
        return false;

    }

    public void checkMyPermission(){
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                Toast.makeText(mapPage1.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                isPermissionGranted=true;

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Intent intent=new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);

                Toast.makeText(mapPage1.this, "Location permission needed", Toast.LENGTH_SHORT).show();

                Uri uri = Uri.fromParts("package",getPackageName(),"");
                intent.setData(uri);
                startActivity(intent);

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();

            }
        }).check();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GPS_REQUEST_CODE){
            LocationManager locationManager=(LocationManager) getSystemService(LOCATION_SERVICE);

            boolean providerEnable =locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if(providerEnable){
                Toast.makeText(this, "GPS enabled", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "GPS not enabled", Toast.LENGTH_SHORT).show();
            }
        }
    }
}