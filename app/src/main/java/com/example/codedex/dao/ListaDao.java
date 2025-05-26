package com.example.codedex.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Update;

import com.example.codedex.models.ListaPersonalizada;

import java.util.List;

@Dao
public interface ListaDao {

    @Insert
    void insertarLista(ListaPersonalizada lista);

    @Query("SELECT * FROM ListaPersonalizada")
    List<ListaPersonalizada> obtenerListas();

    @Delete
    void eliminarLista(ListaPersonalizada lista);

    @Update
    void actualizarLista(ListaPersonalizada lista);

    @Query("SELECT * FROM ListaPersonalizada WHERE nombre = :nombre LIMIT 1")
    ListaPersonalizada obtenerListaPorNombre(String nombre);

}

