package com.example.mueveteunac2.viewUser.view.interfaces;

import com.example.mueveteunac2.viewUser.view.response.PointResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface OpenRouteServiceAPI {
    @Headers({
            "Content-Type: application/json",
            "Authorization: 5b3ce3597851110001cf6248d0541a72e6204df6868b461e604c0e6e" // Reemplaza "your_api_key" con tu clave de API válida
    })
    @POST("/v2/directions/driving-car")
    Call<PointResponse> getRoute(
            @Body RequestBody requestBody
    );
}
