package com.example.mueveteunac2.viewUser.data.model;
import java.util.List;

public class Route {
    private String routeId;
    private String routeSchedule;
    private String turnId;
    private String turn;
    private List<Stop> stopList;
    private String lineId;
    private String lineName;

    // empty constructor for firebase
    public Route() {
        // Constructor sin argumentos
    }

    public Route(String routeId, String routeSchedule, String turnId, String turn,
                 List<Stop> stopList, String lineId, String lineName) {
        this.routeId = routeId;
        this.routeSchedule = routeSchedule;
        this.turnId = turnId;
        this.turn = turn;
        this.stopList = stopList;
        this.lineId = lineId;
        this.lineName = lineName;
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

    public String getTurnId() {
        return turnId;
    }

    public void setTurnId(String tunId) {
        this.turnId = tunId;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public List<Stop> getStopList() {
        return stopList;
    }

    public void setStopList(List<Stop> stopList) {
        this.stopList = stopList;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }
}
