package com.bilet3.activities;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bilet3.R;
import com.bilet3.db.ArticoleDbHelper;
import com.bilet3.model.Articol;
import com.bilet3.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class InregistrareArticolActivity extends AppCompatActivity {

    public static final String ARTICOL_TAG = "Articol";

    EditText eTitlu;
    EditText eAbstractArticol;
    EditText eAutori;
    Button creareArticol;
    ArticoleDbHelper mDbHelper;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inregistrarearticol);

        mDbHelper = new ArticoleDbHelper(getApplicationContext());
        sqLiteDatabase = mDbHelper.getWritableDatabase();


        eTitlu = findViewById(R.id.titlu);
        eAbstractArticol = findViewById(R.id.abstractarticol);
        eAutori = findViewById(R.id.listaautori);
        creareArticol = findViewById(R.id.creerearticol);

        final JSONArray jsonArray = Util.getJsonFromAssetFile(this);

        creareArticol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Articol articol = new Articol();

                articol.setTitlu(eTitlu.getText().toString());
                articol.setAbstractArticol(eAbstractArticol.getText().toString());
                articol.setAutori(eAutori.getText().toString());

                JSONObject jsonObject = new JSONObject();

                try {
                    jsonObject.put("titlu", articol.getTitlu());
                    jsonObject.put("abstractArticol", articol.getAbstractArticol());
                    jsonObject.put("autori", articol.getAutori());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                jsonArray.put(jsonObject);
                String jsonToWrite = jsonArray.toString();

                String filename = "b.json";
                FileOutputStream outputStream;

                Util.writeFileOnInternalStorage(getApplicationContext(), filename, jsonToWrite);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        Articol articol = new Articol();

        articol.setTitlu(eTitlu.getText().toString());
        articol.setAbstractArticol(eAbstractArticol.getText().toString());
        articol.setAutori(eAutori.getText().toString());

        mDbHelper.insertInfo(articol.getTitlu(), articol.getAbstractArticol(), articol.getAutori(), sqLiteDatabase);

        mDbHelper.close();
    }
}
