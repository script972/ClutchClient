package com.script972.clutchclient.mvp.impl;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.script972.clutchclient.R;
import com.script972.clutchclient.model.api.Company;
import com.script972.clutchclient.mvp.contracts.MapsContract;
import com.script972.clutchclient.ui.activitys.DiscountMapsActivity;

import java.util.List;

public class MapsPresenterImpl implements MapsContract.Presenter{
    private GoogleMap mapG;
    private CameraPosition cameraPosition;
    private BitmapDescriptor image;
    private Marker myPositionMarker;


    private FusedLocationProviderClient mFusedLocationClient;

    private final MapsContract.View view;
    private final Context context;



    public MapsPresenterImpl(DiscountMapsActivity discountMapsActivity) {
        this.view=discountMapsActivity;
        this.context=discountMapsActivity;
    }

    public MapsPresenterImpl(SupportMapFragment mapFragment, DiscountMapsActivity discountMapsActivity) {
        this.view=discountMapsActivity;
        this.context=discountMapsActivity;
        mapFragment.getMapAsync(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }


    @Override
    public void onLocationChanged(Location location) {
        myPositionMarker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
        myPositionMarker.setVisible(true);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i("myLocation", "onStatucChanged=" + provider + " Statuc=" + status);

    }

    @Override
    public void onProviderEnabled(String provider) {
        detectPosition();

        //mapG.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        //Log.i("myLocation", "ProviderEndbled provider=" + provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i("myLocation", "onProviderDisabled");
        myPositionMarker.remove();

    }

    @Override
    public void onCameraIdle() {

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
        initialVars();


        settingMap();
        //initializeVars();

        settingCamera();
        detectPosition();
        view.onMapReady();

    }

    private void initialVars() {
        image = bitmapSizeByScall(R.drawable.mylocationmarker, 0.1f);
        MarkerOptions myLocationMarker = new MarkerOptions().position(new LatLng(46.97, 32.02)).icon(image);
        myPositionMarker=mapG.addMarker(myLocationMarker);
        myPositionMarker.setVisible(false);
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
                            Log.i("myLocation", "myLocation=" + location);
                            cameraPosition = new CameraPosition.Builder()
                                    .target(new LatLng(location.getLatitude(), location.getLongitude()))        //Позиция на карты
                                    .zoom(15)                   //Зумм карты
                                    .bearing(0)                //Направление на север в градусах
                                    .tilt(45)                   //Градус на карту
                                    .build();

                            ;



                            mapG.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                            myPositionMarker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
                            myPositionMarker.setVisible(true);


                            // ...
                        } else {
                            Log.i("myLocation", "myLocation is null");

                        }

                    }
                });

    }

    private void settingMap() {

        mapG.setBuildingsEnabled(true);
        mapG.setOnCameraMoveListener(this);//Устанавливаем слушатель на передвижение карты
        mapG.setOnCameraIdleListener(this);







        mapG.getUiSettings().setCompassEnabled(false);//switch off compas
        mapG.getUiSettings().setRotateGesturesEnabled(false); //switch off rotation map
        mapG.getUiSettings().setZoomControlsEnabled(false);//switch off zoom button
        mapG.getUiSettings().setTiltGesturesEnabled(true);//switch on anchor map
        //  mapG.setPadding(0, 300, 0,0); //установка отступов для помещение в видимый район кнопки onMyPossition


        mapG.animateCamera(CameraUpdateFactory.zoomIn(), 1500, null);// анимирование зуум

        mapG.animateCamera(CameraUpdateFactory.zoomTo(12), 1500, null);// анимирование

/*

        //Подлключаем АПИ
        mGoogleApiClient = new GoogleApiClient
                .Builder(context)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(LocationServices.API)
                .enableAutoManage((FragmentActivity) context, this)
                .build();
        mGoogleApiClient.connect();
*/

        //positions
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i("myLocation", "MyPosition error");
            return;
        }
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 200, 20, this);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 200, 20, this);



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


    private Marker animateMarker(final Marker marker, final LatLng toPosition, final double vector,
                                 final boolean hideMarker) {
        Log.i("driverComing", "Driver in animateMarker");
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = mapG.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());

        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 6500;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                double route=t*vector+(1-t)*marker.getRotation();
                marker.setPosition(new LatLng(lat, lng));
                marker.setRotation((float) route);

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
        return marker;
    }

    public void fillDiscountPoints(List<Company> companyList) {
        for (int i = 0; i < companyList.size(); i++) {
            LatLng position=new LatLng(companyList.get(i).getPosition().getLat(), companyList.get(i).getPosition().getLng());
            mapG.addMarker(new MarkerOptions().position(position));
        }
    }

    @Override
    public void onCameraMoveCanceled() {

    }
}
