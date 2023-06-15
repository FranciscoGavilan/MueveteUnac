package com.example.mueveteunac2.viewUser.view.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PointResponse {
    @SerializedName("routes")
    private List<Feature> routes;

    public List<Feature> getFeatures() {
        return routes;
    }

    public void setFeatures(List<Feature> routes) {
        this.routes = routes;
    }

    public static class Feature {
        @SerializedName("geometry")
        private String geometry;

        public String getGeometry() {
            return geometry;
        }

        public void setGeometry(String geometry) {
            this.geometry = geometry;
        }
    }
}
