package com.example.codedex.details;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amrdeveloper.codeview.CodeView;
import com.example.codedex.R;
import com.example.codedex.activities.EditarAlgoritmoActivity;
import com.example.codedex.database.AppDatabase;
import com.example.codedex.models.AlgoritmoPropio;

public class DetalleAlgoritmoPropioActivity extends AppCompatActivity {

    private TextView tvTitulo, tvLenguaje;
    private CodeView codeView;
    private Button btnEditar;
    private int algoritmoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_algoritmo_propio);

        tvTitulo = findViewById(R.id.tv_titulo_algoritmo);
        tvLenguaje = findViewById(R.id.tv_lenguaje_algoritmo);
        codeView = findViewById(R.id.code_view_ver);
        btnEditar = findViewById(R.id.btn_editar_algoritmo);

        algoritmoId = getIntent().getIntExtra("algoritmo_id", -1);

        btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(DetalleAlgoritmoPropioActivity.this, EditarAlgoritmoActivity.class);
            intent.putExtra("algoritmo_id", algoritmoId);
            startActivity(intent);
        });

        cargarAlgoritmo();
    }
    @Override
    protected void onResume() {
        super.onResume();
        cargarAlgoritmo(); // Recarga los datos al volver de editar
    }

    private void cargarAlgoritmo() {
        if (algoritmoId != -1) {
            new Thread(() -> {
                AlgoritmoPropio algoritmo = AppDatabase.getInstance(getApplicationContext())
                        .algoritmoPropioDao()
                        .getById(algoritmoId);

                if (algoritmo != null) {
                    runOnUiThread(() -> {
                        tvTitulo.setText(algoritmo.getTitulo());
                        codeView.setText(algoritmo.getCodigo());
                        tvLenguaje.setText(algoritmo.getLenguaje());
                    });
                }
            }).start();
        }
    }

}