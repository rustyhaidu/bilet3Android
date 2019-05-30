package com.bilet3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bilet3.R;
import com.bilet3.model.Articol;

public class InregistrareArticolActivity extends AppCompatActivity {

    public static final String ARTICOL_TAG = "Articol";

    EditText eTitlu;
    EditText eAbstractArticol;
    EditText eAutori;
    Button creareArticol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inregistrarearticol);

        eTitlu = findViewById(R.id.titlu);
        eAbstractArticol = findViewById(R.id.abstractarticol);
        eAutori = findViewById(R.id.listaautori);
        creareArticol = findViewById(R.id.creerearticol);


        creareArticol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Articol articol = new Articol();
                articol.setTitlu(eTitlu.getText().toString());
                articol.setAbstractArticol(eAbstractArticol.getText().toString());
                articol.setAutori(eAutori.getText().toString());

                Intent intent = new Intent(getApplicationContext(), ListaArticoleActivity.class);
                intent.putExtra(ARTICOL_TAG, articol);
                startActivity(intent);
            }
        });


    }
}