package com.gcssloop.roundcornerlayouttest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view) {
        Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();
    }

    public void bgclick(View view) {
        Toast.makeText(this, "BgClick", Toast.LENGTH_SHORT).show();
    }
}
