package com.mp.myfilms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.mp.myfilms.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void navigationListActivity(View view) {
        Intent intent = new Intent(MainActivity.this, RegisterFilmActivity.class);
        startActivity(intent);
    }

}
