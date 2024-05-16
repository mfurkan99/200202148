package com.nexis.a200202148;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;



public class WordTrie implements Vocab {
    private List<String> words = new ArrayList<String>();
    TrieNode root=null;



    public boolean add(String word) {
        int pos = Collections.binarySearch(words, word);
        if (pos < 0) {
            words.add(-(pos+1), word);
            return true;
        }
        return false;
    }

    public void display() {
        for(int i=0;i<words.size();i++)
            Log.e("mes",words.get(i));
    }

    public boolean isPrefix(String prefix) {

        prefix=prefix.toLowerCase();
        for(int i=0;i<words.size();i++) {
            if(words.get(i).startsWith(prefix)) {
                Log.e("word",words.get(i));
                return true;
            }

        }
        return false;


    }

    public boolean contais(String word) {
        int pos = Collections.binarySearch(words, word.toLowerCase());
        if(pos>0) {
            return true;
        }
        return false;
    }
}
