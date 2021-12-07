package com.presenter;

import java.io.File;
import java.nio.file.Path;
import java.util.Date;

public class FileSelect {
    private final Path audioFilePath;
    private String pathStr;
    private String fileName;
    private long fileSize;

    public FileSelect() {
        audioFilePath = new com.model.FileSelect().searchFilePath();
        File file = new File(audioFilePath.toString());
        String pathStr = audioFilePath.toString();
        String fileName = file.getName();
        long fileSize = file.length();
    }

    public Path getFilePath() {
        return audioFilePath;
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileSize() {
        return fileSize;
    }
}