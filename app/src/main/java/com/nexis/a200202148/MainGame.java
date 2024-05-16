package com.nexis.a200202148;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SimpleTimeZone;

import android.preference.PreferenceManager;
import android.view.animation.TranslateAnimation;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

public class MainGame extends AppCompatActivity {

    TextView tv,tv2, gameStatus, scoretext;
    String[][] twod;
    String submit_word="";
    ArrayList<Integer> clicked = new ArrayList<>();

    HashMap<String, String[]> store;
    WordTrie trie=new WordTrie();
    int score=0, counter=0;
    ArrayList<String> submitted_words = new ArrayList<>();
    View parent=null;
    TextView timer;

    private Handler handler = new Handler();
    private Random randomm = new Random();


    ArrayList<String> ar = new ArrayList<String>();


    private static final int ROWS = 8;
    private static final int COLUMNS = 10;
    private static final int TIME_INTERVAL = 5000;

    private final Map<Character, Integer> letterValues = new HashMap<>();
    {
        letterValues.put('A', 1);
        letterValues.put('B', 3);
        letterValues.put('C', 4);
        letterValues.put('Ç', 4);
        letterValues.put('D', 3);
        letterValues.put('E', 1);
        letterValues.put('F', 7);
        letterValues.put('G', 5);
        letterValues.put('Ğ', 8);
        letterValues.put('H', 5);
        letterValues.put('I', 2);
        letterValues.put('İ', 1);
        letterValues.put('J', 10);
        letterValues.put('K', 1);
        letterValues.put('L', 1);
        letterValues.put('M', 2);
        letterValues.put('N', 1);
        letterValues.put('O', 2);
        letterValues.put('Ö', 7);
        letterValues.put('P', 5);
        letterValues.put('R', 1);
        letterValues.put('S', 2);
        letterValues.put('Ş', 4);
        letterValues.put('T', 1);
        letterValues.put('U', 2);
        letterValues.put('Ü', 3);
        letterValues.put('V', 7);
        letterValues.put('Y', 3);
        letterValues.put('Z', 4);

    }
    public int calculateScore(String word) {
        int score2 = 0;
        for (int i = 0; i < word.length(); i++) {
            char c = Character.toUpperCase(word.charAt(i));
            if (letterValues.containsKey(c)) {
                score2 += letterValues.get(c);
            }
        }
        return score2;
    }



    public void select(View view) {
        int wrongCounter=0;
        tv = (TextView)findViewById(view.getId());
        if( !clicked.contains(tv.getText())) {

            tv.setBackgroundResource(R.drawable.buttonselect);
            tv.setTextColor(Color.WHITE);
            clicked.add(view.getId());
            submit_word += tv.getText();
            if (trie.isPrefix(submit_word)) {
                gameStatus.setText("Başarılı!");
            }
            else {
                wrongCounter++;
                //System.out.println("HATA"+wrongCounter);
                gameStatus.setText("Hatalı!");
                reset(view);

            }
            Log.e("len", submit_word.length() + "");
            if (submit_word.length() >= 3) {
                Log.e("as", trie.contais(submit_word) + "");
                if (trie.contais(submit_word)) {
                    if (!submitted_words.contains(submit_word)) {
                        submitted_words.add(submit_word);
                        score += calculateScore(submit_word) ;
                        //System.out.println(submit_word);
                        ArrayList<String> myList = new ArrayList<String>();

                        myList.add(submit_word);
                        System.out.println(myList);

                        Gson gson = new Gson();
                        String json = gson.toJson(myList);

                        SharedPreferences sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("myList", json);
                        editor.apply();


                        //ar.add(submit_word);
                        Intent e = new Intent(MainGame.this, FinalActivity.class);
                        e.putExtra("submitword",submit_word.toString());

                      //  SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                       // SharedPreferences.Editor editor = prefs.edit();


                     //   editor.putString("submitwords2",submit_word);
                      //  editor.apply();


                        scoretext.setText("Skorun: " + score);
                        gameStatus.setText("Devam!");
                        reset(view);
                    }
                }
            }
        }
        else {

        }
    }


    public void reset(View view) {
        for (int i=0; i<clicked.size(); i++) {
            int x = clicked.get(i);
            TextView current = (TextView) findViewById(x);

            current.setBackgroundResource(R.drawable.roundbuttons);
            current.setTextColor(0xff002338);
        }
        for (int i=clicked.size()-1; i>=0; i--) {
            clicked.remove(i);
        }
        submit_word = "";
        parent=null;
    }

