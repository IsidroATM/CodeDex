package com.example.codedex.details;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.codedex.R;
import com.example.codedex.database.AppDatabase;
import com.example.codedex.models.Algoritmo;
import com.example.codedex.models.ListaPersonalizada;

import java.util.List;
import java.util.Map;

public class DetalleAlgoritmoActivity extends AppCompatActivity {

    private TextView tvNombre, tvDescripcion, tvProyectos, tvComplejidad;
    private LinearLayout contenedorSintaxisDinamico;
    private ScrollView scrollDetalleAlgoritmo;
    private Button btnAgregarALista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_algoritmo);

        // Obtener el objeto Algoritmo desde el Intent
        Algoritmo algoritmo = (Algoritmo) getIntent().getSerializableExtra("algoritmo");

        // Inicializar vistas
        tvNombre = findViewById(R.id.tvNombre);
        tvDescripcion = findViewById(R.id.tvDescripcion);
        tvProyectos = findViewById(R.id.tvProyectos);
        tvComplejidad = findViewById(R.id.tvComplejidad);
        contenedorSintaxisDinamico = findViewById(R.id.contenedorSintaxisDinamico);
        scrollDetalleAlgoritmo = findViewById(R.id.scrollDetalleAlgoritmo);
        btnAgregarALista = findViewById(R.id.btnAgregarALista);

        if (algoritmo != null) {
            // Datos básicos
            tvNombre.setText(algoritmo.getNombre());
            tvDescripcion.setText(algoritmo.getDescripcion());
            tvProyectos.setText("Usos: " + algoritmo.getProyectosUso());
            tvComplejidad.setText("Complejidad: " + algoritmo.getComplejidad());

            // Aplicar color de fondo dinámico (con fallback)
            try {
                scrollDetalleAlgoritmo.setBackgroundColor(Color.parseColor(algoritmo.getColorFondo()));
            } catch (Exception e) {
                scrollDetalleAlgoritmo.setBackgroundColor(Color.LTGRAY);
            }

            // Mostrar sintaxis dinámicamente
            if (algoritmo.getSintaxis() != null && algoritmo.getSintaxis().tieneLenguajes()) {
                Map<String, String> codigos = algoritmo.getSintaxis().getCodigoPorLenguaje();

                for (Map.Entry<String, String> entrada : codigos.entrySet()) {
                    String lenguaje = entrada.getKey();
                    String codigo = entrada.getValue();

                    // Crear y configurar TextView para título del lenguaje
                    TextView tvTitulo = new TextView(this);
                    tvTitulo.setText(capitalizeFirstLetter(lenguaje));
                    tvTitulo.setTextSize(18);
                    tvTitulo.setTypeface(Typeface.DEFAULT_BOLD);
                    tvTitulo.setPadding(0, 16, 0, 4);
                    contenedorSintaxisDinamico.addView(tvTitulo);

                    // Crear y configurar TextView para el código fuente
                    TextView tvCodigo = new TextView(this);
                    tvCodigo.setText(codigo);
                    tvCodigo.setTypeface(Typeface.MONOSPACE);
                    tvCodigo.setPadding(16, 8, 16, 16);
                    tvCodigo.setBackgroundColor(Color.parseColor("#f4f4f4"));
                    tvCodigo.setTextColor(Color.BLACK);
                    tvCodigo.setTextSize(14);
                    contenedorSintaxisDinamico.addView(tvCodigo);
                }
            } else {
                contenedorSintaxisDinamico.setVisibility(View.GONE);
            }
        } else {
            // Caso donde no llega el objeto
            tvNombre.setText("Algoritmo no encontrado");
        }
        btnAgregarALista.setOnClickListener(v -> {
            if (algoritmo != null) {
                new Thread(() -> {
                    AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                    List<ListaPersonalizada> listas = db.listaDao().obtenerListas();

                    runOnUiThread(() -> {
                        if (listas.isEmpty()) {
                            Toast.makeText(this, "No hay listas disponibles", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String[] nombresListas = new String[listas.size()];
                        for (int i = 0; i < listas.size(); i++) {
                            nombresListas[i] = listas.get(i).getNombre();
                        }

                        // Mostrar el diálogo
                        new android.app.AlertDialog.Builder(this)
                                .setTitle("Selecciona una lista")
                                .setItems(nombresListas, (dialog, which) -> {
                                    ListaPersonalizada listaSeleccionada = listas.get(which);

                                    new Thread(() -> {
                                        List<Algoritmo> existentes = listaSeleccionada.getAlgoritmos();

                                        boolean yaExiste = false;
                                        for (Algoritmo a : existentes) {
                                            if (a.getId() == algoritmo.getId()) {

                                                yaExiste = true;
                                                break;
                                            }
                                        }

                                        if (!yaExiste) {
                                            existentes.add(algoritmo);
                                            listaSeleccionada.setAlgoritmos(existentes);
                                            db.listaDao().actualizarLista(listaSeleccionada);

                                            runOnUiThread(() ->
                                                    Toast.makeText(this, "Agregado a " + listaSeleccionada.getNombre(), Toast.LENGTH_SHORT).show());
                                        } else {
                                            runOnUiThread(() ->
                                                    Toast.makeText(this, "Ya está en " + listaSeleccionada.getNombre(), Toast.LENGTH_SHORT).show());
                                        }
                                    }).start();
                                })
                                .setNegativeButton("Cancelar", null)
                                .show();
                    });
                }).start();
            }
        });
    }



    // Método auxiliar para capitalizar la primera letra de un string
    private String capitalizeFirstLetter(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
