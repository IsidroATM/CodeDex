package com.example.codedex.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.codedex.R;
import com.example.codedex.activities.NuevoAlgoritmoActivity;
import com.example.codedex.adapters.AlgoritmoPropioAdapter;
import com.example.codedex.adapters.AlgoritmoPropioViewModel;
import com.example.codedex.models.AlgoritmoPropio;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class AlgoritmosPropiosFragment extends Fragment {

    private AlgoritmoPropioViewModel viewModel;
    private AlgoritmoPropioAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_algoritmos_propios, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_algoritmos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AlgoritmoPropioAdapter();
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = view.findViewById(R.id.fab_add_algoritmo);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), NuevoAlgoritmoActivity.class);
            startActivity(intent);
        });

        viewModel = new ViewModelProvider(this).get(AlgoritmoPropioViewModel.class);
        viewModel.getAlgoritmos().observe(getViewLifecycleOwner(), new Observer<List<AlgoritmoPropio>>() {
            @Override
            public void onChanged(List<AlgoritmoPropio> algoritmos) {
                adapter.setAlgoritmos(algoritmos);
            }
        });

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
    }
}