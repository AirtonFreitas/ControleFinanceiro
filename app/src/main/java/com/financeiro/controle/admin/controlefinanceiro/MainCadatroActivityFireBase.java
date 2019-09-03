package com.financeiro.controle.admin.controlefinanceiro;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainCadatroActivityFireBase extends Activity {

    private FirebaseAuth firebaseAuth;
    private Button botaoVoltar, botaoCadastrar;
    private EditText textoNome, textoEmail, textoSenha, textoConSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cadatro_activity_fire_base);

        botaoVoltar = (Button) findViewById(R.id.voltarIDfb);
        botaoCadastrar = (Button) findViewById(R.id.cadastrarIDfb);
        textoNome = (EditText) findViewById(R.id.nomeIDfb);
        textoEmail = (EditText) findViewById(R.id.emailIDfb);
        textoSenha = (EditText) findViewById(R.id.senhaIDfb);
        textoConSenha = (EditText) findViewById(R.id.senhaConIDfb);

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainCadatroActivityFireBase.this, MainActivity.class));

            }
        });

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {

                String nome, email, senha, conSenha;
                nome = textoNome.getText().toString();
                email = textoEmail.getText().toString();
                senha = textoSenha.getText().toString();
                conSenha = textoConSenha.getText().toString();

                firebaseAuth = FirebaseAuth.getInstance();



                // CADASTRO USUARIO



                if(     Objects.equals(senha.toString(), conSenha.toString())     ){

                    firebaseAuth.createUserWithEmailAndPassword(email, senha)
                            .addOnCompleteListener(MainCadatroActivityFireBase.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful() ){
                               Toast.makeText(MainCadatroActivityFireBase.this, "Cadastrado com Sucesso!", Toast.LENGTH_LONG).show();
                               startActivity(new Intent(MainCadatroActivityFireBase.this, Main3Activity.class));
                                Log.i("CreaterUser", "Sucesso ao Cadastrar!");
                            }else{
                               Toast.makeText(MainCadatroActivityFireBase.this, "Cadastro não realizado. Verifique se sua senha tem + de 6 caracteres. Verifiqu se seu e-mail é valido.", Toast.LENGTH_LONG).show();
                                Log.i("CreaterUser", "Erro ao Cadastrar!");
                            }
                        }
                    });


                }else{
                    Toast.makeText(MainCadatroActivityFireBase.this, "As senhas devem ser iguais!", Toast.LENGTH_SHORT).show();
                }



            }
        });




    }
}