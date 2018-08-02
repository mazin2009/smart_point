
package com.example.mm_kau.smart_point;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class HomePage extends AppCompatActivity implements Designable {


    private TextView Points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Points = findViewById(R.id.TextPoints);
        Points.setText(getIntent().getStringExtra("Pointe"));
        InitializeView();
    }

    @Override
    public void InitializeView() {

        this.Points = findViewById(R.id.TextPoints);
        Design();
    }

    @Override
    public void Design() {
        HandleAction();
    }

    @Override
    public void HandleAction() {

    }
}
