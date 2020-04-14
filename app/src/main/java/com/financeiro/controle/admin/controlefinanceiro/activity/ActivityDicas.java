package com.financeiro.controle.admin.controlefinanceiro.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.financeiro.controle.admin.controlefinanceiro.R;

public class ActivityDicas extends AppCompatActivity {

    private Button botaoVoltar;
    private TextView vermelho, verde, azul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_dicas);

        botaoVoltar = (Button) findViewById(R.id.voltarIDfb);
        vermelho = (TextView) findViewById(R.id.textViewVermelho);
        verde = (TextView) findViewById(R.id.textViewVerde);
        azul = (TextView) findViewById(R.id.textViewAzul);


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
