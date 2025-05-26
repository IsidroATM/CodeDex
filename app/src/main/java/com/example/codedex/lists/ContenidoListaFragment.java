package com.example.codedex.lists;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codedex.R;
import com.example.codedex.adapters.AlgoritmoAdapter;
import com.example.codedex.models.Algoritmo;
import com.example.codedex.models.ListaPersonalizada;

import java.util.List;

public class ContenidoListaFragment extends Fragment {

    private static final String ARG_LISTA = "arg_lista";

    private TextView tvNombreLista;
    private RecyclerView recyclerAlgoritmos;
    private ListaPersonalizada lista;

    public static ContenidoListaFragment newInstance(ListaPersonalizada lista) {
        ContenidoListaFragment fragment = new ContenidoListaFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_LISTA, lista);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_contenido_lista, container, false);

        tvNombreLista = view.findViewById(R.id.tvNombreLista);
        recyclerAlgoritmos = view.findViewById(R.id.recyclerAlgoritmosEnLista);
        recyclerAlgoritmos.setLayoutManager(new LinearLayoutManager(requireContext()));

        if (getArguments() != null) {
            lista = (ListaPersonalizada) getArguments().getSerializable(ARG_LISTA);
            if (lista != null) {
                tvNombreLista.setText(lista.getNombre());

                List<Algoritmo> algoritmos = lista.getAlgoritmos(); // Puede estar vacía inicialmente
                AlgoritmoAdapter adapter = new AlgoritmoAdapter(requireContext(), algoritmos);
                recyclerAlgoritmos.setAdapter(adapter);
            }
        }

        return view;
    }
}