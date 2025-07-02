package com.example.codedex.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
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

public class AlgoritmosFragment extends Fragment {

    private RecyclerView recyclerView;
    private AlgoritmoAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Spinner spinnerFiltro;
    private List<Algoritmo> listaCompleta = new ArrayList<>(); // Lista completa
    private SearchView searchView;
    private String tipoSeleccionado = "All";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_algoritmos_fragment, container, false);

        // Referencias
        ImageView iconoBusqueda = view.findViewById(R.id.iconoBusqueda);
        ImageView iconoFiltro = view.findViewById(R.id.iconoFiltro);
        searchView = view.findViewById(R.id.searchView);
        spinnerFiltro = view.findViewById(R.id.spinnerFiltro);

        recyclerView = view.findViewById(R.id.recyclerAlgoritmos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        // Mostrar SearchView y ocultar Spinner si está visible
        iconoBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchView.getVisibility() == View.GONE) {
                    searchView.setVisibility(View.VISIBLE);
                    spinnerFiltro.setVisibility(View.GONE);
                } else {
                    searchView.setVisibility(View.GONE);
                }
            }
        });

        // Mostrar Spinner y ocultar SearchView si está visible
        iconoFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinnerFiltro.getVisibility() == View.GONE) {
                    spinnerFiltro.setVisibility(View.VISIBLE);
                    searchView.setVisibility(View.GONE);
                } else {
                    spinnerFiltro.setVisibility(View.GONE);
                }
            }
        });

        // Listeners
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cargarDatos();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                filtrarLista(newText, tipoSeleccionado);
                return true;
            }
        });

        spinnerFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoSeleccionado = parent.getItemAtPosition(position).toString();
                filtrarLista(searchView.getQuery().toString(), tipoSeleccionado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        cargarDatos();
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
                //Busqueda(texto) y Filtro
                String textoBusqueda = searchView.getQuery().toString();
                filtrarLista(textoBusqueda, tipoSeleccionado);

                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error al cargar desde Firebase", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    private void filtrarLista(String textoBusqueda, String tipo) {
        List<Algoritmo> listaFiltrada = new ArrayList<>();
        for (Algoritmo algoritmo : listaCompleta) {
            boolean coincideTipo = tipo.equals("All") || (algoritmo.getTipo() != null && algoritmo.getTipo().equalsIgnoreCase(tipo));
            boolean coincideBusqueda = algoritmo.getNombre().toLowerCase().contains(textoBusqueda.toLowerCase());
            if (coincideTipo && coincideBusqueda) {
                listaFiltrada.add(algoritmo);
            }
        }
        adapter = new AlgoritmoAdapter(getContext(), listaFiltrada);
        recyclerView.setAdapter(adapter);
    }
}

