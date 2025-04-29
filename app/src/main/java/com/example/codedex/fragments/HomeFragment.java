package com.example.codedex.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.codedex.R;
import com.example.codedex.MenuActivity;

public class HomeFragment extends Fragment {

    private Button btnAlgoritmos, btnLenguajes;

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflamos el layout del fragmento
        View view = inflater.inflate(R.layout.activity_home_fragment, container, false);

        // Inicializamos los botones
        btnAlgoritmos = view.findViewById(R.id.btnAlgoritmos);
        btnLenguajes = view.findViewById(R.id.btnLenguajes);

        // Configuramos los listeners de los botones
        btnAlgoritmos.setOnClickListener(v -> {
            // Reemplazamos el fragmento actual con el de algoritmos
            loadFragment(new AlgoritmosFragment());
        });

        btnLenguajes.setOnClickListener(v -> {
            // Reemplazamos el fragmento actual con el de lenguajes
            loadFragment(new LenguajesFragment());
        });

        return view;
    }

    // Método para cargar el fragmento
    private void loadFragment(Fragment fragment) {
        // Accedemos al fragment manager de la actividad para hacer la transacción
        MenuActivity activity = (MenuActivity) getActivity();
        if (activity != null) {
            // Iniciamos la transacción para reemplazar el fragmento
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)  // El contenedor donde se cargan los fragmentos
                    .addToBackStack(null)  // Opcional: agrega la transacción al backstack para poder retroceder
                    .commit();
        }
    }
}
