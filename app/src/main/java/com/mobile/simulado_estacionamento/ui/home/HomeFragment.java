package com.mobile.simulado_estacionamento.ui.home;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mobile.simulado_estacionamento.R;
import com.mobile.simulado_estacionamento.databinding.FragmentHomeBinding;
import com.mobile.simulado_estacionamento.ui.home.veiculo.Veiculo;
import com.mobile.simulado_estacionamento.ui.home.veiculo.VeiculoAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private ArrayList<Veiculo> listaVeiculos;
    private VeiculoAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Lista e Adapter
        listaVeiculos = new ArrayList<>();
        adapter = new VeiculoAdapter(listaVeiculos);

        binding.recyclerVeiculos.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerVeiculos.setAdapter(adapter);

        binding.botaoAdd.setOnClickListener(v -> {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            View dialogView = layoutInflater.inflate(R.layout.dialog_add_vehicle, null);
            EditText editPlaca = dialogView.findViewById(R.id.editPlaca);

            AlertDialog dialog = new AlertDialog.Builder(requireContext())
                    .setTitle("Cadastrar Veículo")
                    .setView(dialogView)
                    .setPositiveButton("Salvar", (dialogInterface, i) -> {
                        String placa = editPlaca.getText().toString();
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                        sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
                        String hora = sdf.format(new Date());


                        Veiculo novo = new Veiculo(placa, hora);
                        listaVeiculos.add(novo);
                        adapter.notifyItemInserted(listaVeiculos.size() - 1);
                    })
                    .setNegativeButton("Cancelar", null)
                    .create();

            dialog.show();
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
