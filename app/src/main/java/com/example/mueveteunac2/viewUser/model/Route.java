package com.example.mueveteunac2.viewUser.model;
import com.google.firebase.firestore.GeoPoint;

public class Route {
    private String routeId;
    private String routeSchedule;
    private String tunId;
    private String turn;
    private String stopId;
    private String stopName;
    private GeoPoint stopPosition;
    private Integer stopOrder;
    private String lineId;

    // empty constructor for firebase
    public Route() {
        // Constructor sin argumentos
    }

    public Route(String routeId, String routeSchedule, String tunId, String turn, String stopId,
                 String stopName, GeoPoint stopPosition, Integer stopOrder, String lineId) {
        this.routeId = routeId;
        this.routeSchedule = routeSchedule;
        this.tunId = tunId;
        this.turn = turn;
        this.stopId = stopId;
        this.stopName = stopName;
        this.stopPosition = stopPosition;
        this.stopOrder = stopOrder;
        this.lineId = lineId;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getRouteSchedule() {
        return routeSchedule;
    }

    public void setRouteSchedule(String routeSchedule) {
        this.routeSchedule = routeSchedule;
    }

    public String getTunId() {
        return tunId;
    }

    public void setTunId(String tunId) {
        this.tunId = tunId;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
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

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }
}
