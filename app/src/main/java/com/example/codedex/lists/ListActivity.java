package com.example.codedex.lists;

import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.codedex.R;
import com.example.codedex.adapters.ListaAdapter;
import com.example.codedex.database.AppDatabase;
import com.example.codedex.models.ListaPersonalizada;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListaAdapter adapter;
    private List<ListaPersonalizada> listas = new ArrayList<>();
    private FloatingActionButton btnAgregarLista;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Inicializar Room DB
        db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "codedex-db")
                .allowMainThreadQueries() // Para pruebas simples
                .fallbackToDestructiveMigration()
                .build();

        listas = new ArrayList<>(db.listaDao().obtenerListas());
        Toast.makeText(this, "Listas cargadas: " + listas.size(), Toast.LENGTH_SHORT).show();

        // Añadir "Favoritos" si no existe
        boolean favoritosExiste = false;
        for (ListaPersonalizada lista : listas) {
            if ("favoritos".equals(lista.getId())) {
                favoritosExiste = true;
                break;
            }
        }
        if (!favoritosExiste) {
            ListaPersonalizada favoritos = new ListaPersonalizada("favoritos", "Favoritos");
            listas.add(0, favoritos);
            db.listaDao().insertarLista(favoritos);
        }

        recyclerView = findViewById(R.id.recyclerListas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ListaAdapter(this, listas);
        recyclerView.setAdapter(adapter);

        btnAgregarLista = findViewById(R.id.btnAgregarLista);
        btnAgregarLista.setOnClickListener(v -> mostrarDialogoNuevaLista());
    }

    private void mostrarDialogoNuevaLista() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nombre de la nueva lista");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Crear", (dialog, which) -> {
            String nombreLista = input.getText().toString().trim();
            if (!nombreLista.isEmpty()) {
                ListaPersonalizada nuevaLista = new ListaPersonalizada(UUID.randomUUID().toString(), nombreLista);
                listas.add(nuevaLista);
                db.listaDao().insertarLista(nuevaLista);
                adapter.notifyItemInserted(listas.size() - 1);
            } else {
                Toast.makeText(this, "Nombre inválido", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
