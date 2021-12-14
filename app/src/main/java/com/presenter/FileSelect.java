package com.presenter;

import android.net.Uri;

import com.view.MainActivity;

import java.io.File;

public class FileSelect {
    private Uri audioFilePath = null;
    private File file;

    public void changeFile(Uri uri) {
        audioFilePath = uri;
        file = new File(audioFilePath.getPath());
    }

    public Uri getFilePath() {
        return audioFilePath;
    }

    public String getFileName() {
        String fileName = file.getName();
        return fileName;
    }

    public long getFileSize() {
        long fileSize = file.length();
        return fileSize;
    }
}