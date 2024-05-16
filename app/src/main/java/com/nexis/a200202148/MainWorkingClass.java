package com.nexis.a200202148;

import android.util.Log;

import java.util.Arrays;
import java.util.HashMap;


public class MainWorkingClass {

    private String[][] m;
    public MainWorkingClass(String[][] twod) {
        m = twod;
    }

    HashMap<String, String[]> start()
    {
        HashMap<String, String[]> temp=new HashMap<>();
        for(int i=0;i<=9;i++)
        {
            for(int j=0;j<=7;j++)
            {
                String[] adj;
                if(i==0 && j==0)
                {adj=new String[]{m[i+1][j],m[i][j+1],m[i+1][j+1]};}
                else if(i==0 && j==7)
                {adj=new String[]{m[i+1][j],m[i][j-1],m[i+1][j-1]};}
                else if(i==9 && j==7)
                {adj=new String[]{m[i-1][j],m[i][j-1],m[i-1][j-1]};}
                else if(i==9 && j==0)
                {adj=new String[]{m[i-1][j],m[i][j+1],m[i-1][j+1]};}
                else if(i==0)
                {adj=new String[]{m[i+1][j],m[i+1][j-1],m[i+1][j+1],m[i][j-1],m[i][j+1]};}
                else if(i==9)
                {adj=new String[]{m[i-1][j],m[i-1][j-1],m[i-1][j+1],m[i][j-1],m[i][j+1]};}
                else if(j==0)
                {adj=new String[]{m[i+1][j],m[i-1][j],m[i+1][j+1],m[i][j+1],m[i-1][j+1]};}
                else if(j==7)
                {adj=new String[]{m[i+1][j],m[i-1][j],m[i+1][j-1],m[i][j-1],m[i-1][j-1]};}
                else
                {adj=new String[]{m[i-1][j],m[i-1][j-1],m[i-1][j+1],m[i+1][j],m[i+1][j-1],m[i+1][j+1],m[i][j-1],m[i][j+1]};}
                temp.put(m[i][j], adj);
            }
        }
        return temp;
    }

}



