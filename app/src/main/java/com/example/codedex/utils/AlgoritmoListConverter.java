package com.example.codedex.utils;

import androidx.room.TypeConverter;
import com.example.codedex.models.Algoritmo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class AlgoritmoListConverter {

    @TypeConverter
    public String fromAlgoritmoList(List<Algoritmo> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public List<Algoritmo> toAlgoritmoList(String data) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Algoritmo>>() {}.getType();
        return gson.fromJson(data, listType);
    }
}