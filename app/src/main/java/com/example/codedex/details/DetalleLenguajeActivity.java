package com.example.codedex.details;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.codedex.R;
import com.example.codedex.models.LenguajeProgramacion;

public class DetalleLenguajeActivity extends AppCompatActivity {

    private TextView textTitulo, textDescripcion, textFundador, textAnio, textProyectos, textIDES;
    private LinearLayout containerDetalle;
    private ScrollView scrollDetalle;
    private LinearLayout containerIDES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_lenguaje);

        LenguajeProgramacion lenguaje = (LenguajeProgramacion) getIntent().getSerializableExtra("lenguaje");

        scrollDetalle = findViewById(R.id.scrollDetalleLenguaje); // nuevo
        textTitulo = findViewById(R.id.textTituloDetalle);
        textDescripcion = findViewById(R.id.textDescripcionLenguaje);
        textFundador = findViewById(R.id.textFundadorLenguaje);
        textAnio = findViewById(R.id.textAnioLenguaje);
        textProyectos = findViewById(R.id.textProyectosLenguaje);
        containerIDES = findViewById(R.id.containerIDES);
        containerDetalle = findViewById(R.id.containerDetalleLenguaje);

        textTitulo.setText(lenguaje.getNombre());
        textDescripcion.setText(lenguaje.getDescripcion());
        textFundador.setText("Fundador: " + lenguaje.getFundador());
        textAnio.setText("Año de creación: " + lenguaje.getACreacion());
        textProyectos.setText("Proyectos:\n" + String.join(", ", lenguaje.getProyectosUsados()));
        for (String ide : lenguaje.getIdes()) {
            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 24); // margen inferior entre tarjetas
            card.setLayoutParams(params);
            card.setPadding(24, 24, 24, 24);
            card.setBackgroundResource(R.drawable.cmd_background); // usa un drawable como fondo

            TextView nombreIDE = new TextView(this);
            nombreIDE.setText(ide);
            nombreIDE.setTextSize(16f);
            nombreIDE.setTextColor(android.graphics.Color.WHITE);
            nombreIDE.setTypeface(null, android.graphics.Typeface.BOLD);

            card.addView(nombreIDE);
            containerIDES.addView(card);
        }


        try {
            scrollDetalle.setBackgroundColor(android.graphics.Color.parseColor(lenguaje.getColorHex()));
        } catch (Exception e) {
            scrollDetalle.setBackgroundColor(android.graphics.Color.LTGRAY);
        }
    }
}