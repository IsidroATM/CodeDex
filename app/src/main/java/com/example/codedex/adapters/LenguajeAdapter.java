package com.example.codedex.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codedex.R;
import com.example.codedex.details.DetalleLenguajeActivity;
import com.example.codedex.models.LenguajeProgramacion;

import java.util.List;

public class LenguajeAdapter extends RecyclerView.Adapter<LenguajeAdapter.ViewHolder> {

    private List<LenguajeProgramacion> lenguajes;
    private Context context;

    public LenguajeAdapter(Context context, List<LenguajeProgramacion> lenguajes) {
        this.context = context;
        this.lenguajes = lenguajes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lenguaje, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LenguajeProgramacion lenguaje = lenguajes.get(position);

        holder.nombre.setText(lenguaje.getNombre());
        holder.abreviacion.setText(lenguaje.getAbreviacion());

        try {
            holder.cardView.setCardBackgroundColor(Color.parseColor(lenguaje.getColorHex()));
        } catch (Exception e) {
            e.printStackTrace();
            holder.cardView.setCardBackgroundColor(Color.LTGRAY);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetalleLenguajeActivity.class);
            intent.putExtra("lenguaje", lenguaje);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return lenguajes.size();
    }

    public void addLenguajes(List<LenguajeProgramacion> nuevosLenguajes) {
        int posicionInicial = lenguajes.size();
        lenguajes.addAll(nuevosLenguajes);
        notifyItemRangeInserted(posicionInicial, nuevosLenguajes.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, abreviacion;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.textNombreLenguaje);
            abreviacion = itemView.findViewById(R.id.textAbreviacionLenguaje);
            cardView = (CardView) itemView;
        }
    }
}
