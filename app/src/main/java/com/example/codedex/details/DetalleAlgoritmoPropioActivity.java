package com.example.codedex.details;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amrdeveloper.codeview.CodeView;
import com.example.codedex.R;
import com.example.codedex.database.AppDatabase;
import com.example.codedex.models.AlgoritmoPropio;

public class DetalleAlgoritmoPropioActivity extends AppCompatActivity {

    private TextView tvTitulo, tvLenguaje;
    private CodeView codeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_algoritmo_propio);

        tvTitulo = findViewById(R.id.tv_titulo_algoritmo);
        tvLenguaje = findViewById(R.id.tv_lenguaje_algoritmo);



        codeView = findViewById(R.id.code_view_ver);

        int algoritmoId = getIntent().getIntExtra("algoritmo_id", -1);

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