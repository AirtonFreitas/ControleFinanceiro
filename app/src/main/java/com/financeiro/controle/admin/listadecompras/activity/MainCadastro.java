package com.financeiro.controle.admin.listadecompras.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.financeiro.controle.admin.listadecompras.R;
import com.financeiro.controle.admin.listadecompras.config.ConfiguracaoFirebase;
import com.financeiro.controle.admin.listadecompras.helper.Base64Custom;
import com.financeiro.controle.admin.listadecompras.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.util.Objects;

public class MainCadastro extends Activity {

    private Button botaoVoltar, botaoCadastrar;
    private EditText textoNome, textoEmail, textoSenha, textoConSenha;
    private FirebaseAuth autenticacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        botaoVoltar = (Button) findViewById(R.id.btnVoltID);
        botaoCadastrar = (Button) findViewById(R.id.cadastrarIDfb);
        textoNome = (EditText) findViewById(R.id.nomeIDfb);
        textoEmail = (EditText) findViewById(R.id.emailIDfb);
        textoSenha = (EditText) findViewById(R.id.senhaIDfb);
        textoConSenha = (EditText) findViewById(R.id.senhaConIDfb);

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainCadastro.this, LoginActivity.class));
                finish();
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
                if(!nome.isEmpty()){
                    if(!email.isEmpty()){
                        if(!senha.isEmpty()){
                            if(     Objects.equals(senha.toString(), conSenha.toString())     ) {
                                usuario = new Usuario();
                                usuario.setNome( nome );
                                usuario.setEmail( email );
                                usuario.setSenha( senha );
                                cadastrarUsuario2();
                            }else{
                                Toast.makeText(MainCadastro.this, "As senhas devem ser iguais!", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(MainCadastro.this, "Ops ;( coloque a senha por favor!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(MainCadastro.this, "Ops ;( coloque o e-mail, por favor", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(MainCadastro.this, "Ops ;( coloque o nome, por favor", Toast.LENGTH_SHORT).show();
                }

                }
            }
        );
    }

    public void cadastrarUsuario2(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if ( task.isSuccessful() ){

                    String idUsuario = Base64Custom.codificarBase64( usuario.getEmail() );
                    usuario.setIdUsuario( idUsuario );
                    usuario.salvar();
                    startActivity(new Intent(MainCadastro.this, principalActivity.class));
                    finish();

                }else {

                    String excecao = "";
                    try {
                        throw task.getException();
                    }catch ( FirebaseAuthWeakPasswordException e){
                        excecao = "Digite uma senha mais forte!";
                    }catch ( FirebaseAuthInvalidCredentialsException e){
                        excecao= "Por favor, digite um e-mail válido";
                    }catch ( FirebaseAuthUserCollisionException e){
                        excecao = "Este conta já foi cadastrada";
                    }catch (Exception e){
                        excecao = "Erro ao cadastrar usuário: "  + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(MainCadastro.this,
                            excecao,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}