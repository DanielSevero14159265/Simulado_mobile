package com.mobile.simulado_estacionamento.ui;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mobile.simulado_estacionamento.ui.home.veiculo.Veiculo;
import com.mobile.simulado_estacionamento.ui.home.veiculo.VeiculoAdapter;

import java.util.List;

public class Database {

    public void salvar(Veiculo veiculo, Context c, boolean status) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (veiculo.getId() > 0) {
            // Atualiza veículo existente
            db.collection("registros")
                    .document(String.valueOf(veiculo.getId()))
                    .set(veiculo)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(c, "Registro atualizado com sucesso", Toast.LENGTH_SHORT).show();
                        Log.d("Database", "Atualizou registro com ID: " + veiculo.getId());
                    });
            return;
        }

        // Usa transaction para criar novo registro com ID único e atualizado
        db.runTransaction(transaction -> {
            DocumentSnapshot doc = transaction.get(db.collection("CountersID").document("registro_id"));
            int id = 1;
            if (doc.exists()) {
                Long contadorAtual = doc.getLong("id");
                if (contadorAtual != null) {
                    id = contadorAtual.intValue() + 1;
                }
            }
            veiculo.setId(id);

            // Atualiza o contador
            transaction.update(db.collection("CountersID").document("registro_id"), "id", id);

            // Salva o novo veículo com o ID gerado
            transaction.set(db.collection("registros").document(String.valueOf(id)), veiculo);

            return id; // retorno da transaction
        }).addOnSuccessListener(id -> {
            Toast.makeText(c, "Registro salvo com sucesso com ID: " + id, Toast.LENGTH_SHORT).show();
            Log.d("Database", "Registro salvo com ID: " + id);
        }).addOnFailureListener(e -> {
            Toast.makeText(c, "Erro ao salvar registro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("Database", "Erro ao salvar registro", e);
        });
    }



    public void remover(Veiculo veiculo, Context c) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("registros").document(String.valueOf(veiculo.getId())).delete()
                .addOnSuccessListener(unused ->
                        Toast.makeText(c, "Registro removido com sucesso", Toast.LENGTH_SHORT).show()
                );
    }

    public void listar(List<Veiculo> argLista, VeiculoAdapter argAdapter, Context c) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("registros").addSnapshotListener((value, error) -> {
            if (error != null || value == null) {
                System.out.println("Erro: " + (error != null ? error.getMessage() : "Snapshot vazio"));
                return;
            }

            argLista.clear();
            for (DocumentSnapshot doc : value.getDocuments()) {
                Veiculo v = doc.toObject(Veiculo.class);
                if (v != null && doc.getId() != null) {
                    try {
                        // o ID está no nome do documento
                        v.setId(Integer.parseInt(doc.getId()));
                    } catch (NumberFormatException e) {
                        v.setId(0);
                    }
                    argLista.add(v);
                }
            }
            argAdapter.notifyDataSetChanged();
        });
    }
    public void registrarSaida(Veiculo veiculo, String horaSaida, Context c) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        veiculo.setSaida(horaSaida);
        veiculo.setInside(false); // marca como fora do estacionamento

        db.collection("registros")
                .document(String.valueOf(veiculo.getId()))
                .set(veiculo)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(c, "Saída registrada com sucesso", Toast.LENGTH_SHORT).show();
                    Log.d("Database", "Saída registrada para veículo ID: " + veiculo.getId());
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(c, "Erro ao registrar saída", Toast.LENGTH_SHORT).show();
                    Log.e("Database", "Erro ao registrar saída", e);
                });
    }

}
