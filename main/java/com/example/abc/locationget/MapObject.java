package com.example.abc.locationget;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by Vishal2.vasundhara on 6/30/2016.
 */
public class MapObject implements Serializable,com.google.maps.android.clustering.ClusterItem {


    private LatLng position;

    String id;
    String name;
    String vicinity;
    String icon;
    String place_id;
    String reference;
    String scope;
    String lat;
    String lon;


//    public MapObject(LatLng latLng) {
//      position = latLng;
//
//    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }


    public MapObject(String id, String name, String vicinity, String icon, String place_id, String reference, String scope, String lat, String lon, LatLng latLng)
    {
        this.id=id;
        this.name = name;
        this.vicinity=vicinity;
        this.icon=icon;
        this.place_id=place_id;
        this.reference=reference;
        this.scope=scope;
        this.lat=lat;
        this.lon=lon;
        this.position = latLng;
    }
    public MapObject(){

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

//    public String getTypes() {
//        return types;
//    }
//
//    public void setTypes(String types) {
//        this.types = types;
//    }


    @Override
    public LatLng getPosition() {
        return position;
    }


    public void setPosition(LatLng position) {
        this.position = position;
    }


}
