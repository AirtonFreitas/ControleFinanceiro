package com.financeiro.controle.admin.controlefinanceiro.activity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.financeiro.controle.admin.controlefinanceiro.R;
import com.financeiro.controle.admin.controlefinanceiro.config.ConfiguracaoFirebase;
import com.financeiro.controle.admin.controlefinanceiro.helper.Base64Custom;
import com.financeiro.controle.admin.controlefinanceiro.model.Movimentacao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;


public class ActivityGrafico extends AppCompatActivity {

    private Button btVoltar;

    //
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private DatabaseReference movimentacaoMesPassado;
    private DatabaseReference movimentacaoAtual;
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
    private ValueEventListener valueEventListener;
    private ValueEventListener valueEventListenerDois;

    private int receitaMesAtual;
    private int receitaMesPassado;

    String[] axisData = {"Jan", "Fev", "Mar", "Abr", "Mai"};
    int[] yAxisData = {receitaMesPassado, receitaMesAtual, 30, 20, 50};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico);

        btVoltar = (Button) findViewById(R.id.btnVoltID);
        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityGrafico.this, principalActivity.class));
                finish();
            }
        });

        recuperaDespesaParaGrafico();

        LineChartView lineChartView = (LineChartView) findViewById(R.id.chart);


        List yAxisValues = new ArrayList();
        List axisValues = new ArrayList();

        Line line = new Line(yAxisValues);

        for(int i = 0; i < axisData.length; i++){
            axisValues.add(i, new AxisValue(i).setLabel(axisData[i]));
        }

        for (int i = 0; i < yAxisData.length; i++){
            yAxisValues.add(new PointValue(i, yAxisData[i]));
        }
        List lines = new ArrayList();
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);
        lineChartView.setLineChartData(data);

        Axis axis = new Axis();
        axis.setValues(axisValues);
        data.setAxisXBottom(axis);

        Axis yAxis = new Axis();
        data.setAxisYLeft(yAxis);

        lineChartView.setLineChartData(data);
        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
        viewport.top = 110;
        lineChartView.setMaximumViewport(viewport);
        lineChartView.setCurrentViewport(viewport);






    }

   public void recuperaDespesaParaGrafico(){


       //Toast.makeText(ActivityGrafico.this,(CharSequence) movimentacaoRef, Toast.LENGTH_LONG).show();


       SimpleDateFormat formataDataMes = new SimpleDateFormat("MM");
       SimpleDateFormat formataDataAno = new SimpleDateFormat("yyyy");
       Date data = new Date();
       String mes = formataDataMes.format(data);
       String ano = formataDataAno.format(data);


       int anoAtual = Integer.parseInt(String.valueOf(ano));
       int mesAtual = Integer.parseInt(String.valueOf(mes));

       int mesAntepenultimo = mesAtual-3;
       int mesPenultimo = mesAtual-2;
       int mesUltimo = mesAtual-1;
       int mesProximo = mesAtual+1;




       String mesAtualAnoAtual = mesAtual + "" + anoAtual;
       String mesPassadoAnoAtual = mesUltimo + "" + anoAtual;

       String emailUsuario = autenticacao.getCurrentUser().getEmail();
       String idUsuario = Base64Custom.codificarBase64( emailUsuario );

       movimentacaoAtual = firebaseRef
               .child("movimentacao")
               .child(idUsuario)
               .child(String.valueOf(052020));

       //Toast.makeText(ActivityGrafico.this, (CharSequence) movimentacaoRef, Toast.LENGTH_LONG).show();

       valueEventListener = movimentacaoAtual.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               for(DataSnapshot dadosR: dataSnapshot.getChildren() ){
                    Movimentacao movR = dadosR.getValue(Movimentacao.class);
                    movR.setKey(dadosR.getKey());
                    movR.getTipo().equals("r");
                    receitaMesAtual = Integer.parseInt(String.valueOf(dadosR.getValue()));
               }


              // Log.i("Movimentacao", dataSnapshot.getValue().toString());
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });

       movimentacaoMesPassado = firebaseRef
               .child("movimentacao")
               .child(idUsuario)
               .child(String.valueOf(042020));

       //Toast.makeText(ActivityGrafico.this, (CharSequence) movimentacaoRef, Toast.LENGTH_LONG).show();

       valueEventListenerDois = movimentacaoMesPassado.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {

               DataSnapshot dados = null;
               dataSnapshot.getChildren();
                   Movimentacao moviment = dados.getValue(Movimentacao.class);
                   moviment.setKey(dados.getKey());
                   receitaMesPassado = Integer.parseInt(String.valueOf(moviment.getValor()));



               // Log.i("Movimentacao", dataSnapshot.getValue().toString());
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });





    }
}
