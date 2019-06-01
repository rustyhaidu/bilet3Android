package com.bilet3.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.bilet3.R;

public class DespreActivity extends AppCompatActivity {

    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despre);

        mTextView = findViewById(R.id.despre);
        mTextView.setText("DESPRE");
    }
}
