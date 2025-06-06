package com.mobile.simulado_estacionamento.ui.home.veiculo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobile.simulado_estacionamento.R;

import org.jspecify.annotations.NonNull;

import java.util.List;

public class VeiculoAdapter extends RecyclerView.Adapter<VeiculoAdapter.VeiculoViewHolder> {

    private List<Veiculo> lista;

    public VeiculoAdapter(List<Veiculo> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public VeiculoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_veiculo, parent, false);
        return new VeiculoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VeiculoViewHolder holder, int position) {
        Veiculo v = lista.get(position);
        holder.textPlaca.setText(v.getPlaca());
        holder.textDataHora.setText(v.getDataHora());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class VeiculoViewHolder extends RecyclerView.ViewHolder {
        TextView textPlaca, textDataHora;

        public VeiculoViewHolder(@NonNull View itemView) {
            super(itemView);
            textPlaca = itemView.findViewById(R.id.textPlaca);
            textDataHora = itemView.findViewById(R.id.textDataHora);
        }
    }
}
