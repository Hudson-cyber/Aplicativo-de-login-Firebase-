   package com.hd.teladelogin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.hd.teladelogin.R;
import com.hd.teladelogin.conexao.ConexaoBD;
import com.hd.teladelogin.conexao.ConexaoRede;

   public class Login extends AppCompatActivity {
    private EditText edtSenha, edtUsuario;
    private Button btnLogin;
    private TextView txtCadastrar, txtRedefinirSenha;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ligando componentes
        setContentView(R.layout.activity_login);
        inicializarComponentes();
        eventosOnclick();


    }

       private void eventosOnclick() {
           txtCadastrar.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   // inicializando a tela de cadastro ao clicar no botão
                   Intent intent = new Intent(getApplicationContext(),Cadastro.class);
                   startActivity(intent);

               }
           });
           btnLogin.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    String strEmail = edtUsuario.getText().toString().trim();
                    String strSenha = edtSenha.getText().toString().trim();
                    //tratar dados
                    logar(strEmail,strSenha);

               }
           });
           txtRedefinirSenha.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(Login.this,RedefinirSenha.class);
                   startActivity(intent);
               }
           });

       }

       private void logar(String strEmail, String strSenha) {
            firebaseAuth.signInWithEmailAndPassword(strEmail,strSenha)
                    .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(Login.this,Perfil.class);
                                startActivity(intent);
                            }else{
                                alertar("Erro ao logar");
                                edtSenha.setText("");
                                edtUsuario.setText("");
                            }
                        }
                    });
       }

       private void alertar(String mensagem){
           Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();

       }


       private void inicializarComponentes() {
           edtSenha = findViewById(R.id.textInpSenha);
           edtUsuario = findViewById(R.id.textInpEmail);
           btnLogin = findViewById(R.id.btnCadastro);
           txtCadastrar = findViewById(R.id.txtCadastrar);
           txtRedefinirSenha = findViewById(R.id.txtRedefinirSenha);
       }

       @Override
       protected void onStart() {
           super.onStart();
           firebaseAuth = ConexaoBD.getFirebaseAuth();
       }
   }