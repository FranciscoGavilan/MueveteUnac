package com.example.mueveteunac2.viewUser.data.repository;

import com.example.mueveteunac2.viewUser.data.model.Stop;
import com.example.mueveteunac2.viewUser.data.network.OpenRouteServiceAPI;
import com.example.mueveteunac2.viewUser.data.model.PointResponse;
import com.example.mueveteunac2.viewUser.data.network.core.RetrofitRequest;
import com.example.mueveteunac2.viewUser.domain.useCase.GetPointsApiUseCase;

import org.json.JSONArray;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PointRepository {
        private OpenRouteServiceAPI serviceAPI;
        private GetPointsApiUseCase getPointsApiUseCase;

        public PointRepository(GetPointsApiUseCase getPointsApiUseCase) {
            serviceAPI = RetrofitRequest.getRetrofitInstance().create(OpenRouteServiceAPI.class);
            this.getPointsApiUseCase=getPointsApiUseCase;
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
                            getPointsApiUseCase.pointsAdded(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<PointResponse> call, Throwable t) {

                    }
                });
        }
}
