package com.example.codedex.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codedex.R;
import com.example.codedex.details.DetalleAlgoritmoActivity;
import com.example.codedex.models.Algoritmo;

import java.util.List;

public class AlgoritmoAdapter extends RecyclerView.Adapter<AlgoritmoAdapter.ViewHolder> {

    private List<Algoritmo> algoritmos;
    private Context context;

    public AlgoritmoAdapter(Context context, List<Algoritmo> algoritmos) {
        this.context = context;
        this.algoritmos = algoritmos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_algoritmo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Algoritmo algoritmo = algoritmos.get(position);

        holder.tvNombre.setText(algoritmo.getNombre());
        holder.tvComplejidad.setText("Complejidad: " + algoritmo.getComplejidad());

        try {
            holder.itemView.setBackgroundColor(Color.parseColor(algoritmo.getColorFondo()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Cambiar la vista
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetalleAlgoritmoActivity.class);
            intent.putExtra("algoritmo", algoritmo);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return algoritmos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvComplejidad;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvComplejidad = itemView.findViewById(R.id.tvComplejidad);
        }
    }
}

