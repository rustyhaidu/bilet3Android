package com.bilet3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.bilet3.R;

public class MainActivity extends AppCompatActivity {

    Button bInregistrare;
    Button bListaArticole;
    Button bPreluareArticoleJsonSauXml;
    Button bRaport;
    Button bDespre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bInregistrare = findViewById(R.id.inregistrarearticol);
        bListaArticole = findViewById(R.id.listaarticole);
        bPreluareArticoleJsonSauXml = findViewById(R.id.preluaredinfisiere);
        bRaport = findViewById(R.id.raport);
        bDespre = findViewById(R.id.despre);

        bInregistrare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InregistrareArticolActivity.class);
                startActivity(intent);
            }
        });

        bListaArticole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListaArticoleActivity.class);
                startActivity(intent);
            }
        });

        bPreluareArticoleJsonSauXml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PreluareArticoleActivity.class);
                startActivity(intent);
            }
        });

        bRaport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RaportActivity.class);
                startActivity(intent);
            }
        });

        bDespre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DespreActivity.class);
                startActivity(intent);
            }
        });




    }
}
