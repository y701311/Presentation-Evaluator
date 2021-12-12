package com.view;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.main.R;

public class ReturnToStart extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        TextView speakinginterval = findViewById(R.id.speakinginterval);
        TextView volume = findViewById(R.id.volume);
        TextView speakingspeed = findViewById(R.id.speakingspeed);
        TextView meanlesswords = findViewById(R.id.meanlesswords);
        TextView accents = findViewById(R.id.accents);
        TextView total = findViewById(R.id.total);
        TextView speakingintervaltext = findViewById(R.id.speakingintervaltext);
        TextView volumetext = findViewById(R.id.volumetext);
        TextView speakingspeedtext = findViewById(R.id.speakingspeedtext);
        TextView meanlesswordstext = findViewById(R.id.meanlesswordstext);
        TextView accentstext = findViewById(R.id.accentstext);
        TextView totaltext = findViewById(R.id.totaltext);
        speakinginterval.setText();//ここにstringをいれたい
        volume.setText();
        speakingspeed.setText();
        meanlesswords.setText();
        accents.setText();
        total.setText();
        speakingintervaltext.setText();
        volumetext.setText();
        speakingspeedtext.setText();
        meanlesswordstext.setText();
        accentstext.setText();
        totaltext.setText();
    }
}