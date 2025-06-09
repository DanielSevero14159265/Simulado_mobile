package com.mobile.simulado_estacionamento.ui.home.veiculo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobile.simulado_estacionamento.R;

import org.jspecify.annotations.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class VeiculoAdapter extends RecyclerView.Adapter<VeiculoAdapter.VeiculoViewHolder> {

    private List<Veiculo> lista;

    public VeiculoAdapter(List<Veiculo> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public VeiculoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nota, parent, false);
        return new VeiculoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VeiculoViewHolder holder, int position) {
        Veiculo v = lista.get(position);
        holder.textPlaca.setText(v.getPlaca());
        holder.textDataHora.setText(v.getDataHora());
        View sair = holder.itemView.findViewById(R.id.bt_sair);
        sair.setOnClickListener(View -> {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
            String horaSaida = sdf.format(new Date());
            holder.horario_saida.setText(horaSaida);
            sair.setVisibility(View.INVISIBLE);
        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class VeiculoViewHolder extends RecyclerView.ViewHolder {
        TextView textPlaca, textDataHora, horario_saida;

        public VeiculoViewHolder(@NonNull View itemView) {
            super(itemView);
            textPlaca = itemView.findViewById(R.id.id_placa);
            textDataHora = itemView.findViewById(R.id.id_entrada);
            horario_saida = itemView.findViewById(R.id.id_saida);

        }
    }
}
