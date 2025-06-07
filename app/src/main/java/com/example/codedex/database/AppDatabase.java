package com.example.codedex.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.codedex.dao.AlgoritmoPropioDao;
import com.example.codedex.dao.ListaDao;
import com.example.codedex.models.AlgoritmoPropio;
import com.example.codedex.models.ListaPersonalizada;
import com.example.codedex.utils.AlgoritmoListConverter;

@Database(entities = {ListaPersonalizada.class, AlgoritmoPropio.class}, version = 3)
@TypeConverters({AlgoritmoListConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract ListaDao listaDao();
    public abstract AlgoritmoPropioDao algoritmoPropioDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "codedex-db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
