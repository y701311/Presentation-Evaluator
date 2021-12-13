package com.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.main.R;
import com.presenter.Evaluator;

public class EvalStart extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            FileSelect fileselect = new FileSelect();
            setContentView(R.layout.);
            Button button_select = findViewById(R.id.button_select);
            Button start_eval = findViewById(R.id.start_eval);
            TextView filename = (TextView)findViewById(R.id.filename);
            TextView filesize = (TextView)findViewById(R.id.filesize);
            filename.setText(fileselect.getFilename);
            filename.setText(fileselect.getFilesize);
            button_select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplication(),FileSelect.class);
                    startActivity(intent);
                }
            });
            start_eval.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    com.presenter.Evaluator evaluator = new Evaluator();
                    com.presenter.EvaluatonValues evaluationvalues = new Evaluator();
                    evaluator.startEvaluate();
                    evaluationvalues = evaluator.getEvaluationValues();
                    setContentView(R.layout.);
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
                    Button returntostart = findViewById(R.id.returntostart);
                    speakinginterval.setText(evaluationvalues.speakingInterval);//ここにstringをいれたい
                    volume.setText(evaluationvalues.volume);
                    speakingspeed.setText(evaluationvalues.speakingSpeed);
                    meanlesswords.setText(evaluationvalues.meanlessWords);
                    accents.setText(evaluationvalues.accents);
                    total.setText(evaluationvalues.total);
                    speakingintervaltext.setText(evaluationvalues.speakingIntervalText);
                    volumetext.setText(evaluationvalues.volumeText);
                    speakingspeedtext.setText(evaluationvalues.speakingSpeedText);
                    meanlesswordstext.setText(evaluationvalues.meanlessWordsText);
                    accentstext.setText(evaluationvalues.accentsText);
                    totaltext.setText(evaluationvalues.totalText);
                    returntostart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplication(),FileSelect.class);
                            startActivity(intent);
                        }
                    });
                }
            });
        }
    }
}