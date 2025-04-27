package com.example.codedex.services;

import com.example.codedex.models.Algoritmo;
import com.example.codedex.models.LenguajeProgramacion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("algoritmos")
    Call<List<Algoritmo>> getAlgoritmos();


    @GET("lenguajesDeProgramacion")
    Call<List<LenguajeProgramacion>> getLenguajes();
}

