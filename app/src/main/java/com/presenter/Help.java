package com.presenter;

import android.content.res.Resources;
import androidx.appcompat.app.AppCompatActivity;

import com.view.MainActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Help extends AppCompatActivity{
    private final MainActivity activity;
    public String[] helpText = new String[5];
    public ArrayList<String> helpList;

    public Help(MainActivity activity) {
        this.activity = activity;
        for(int i=0;i<5;i++) {
            helpText[i] = "";
        }
        setHelpText();
    }

    public void helpClose() {

    }

    public void displayHelp() {
        //TODO    help.displayHelpText(helpText);
    }

    private void setHelpText() {
        InputStream stream = null;
        BufferedReader bufferedReader = null;
        int Line=0;
        try {
            try {
                // assetsフォルダ内の sample.txt をオープンする
                Resources res = activity . getResources ();
                stream = res.getAssets().open("HelpText/HelpText.txt");
                bufferedReader = new BufferedReader(new InputStreamReader(stream));

                // １行ずつ読み込み、改行を付加する
                String str;
                while ((str = bufferedReader.readLine()) != null) {
                    helpText[Line] += str;
                    Line++;
                }
            } finally {
                if (stream != null) stream.close();
                if (bufferedReader != null) bufferedReader.close();
            }
        } catch (Exception e){
            // エラー発生時の処理
        }

    }
}