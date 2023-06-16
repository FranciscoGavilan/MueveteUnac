package com.example.mueveteunac2.viewUser.data.model;
import com.google.firebase.firestore.GeoPoint;

public class Stop {
    private String stopId;
    private String stopName;
    private GeoPoint stopPosition;
    private Integer stopOrder;

    // empty constructor for firebase
    public Stop() {
        // Constructor sin argumentos
    }

    public Stop(String stopId, String stopName, GeoPoint stopPosition, Integer stopOrder) {
        this.stopId = stopId;
        this.stopName = stopName;
        this.stopPosition = stopPosition;
        this.stopOrder = stopOrder;
    }

    public String getStopId() {
        return stopId;
    }

    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public GeoPoint getStopPosition() {
        return stopPosition;
    }

    public void setStopPosition(GeoPoint stopPosition) {
        this.stopPosition = stopPosition;
    }

    public Integer getStopOrder() {
        return stopOrder;
    }

    public void setStopOrder(Integer stopOrder) {
        this.stopOrder = stopOrder;
    }
}
