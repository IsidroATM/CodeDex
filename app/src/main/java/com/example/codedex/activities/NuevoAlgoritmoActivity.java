package com.example.codedex.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.amrdeveloper.codeview.CodeView;
import com.example.codedex.R;
import com.example.codedex.database.AppDatabase;
import com.example.codedex.models.AlgoritmoPropio;
import com.amrdeveloper.codeview.CodeView;


public class NuevoAlgoritmoActivity extends AppCompatActivity {

    private EditText etTitulo;
    private CodeView codeView;
    private Button btnGuardar;
    private Spinner spinnerLenguaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_algoritmo);

        etTitulo = findViewById(R.id.et_titulo_algoritmo);
        codeView = findViewById(R.id.code_view);
        btnGuardar = findViewById(R.id.btn_guardar_algoritmo);
        spinnerLenguaje = findViewById(R.id.spinner_lenguaje);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.lenguajes_disponibles,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLenguaje.setAdapter(adapter);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarAlgoritmo();
            }
        });
    }

    private void guardarAlgoritmo() {
        String titulo = etTitulo.getText().toString().trim();
        String codigo = codeView.getText().toString();
        String lenguaje = spinnerLenguaje.getSelectedItem().toString();

        if (!titulo.isEmpty() && !codigo.isEmpty()) {
            AlgoritmoPropio algoritmo = new AlgoritmoPropio(titulo, codigo,lenguaje);

            new Thread(() -> {
                AppDatabase.getInstance(getApplicationContext())
                        .algoritmoPropioDao()
                        .insert(algoritmo);
                // Vuelve a la lista cuando termine de guardar
                runOnUiThread(this::finish);
            }).start();
        }
    }
}
