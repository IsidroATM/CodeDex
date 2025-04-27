package com.example.codedex.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.codedex.R;
import com.example.codedex.adapters.AlgoritmoAdapter;
import com.example.codedex.models.Algoritmo;
import com.example.codedex.services.ApiClient;
import com.example.codedex.services.ApiService;

public class AlgoritmosFragment extends Fragment {

    private RecyclerView recyclerView;
    private AlgoritmoAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_algoritmos_fragment, container, false);

        recyclerView = view.findViewById(R.id.recyclerAlgoritmos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout); // Inicializa el SwipeRefreshLayout

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cargarDatos(); // Recargar los datos al deslizar
            }
        });

        cargarDatos(); // Cargar los datos inicialmente

        return view;
    }

    private void cargarDatos() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getAlgoritmos().enqueue(new Callback<List<Algoritmo>>() {
            @Override
            public void onResponse(Call<List<Algoritmo>> call, Response<List<Algoritmo>> response) {
                if (response.isSuccessful()) {
                    List<Algoritmo> lista = response.body();
                    adapter = new AlgoritmoAdapter(getContext(), lista);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "Error en la respuesta", Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.setRefreshing(false); // Dejar de mostrar el indicador de carga
            }

            @Override
            public void onFailure(Call<List<Algoritmo>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false); // Dejar de mostrar el indicador de carga en caso de error
            }
        });
    }
}

