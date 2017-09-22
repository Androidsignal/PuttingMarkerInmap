package com.example.abc.locationget;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements  ClusterManager.OnClusterItemInfoWindowClickListener<MapObject> {
    private GoogleMap map;
    List<MapObject> list = new ArrayList<>();
    ClusterManager<MapObject> mClusterManager;
    private MapObject mapObject;
    CameraUpdate cu;
    double latMain;
    double lngMain;

    String url = "https://maps.googleapis.com/maps/api/place/search/json?key=AIzaSyBnBodN9UNp7Gjv_GJtP4tISWyTcWtFXeE&location=21.170240,72.831062&radius=1000";

    private static final LatLng surat = new LatLng(21.231445,72.831062);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replaceMapFragment();
    }
    private void replaceMapFragment() {


        if (map == null) {

            map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                    .getMap();



            map.setMyLocationEnabled(true);

            if (map != null) {

                getData();

            }
        }
    }

    private void setUpMap() {

        map.getUiSettings().setMapToolbarEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.setMyLocationEnabled(true);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mClusterManager = new ClusterManager<>(this, map);

        CameraPosition cameraPosition = new CameraPosition.Builder().target(surat).zoom(10).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        map.setOnCameraChangeListener(mClusterManager);

        map.setOnMarkerClickListener(mClusterManager);

        map.setInfoWindowAdapter(mClusterManager.getMarkerManager());

        map.setOnInfoWindowClickListener(mClusterManager);

        mClusterManager.setOnClusterItemInfoWindowClickListener(this);

        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MapObject>() {
            @Override
            public boolean onClusterItemClick(MapObject mobj) {
                mapObject = mobj;
                return false;
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        replaceMapFragment();
    }


    private void addItems() {

        try {
            for (int i = 0; i < list.size(); i++) {

                double lat = Double.parseDouble(list.get(i).getLat());
                double lng = Double.parseDouble(list.get(i).getLon());



                MapObject offsetItem = new MapObject(list.get(i).getId(),list.get(i).getName(),list.get(i).getVicinity(),list.get(i).getIcon(),list.get(i).getPlace_id(),list.get(i).getReference(),list.get(i).getScope(),list.get(i).getLat(),list.get(i).getLon(),new LatLng(Double.parseDouble(list.get(i).getLat()),Double.parseDouble(list.get(i).getLon())));

                mClusterManager.addItem(offsetItem);

            }

        }catch (NumberFormatException e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public void onClusterItemInfoWindowClick(MapObject mapObject) {
        Intent i = new Intent(MainActivity.this,Main2Activity.class);


        for(int j=0;j<list.size();j++)
        {
            if(list.get(j).getName().equals(mapObject.getName())) {

                MapObject itemData = list.get(j);
                i.putExtra("address", itemData);


                break;
            }
        }
        startActivity(i);
    }

    private void getData() {

        AsyncHttpClient client = new AsyncHttpClient();

        final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        dialog.setTitle("Wait");
        dialog.setMessage("Data is Loading...");

        client.post(url,new JsonHttpResponseHandler(){

            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onFinish() {
                dialog.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {

                    JSONArray array = response.getJSONArray("results");


                    for ( int i=0; i<array.length();i++) {

                        final JSONObject jsonObject = array.getJSONObject(i);


                        MapObject m = new MapObject();
                        JSONObject geometry = jsonObject.getJSONObject("geometry");
                        JSONObject location = geometry.getJSONObject("location");

                        Log.e("", "lat===>" + location.getString("lat"));
                        Log.e("", "lng===>" + location.getString("lng"));
                        latMain = Double.parseDouble(location.getString("lat"));
                        lngMain = Double.parseDouble(location.getString("lng"));

                        Log.e("", "new lat===>" + latMain);
                        Log.e("", "new lng===>" + lngMain);

                        m.setId(jsonObject.getString("id"));
                        m.setName(jsonObject.getString("name"));
                        m.setPlace_id(jsonObject.getString("place_id"));
                        m.setIcon(jsonObject.getString("icon"));
                        m.setReference(jsonObject.getString("reference"));
                        m.setLat((location.getString("lat")));
                        m.setLon((location.getString("lng")));

                        list.add(m);


                    }

                }catch (Exception e)
                {
                    e.printStackTrace();
                }



                setUpMap();
                addItems();

                mClusterManager.getMarkerCollection().setOnInfoWindowAdapter(
                        new MyCustomAdapterForItems());


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                Toast.makeText(MainActivity.this,"Data Not Found",Toast.LENGTH_SHORT).show();


            }
        });

    }
    public class MyCustomAdapterForItems implements GoogleMap.InfoWindowAdapter {

        private final View myContentsView;

        MyCustomAdapterForItems() {
            myContentsView = getLayoutInflater().inflate(
                    R.layout.popupbar, null);
        }
        @Override
        public View getInfoWindow(Marker marker) {

            TextView tvTitle = ((TextView) myContentsView
                    .findViewById(R.id.txtHeader));

            TextView tvSnippet = ((TextView) myContentsView
                    .findViewById(R.id.txtSnippet));

            tvTitle.setText(mapObject.getName());

            tvSnippet.setText(mapObject.getPlace_id());

            return myContentsView;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    }
}
