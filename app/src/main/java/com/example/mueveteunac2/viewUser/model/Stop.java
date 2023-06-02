package com.example.mueveteunac2.viewUser.model;

import java.io.Serializable;

public class Stop implements Serializable {
    private String idStop;
    private String nameStop;
    private String shift;
    private String idLine;
    private String nameLine;

    public Stop(String idStop, String nameStop, String shift, String idLine, String nameLine) {
        this.idStop = idStop;
        this.nameStop = nameStop;
        this.shift = shift;
        this.idLine = idLine;
        this.nameLine = nameLine;
    }

    public String getIdStop() {
        return idStop;
    }

    public void setIdStop(String idStop) {
        this.idStop = idStop;
    }

    public String getNameStop() {
        return nameStop;
    }

    public void setNameStop(String nameStop) {
        this.nameStop = nameStop;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getIdLine() {
        return idLine;
    }

    public void setIdLine(String idLine) {
        this.idLine = idLine;
    }

    public String getNameLine() {
        return nameLine;
    }

    public void setNameLine(String nameLine) {
        this.nameLine = nameLine;
    }
}
