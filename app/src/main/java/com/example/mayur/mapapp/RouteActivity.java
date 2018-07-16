package com.example.mayur.mapapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mayur.mapapp.Constant.BASE_URL_POLYLINE;
import static com.example.mayur.mapapp.Constant.DESTINATION_PLACEHOLDER;
import static com.example.mayur.mapapp.Constant.DIRECTION_API_KEY;
import static com.example.mayur.mapapp.Constant.KEY_PLACEHOLDER;
import static com.example.mayur.mapapp.Constant.ORIGIN_PLACEHOLDER;

public class RouteActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    Button btnDraw;

    EditText edtSource,edtDes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnDraw=findViewById(R.id.btndraw);
        edtSource=findViewById(R.id.edtsource);
        edtDes=findViewById(R.id.edtdes);
        /*btnBack=findViewById(R.id.btnback);
*/
    }


    public  void OnClickDraw ( View view ){

        mMap.clear();


        final String source = edtSource.getText().toString().trim();
        final String destination = edtDes.getText().toString().trim();

        if (TextUtils.isEmpty(source)) {
            edtSource.setError("Please Enter the source here");
            edtSource.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(destination)) {
            edtDes.setError("Please Enter the destination here");
            edtDes.requestFocus();
            return;
        }

        final String URL = BASE_URL_POLYLINE + ORIGIN_PLACEHOLDER + source + DESTINATION_PLACEHOLDER + destination + KEY_PLACEHOLDER + DIRECTION_API_KEY;


        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final Call<PolylineResponse> polylineResponseCall = apiService.getPolylineData(source,destination,DIRECTION_API_KEY);


        polylineResponseCall.enqueue(new Callback<PolylineResponse>() {
            @Override
            public void onResponse(Call<PolylineResponse> call, Response<PolylineResponse> response) {

                PolylineResponse polylineResponse = response.body();

                Toast.makeText(getApplicationContext(),polylineResponse.getStatus(), Toast.LENGTH_LONG).show();

                String route = polylineResponse.getRoutes().get(0).getOverviewPolyline().getPoints();

                List<LatLng> point = PolyUtil.decode(route);

                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.width(10);
                polylineOptions.color(Color.CYAN);
                polylineOptions.geodesic(true);
                polylineOptions.addAll(point);
                mMap.addPolyline(polylineOptions);

                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(point.get(0));
                builder.include(point.get(point.size()-1));
                LatLngBounds bounds = builder.build();

                int padding = 20; // offset from edges of the map in pixels
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

                mMap.animateCamera(cu);



                LatLng origin = point.get(0);
                mMap.addMarker(new MarkerOptions().position(origin).title(source));
                LatLng dest=point.get(point.size()-1);
                mMap.addMarker(new MarkerOptions().position(dest).title(destination));


            }

            @Override
            public void onFailure(Call<PolylineResponse> call, Throwable t) {

            }
        });

       /* btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RouteActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });*/


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }
}
