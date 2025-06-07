package com.example.codedex.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.codedex.R;
import com.example.codedex.details.DetalleAlgoritmoPropioActivity;
import com.example.codedex.models.AlgoritmoPropio;
import java.util.ArrayList;
import java.util.List;

public class AlgoritmoPropioAdapter extends RecyclerView.Adapter<AlgoritmoPropioAdapter.AlgoritmoViewHolder> {

    private List<AlgoritmoPropio> algoritmos = new ArrayList<>();

    @NonNull
    @Override
    public AlgoritmoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_algoritmo_propio, parent, false);
        return new AlgoritmoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlgoritmoViewHolder holder, int position) {
        AlgoritmoPropio algoritmo = algoritmos.get(position);
        holder.tvTitulo.setText(algoritmo.getTitulo());
        holder.tvLenguaje.setText("Lenguaje: " + algoritmo.getLenguaje());

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, DetalleAlgoritmoPropioActivity.class);
            intent.putExtra("algoritmo_id", algoritmo.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return algoritmos.size();
    }

    public void setAlgoritmos(List<AlgoritmoPropio> algoritmos) {
        this.algoritmos = algoritmos;
        notifyDataSetChanged();
    }

    static class AlgoritmoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitulo;
        private TextView tvLenguaje;

        public AlgoritmoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tv_titulo_algoritmo_item);
            tvLenguaje = itemView.findViewById(R.id.tv_lenguaje_algoritmo_item);
        }
    }
}