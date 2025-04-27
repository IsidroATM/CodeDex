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

import com.example.codedex.R;
import com.example.codedex.adapters.LenguajeAdapter;
import com.example.codedex.models.LenguajeProgramacion;
import com.example.codedex.services.ApiClient;
import com.example.codedex.services.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LenguajesFragment extends Fragment {

    private RecyclerView recyclerView;
    private LenguajeAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_lenguajes_fragment, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewLenguajes);
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
        apiService.getLenguajes().enqueue(new Callback<List<LenguajeProgramacion>>() {
            @Override
            public void onResponse(Call<List<LenguajeProgramacion>> call, Response<List<LenguajeProgramacion>> response) {
                if (response.isSuccessful()) {
                    List<LenguajeProgramacion> lista = response.body();
                    adapter = new LenguajeAdapter(getContext(), lista);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "Error en la respuesta", Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.setRefreshing(false); // Dejar de mostrar el indicador de carga
            }

            @Override
            public void onFailure(Call<List<LenguajeProgramacion>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false); // Dejar de mostrar el indicador de carga en caso de error
            }
        });
    }
}
