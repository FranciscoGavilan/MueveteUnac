package com.example.mueveteunac2.viewUser.repository;

import com.example.mueveteunac2.viewUser.model.Stop;
import com.example.mueveteunac2.viewUser.view.interfaces.OpenRouteServiceAPI;
import com.example.mueveteunac2.viewUser.view.response.PointResponse;
import com.example.mueveteunac2.viewUser.view.retrofit.RetrofitRequest;

import org.json.JSONArray;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PointRepository {
        private OpenRouteServiceAPI serviceAPI;
        private OnPointAPIAdded onPointAPIAdded;

        public PointRepository(OnPointAPIAdded onPointAPIAdded) {
            serviceAPI = RetrofitRequest.getRetrofitInstance().create(OpenRouteServiceAPI.class);
            this.onPointAPIAdded=onPointAPIAdded;
        }

        public void getPoints(List<Stop> stopList) {
                JSONArray coordinates = new JSONArray();
                Double lat,lon;
                for (Stop stops: stopList) {
                    lat=stops.getStopPosition().getLatitude();
                    lon=stops.getStopPosition().getLongitude();
                    JSONArray coordinatesArray = new JSONArray();
                    coordinatesArray.put(lon);
                    coordinatesArray.put(lat);
                    coordinates.put(coordinatesArray);
                }

            String jsonString = "{\"coordinates\":" + coordinates.toString() + "}";

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonString);
            serviceAPI.getRoute(requestBody).enqueue(new Callback<PointResponse>() {
                    @Override
                    public void onResponse(Call<PointResponse> call, Response<PointResponse> response) {
                        if (response.body() != null) {
                            onPointAPIAdded.pointAdded(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<PointResponse> call, Throwable t) {

                    }
                });
        }

        public interface OnPointAPIAdded {
            void pointAdded(PointResponse pointResponse);
        }
}
