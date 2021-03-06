package com.bilet3.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bilet3.R;
import com.bilet3.db.ArticoleDbHelper;
import com.bilet3.model.Articol;
import com.bilet3.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.bilet3.activities.MainActivity.FILE_NAME;
import static com.bilet3.util.Util.selectAll;

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

        final Articol articol;
        try {
            articol = (Articol) getIntent().getSerializableExtra("Articol");

            if (articol != null) {
                creareArticol.setText("Update Articol");

                eTitlu.setText(articol.getTitlu());
                eAbstractArticol.setText(articol.getAbstractArticol());
                eAutori.setText(articol.getAutori());

                creareArticol.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String textTitlu = eTitlu.getText().toString();
                        String textAbstract = eAbstractArticol.getText().toString();
                        String textAutor = eAutori.getText().toString();

                        Articol articolNew = new Articol(textTitlu, textAbstract, textAutor);

                        updateToFile(articol, articolNew);
                        updateArticol(articol, articolNew);

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                });
            } else {
                creareArticol.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String textTitlu = eTitlu.getText().toString();
                        String textAbstract = eAbstractArticol.getText().toString();
                        String textAutor = eAutori.getText().toString();

                        Articol articol = new Articol(textTitlu, textAbstract, textAutor);

                        saveToFile(articol);
                        insertToDb(articol);
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                });
            }

        } catch (Exception e) {
            // Bundle not found
            e.printStackTrace();
        }

    }

    public void saveToFile(Articol articol) {

        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        JSONArray jsonArray = null;

        try {
            File file = new File(FILE_NAME);

            if (file.exists()) {
                fileInputStream = openFileInput(FILE_NAME);
                InputStreamReader isr = new InputStreamReader(fileInputStream);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String text;

                while ((text = br.readLine()) != null) {
                    sb.append(text);
                }
                jsonArray = new JSONArray(sb.toString());
            } else {
                jsonArray = new JSONArray();
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("titlu", articol.getTitlu());
            jsonObject.put("abstractArticol", articol.getAbstractArticol());
            jsonObject.put("autori", articol.getAutori());

            jsonArray.put(jsonObject);

            fileOutputStream = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fileOutputStream.write(jsonArray.toString().getBytes());

            eAutori.getText().clear();
            eAbstractArticol.getText().clear();
            eTitlu.getText().clear();
            Toast.makeText(this, "Save to" + getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void updateToFile(Articol articolOld, Articol articolNew) {
        //Articol articol = new Articol(textTitlu, textAbstract, textAutor);

        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream = null;

        try {
            fileInputStream = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fileInputStream);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text);
            }

            JSONArray jsonArray = new JSONArray(sb.toString());
            int index = Util.getJsonArrayIndex(jsonArray, articolOld);
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("titlu", articolNew.getTitlu());
            jsonObject.put("abstractArticol", articolNew.getAbstractArticol());
            jsonObject.put("autori", articolNew.getAutori());

            jsonArray.put(index, jsonObject);

            fileOutputStream = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fileOutputStream.write(jsonArray.toString().getBytes());

            eAutori.getText().clear();
            eAbstractArticol.getText().clear();
            eTitlu.getText().clear();
            Toast.makeText(this, "Save to" + getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void insertToDb(Articol articol) {
        mDbHelper.insertInfo(articol.getTitlu(), articol.getAbstractArticol(), articol.getAutori(), sqLiteDatabase);
        selectAll(mDbHelper, sqLiteDatabase);
        mDbHelper.close();
    }


    private void updateArticol(Articol articolOld, Articol articolNew) {
        mDbHelper.updateInformation(articolOld.getTitlu(), articolNew.getTitlu(), articolNew.getAbstractArticol(), articolNew.getAutori(), sqLiteDatabase);
        selectAll(mDbHelper, sqLiteDatabase);
        mDbHelper.close();
    }
}
