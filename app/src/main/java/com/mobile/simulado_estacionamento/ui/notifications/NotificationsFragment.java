package com.mobile.simulado_estacionamento.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mobile.simulado_estacionamento.R;

public class NotificationsFragment extends Fragment {

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    private EditText edtEmail, edtSenha;
    private Button btnLogin;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        edtEmail = view.findViewById(R.id.editTextText);
        edtSenha = view.findViewById(R.id.editTextTextPassword);
        btnLogin = view.findViewById(R.id.button);

        btnLogin.setOnClickListener(v -> fazerLogin());

        return view;
    }

    private void fazerLogin() {
        String email = edtEmail.getText().toString().trim();
        String senha = edtSenha.getText().toString().trim();

        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(getContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("usuarios")
                .whereEqualTo("email", email)
                .whereEqualTo("senha", senha)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        Toast.makeText(getContext(), "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show();
                    } else {
                        DocumentSnapshot doc = queryDocumentSnapshots.getDocuments().get(0);
                        boolean isAdmin = doc.getBoolean("isAdmin") != null && doc.getBoolean("isAdmin");

                        SharedPreferences prefs = requireContext().getSharedPreferences("usuario", Context.MODE_PRIVATE);
                        prefs.edit().clear().apply(); // limpa tudo antes
                        prefs.edit().putBoolean("isAdmin", isAdmin).apply(); // salva o novo valor
                        Toast.makeText(getContext(), "Admin? " + isAdmin, Toast.LENGTH_SHORT).show();


                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                        navController.navigate(R.id.action_notificationsFragment_to_navigation_home);
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(getContext(), "Erro ao acessar banco de dados", Toast.LENGTH_SHORT).show()
                );
    }

}
