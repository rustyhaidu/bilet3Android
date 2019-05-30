package com.bilet3.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.bilet3.R;
import com.bilet3.adapters.ArticolAdapter;
import com.bilet3.model.Articol;

public class ListaArticoleActivity extends AppCompatActivity {

    private ArticolAdapter mArticolAdapter;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listaarticole);


        Articol articol = (Articol) getIntent().getSerializableExtra(InregistrareArticolActivity.ARTICOL_TAG);
        mListView = findViewById(R.id.listviewarticole);

        mArticolAdapter = new ArticolAdapter(getApplicationContext(), R.layout.item_articol);
        mArticolAdapter.add(articol);
        mListView.setAdapter(mArticolAdapter);
    }


}
