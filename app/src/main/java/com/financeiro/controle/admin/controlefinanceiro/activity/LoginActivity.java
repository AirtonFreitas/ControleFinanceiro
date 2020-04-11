package com.financeiro.controle.admin.controlefinanceiro.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.financeiro.controle.admin.controlefinanceiro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;


public class LoginActivity extends Activity {



    private EditText caixaEmail;
    private EditText caixaSenha;
    private Button botaoEntrar;
    private Button botaoNovoCadastro;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onStart() {
        super.onStart();
        verificarUsuarioLogado();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        caixaEmail = (EditText) findViewById(R.id.emailcxID);
        caixaSenha = (EditText) findViewById(R.id.senhacxID);
        botaoEntrar = (Button) findViewById(R.id.botaoEntrarID);
        botaoNovoCadastro = (Button) findViewById(R.id.botaoNovoCadID);


        firebaseAuth = FirebaseAuth.getInstance();

        verificarUsuarioLogado();



        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email, senha;
                email = caixaEmail.getText().toString();
                senha = caixaSenha.getText().toString();


                if(!email.isEmpty()){
                    if(!senha.isEmpty()){
                        validarLogin();
                    }else{
                        Toast.makeText(LoginActivity.this, "Informe a senha", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Preencha o e-mail", Toast.LENGTH_SHORT).show();
                }
            }
        });

        botaoNovoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MainCadatroActivityFireBase.class) );
            }
        });


    }




    public void validarLogin(){
        firebaseAuth.signInWithEmailAndPassword(caixaEmail.getText().toString(), caixaSenha.getText().toString())
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            abrirTelaPrincipal();
                        } else {

                            String excecao = "";
                            try{
                                throw task.getException();
                            }catch (FirebaseAuthInvalidCredentialsException e){
                                excecao = "Seu e-mail parece não ser válido :( ";
                            }catch (FirebaseAuthInvalidUserException e){
                                excecao = "Usuário não cadastrado";
                            }catch (Exception e){
                                excecao = "Erro ao cadastrar usuário" + e.getMessage();
                            }
                            Toast.makeText(LoginActivity.this, excecao, Toast.LENGTH_LONG).show();
                            Log.i("CreaterUser", "Erro ao Cadastrar!");
                        }
                    }
                });


    }
    public void abrirTelaPrincipal(){
        startActivity(new Intent(this, principalActivity.class));
        finish();
    }
    public void verificarUsuarioLogado(){
        if(firebaseAuth.getCurrentUser() != null){
            abrirTelaPrincipal();
        }
    }

}
