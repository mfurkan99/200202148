package com.nexis.a200202148;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FinalActivity extends AppCompatActivity {
    TextView sc;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        Intent i = getIntent();
        //Intent e = getIntent();


        String submitw = getIntent().getExtras().getString("submitword","");

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //ArrayList data = prefs.getString("submitword2", "");

        SharedPreferences sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String json = sharedPref.getString("myList", "");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList<String> myList = gson.fromJson(json, type);
        int size = myList.size();
        System.out.println(size);
        String listString = "";
        for (String s : myList) {
            listString += s + "\n";
        }


        int score = i.getIntExtra("score", 0);
        sc = (TextView) findViewById(R.id.textView2);
        sc.setText(score+"");





        System.out.println(score+submitw);


        b = (Button) findViewById(R.id.button5);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(FinalActivity.this, MainGame.class);
                startActivity(a);
            }
        });
    }
}
