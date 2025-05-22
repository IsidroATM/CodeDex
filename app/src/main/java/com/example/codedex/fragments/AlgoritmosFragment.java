package com.example.codedex.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import com.example.codedex.R;
import com.example.codedex.adapters.AlgoritmoAdapter;
import com.example.codedex.models.Algoritmo;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//Unused
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import com.example.codedex.services.ApiClient;
//import com.example.codedex.services.ApiService;

public class AlgoritmosFragment extends Fragment {

    private RecyclerView recyclerView;
    private AlgoritmoAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Algoritmo> listaCompleta = new ArrayList<>(); // Lista completa
    private SearchView searchView;

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

        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false; // No hacemos nada en el submit
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filtrarLista(newText);
                return true;
            }
        });

        cargarDatos(); // Cargar los datos inicialmente
        return view;
    }

    private void cargarDatos() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("algoritmos");
        swipeRefreshLayout.setRefreshing(true);

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaCompleta.clear(); // Asegúrate de limpiar antes
                for (DataSnapshot algoritmoSnapshot : snapshot.getChildren()) {
                    Algoritmo algoritmo = algoritmoSnapshot.getValue(Algoritmo.class);
                    listaCompleta.add(algoritmo);
                }

                // Verificar si hay texto en el SearchView
                String textoBusqueda = searchView.getQuery().toString();
                if (!textoBusqueda.isEmpty()) {
                    filtrarLista(textoBusqueda); // Aplica el filtro actual
                } else {
                    adapter = new AlgoritmoAdapter(getContext(), listaCompleta);
                    recyclerView.setAdapter(adapter);
                }

                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error al cargar desde Firebase", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }



    private void filtrarLista(String texto) {
        List<Algoritmo> listaFiltrada = new ArrayList<>();
        for (Algoritmo algoritmo : listaCompleta) {
            if (algoritmo.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                listaFiltrada.add(algoritmo);
            }
        }
        adapter = new AlgoritmoAdapter(getContext(), listaFiltrada);
        recyclerView.setAdapter(adapter);
    }


}

