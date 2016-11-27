package com.example.root.mapdemo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.root.mapdemo.utils.HttpsTrustManager;
import com.example.root.mapdemo.utils.Model;
import com.example.root.mapdemo.utils.Office;
import com.example.root.mapdemo.utils.RVAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.root.mapdemo.R.id.map;
import static com.example.root.mapdemo.R.id.textSucursal;
import static com.example.root.mapdemo.SearchActivity.getAppContext;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Office> lista;

    private static final int OK_RESULT_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        /*setContentView(R.layout.legal_info);
        TextView legalInfoTextView = (TextView) findViewById(R.id.legal_info);
        String openSourceSoftwareLicenseInfo =
                GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(this);
        if (openSourceSoftwareLicenseInfo != null) {
            legalInfoTextView.setText(openSourceSoftwareLicenseInfo);
        } else {
            legalInfoTextView.setText(R.string.play_services_not_installed);
        }*/
        HttpsTrustManager.allowAllSSL();

        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public  void onResponse(String response){
                JSONArray jsonResoponse = null;
                try {

                    jsonResoponse = new JSONArray(response);
                    int count = 0;
                    //lista = new ArrayList<>();
                    while(count < jsonResoponse.length()) {
                        JSONObject childJSONObject = jsonResoponse.getJSONObject(count);

                        Office office = new Office();
                        office.setId(childJSONObject.getInt("id"));
                        office.setName(childJSONObject.getString("name"));
                        JSONObject locationJson = childJSONObject.getJSONObject("location");
                        office.setLogitude(locationJson.getString("longitude"));
                        office.setLatitude(locationJson.getString("latitude"));
                        office.setCity(childJSONObject.getString("city"));
                        office.setAddress(childJSONObject.getString("address"));
                        office.setClosed(childJSONObject.getBoolean("closed"));
                        office.setApertureHour(childJSONObject.getString("apertureHour"));
                        office.setClosingHour(childJSONObject.getString("closingHour"));

//                        lista.add(office);

                        LatLng mont = new LatLng(Float.parseFloat(office.getLatitude()),Float.parseFloat(office.getLogitude()));

                        mMap.addMarker(new MarkerOptions().position(mont).title(office.getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.pushpin)).snippet(String.valueOf(office.getId())));


                        count++;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            };
        };

        SucursalRequest sucursalRequest = new SucursalRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MapsActivity.this);
        queue.add(sucursalRequest);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker in Sydney and move the camera
        LatLng mont = new LatLng(-34.8938383,-56.1655067);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mont));

        mMap.addMarker(new MarkerOptions().position(mont).title("Montevideo").icon(BitmapDescriptorFactory.fromResource(R.drawable.pushpin)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mont, 13));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);


        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker arg0) {
               //Toast.makeText(getApplicationContext(), arg0.getTitle(), Toast.LENGTH_SHORT).show();
                try {
                    Intent intent = new Intent();

                    intent.putExtra("officeName", arg0.getTitle());
                    intent.putExtra("officeId", arg0.getSnippet());
                    setResult(OK_RESULT_CODE, intent);
                    finish();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
//        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                // show dialog
//                Toast.makeText(getApplicationContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();
////                String nombre = getIntent().getStringExtra("sucursal");
//                Bundle reicieveParams = getIntent().getExtras();
//
//
////                try {
////                    Intent intent = new Intent();
////
////                    intent.putExtra("result", marker.getTitle());
////                    setResult(OK_RESULT_CODE, intent);
////                    finish();
////                } catch (Throwable throwable) {
////                    throwable.printStackTrace();
////                }
//                return true;
//            }
//
//        });
    }

}
