package com.example.mueveteunac2.viewUser.model;

public class Line {
    private String lineId;
    private String lineName;
    private String lineSymbol;
    private String stopFirst;
    private String stopLast;
    private String route1stSchedule;
    private String route2ndSchedule;
    private String driverName;
    private String driverLastname;
    private String busPlate;

    // empty constructor for firebase
    public Line() {
        // Constructor sin argumentos
    }

    public Line(String lineId, String lineName, String lineSymbol, String stopFirst,
                String stopLast, String route1stSchedule, String route2ndSchedule,
                String driverName, String driverLastname, String busPlate) {
        this.lineId = lineId;
        this.lineName = lineName;
        this.lineSymbol = lineSymbol;
        this.stopFirst = stopFirst;
        this.stopLast = stopLast;
        this.route1stSchedule = route1stSchedule;
        this.route2ndSchedule = route2ndSchedule;
        this.driverName = driverName;
        this.driverLastname = driverLastname;
        this.busPlate = busPlate;
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

    public String getLineSymbol() {
        return lineSymbol;
    }

    public void setLineSymbol(String lineSymbol) {
        this.lineSymbol = lineSymbol;
    }

    public String getStopFirst() {
        return stopFirst;
    }

    public void setStopFirst(String stopFirst) {
        this.stopFirst = stopFirst;
    }

    public String getStopLast() {
        return stopLast;
    }

    public void setStopLast(String stopLast) {
        this.stopLast = stopLast;
    }

    public String getRoute1stSchedule() {
        return route1stSchedule;
    }

    public void setRoute1stSchedule(String route1stSchedule) {
        this.route1stSchedule = route1stSchedule;
    }

    public String getRoute2ndSchedule() {
        return route2ndSchedule;
    }

    public void setRoute2ndSchedule(String route2ndSchedule) {
        this.route2ndSchedule = route2ndSchedule;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverLastname() {
        return driverLastname;
    }

    public void setDriverLastname(String driverLastname) {
        this.driverLastname = driverLastname;
    }

    public String getBusPlate() {
        return busPlate;
    }

    public void setBusPlate(String busPlate) {
        this.busPlate = busPlate;
    }
}
