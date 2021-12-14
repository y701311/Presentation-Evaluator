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

public class FileSelect extends MainActivity {

//    private AppCompatActivity appActivity;
//    public FileSelect(AppCompatActivity mainActivity){ appActivity = mainActivity ;};

    Uri ReturnUri = null;
    public Uri searchFilePath(){
        //openfile();
        return ReturnUri;
    }




}