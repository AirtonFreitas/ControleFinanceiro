package com.financeiro.controle.admin.controlefinanceiro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends Activity {



    private EditText caixaEmail;
    private EditText caixaSenha;
    private Button botaoEntrar;
    private Button botaoNovoCadastro;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        caixaEmail = (EditText) findViewById(R.id.emailcxID);
        caixaSenha = (EditText) findViewById(R.id.senhacxID);
        botaoEntrar = (Button) findViewById(R.id.botaoEntrarID);
        botaoNovoCadastro = (Button) findViewById(R.id.botaoNovoCadID);

        firebaseAuth = FirebaseAuth.getInstance();

        /*  DESLOGA USUÁRIO
            firebaseAuth.signOut();

            VERIRICA SE USUÁRIO ESTÁ LOGADO
              if(firebaseAuth.getCurrentUser() != null){
                 Toast.makeText(MainActivity.this, "O usuário está logado", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, "O usuário não está logado", Toast.LENGTH_LONG).show();
                     }
        */



        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseAuth.signInWithEmailAndPassword(caixaEmail.getText().toString(), caixaSenha.getText().toString())
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Entrou!", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(MainActivity.this, Main3Activity.class));

                                } else {
                                    Toast.makeText(MainActivity.this, "Usuario ou senha inválidos! Tente Novamente", Toast.LENGTH_LONG).show();

                                }
                            }
                        });


            }
        });

        botaoNovoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, MainCadatroActivityFireBase.class) );
            }
        });


    }
}
