package com.example.codedex.adapters;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.codedex.database.AppDatabase;
import com.example.codedex.models.AlgoritmoPropio;
import java.util.List;

public class AlgoritmoPropioViewModel extends AndroidViewModel {

    private LiveData<List<AlgoritmoPropio>> algoritmos;

    public AlgoritmoPropioViewModel(@NonNull Application application) {
        super(application);
        algoritmos = AppDatabase.getInstance(application).algoritmoPropioDao().getAllAlgoritmos();
    }

    public LiveData<List<AlgoritmoPropio>> getAlgoritmos() {
        return algoritmos;
    }
}