package com.example.codedex.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Sintaxis implements Serializable {
    private Map<String, String> codigoPorLenguaje;

    public Sintaxis() {
        this.codigoPorLenguaje = new HashMap<>();
    }

    public void agregarCodigo(String lenguaje, String codigo) {
        if (codigo != null && !codigo.trim().isEmpty()) {
            codigoPorLenguaje.put(lenguaje, codigo);
        }
    }

    public Map<String, String> getCodigoPorLenguaje() {
        return codigoPorLenguaje;
    }

    public boolean tieneLenguajes() {
        return !codigoPorLenguaje.isEmpty();
    }
}

