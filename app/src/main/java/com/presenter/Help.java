package com.presenter;

import android.content.res.AssetManager;

import com.view.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Help {
    private final MainActivity activity;
    private final com.view.Help help;
    private String helpText;

    public Help(MainActivity activity) {
        this.activity = activity;
        helpText = "";
        help = new com.view.Help();
        setHelpText();
    }

    public void helpClose() {

    }

    public void displayHelp() {
        //TODO    help.displayHelpText(helpText);
    }

    private void setHelpText() {
        InputStream stream;
        BufferedReader bufferedReader;
        AssetManager assetManager;
        try {
            assetManager = activity.getResources().getAssets();
            stream = assetManager.open("HelpText/HelpText.txt");
            bufferedReader = new BufferedReader(new InputStreamReader(stream));
            while (bufferedReader.ready()) {
                helpText = helpText.concat(bufferedReader.readLine());
            }
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}