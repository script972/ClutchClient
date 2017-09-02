package com.devas.bear.clutchclient.controller;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.devas.bear.clutchclient.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * Created by script972 on 02.09.2017.
 */

public class DiscountMapsController implements GoogleMapsController {


    private Context context;
    private GoogleMap mapG;
    private GoogleApiClient mGoogleApiClient;
    private  CameraPosition cameraPosition;

    private FusedLocationProviderClient mFusedLocationClient;


    public DiscountMapsController(Context context, SupportMapFragment mapG) {
        this.context = context;
        mapG.getMapAsync(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

    }


    @Override
    public void fillPoint() {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onCameraIdle() {

    }

    @Override
    public void onCameraMoveCanceled() {

    }

    @Override
    public void onCameraMove() {

    }

    @Override
    public void onCameraMoveStarted(int i) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mapG = googleMap;
        settingMap();
        //initializeVars();

        settingCamera();
        detectPosition();

    }

    private void detectPosition() {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener((Activity) context, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            Log.i("myLocation", "myLocation="+location);
                            cameraPosition=new CameraPosition.Builder()
                                    .target(new LatLng(location.getLatitude(), location.getLongitude()))        //Позиция на карты
                                    .zoom(15)                   //Зумм карты
                                    .bearing(0)                //Направление на север в градусах
                                    .tilt(45)                   //Градус на карту
                                    .build();;

                            BitmapDescriptor image=bitmapSizeByScall(R.drawable.mylocationmarker,0.1f);
                            mapG.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                            mapG.addMarker(new MarkerOptions()
                                                        .position(new LatLng(location.getLatitude(), location.getLongitude())))
                                                        .setIcon(image);

                            // ...
                        }else{
                            Log.i("myLocation", "myLocation is null");

                        }

                    }
                });
    }

    private void settingMap() {

        mapG.setBuildingsEnabled(true);
        mapG.setOnCameraMoveListener(this);//Устанавливаем слушатель на передвижение карты
        mapG.setOnCameraMoveCanceledListener(this);
        mapG.setOnCameraMoveListener(this);
        mapG.setOnCameraIdleListener(this);

        mapG.getUiSettings().setCompassEnabled(false);//отключаем компас
        mapG.getUiSettings().setRotateGesturesEnabled(false); //отключаем вращение карты
        mapG.getUiSettings().setZoomControlsEnabled(false);//отключаем елементы зуум карты
        mapG.getUiSettings().setTiltGesturesEnabled(true);//накол угла карты
        //  mapG.setPadding(0, 300, 0,0); //установка отступов для помещение в видимый район кнопки onMyPossition

        mapG.animateCamera(CameraUpdateFactory.zoomIn(), 1500, null);// анимирование зуум

        mapG.animateCamera(CameraUpdateFactory.zoomTo(12), 1500, null);// анимирование


        //Подлключаем АПИ
        mGoogleApiClient = new GoogleApiClient
                .Builder(context)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(LocationServices.API)
                .enableAutoManage((FragmentActivity) context, this)
                .build();

    }

    private void settingCamera() {
        LatLng curentTemp;
       /* if(myPosistion!=null){
            Log.i("currentPosition", "myPosBefore "+myPosistion.getLatitude()+";"+myPosistion.getLongitude());
            curentTemp=new LatLng(myPosistion.getLatitude(), myPosistion.getLongitude());
        }
        else {
            curentTemp=currentCity;
        }*/
        //   Log.i("currentPosition", curentTemp.latitude+";"+curentTemp.longitude);

        cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(46.976303, 31.992626))        //Позиция на карты
                .zoom(15)                   //Зумм карты
                .bearing(0)                //Направление на север в градусах
                .tilt(45)                   //Градус на карту
                .build();
        //mapG.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mapG.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    private BitmapDescriptor bitmapSizeByScall(int bitmapIn, float scall_zero_to_one_f) {
        Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(), bitmapIn);
        Bitmap bitmapOut = Bitmap.createScaledBitmap(bitmap,
                Math.round(bitmap.getWidth() * scall_zero_to_one_f),
                Math.round(bitmap.getHeight() * scall_zero_to_one_f), false);

        return BitmapDescriptorFactory.fromBitmap(bitmapOut);

    }

}
