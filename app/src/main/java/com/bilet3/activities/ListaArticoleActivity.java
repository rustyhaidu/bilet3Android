package com.bilet3.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.bilet3.R;
import com.bilet3.adapters.ArticolAdapter;
import com.bilet3.db.ArticoleDbHelper;
import com.bilet3.model.Articol;

import java.util.ArrayList;
import java.util.List;

public class ListaArticoleActivity extends AppCompatActivity {

    private ArticolAdapter mArticolAdapter;
    private ListView mListView;
    SQLiteDatabase sqLiteDatabase;
    ArticoleDbHelper userDBHelper;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listaarticole);

        userDBHelper = new ArticoleDbHelper(getApplicationContext());
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
        }

        //Articol articol = (Articol) getIntent().getSerializableExtra(InregistrareArticolActivity.ARTICOL_TAG);
        mListView = findViewById(R.id.listviewarticole);

        mArticolAdapter = new ArticolAdapter(getApplicationContext(), R.layout.item_articol);
        //mArticolAdapter.addAll(MainActivity.articolList);
        mListView.setAdapter(mArticolAdapter);
    }


}
