package com.financeiro.controle.admin.controlefinanceiro.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.financeiro.controle.admin.controlefinanceiro.R;

public class ActivityDicas extends AppCompatActivity {

    private Button botaoVoltar, vermelho, verde, azul;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dicas);


        botaoVoltar = (Button) findViewById(R.id.btnVoltID);
        vermelho = (Button) findViewById(R.id.buttonVermelho);
        verde = (Button) findViewById(R.id.buttonVerde);
        azul = (Button) findViewById(R.id.buttonAzul);


        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityDicas.this, principalActivity.class));
                finish();
            }
        });
        vermelho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityDicas.this, ActivityVermelho.class));
            }
        });
        verde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityDicas.this, ActivityVerde.class));
            }
        });
       azul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityDicas.this, ActivityAzul.class));
            }
        });
    }
}
