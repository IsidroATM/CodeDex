package com.example.codedex.details;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.codedex.R;
import com.example.codedex.models.LenguajeProgramacion;

public class DetalleLenguajeActivity extends AppCompatActivity {

    private TextView textTitulo, textDescripcion, textFundador, textAnio, textProyectos, textIDES;
    private LinearLayout containerDetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_lenguaje);

        LenguajeProgramacion lenguaje = (LenguajeProgramacion) getIntent().getSerializableExtra("lenguaje");

        textTitulo = findViewById(R.id.textTituloDetalle);
        textDescripcion = findViewById(R.id.textDescripcionLenguaje);
        textFundador = findViewById(R.id.textFundadorLenguaje);
        textAnio = findViewById(R.id.textAnioLenguaje);
        textProyectos = findViewById(R.id.textProyectosLenguaje);
        textIDES = findViewById(R.id.textIDESLenguaje);
        containerDetalle = findViewById(R.id.containerDetalleLenguaje);

        textTitulo.setText(lenguaje.getNombre());
        textDescripcion.setText(lenguaje.getDescripcion());
        textFundador.setText("Fundador: " + lenguaje.getFundador());
        textAnio.setText("Año de creación: " + lenguaje.getACreacion());
        textProyectos.setText("Proyectos:\n" + String.join(", ", lenguaje.getProyectosUsados()));
        textIDES.setText("IDEs compatibles:\n" + String.join(", ", lenguaje.getIdes()));

        try {
            containerDetalle.setBackgroundColor(android.graphics.Color.parseColor(lenguaje.getColorHex()));
        } catch (Exception e) {
            containerDetalle.setBackgroundColor(android.graphics.Color.LTGRAY);
        }
    }
}