package com.example.mueveteunac2.viewUser.model;

import java.io.Serializable;

public class Line implements Serializable {
    private String idLine;
    private String symbol;
    private String nameLine;
    private String firstStop;
    private String lastStop;
    private String fDepSchedule;
    private String sDepSchedule;
    private String firstName;
    private String firstSurname;
    private String secondSurname;
    private String plate;

    public Line(String idLine, String symbol, String nameLine, String firstStop, String lastStop, String fDepSchedule, String sDepSchedule, String firstName, String firstSurname, String secondSurname, String plate) {
        this.idLine = idLine;
        this.symbol = symbol;
        this.nameLine = nameLine;
        this.firstStop = firstStop;
        this.lastStop = lastStop;
        this.fDepSchedule = fDepSchedule;
        this.sDepSchedule = sDepSchedule;
        this.firstName = firstName;
        this.firstSurname = firstSurname;
        this.secondSurname = secondSurname;
        this.plate = plate;
    }

    public String getIdLine() {
        return idLine;
    }

    public void setIdLine(String idLine) {
        this.idLine = idLine;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getNameLine() {
        return nameLine;
    }

    public void setNameLine(String nameLine) {
        this.nameLine = nameLine;
    }

    public String getFirstStop() {
        return firstStop;
    }

    public void setFirstStop(String firstStop) {
        this.firstStop = firstStop;
    }

    public String getLastStop() {
        return lastStop;
    }

    public void setLastStop(String lastStop) {
        this.lastStop = lastStop;
    }

    public String getfDepSchedule() {
        return fDepSchedule;
    }

    public void setfDepSchedule(String fDepSchedule) {
        this.fDepSchedule = fDepSchedule;
    }

    public String getsDepSchedule() {
        return sDepSchedule;
    }

    public void setsDepSchedule(String sDepSchedule) {
        this.sDepSchedule = sDepSchedule;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstSurname() {
        return firstSurname;
    }

    public void setFirstSurname(String firstSurname) {
        this.firstSurname = firstSurname;
    }

    public String getSecondSurname() {
        return secondSurname;
    }

    public void setSecondSurname(String secondSurname) {
        this.secondSurname = secondSurname;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }
}
