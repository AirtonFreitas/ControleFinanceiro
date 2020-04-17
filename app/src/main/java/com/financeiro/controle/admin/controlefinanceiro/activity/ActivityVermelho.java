package com.financeiro.controle.admin.controlefinanceiro.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.financeiro.controle.admin.controlefinanceiro.R;

public class ActivityVermelho extends AppCompatActivity {

    private Button botaoVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vermelho);

        botaoVoltar = (Button) findViewById(R.id.voltarIDfb);
        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityVermelho.this, principalActivity.class) );
                finish();
            }
        });

    }
}