    CountDownTimer cdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        timer = (TextView) findViewById(R.id.timer);
        scoretext=(TextView)findViewById(R.id.score);

        cdt = new CountDownTimer(90000, 1000) {
            public void onTick(long millisecondsUntilDone) {

                timer.setText("Süre: " + millisecondsUntilDone / 1000 + "s");
            }
            public void onFinish() {

                timer.setText("Oyun Bitti!");
                Intent i = new Intent(MainGame.this, FinalActivity.class);
                i.putExtra("score", score);
                startActivity(i);



            }

        }.start();

        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainGame.this, FinalActivity.class);
                i.putExtra("score", score);
                startActivity(i);
                cdt.cancel();
            }
        });
        Random random=new Random();
        gameStatus = (TextView) findViewById(R.id.gameStatus);
        List<String> alphabets = new ArrayList<>();
        alphabets.add("A");
        alphabets.add("E");
        alphabets.add("I");
        alphabets.add("O");
        alphabets.add("U");
        alphabets.add("Ö");
        alphabets.add("Ü");
        ArrayList<String> higher_priority = new ArrayList<String>(Arrays.asList( "T", "N", "S", "K",  "L", "D", "C", "M", "F", "G","Ş", "Y", "B","A", "E", "I"));
        ArrayList<String> lower_priority = new ArrayList<String>(Arrays.asList( "V","R", "J", "Ö", "Z", "Ç","Ğ", "O", "U","Ü","H"));
        while(alphabets.size()<=80)
        {
            int d=random.nextInt(2);
            String c;
            if (d==1) {
                int a1 = random.nextInt(higher_priority.size());
                int a2 = random.nextInt(higher_priority.size());
                alphabets.add(higher_priority.get(a1));
                alphabets.add(higher_priority.get(a2));
            } else {
                int a1 = random.nextInt(higher_priority.size());
                int a2 = random.nextInt(higher_priority.size());
                int b1 = random.nextInt(lower_priority.size());
                alphabets.add(higher_priority.get(a1));
                alphabets.add(higher_priority.get(a2));
                alphabets.add(lower_priority.get(b1));
            }
        }
        Collections.shuffle(alphabets);



        int k=0;
        twod= new String[10][8];
        for(int i=0;i<=9;i++) {
            for (int j = 0; j <= 7; j++) {
                Random ran = new Random();
                Random rowran= new Random();
                int rowxt=rowran.nextInt(7);
                int nxt = ran.nextInt(8);
                String s = "text" + i + j;
                //String a ="text"+i+nxt;
                //int xa = getResources().getIdentifier(a, "id", getPackageName());
                int x = getResources().getIdentifier(s, "id", getPackageName());

                tv = (TextView) findViewById(x);
                tv.setText(alphabets.get(k));
                twod[i][j] = alphabets.get(k);
                tv.setVisibility(View.INVISIBLE);
                if (alphabets.get(k).equals("Qu")) {
                    tv.setPadding(15, 10, 0, 10);
                }
                if (i > 6) {
                    tv.setVisibility(View.VISIBLE);
                    Animation animation = new TranslateAnimation(0, 0, -1000, tv.getHeight());
                    animation.setDuration(10000);
                    tv.startAnimation(animation);
                    //tv.setAlpha(0.0f);
                    //tv.animate().alpha(1.0f).setDuration(500);
                    //tv.animate().setStartDelay(500);

                }
                else if (i<=6){
                    handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Random rand = new Random();

                            tv = (TextView) findViewById(x);

                            tv.setVisibility(View.VISIBLE);

                            Animation animation = new TranslateAnimation(0, 0, -1000, tv.getHeight()-100);
                            animation.setDuration(10000);
                            tv.startAnimation(animation);



                        }
                    }, 15000);



                }


                k++;
            }
        }


        AssetManager assetManager = getAssets();
        try {
            BufferedReader br;
            br = new BufferedReader(new InputStreamReader(assetManager.open("words2.txt")));

            String line = null;
            while((line = br.readLine()) != null) {
                String word = line.trim();
                if (word.length() >= 3)
                    trie.add(line.trim());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MainWorkingClass mwc = new MainWorkingClass(twod);
        store = mwc.start();
    }
}