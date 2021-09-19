package com.hd.teladelogin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hd.teladelogin.conexao.ConexaoBD;
import com.hd.teladelogin.R;

public class Perfil extends AppCompatActivity {
    private TextView txtNome, txtEmail;
    private Button btnSair;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        inicializarComponentes();
        eventoClick();
    }

    private void eventoClick() {
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConexaoBD.logout();
                finish();
            }
        });
    }

    private void inicializarComponentes() {
        txtNome = findViewById(R.id.txtNome);
        txtEmail = findViewById(R.id.txtEmail);
        btnSair = findViewById(R.id.btnSair);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth = ConexaoBD.getFirebaseAuth();
        firebaseUser = ConexaoBD.getFirebaseUser();
        verificarUsuario();
    }

    private void verificarUsuario() {
        if(firebaseUser == null){
            finish();
        }else{
            txtEmail.setText("Email: "+ firebaseUser.getEmail());
            txtNome.setText("ID: "+ firebaseUser.getUid());
        }
    }
}