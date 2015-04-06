package com.caballero.billy.forestmap;

import android.graphics.Color;
import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener {

        GoogleMap googleMap;
        MapView mapView;
        Location myLocation;
        boolean markerClicked;
        PolygonOptions polygonOptions;
        Polygon polygon;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            mapView = (MapView) rootView.findViewById(R.id.my_map);
            mapView.onCreate(savedInstanceState);

            googleMap = mapView.getMap();
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            googleMap.setMyLocationEnabled(true);


            googleMap.setOnMapClickListener(this);
            googleMap.setOnMapLongClickListener(this);
            googleMap.setOnMarkerClickListener(this);

            markerClicked = false;
            return rootView;
        }

        @Override
        public void onResume() {
            super.onResume();
            mapView.onResume();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            mapView.onDestroy();
        }

        @Override
        public void onPause() {
            super.onPause();
            mapView.onPause();
        }

        @Override
        public void onMapClick(LatLng point) {
            //Log.e("FOREST MAP MARKER LOCATION ", point.toString());
            //googleMap.animateCamera(CameraUpdateFactory.newLatLng(point));

            markerClicked = false;
        }

        @Override
        public void onMapLongClick(LatLng point) {
           // Log.e("FOREST MAP MARKER LOCATION", "New marker added@" + point.toString());
            googleMap.addMarker(new MarkerOptions().position(point).title(point.toString()));

            markerClicked = false;
        }

        @Override
        public boolean onMarkerClick(Marker marker) {

            if(markerClicked){

                if(polygon != null){
                    polygon.remove();
                    polygon = null;
                }

                polygonOptions.add(marker.getPosition());
                polygonOptions.strokeColor(Color.BLACK);
                polygonOptions.strokeWidth(8);
                polygonOptions.fillColor(Color.argb(100, 23, 100, 200));

                polygon = googleMap.addPolygon(polygonOptions);
            }else{
                if(polygon != null){
                    polygon.remove();
                    polygon = null;
                }

                polygonOptions = new PolygonOptions().add(marker.getPosition());
                markerClicked = true;
            }

            return true;
        }
    }
}
