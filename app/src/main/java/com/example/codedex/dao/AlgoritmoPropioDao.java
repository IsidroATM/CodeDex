package com.example.codedex.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.codedex.models.AlgoritmoPropio;
import com.example.codedex.models.ListaPersonalizada;

import java.util.List;

@Dao
public interface AlgoritmoPropioDao {
    @Insert
    void insert(AlgoritmoPropio algoritmo);

    @Query("SELECT * FROM algoritmos_propios ORDER BY id DESC")
    LiveData<List<AlgoritmoPropio>> getAllAlgoritmos();
    @Query("SELECT * FROM algoritmos_propios WHERE id = :id")
    AlgoritmoPropio getById(int id);

    @Update
    void update(AlgoritmoPropio algoritmo);;
}