package com.example.codedex.lists;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
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

public class ListActivity extends Fragment {

    private RecyclerView recyclerView;
    private ListaAdapter adapter;
    private List<ListaPersonalizada> listas = new ArrayList<>();
    private FloatingActionButton btnAgregarLista;
    private AppDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_list, container, false);

        // Inicializar Room DB
        db = Room.databaseBuilder(requireContext(), AppDatabase.class, "codedex-db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        listas = new ArrayList<>(db.listaDao().obtenerListas());

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

        recyclerView = view.findViewById(R.id.recyclerListas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ListaAdapter(getContext(), listas);
        recyclerView.setAdapter(adapter);

        btnAgregarLista = view.findViewById(R.id.btnAgregarLista);
        btnAgregarLista.setOnClickListener(v -> mostrarDialogoNuevaLista());

        return view;
    }

    private void mostrarDialogoNuevaLista() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Nombre de la nueva lista");

        final EditText input = new EditText(requireContext());
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
                Toast.makeText(requireContext(), "Nombre inválido", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
