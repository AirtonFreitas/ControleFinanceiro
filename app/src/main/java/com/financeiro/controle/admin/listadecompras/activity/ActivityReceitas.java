package com.financeiro.controle.admin.listadecompras.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.financeiro.controle.admin.listadecompras.R;
import com.financeiro.controle.admin.listadecompras.config.ConfiguracaoFirebase;
import com.financeiro.controle.admin.listadecompras.helper.Base64Custom;
import com.financeiro.controle.admin.listadecompras.helper.DateCustom;
import com.financeiro.controle.admin.listadecompras.model.Movimentacao;
import com.financeiro.controle.admin.listadecompras.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ActivityReceitas extends AppCompatActivity {

    private EditText campoData, campoCategoria, campoDescricao;
    //private EditText campoValor;
    private Movimentacao movimentacao;
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private Double receitaTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receitas);

        campoData = (EditText) findViewById(R.id.editData);
        campoCategoria = (EditText) findViewById(R.id.editCategoria);
        campoDescricao = (EditText) findViewById(R.id.editDescricao);

        // Preenche o campo data com a date atual
        campoData.setText( DateCustom.dataAtual() );

        recuperarReceitaTotal();



    }
    public void recuperarReceitaTotal(){

        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64( emailUsuario );
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child( idUsuario );

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue( Usuario.class );
                receitaTotal = usuario.getReceitaTotal();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void atualizarReceita(Double receita){
        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);

        usuarioRef.child("receitaTotal").setValue(receita);


    }

    public void salvarReceita(View view){

        if(validarCamposReceita()){
            movimentacao = new Movimentacao();
            String data = campoData.getText().toString();
            //Double valorRecuperado = Double.parseDouble(campoValor.getText().toString());
            //movimentacao.setValor( Double.parseDouble(campoValor.getText().toString()) );
            movimentacao.setValor(1);
            movimentacao.setCategoria( campoCategoria.getText().toString() );
            movimentacao.setDescricao( campoDescricao.getText().toString() );
            movimentacao.setData( data );
            movimentacao.setTipo( "r" );


            //Double receitaAtualizada = receitaTotal + valorRecuperado;

            //atualizarReceita(receitaAtualizada);

            movimentacao.salvar( data );
            finish();
        }
    }

    public Boolean validarCamposReceita(){

        String textoData = campoData.getText().toString();
        String textoCategoria = campoCategoria.getText().toString();
        String textoDescricao = campoDescricao.getText().toString();

            if(!textoData.isEmpty()){
                if(!textoCategoria.isEmpty()){
                    if(!textoDescricao.isEmpty()){
                        return true;
                    }else{
                        Toast.makeText(ActivityReceitas.this, "Coloque o Nome", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }else{
                    Toast.makeText(ActivityReceitas.this, "Sem Observação", Toast.LENGTH_SHORT).show();
                    return true;
                }
            }else{
                Toast.makeText(ActivityReceitas.this, "Coloque a Data", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
}
