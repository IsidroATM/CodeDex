package com.example.codedex.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.codedex.utils.AlgoritmoListConverter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ListaPersonalizada implements Serializable {

    @PrimaryKey
    @NonNull
    private String id;

    private String nombre;

    @TypeConverters(AlgoritmoListConverter.class)
    private List<Algoritmo> algoritmos = new ArrayList<>();

    public ListaPersonalizada() {}

    public ListaPersonalizada(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.algoritmos = new ArrayList<>();
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public List<Algoritmo> getAlgoritmos() { return algoritmos; }

    public void setId(String id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setAlgoritmos(List<Algoritmo> algoritmos) { this.algoritmos = algoritmos; }
}

