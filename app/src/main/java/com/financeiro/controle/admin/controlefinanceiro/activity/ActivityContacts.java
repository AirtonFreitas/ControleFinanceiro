package com.financeiro.controle.admin.controlefinanceiro.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.financeiro.controle.admin.controlefinanceiro.R;

public class ActivityContacts extends AppCompatActivity {

    private Button botaoVoltar;
    private TextView text1, text2, text3, text4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        TextView text1 = (TextView) findViewById(R.id.textEmail);
        TextView text2 = (TextView) findViewById(R.id.textEmail2);
        TextView text3 = (TextView) findViewById(R.id.textLinked);
        TextView text4 = (TextView) findViewById(R.id.textLinked2);

        text1.setMovementMethod(LinkMovementMethod.getInstance());
        text2.setMovementMethod(LinkMovementMethod.getInstance());
        text3.setMovementMethod(LinkMovementMethod.getInstance());
        text4.setMovementMethod(LinkMovementMethod.getInstance());


        botaoVoltar = (Button) findViewById(R.id.btnVoltID);

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityContacts.this, principalActivity.class));
                finish();
            }
        });
    }
}
