package com.bilet3.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.bilet3.R;
import com.bilet3.db.ArticoleDbHelper;
import com.bilet3.model.Articol;
import com.bilet3.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static List<Articol> articolList = new ArrayList<>();

    private Button bInregistrare;
    private Button bListaArticole;
    private Button bPreluareArticoleJsonSauXml;
    private Button bRaport;
    private Button bDespre;

    ArticoleDbHelper mDbHelper;
    SQLiteDatabase sqLiteDatabase;


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

                /*AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("Information")
                        .setMessage("Look at this dialog!")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();*/
            }
        });

        /*String json;

        Articol articol;

        JSONArray jsonArray = Util.getJsonFromAssetFile(this);

        for(int i = 0; i < jsonArray.length(); i ++){
            JSONObject jsonObject = null;
            try {
                jsonObject = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            articol = new Articol();

            try {
                articol.setTitlu(jsonObject.get("titlu").toString());
                articol.setAbstractArticol(jsonObject.get("abstractArticol").toString());
                articol.setAutori(jsonObject.get("autori").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            articolList.add(articol);

        }*/



    }
}
