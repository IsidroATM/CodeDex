package com.example.codedex.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.amrdeveloper.codeview.CodeView;
import com.example.codedex.R;
import com.example.codedex.database.AppDatabase;
import com.example.codedex.models.AlgoritmoPropio;

public class EditarAlgoritmoActivity extends AppCompatActivity {

    private EditText etTitulo;
    private CodeView codeView;
    private Spinner spinnerLenguaje;
    private Button btnActualizar;

    private int algoritmoId;
    private AlgoritmoPropio algoritmoActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_algoritmo);

        etTitulo = findViewById(R.id.et_titulo_algoritmo_edit);
        codeView = findViewById(R.id.code_view_edit);
        spinnerLenguaje = findViewById(R.id.spinner_lenguaje_edit);
        btnActualizar = findViewById(R.id.btn_actualizar_algoritmo);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.lenguajes_disponibles,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLenguaje.setAdapter(adapter);

        algoritmoId = getIntent().getIntExtra("algoritmo_id", -1);

        if (algoritmoId != -1) {
            new Thread(() -> {
                algoritmoActual = AppDatabase.getInstance(getApplicationContext())
                        .algoritmoPropioDao()
                        .getById(algoritmoId);

                if (algoritmoActual != null) {
                    runOnUiThread(() -> {
                        etTitulo.setText(algoritmoActual.getTitulo());
                        codeView.setText(algoritmoActual.getCodigo());

                        // Selecciona el lenguaje en el spinner
                        int index = adapter.getPosition(algoritmoActual.getLenguaje());
                        spinnerLenguaje.setSelection(index);
                    });
                }
            }).start();
        }

        btnActualizar.setOnClickListener(v -> actualizarAlgoritmo());
    }

    private void actualizarAlgoritmo() {
        String nuevoTitulo = etTitulo.getText().toString().trim();
        String nuevoCodigo = codeView.getText().toString();
        String nuevoLenguaje = spinnerLenguaje.getSelectedItem().toString();

        if (!nuevoTitulo.isEmpty() && !nuevoCodigo.isEmpty()) {
            algoritmoActual.setTitulo(nuevoTitulo);
            algoritmoActual.setCodigo(nuevoCodigo);
            algoritmoActual.setLenguaje(nuevoLenguaje);

            new Thread(() -> {
                AppDatabase.getInstance(getApplicationContext())
                        .algoritmoPropioDao()
                        .update(algoritmoActual);//  update reemplaza si existe por PK
                runOnUiThread(this::finish);
            }).start();
        }
    }
}