package com.ShrinkMyIssues.App;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Call_Back.Api_callback;
import Controllers.Config;
import Controllers.Controller;

public class Address_location_get extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, AdapterView.OnItemClickListener, TextWatcher, View.OnClickListener {
    private GoogleMap mMap;

    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    protected final static String LOCATION_KEY = "location-key";
    protected final static String LAST_UPDATED_TIME_STRING_KEY = "last-updated-time-string-key";
    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest mLocationRequest;
    protected Location mCurrentLocation;
    protected String mLastUpdateTime, address_loc;
    private boolean check_map_ready, get_location_onlocation_change, check_location;
    private LocationManager manager;
    private double latitude, longitude;
    private Context context;
    private LatLng latLng;
    private Marker marker;
    private AutoCompleteTextView search_place;
    private static final String API_KEY = "AIzaSyD2kdugukhu2noukkdzvIWDlcO5blO4HJQ";
    private Controller controller;
    private Api_callback api_callback;
    private StringBuilder sb;
    private ArrayList<String> place_results;
    private Animation bottomUp;
    private EditText et_address;
    private Button btn_save_address;
    private LinearLayout hide_layout;
    private long duration = 400;
    private Handler handler;
    private long start;
    private Projection projection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_location_get);

        context = Address_location_get.this;

        controller = new Controller();

        initialise();

        check_map_ready = false;
        get_location_onlocation_change = true;
        check_location = false;

        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLastUpdateTime = "";
        updateValuesFromBundle(savedInstanceState);

        api_callback = new Api_callback() {
            @Override
            public void onPost_data(String response, String type) {
                if (!response.equals("Error")) {
                    call_result_method(response);
                } else {
                    controller.Toast_show(context, getString(R.string.oops_error_occur));
                }
            }
        };
    }

    private void initialise() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.address_map);
        mapFragment.getMapAsync(this);

        search_place = (AutoCompleteTextView) findViewById(R.id.search_place);
        search_place.setAdapter(new GooglePlacesAutocompleteAdapter(this, R.layout.list_item));
        search_place.setOnItemClickListener(this);
        search_place.addTextChangedListener(this);

        hide_layout = (LinearLayout) findViewById(R.id.hide_layout);
        et_address = (EditText) findViewById(R.id.et_address);
        btn_save_address = (Button) findViewById(R.id.btn_save_address);
        btn_save_address.setOnClickListener(this);
        hide_layout.setVisibility(View.GONE);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        buildGoogleApiClient();
        mGoogleApiClient.connect();
        check_map_ready = true;


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                drawPin(latLng);

                latitude = latLng.latitude;
                longitude = latLng.longitude;
                address_loc = getCompleteAddressString(latitude, longitude);
                bottom_up_address(address_loc);

            }

        });

        if (!manager.isProviderEnabled((LocationManager.GPS_PROVIDER))) {
            alert_dialog();
        }
    }

    private void alert_dialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(R.string.gps_enable);
        alertDialog.setMessage(R.string.location_near_you);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {

        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

        if (get_location_onlocation_change) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            latLng = new LatLng(latitude, longitude);

            drawPin(latLng);
            address_loc = getCompleteAddressString(latitude, longitude);
            bottom_up_address(address_loc);
            get_location_onlocation_change = false;

        }
    }

    private void call_result_method(String response) {
        try {
            JSONObject jsonObj = new JSONObject(response);
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            place_results = new ArrayList<String>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                place_results.add(predsJsonArray.getJSONObject(i).getString("description"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            search_place.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        } else {
            search_place.setCompoundDrawablesWithIntrinsicBounds(R.drawable.search_ic, 0, 0, 0);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save_address:
                if (controller.Check_all_empty(et_address)) {
                    et_address.setError(getString(R.string.missing_address));
                } else if (controller.Check_all_matches(et_address, getString(R.string.missing_address))) {
                    et_address.setError(getString(R.string.missing_address));
                } else {
                    address_loc = et_address.getText().toString();

                    Intent intent = new Intent();
                    intent.putExtra("Address", address_loc);
                    setResult(RESULT_OK, intent);
                    controller.Animation_backward(context);
                    finish();
                }
                break;
        }
    }

    class GooglePlacesAutocompleteAdapter extends ArrayAdapter<String> implements Filterable {
        private ArrayList<String> resultList;

        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            return resultList.get(index);
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        autocomplete(constraint.toString());
                        resultList = place_results;
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }

    public void autocomplete(String input) {
        try {
            sb = new StringBuilder(Config.Place_API);
            sb.append("?key=" + API_KEY);
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        controller.Api_start(context, sb.toString(), api_callback, null, getString(R.string.type_location_search));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String str = (String) adapterView.getItemAtPosition(position);
        Geocoder coder = new Geocoder(this);
        try {
            ArrayList<Address> address = (ArrayList<Address>) coder.getFromLocationName(str, 50);
            for (Address add : address) {
                longitude = add.getLongitude();
                latitude = add.getLatitude();
                latLng = new LatLng(latitude, longitude);

                drawPin(latLng);

                address_loc = getCompleteAddressString(latitude, longitude);
                bottom_up_address(address_loc);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
            } else {
                strAdd = "Error";
            }
        } catch (Exception e) {
            strAdd = "Error";
        }
        return strAdd;
    }

    private void bottom_up_address(String address_location) {
        bottomUp = AnimationUtils.loadAnimation(context, R.anim.bottom_up);
        hide_layout.startAnimation(bottomUp);
        hide_layout.setVisibility(View.VISIBLE);
        if (address_location != null) {
            if (address_location.length() > 0) {
                et_address.setText(address_location);
            } else {
                et_address.setText(getString(R.string.missing_address));
            }
        } else {
            et_address.setText(getString(R.string.missing_address));
        }
    }

    //Update location from google fused api
    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.keySet().contains(LOCATION_KEY)) {
                mCurrentLocation = savedInstanceState.getParcelable(LOCATION_KEY);
            }
            if (savedInstanceState.keySet().contains(LAST_UPDATED_TIME_STRING_KEY)) {
                mLastUpdateTime = savedInstanceState.getString(LAST_UPDATED_TIME_STRING_KEY);
            }
        }
    }

    //synchronized google fused location api
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    //create location request
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }


    @Override
    public void onConnected(Bundle bundle) {

        if (mCurrentLocation == null) {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
            check_location = true;
        }
        startLocationUpdates();

    }

    //check connection suspended
    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    //Location update
    protected void startLocationUpdates() {
        if (check_location) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    //location update close when activity closed
    protected void stopLocationUpdates() {
        if (check_location) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void drawPin(LatLng latlng) {

        this.latLng = latlng;

        mMap.getUiSettings().setCompassEnabled(false);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).bearing(90).tilt(30).zoom(17).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        if (marker != null) {
            marker.remove();
        }

        marker = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin_user)));
    }

    //on resume activity
    @Override
    public void onResume() {
        super.onResume();

        if (check_map_ready) {
            if (mGoogleApiClient.isConnected()) {
                startLocationUpdates();
            }
        }
    }

    //when activity goes on pause
    @Override
    protected void onPause() {

        if (check_map_ready) {
            if (mGoogleApiClient.isConnected()) {
                stopLocationUpdates();
            }
        }
        super.onPause();
    }


    //when activity stops
    @Override
    protected void onStop() {
        if (check_map_ready) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        controller.Animation_backward(context);
    }
}