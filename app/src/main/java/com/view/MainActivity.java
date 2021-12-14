package com.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.R;
import com.presenter.EvaluationValues;
import com.presenter.Evaluator;

public class MainActivity extends AppCompatActivity {
    Uri getUri;

    ActivityResultLauncher<Intent> _launcherSelectAudioFile =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent resultData = result.getData();
                        if (resultData != null) {
                            Uri uri = resultData.getData();
                            if (uri != null) {
                                getUri = uri;
                            }
                        }
                    }
                }
            });

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
                com.presenter.Evaluator evaluator = new Evaluator(activity, fileSelect);
                evaluator.startEvaluate();
            }
        });

        ButtonFileSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileSelect.changeFile(fileSelect());
                fileName.setText(String.valueOf(fileSelect.getFileName()));
                fileSize.setText(String.valueOf(fileSelect.getFileSize()));
            }
        });
    }

    public void transitionDisplayResult(EvaluationValues val) {
        Intent intent = new Intent(getApplication(), ResultDisplay.class);
        startActivity(intent);
    }

    private void openFile() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("audio/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        Intent chooserIntent = Intent.createChooser(intent, "音声ファイルの選択");
        _launcherSelectAudioFile.launch(chooserIntent);
    }

    public Uri fileSelect() {
        openFile();
        return getUri;
    }
}