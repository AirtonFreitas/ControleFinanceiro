package com.financeiro.controle.admin.controlefinanceiro.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.financeiro.controle.admin.controlefinanceiro.R;
import com.financeiro.controle.admin.controlefinanceiro.helper.Date;
import com.financeiro.controle.admin.controlefinanceiro.model.Movimentacao;

public class ActivityDespesas extends AppCompatActivity {

    private TextInputEditText campoData, campoCategoria, campoDescricao;
    private EditText campoValor;
    private Movimentacao movimentacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);

        campoValor = (EditText) findViewById(R.id.editValor);
        campoData = (TextInputEditText) findViewById(R.id.editData);
        campoCategoria = (TextInputEditText) findViewById(R.id.editCategoria);
        campoDescricao = (TextInputEditText) findViewById(R.id.editDescricao);

        campoData.setText(Date.dataAtual());
    }

    public void salvarDespesa(View view){
        movimentacao = new Movimentacao();
        String data = campoData.getText().toString();
        movimentacao.setValor(Double.parseDouble(campoValor.getText().toString()));
        movimentacao.setCategoria(campoCategoria.getText().toString());
        movimentacao.setDescricao(campoDescricao.getText().toString());
        movimentacao.setData(data);
        movimentacao.setTipo("d");

        movimentacao.salvar(data);
    }

}
