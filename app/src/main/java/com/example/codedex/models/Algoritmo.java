package com.example.codedex.models;

import java.io.Serializable;

public class Algoritmo implements Serializable {
    private int id;
    private String nombre;
    private String complejidad;
    private String colorFondo;
    private String descripcion;
    private String proyectosUso;
    private Sintaxis sintaxis;

    public Algoritmo() {}

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getComplejidad() {
        return complejidad;
    }

    public void setComplejidad(String complejidad) {
        this.complejidad = complejidad;
    }

    public String getColorFondo() {
        return colorFondo;
    }

    public void setColorFondo(String colorFondo) {
        this.colorFondo = colorFondo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getProyectosUso() {
        return proyectosUso;
    }

    public void setProyectosUso(String proyectosUso) {
        this.proyectosUso = proyectosUso;
    }

    public Sintaxis getSintaxis() {
        return sintaxis;
    }

    public void setSintaxis(Sintaxis sintaxis) {
        this.sintaxis = sintaxis;
    }

}
