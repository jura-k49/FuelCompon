package net.ukr.jura.fuelcompon.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import net.ukr.jura.compon.base.BaseFragment;
import net.ukr.jura.fuelcompon.R;

public class MapFragment extends BaseFragment
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleMap.OnCameraChangeListener,
        GoogleApiClient.OnConnectionFailedListener,
        OnMapReadyCallback {

    private GoogleApiClient mGoogleApiClient;
    private GoogleMap googleMap;
    private float LEVEL_ZOOM = 16.0f;
    private Double latOrign = null;
    private Double lonOrign = null;
//    private String marker;

    MapView mapView;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_map;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
//        marker = bundle.getString(SyncStateContract.Constants.MARKER);
        initGoogleLocationService();
//        mapView = (MapView) parentLayout.findViewById(R.id.map);
//        mapView.onCreate(savedInstanceState);
//        mapView.onResume();
//        mapView.getMapAsync(this);
//        try {
//            MapsInitializer.initialize(getActivity().getApplicationContext());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    public void onPause() {
        mGoogleApiClient.disconnect();
        super.onPause();
    }

    private void initGoogleLocationService() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getLocationData();
    }

    private void getLocationData() {
        if (mGoogleApiClient.isConnected()) {
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setNumUpdates(1);
            if (ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, locationListener);
        }
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();
//                if ((latOrign == null || lonOrign == null)
//                        || (lat != latOrign || lon != lonOrign)) {
//                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), LEVEL_ZOOM));
//                }
                latOrign = lat;
                lonOrign = lon;
//                decodeLocationData(location);
            }
        }
    };

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        LatLng pos = cameraPosition.target;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
