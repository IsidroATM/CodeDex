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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.codedex.R;
import com.example.codedex.adapters.LenguajeAdapter;
import com.example.codedex.models.LenguajeProgramacion;

import java.util.ArrayList;
import java.util.List;

//Unused
//import com.example.codedex.services.ApiClient;
//import com.example.codedex.services.ApiService;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;

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
                cargarDatos();
            }
        });

        cargarDatos();

        return view;
    }

    private void cargarDatos() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("lenguajes");

        swipeRefreshLayout.setRefreshing(true);

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<LenguajeProgramacion> lista = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    LenguajeProgramacion lenguaje = dataSnapshot.getValue(LenguajeProgramacion.class);
                    lista.add(lenguaje);
                }

                adapter = new LenguajeAdapter(getContext(), lista);
                recyclerView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error al cargar desde Firebase", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

}
