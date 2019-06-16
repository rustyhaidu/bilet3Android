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
import android.widget.EditText;
import android.widget.Toast;

import com.bilet3.R;
import com.bilet3.db.ArticoleDbHelper;
import com.bilet3.model.Articol;
import com.bilet3.util.Util;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static List<Articol> articolList = new ArrayList<>();
    public static final String FILE_NAME = "example.txt";

    private Button bInregistrare;
    private Button bListaArticole;
    private Button bPreluareArticoleJsonSauXml;
    private Button bRaport;
    private Button bDespre;

    private EditText mEditTextAbstractArticol;
    private EditText mEditTextTitlu;
    private EditText mEditTextAutor;

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

        mEditTextTitlu = findViewById(R.id.titlu);
        mEditTextAbstractArticol = findViewById(R.id.abstractarticol);
        mEditTextAutor = findViewById(R.id.autor);

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

    public void saveToFile(View v) throws IOException, JSONException {
        String textTitlu = mEditTextTitlu.getText().toString();
        String textAbstract = mEditTextAbstractArticol.getText().toString();
        String textAutor = mEditTextAutor.getText().toString();

        Articol articol = new Articol(textTitlu,textAbstract, textAutor);

        FileInputStream fileInputStream = null;
        fileInputStream = openFileInput(FILE_NAME);
        InputStreamReader isr = new InputStreamReader(fileInputStream);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String text;

        while((text = br.readLine()) != null){
            sb.append(text);
        }

        JSONArray jsonArray = new JSONArray(sb.toString());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("titlu",articol.getTitlu());
        jsonObject.put("abstractArticol",articol.getAbstractArticol());
        jsonObject.put("autori",articol.getAutori());

        jsonArray.put(jsonObject);

        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fileOutputStream.write(jsonArray.toString().getBytes());

            mEditTextAutor.getText().clear();
            mEditTextAbstractArticol.getText().clear();
            mEditTextTitlu.getText().clear();
            Toast.makeText(this, "Save to" + getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG).show();

        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fileOutputStream != null){
                try{
                    fileOutputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void load(View v){
        FileInputStream fileInputStream = null;
        List<Articol> loadListArticole = new ArrayList<>();

        try{
            fileInputStream = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fileInputStream);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while((text = br.readLine()) != null){
                sb.append(text);
            }

            JSONArray jsonArray = new JSONArray(sb.toString());
            Gson gson = new Gson();
            for(int i = 0; i< jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                JsonElement jsonElement = gson.fromJson(jsonObject.toString(), JsonElement.class);
                Articol articol = gson.fromJson(jsonElement, Articol.class);
                loadListArticole.add(articol);
            }

            mEditTextTitlu.setText(loadListArticole.get(0).getTitlu());
            mEditTextAbstractArticol.setText(loadListArticole.get(0).getAbstractArticol());
            mEditTextAutor.setText(loadListArticole.get(0).getAutori());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if(fileInputStream != null){
                try{
                    fileInputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

}
