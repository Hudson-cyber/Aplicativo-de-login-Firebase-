package com.hd.teladelogin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.hd.teladelogin.R;
import com.hd.teladelogin.conexao.ConexaoBD;

public class RedefinirSenha extends AppCompatActivity {
    private EditText edtEmail;
    private Button btnRedefinir, btnVoltar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefinir_senha);
        inicializarComponentes();
        eventoClick();
    }

    private void eventoClick() {
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RedefinirSenha.this,Login.class);
                startActivity(intent);
            }
        });
        btnRedefinir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail = edtEmail.getText().toString().trim();
                redefinirSenha(strEmail);
            }
        });
    }

    private void redefinirSenha(String strEmail) {
        firebaseAuth.sendPasswordResetEmail(strEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            alertar("Redefinição de senha enviada para"+strEmail);
                            edtEmail.setText("");
                        }else{
                            alertar("Erro ao redefinir Senha");
                            edtEmail.setText("");
                        }
                    }
                });
    }

    private void inicializarComponentes() {
        edtEmail= findViewById(R.id.edtEmail);
        btnRedefinir = findViewById(R.id.btnRedefinirSenha);
        btnVoltar = findViewById(R.id.btnVoltarLogin);
    }

    private void alertar(String mensagem){
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth = ConexaoBD.getFirebaseAuth();
    }
}