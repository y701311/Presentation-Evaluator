package com.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.R;

public class ResultDisplay extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String textaccents = intent.getStringExtra("putaccentsText");
        String textmeanLessWords = intent.getStringExtra("putmeanLessWordsText");
        String textspeakingInterval = intent.getStringExtra("putspeakingIntervalText");
        String textspeakingSpeed = intent.getStringExtra("putspeakingSpeedText");
        String texttotal = intent.getStringExtra("puttotalText");
        String textvolume = intent.getStringExtra("putvolumeText");
        double doubleaccents = intent.getDoubleExtra("putaccents",0.0);
        double doublemeanLessWords= intent.getDoubleExtra("putmeanLessWords",0.0);
        double doublespeakingInterval = intent.getDoubleExtra("putspeakingInterval",0.0);
        double doublespeakingSpeed = intent.getDoubleExtra("putspeakingSpeed",0.0);
        double doubletotal = intent.getDoubleExtra("puttotal",0.0);
        double doublevolume = intent.getDoubleExtra("putvolume",0.0);

        setContentView(R.layout.resulteval);
        Button returntostart= findViewById(R.id.retrun_to_start);
        TextView speakingintervaltext= findViewById(R.id.speaking_interval);
        TextView volumetext= findViewById(R.id.volume);
        TextView speakingspeedtext= findViewById(R.id.speaking_speed);
        TextView meanLesswordstext= findViewById(R.id.mean_less_words);
        TextView accentstext= findViewById(R.id.accent);
        TextView totaltext= findViewById(R.id.total);

        TextView speakinginterval= findViewById(R.id.);
        TextView volume= findViewById(R.id.);
        TextView speakingspeed= findViewById(R.id.);
        TextView meanLesswords= findViewById(R.id.);
        TextView accents= findViewById(R.id.);
        TextView total= findViewById(R.id.);
        returntostart.setText("ファイル選択画面に戻る");
        speakingintervaltext.setText(textspeakingInterval);
        volumetext.setText(textvolume);
        speakingspeedtext.setText(textspeakingSpeed);
        meanLesswordstext.setText(textmeanLessWords);
        accentstext.setText(textaccents);
        totaltext.setText(texttotal);
        speakinginterval.setText(String.valueOf(doublespeakingInterval));
        volume.setText(String.valueOf(doublevolume));
        speakingspeed.setText(String.valueOf(doublespeakingSpeed));
        meanLesswords.setText(String.valueOf(doublemeanLessWords));
        accents.setText(String.valueOf(doubleaccents));
        total.setText(String.valueOf(doubletotal));
        returntostart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToStart = new Intent(getApplication(), MainActivity.class);
                startActivity(intentToStart);
            }
        });
    }
}