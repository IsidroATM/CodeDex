package com.example.codedex.models;

import java.io.Serializable;

public class Sintaxis implements Serializable {
    private String csharp;
    private String java;
    private String python;

    public String getCsharp() { return csharp; }
    public String getJava() { return java; }
    public String getPython() { return python; }
}
