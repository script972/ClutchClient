package com.script972.clutchclient.controller;

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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
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

/**
 * Created by script972 on 02.09.2017.
 */

public class DiscountMapsController implements GoogleMapsController {


    private Context context;
    private GoogleMap mapG;
    private GoogleApiClient mGoogleApiClient;
    private CameraPosition cameraPosition;
    private BitmapDescriptor image;
    private Marker myPositionMarker;

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
        Log.i("mylocation", "Changed location " + location.getLatitude() + " " + location.getLongitude());
        myPositionMarker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
        myPositionMarker.setVisible(true);
        Toast.makeText(context, "Changed location" + location.getLatitude() + " " + location.getLongitude(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i("myLocation", "onStatucChanged=" + provider + " Statuc=" + status);

    }

    @Override
    public void onProviderEnabled(String provider) {
        detectPosition();

        //mapG.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        Log.i("myLocation", "ProviderEndbled provider=" + provider);

    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i("myLocation", "onProviderDisabled");
        Log.i("myLocation", "onProviderDisabled "+myPositionMarker.isVisible());
        myPositionMarker.remove();



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
        initialVars();


        settingMap();
        //initializeVars();

        settingCamera();
        detectPosition();

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

        //позиционирование

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

}
