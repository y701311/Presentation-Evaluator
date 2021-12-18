package com.presenter;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;

import java.io.File;

public class FileSelect {
    private Uri audioFilePath = null;
    private String fileName;
    private long fileSize;

    public void changeFile(Uri uri, Activity context) {
        audioFilePath = uri;
        String scheme = uri.getScheme();
        // get file name
        switch (scheme) {
            case "content":
                Cursor cursor = context.getContentResolver()
                        .query(uri, null, null, null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        fileName = cursor.getString(
                                cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));
                        fileSize = Long.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow(OpenableColumns.SIZE)));
                    }
                    cursor.close();
                }
                break;

            case "file":
                fileName = new File(uri.getPath()).getName();
                fileSize = new File(uri.getPath()).length();
                break;
        }
    }

    public Uri getFilePath() {
        return audioFilePath;
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileSize() {
        return fileSize;
    }
}