package com.example.codedex.details;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.codedex.R;
import com.example.codedex.models.Algoritmo;

public class DetalleAlgoritmoActivity extends AppCompatActivity {

    private TextView tvNombre, tvDescripcion, tvProyectos, tvCsharp, tvJava, tvPython;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_algoritmo);

        tvNombre = findViewById(R.id.tvNombre);
        tvDescripcion = findViewById(R.id.tvDescripcion);
        tvProyectos = findViewById(R.id.tvProyectos);
        tvCsharp = findViewById(R.id.tvCsharp);
        tvJava = findViewById(R.id.tvJava);
        tvPython = findViewById(R.id.tvPython);

        // Recibes el objeto Algoritmo completo
        Algoritmo algoritmo = (Algoritmo) getIntent().getSerializableExtra("algoritmo");

        if (algoritmo != null) {
            // Ahora llenas los TextViews directamente con los datos del algoritmo
            tvNombre.setText(algoritmo.getNombre());
            tvDescripcion.setText(algoritmo.getDescripcion());
            tvProyectos.setText(algoritmo.getProyectosUso());

            if (algoritmo.getSintaxis() != null) {
                tvCsharp.setText(algoritmo.getSintaxis().getCsharp());
                tvJava.setText(algoritmo.getSintaxis().getJava());
                tvPython.setText(algoritmo.getSintaxis().getPython());
            }
        } else {
            // Si por alguna razón no llegó el objeto
            tvNombre.setText("Algoritmo no encontrado");
        }
    }

}
