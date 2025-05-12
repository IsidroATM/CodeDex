package com.example.codedex.models;

import java.io.Serializable;
import java.util.List;

public class LenguajeProgramacion implements Serializable {
    private String nombre;
    private String abreviacion;
    private String descripcion;
    private String fundador;
    private Long aCreacion;
    private List<String> proyectosUsados;
    private List<String> ides;
    private String colorHex;

    public LenguajeProgramacion() {}

    // Getters
    public String getNombre() { return nombre; }
    public String getAbreviacion() { return abreviacion; }
    public String getDescripcion() { return descripcion; }
    public String getFundador() { return fundador; }
    public Long getACreacion() { return aCreacion; }
    public List<String> getProyectosUsados() { return proyectosUsados; }
    public List<String> getIdes() { return ides; }
    public String getColorHex() { return colorHex; }
}