package com.bilet3.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bilet3.R;
import com.bilet3.adapters.ArticolAdapter;
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
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.bilet3.activities.MainActivity.articolList;

public class ListaArticoleActivity extends AppCompatActivity {

    public static final String FILE_NAME = "example.txt";

    private ArticolAdapter mArticolAdapter;
    private ListView mListView;
    SQLiteDatabase sqLiteDatabase;
    ArticoleDbHelper userDBHelper;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listaarticole);

        load();

        /*userDBHelper = new ArticoleDbHelper(getApplicationContext());
        sqLiteDatabase = userDBHelper.getReadableDatabase();

        cursor = userDBHelper.selectInfo(sqLiteDatabase);

        if (cursor.moveToFirst()){

            do {
                String titlu, abstractArticol, autori;
                titlu = cursor.getString(0);
                abstractArticol = cursor.getString(1);
                autori = cursor.getString(2);

                Articol articol = new Articol(titlu,abstractArticol,autori);
                mArticolAdapter.add(articol);
            }
            while (cursor.moveToNext());
        }*/

        //Articol articol = (Articol) getIntent().getSerializableExtra(InregistrareArticolActivity.ARTICOL_TAG);
        mListView = findViewById(R.id.listviewarticole);

        mArticolAdapter = new ArticolAdapter(getApplicationContext(), R.layout.item_articol);
        mArticolAdapter.addAll(articolList);
        attachSelectArticleFromList();
        attachDeleteArticleFromList();
        mListView.setAdapter(mArticolAdapter);
    }

    public void load() {
        articolList = new ArrayList<>();
        FileInputStream fileInputStream = null;

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
            Gson gson = new Gson();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                JsonElement jsonElement = gson.fromJson(jsonObject.toString(), JsonElement.class);
                Articol articol = gson.fromJson(jsonElement, Articol.class);
                articolList.add(articol);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void attachSelectArticleFromList() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Articol articol = (Articol) mArticolAdapter.getItem(position);

                Intent intent = new Intent(getApplicationContext(), InregistrareArticolActivity.class);
                intent.putExtra("Articol", articol);
                startActivity(intent);
            }
        });
    }

    private void attachDeleteArticleFromList() {
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Articol articol = (Articol) mArticolAdapter.getItem(position);

                try {
                    JSONArray jsonArray = Util.getJSONArray(getApplicationContext());
                    int index = Util.getJsonArrayIndex(jsonArray, articol);
                    jsonArray.remove(index);

                    FileOutputStream fileOutputStream;
                    fileOutputStream = openFileOutput(FILE_NAME, MODE_PRIVATE);
                    fileOutputStream.write(jsonArray.toString().getBytes());

                    mArticolAdapter.remove(articol);
                    mArticolAdapter.notifyDataSetChanged();

                    Toast.makeText(ListaArticoleActivity.this, "item removed", Toast.LENGTH_SHORT).show();

                    assert articol != null;
                    deleteFromDb(articol.getTitlu());

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return true;
            }
        });
    }

    public void deleteFromDb(String titlu){
        ArticoleDbHelper mDbHelper;
        SQLiteDatabase sqLiteDatabase;
        mDbHelper = new ArticoleDbHelper(getApplicationContext());
        sqLiteDatabase = mDbHelper.getWritableDatabase();

        mDbHelper.deleteInformation(titlu, sqLiteDatabase);
        Util.selectAll(mDbHelper, sqLiteDatabase);
        mDbHelper.close();
    }

}
