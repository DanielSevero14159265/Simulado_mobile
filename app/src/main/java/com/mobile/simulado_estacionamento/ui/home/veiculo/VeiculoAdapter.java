    package com.mobile.simulado_estacionamento.ui.home.veiculo;

    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.TextView;

    import androidx.recyclerview.widget.RecyclerView;

    import com.google.firebase.firestore.FirebaseFirestore;
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
            holder.textDataHora.setText(v.getEntrada());

            View sair = holder.itemView.findViewById(R.id.bt_sair);

            if (v.getSaida() != null && !v.getSaida().isEmpty()) {
                holder.horario_saida.setText(v.getSaida());
                sair.setVisibility(View.INVISIBLE);
            } else {
                sair.setVisibility(View.VISIBLE);
                sair.setOnClickListener(view -> {
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                    sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
                    String horaSaida = sdf.format(new Date());

                    v.setSaida(horaSaida);
                    holder.horario_saida.setText(horaSaida);
                    sair.setVisibility(View.INVISIBLE);

                    FirebaseFirestore.getInstance()
                            .collection("registros")
                            .document(String.valueOf(v.getId()))
                            .update("saida", horaSaida);
                });
            }
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
