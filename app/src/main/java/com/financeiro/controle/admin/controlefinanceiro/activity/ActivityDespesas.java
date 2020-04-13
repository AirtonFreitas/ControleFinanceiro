package com.financeiro.controle.admin.controlefinanceiro.activity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.financeiro.controle.admin.controlefinanceiro.R;
import com.financeiro.controle.admin.controlefinanceiro.config.ConfiguracaoFirebase;
import com.financeiro.controle.admin.controlefinanceiro.helper.Base64Custom;
import com.financeiro.controle.admin.controlefinanceiro.helper.DateCustom;
import com.financeiro.controle.admin.controlefinanceiro.model.Movimentacao;
import com.financeiro.controle.admin.controlefinanceiro.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


public class ActivityDespesas extends AppCompatActivity {

    private EditText campoData, campoCategoria, campoDescricao;
    private EditText campoValor;
    private Movimentacao movimentacao;
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private Double despesaTotal;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);

        campoValor = (EditText) findViewById(R.id.editValor);
        campoData = (EditText) findViewById(R.id.editData);
        campoCategoria = (EditText) findViewById(R.id.editCategoria);
        campoDescricao = (EditText) findViewById(R.id.editDescricao);

       // Preenche o campo data com a date atual
        campoData.setText( DateCustom.dataAtual() );

        recuperarDespesaTotal();
    }

    public void salvarDespesa(View view){

        if(validarCamposDespesa()){
            movimentacao = new Movimentacao();
            String data = campoData.getText().toString();
            Double valorRecuperado = Double.parseDouble(campoValor.getText().toString());
            movimentacao.setValor( Double.parseDouble(campoValor.getText().toString()) );
            movimentacao.setCategoria( campoCategoria.getText().toString() );
            movimentacao.setDescricao( campoDescricao.getText().toString() );
            movimentacao.setData( data );
            movimentacao.setTipo( "d" );


            Double despesaAtualizada = despesaTotal + valorRecuperado;

            atualizarDespesa(despesaAtualizada);

            movimentacao.salvar( data );
            finish();

        }
    }

    public Boolean validarCamposDespesa(){

        String textoValor = campoValor.getText().toString();
        String textoData = campoData.getText().toString();
        String textoCategoria = campoCategoria.getText().toString();
        String textoDescricao = campoDescricao.getText().toString();

        if(!textoValor.isEmpty()){
            if(!textoData.isEmpty()){
                if(!textoCategoria.isEmpty()){
                    if(!textoDescricao.isEmpty()){
                        return true;
                    }else{
                        Toast.makeText(ActivityDespesas.this, "Coloque a Descrição", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }else{
                    Toast.makeText(ActivityDespesas.this, "Coloque o Título", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }else{
                Toast.makeText(ActivityDespesas.this, "Coloque a Data", Toast.LENGTH_SHORT).show();
                return false;
            }
        }else{
            Toast.makeText(ActivityDespesas.this, "Coloque o Valor", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    public void recuperarDespesaTotal(){

        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64( emailUsuario );
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child( idUsuario );

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue( Usuario.class );
                despesaTotal = usuario.getDespesaTotal();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void atualizarDespesa(Double despesa){
        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);

        usuarioRef.child("despesaTotal").setValue(despesa);


    }

}
