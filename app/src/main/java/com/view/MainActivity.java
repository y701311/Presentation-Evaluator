package com.view;

import android.Manifest;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.R;
import com.google.gson.Gson;
import com.presenter.EvaluationValues;
import com.presenter.Evaluator;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private Uri getUri;
    private com.presenter.FileSelect fileSelect;
    private TextView fileName;
    private TextView fileSize;

    private MainActivity thisActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 縦画面に固定する
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        thisActivity=this;
        //ファイル読み取り権限の取得
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }

        fileSelect = new com.presenter.FileSelect();
        setContentView(R.layout.activity_main);
        Button buttonFileSelect = findViewById(R.id.button_select_file);
        Button buttonStartEval = findViewById(R.id.button_start);
        fileName = findViewById(R.id.file_name);
        fileSize = findViewById(R.id.file_size);
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

        buttonFileSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUri = fileSelect();
            }
        });

    }

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
    protected void onResume() {
        super.onResume();
        if (getUri != null) {
            fileSelect.changeFile(getUri, this);
            Button buttonStartEval = findViewById(R.id.button_start);
            buttonStartEval.setEnabled(true); // trueにしてボタンを押せるように
            buttonStartEval.setAlpha(1f); //ボタンを不透明に
            String fileSizeString;
            long fileSizeValue = fileSelect.getFileSize();
            if (fileSizeValue <= 1000)
                fileSizeString = fileSizeValue + " Byte";
            else if (fileSizeValue <= 1000000)
                fileSizeString = String.format("%.2f KB", fileSizeValue / 1000.0);
            else
                fileSizeString = String.format("%.2f MB", fileSizeValue / 1000000.0);
            fileName.setText(String.valueOf(fileSelect.getFileName()));
            fileSize.setText(fileSizeString);
        } else {
            Button buttonStartEval = findViewById(R.id.button_start);
            buttonStartEval.setEnabled(false);
            buttonStartEval.setAlpha(0.3f); //ボタンを透明に
        }


        TextView help_back = findViewById(R.id.help_back);
        TextView help_text_1 = findViewById(R.id.help_text_1);
        TextView help_text_2 = findViewById(R.id.help_text_2);
        TextView help_text_3 = findViewById(R.id.help_text_3);
        TextView help_text_4 = findViewById(R.id.help_text_4);
        TextView help_text_5 = findViewById(R.id.help_text_5);
        TextView help = findViewById(R.id.help);
        Button help_button = findViewById(R.id.help_button);
        Button help_close  = findViewById(R.id.help_close);
        Button file_select = findViewById(R.id.button_select_file);
        Button buttonFileSelect = findViewById(R.id.button_select_file);

        help_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                com.presenter.Help presenter_help=new com.presenter.Help(thisActivity);
                help.setAlpha(1f);
                help_text_1.setAlpha(1f);
                help_text_1.setText(presenter_help.helpText[0]);
                help_text_2.setAlpha(1f);
                help_text_2.setText(presenter_help.helpText[1]);
                help_text_3.setAlpha(1f);
                help_text_3.setText(presenter_help.helpText[2]);
                help_text_4.setAlpha(1f);
                help_text_4.setText(presenter_help.helpText[3]);
                help_text_5.setAlpha(1f);
                help_text_5.setText(presenter_help.helpText[4]);
                help_back.setAlpha(1f);
                help_button.setAlpha(0f);
                buttonFileSelect.setAlpha(0f);
                help_close.setAlpha(1f);
                help_close.setEnabled(true);
                file_select.setEnabled(false);

            }
        });

        help_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                help_close.setEnabled(false);
                file_select.setEnabled(true);
                help.setAlpha(0f);
                help_text_1.setAlpha(0f);
                help_text_2.setAlpha(0f);
                help_text_3.setAlpha(0f);
                help_text_4.setAlpha(0f);
                help_text_5.setAlpha(0f);
                help_back.setAlpha(0f);
                help_button.setAlpha(1f);
                buttonFileSelect.setAlpha(1f);
                help_close.setAlpha(0f);
            }
        });


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

    public void transitionDisplayResult(EvaluationValues val) {
        Intent intent = new Intent(this, ResultDisplay.class);
        intent.putExtra("EvaluationValues", new Gson().toJson(val));
        startActivity(intent);
    }
}