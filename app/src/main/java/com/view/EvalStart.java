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
                    evaluator.startEvaluate();
                    Intent intent = new Intent(getApplication(),ReturnToStart.class);
                    startActivity(intent);
                    content
                }
            });
            TextView filename = (TextView)findViewById(R.id.filename);
            TextView filesize = (TextView)findViewById(R.id.filesize);
            filename.setText(fileselect.getFilename);
            filename.setText(fileselect.getFilesize);
        }
    }
}