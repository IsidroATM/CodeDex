package com.example.codedex.lists;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codedex.R;
import com.example.codedex.adapters.AlgoritmoAdapter;
import com.example.codedex.models.Algoritmo;
import com.example.codedex.models.ListaPersonalizada;

import java.util.List;

public class ContenidoListaActivity extends AppCompatActivity {

    private TextView tvNombreLista;
    private RecyclerView recyclerAlgoritmos;
    private AlgoritmoAdapter adapter;
    private ListaPersonalizada lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenido_lista);

        tvNombreLista = findViewById(R.id.tvNombreLista);
        recyclerAlgoritmos = findViewById(R.id.recyclerAlgoritmosEnLista);
        recyclerAlgoritmos.setLayoutManager(new LinearLayoutManager(this));

        // Obtener la lista seleccionada desde el intent
        lista = (ListaPersonalizada) getIntent().getSerializableExtra("lista");
        if (lista != null) {
            tvNombreLista.setText(lista.getNombre());

            List<Algoritmo> algoritmos = lista.getAlgoritmos(); // Puede estar vacía inicialmente
            adapter = new AlgoritmoAdapter(this, algoritmos);
            recyclerAlgoritmos.setAdapter(adapter);
        }
    }
}