package com.example.mayur.mapapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {

    @GET()

    public Call<PolylineResponse> getPolylineData(String origin, @Url String url); // By passing URL


    @GET("maps/api/directions/json")

    public Call<PolylineResponse> getPolylineData(@Query("origin") String origin,
                                                  @Query("destination") String destination,
                                                  @Query("key") String key);

}
