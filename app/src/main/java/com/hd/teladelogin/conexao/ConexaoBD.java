package com.hd.teladelogin.conexao;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ConexaoBD {
    private static FirebaseAuth firebaseAuth;
    private static FirebaseAuth.AuthStateListener authStateListener;
    private static FirebaseUser firebaseUser;

    private ConexaoBD() {

    }

    public static FirebaseAuth getFirebaseAuth(){
        if(firebaseAuth ==null){
            inicializarFiebaseAuth();
        }
        return firebaseAuth;
    }

    private static void inicializarFiebaseAuth() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //criar novo user resgatando os dados da conexão para ver se o user já é cadastrado
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    // se o usuario for diferente de null meu obj recebe as informações
                    firebaseUser = user;
                }
            }
        };
        firebaseAuth.addAuthStateListener(authStateListener);

    }
    public static FirebaseUser getFirebaseUser(){
        return firebaseUser;
    }
    public static void logout(){
        firebaseAuth.signOut();
    }
}
