package com.presenter;

import java.io.Serializable;

public class EvaluationValues implements Serializable {
    public double speakingInterval;
    public String speakingIntervalText;
    public double volume;
    public String volumeText;
    public double speakingSpeed;
    public String speakingSpeedText;
    public double meanLessWords;
    public String meanLessWordsText;
    public double accents;
    public String accentsText;
    public double total;
    public String totalText;

    public EvaluationValues() {
        speakingInterval = 0.8;
        speakingIntervalText = "aa";
        volume = 0.4;
        volumeText = "bb";
        speakingSpeed = 0.2;
        speakingSpeedText = "cc";
        meanLessWords = 0.7;
        meanLessWordsText = "dd";
        accents = 0.99;
        accentsText = "ee";
        total = 0.6;
        totalText = "ff";
    }
}