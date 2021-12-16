package com.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.R;
import com.google.gson.Gson;
import com.presenter.EvaluationValues;

public class ResultDisplay extends Activity {
    com.presenter.EvaluationValues values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resulteval);
        Gson gson = new Gson();
        String tmpValues = getIntent().getStringExtra("EvaluationValues");
        values = gson.fromJson(tmpValues, EvaluationValues.class);

        Button buttonReturnToStart = findViewById(R.id.return_to_start);
        TextView speakingIntervalText = findViewById(R.id.speaking_interval);
        TextView volumeText = findViewById(R.id.volume);
        TextView speakingSpeedText = findViewById(R.id.speaking_speed);
        TextView meanLessWordsText = findViewById(R.id.mean_less_words);
        TextView accentsText = findViewById(R.id.accent);
        TextView totalText = findViewById(R.id.total);

        TextView speakingInterval = findViewById(R.id.star_speaking_interval);
        TextView volume = findViewById(R.id.star_volume);
        TextView speakingSpeed = findViewById(R.id.star_speaking_speed);
        TextView meanLessWords = findViewById(R.id.star_mean_less_words);
        TextView accents = findViewById(R.id.star_accent);
        TextView total = findViewById(R.id.star_total);

        speakingIntervalText.setText(values.speakingIntervalText);
        volumeText.setText(values.volumeText);
        speakingSpeedText.setText(values.speakingSpeedText);
        meanLessWordsText.setText(values.meanLessWordsText);
        accentsText.setText(values.accentsText);
        totalText.setText(values.totalText);

        speakingInterval.setText(toStarString(values.speakingInterval));
        volume.setText(toStarString(values.volume));
        speakingSpeed.setText(toStarString(values.speakingSpeed));
        meanLessWords.setText(toStarString(values.meanLessWords));
        accents.setText(toStarString(values.accents));
        total.setText(toStarString(values.total));
        buttonReturnToStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToStart = new Intent(getApplication(), MainActivity.class);
                startActivity(intentToStart);
            }
        });
    }

    private String toStarString(double val) {
        if (val <= 0.1) {
            return "☆☆☆☆☆";
        } else if (val <= 0.3) {
            return "★☆☆☆☆";
        } else if (val <= 0.5) {
            return "★★☆☆☆";
        } else if (val <= 0.7) {
            return "★★★☆☆";
        } else if (val <= 0.9) {
            return "★★★★☆";
        } else {
            return "★★★★★";
        }
    }
}