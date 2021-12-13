package com.model;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;


import com.main.MainActivity;

import java.nio.file.Path;
import java.util.Objects;

public class FileSelect extends AppCompatActivity {

    private AppCompatActivity appActivity;
    public FileSelect(AppCompatActivity mainActivity){ appActivity = mainActivity ;};

    Uri ReturnUri = null;
    public Uri searchFilePath(){
        System.out.println("before");
        openFile(null);
        return ReturnUri;
    }

    ActivityResultLauncher<Intent> _launcherSelectAudioFile =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                Intent resultData = result.getData();
                if (resultData != null) {
                    Uri uri = resultData.getData();
                    Log.d("Result File Path", "" + uri); // 取得してきたパス表示
                    if (uri != null) {
                        // to do something（URI取得できた時にやりたい処理をここに記述）
                        //editText.setText(loadStrFromUri(uri));
                        System.out.println("\nResult File Path\n" + uri);
                    }
                }
            }
        }
    });
    private void openFile(Uri pickerInitialUri) {
        if(appActivity==null){
            System.out.println("nullpo");
        }
        else{
            System.out.println("Yeah!!");;
        }
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("audio/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        Intent chooserIntent = Intent.createChooser(intent, "音声ファイルの選択");
        System.out.println("?????");
        if(chooserIntent == null) {
            System.out.println("null");
        }
        _launcherSelectAudioFile.launch(chooserIntent);

    }




}