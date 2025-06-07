package com.example.codedex.models;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "algoritmos_propios")
public class AlgoritmoPropio {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String titulo;
    private String codigo;
    private String lenguaje;

    // Constructor
    public AlgoritmoPropio(String titulo, String codigo, String lenguaje) {
        this.titulo = titulo;
        this.codigo = codigo;
        this.lenguaje = lenguaje;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getLenguaje() { return lenguaje; }
    public void setLenguaje(String lenguaje) { this.lenguaje = lenguaje; }
}