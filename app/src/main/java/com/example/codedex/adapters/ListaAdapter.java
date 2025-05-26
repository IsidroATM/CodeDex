package com.example.codedex.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codedex.R;
import com.example.codedex.lists.ContenidoListaActivity;
import com.example.codedex.models.ListaPersonalizada;


import java.util.List;

public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.ViewHolder> {

    private Context context;
    private List<ListaPersonalizada> listas;

    public ListaAdapter(Context context, List<ListaPersonalizada> listas) {
        this.context = context;
        this.listas = listas;
    }

    @Override
    public ListaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lista, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListaAdapter.ViewHolder holder, int position) {
        ListaPersonalizada lista = listas.get(position);
        holder.nombreLista.setText(lista.getNombre());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ContenidoListaActivity.class);
            intent.putExtra("lista", lista);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nombreLista;

        public ViewHolder(View itemView) {
            super(itemView);
            nombreLista = itemView.findViewById(R.id.tvNombreLista);
        }
    }
}
