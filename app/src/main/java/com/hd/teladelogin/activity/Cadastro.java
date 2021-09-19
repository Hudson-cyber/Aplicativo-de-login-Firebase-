package com.hd.teladelogin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.hd.teladelogin.conexao.ConexaoBD;
import com.hd.teladelogin.R;
import com.hd.teladelogin.conexao.ConexaoRede;

public class Cadastro extends AppCompatActivity {
    private EditText edtNome, edtEmail, edtSenha;
    private Button btnCadastrar, btnVoltar;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        inicializarComponentes();
        eventoClick();
    }

    private void eventoClick() {
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail, strNome, strSenha;
                strEmail = edtEmail.getText().toString().trim();
                strNome = edtNome.getText().toString().trim();
                strSenha = edtSenha.getText().toString().trim();
                if( verificarDados(strEmail,strNome,strSenha) ) {
                    if (ConexaoRede.verificarConexao(getApplicationContext())) {
                        criarUser(strEmail, strSenha);
                    }else{
                        alertar("Erro ao conectar com a internet");
                    }
                }
            }
        });
    }

    private boolean verificarDados(String strEmail, String strNome, String strSenha) {
        if(ValidacaoDados.emBranco(strNome)){
            ValidacaoDados.mensagem(edtNome,"Campo em Branco");
            return false;
        }else if(ValidacaoDados.emBranco(strEmail)){
            ValidacaoDados.mensagem(edtEmail,"Campo em Branco");
            return false;
        }else if(ValidacaoDados.validarDadosEmail(strEmail)){
            ValidacaoDados.mensagem(edtEmail,"Email Inválido");
            return false;
        }else if(ValidacaoDados.emBranco(strSenha)){
            ValidacaoDados.mensagem(edtSenha,"Campo em Branco");
            return false;
        }else{
            return true;
        }
    }

    private void criarUser(String strEmail, String strSenha) {
        firebaseAuth.createUserWithEmailAndPassword(strEmail,strSenha)
                .addOnCompleteListener(Cadastro.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            alertar("Cadastro realizado com sucesso");
                            Intent intent = new Intent(Cadastro.this,Perfil.class);
                            startActivity(intent);
                        }else{
                            alertar("Erro ao se Cadastrar, Tente novamente mais tarde!!!");
                            edtEmail.setText("");
                            edtNome.setText("");
                            edtSenha.setText("");
                        }
                    }
                });
    }
    private void alertar(String mensagem){
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();

    }

    private void inicializarComponentes() {
        edtNome = findViewById(R.id.textInpNome);
        edtEmail = findViewById(R.id.textInpEmail);
        edtSenha = findViewById(R.id.textInpSenha);
        btnCadastrar = findViewById(R.id.btnCadastro);
        btnVoltar = findViewById(R.id.btnVoltar);

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth = ConexaoBD.getFirebaseAuth();
    }
}