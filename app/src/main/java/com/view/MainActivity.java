package com.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.R;
import com.presenter.EvaluationValues;
import com.presenter.Evaluator;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.presenter.FileSelect fileSelect = new com.presenter.FileSelect();
        setContentView(R.layout.activity_main);
        Button ButtonFileSelect = findViewById(R.id.button_select_file);
        Button buttonStartEval = findViewById(R.id.button_start);
        TextView fileName = findViewById(R.id.file_name);
        TextView fileSize = findViewById(R.id.file_size);
        fileName.setText("未選択");
        fileSize.setText("未選択");
        MainActivity activity = this;
        buttonStartEval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.presenter.Evaluator evaluator = new Evaluator(activity);
                evaluator.startEvaluate();
            }
        });

        ButtonFileSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileName.setText(String.valueOf(fileSelect.getFileName()));
                fileSize.setText(String.valueOf(fileSelect.getFileSize()));
            }
        });
    }

    public void TransitionDisplayResult(EvaluationValues val) {
        Intent intent = new Intent(getApplication(), ResultDisplay.class);
        startActivity(intent);
    }
}