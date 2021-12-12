package com.model;

import android.util.Pair;

import java.util.ArrayList;

class MeanLessWordsEval extends Evaluator {
    private ArrayList<String> evalValue;
    private double timePerData;
    // 母音の数
    private final int vowelNum = 5;
    private final String[] vowels = {
            "a",
            "i",
            "u",
            "e",
            "o",
    };
    private final String noVowel = "miss";
    // 考えるフォルマントの数
    private final int formantNum = 2;
    // 母音がa,i,u,e,oの場合のF1,F2の最小値
    private final double[][] formantMin = {
            {560, 1100},
            {160, 1980},
            {170, 1100},
            {280, 1720},
            {400, 600},
    };
    // 母音がa,i,u,e,oの場合のF1,F2の最大値
    private final double[][] formantMax = {
            {1100, 2250},
            {380, 3100},
            {480, 2080},
            {700, 2700},
            {780, 1600},
    };

    MeanLessWordsEval(double timePerData) {
        this.evalValue = new ArrayList<String>();
        this.timePerData = timePerData;
    }

    @Override
    void calculation(double[] audioData) {
        double[] formant = Utility.getFormant(audioData);

        // a,i,u,e,oの5つに対して、初めにマッチした音を話しているとする
        int matchVowelId = -1;
        for(int i = 0; i < this.vowelNum; i++){
            int matchFormant = 0;
            for(int j = 0; j < this.formantNum; j++){
                if(this.formantMin[i][j] <= formant[j] &&
                        formant[j] <= this.formantMax[i][j]){
                    matchFormant++;
                }
            }
            if(matchFormant == this.formantNum){
                matchVowelId = i;
                break;
            }
        }

        if(matchVowelId == -1){
            this.evalValue.add(this.noVowel);
        }else{
            this.evalValue.add(this.vowels[matchVowelId]);
        }
    }

    @Override
    Pair<Double, String> returnResult() {
        double numOfMeanLessWordsPerMinute;
        int numOfMeanLessWords = 0;
        double score = 0;
        String text = "";

        // 同じ母音が3回以上続いているなら意味がないとする
        int meanLessWordsBorder = 3;
        int sameVowelContinues = 0;
        String previousVowel = "";
        for(String vowel : this.evalValue){
            if(vowel.equals(previousVowel) && !vowel.equals(this.noVowel)){
                sameVowelContinues++;
            }else if(sameVowelContinues >= meanLessWordsBorder){
                sameVowelContinues = 0;
                numOfMeanLessWords++;
            }
            previousVowel = vowel;
        }
        if(sameVowelContinues >= meanLessWordsBorder){
            numOfMeanLessWords++;
        }
        numOfMeanLessWordsPerMinute = 60 * numOfMeanLessWords / (this.timePerData);

        // 点数化
        // 意味のない語の頻度が30回/分なら0点になるくらい
        score = 100 - (double)(100 / 30) * numOfMeanLessWordsPerMinute;
        if(score < 0){
            score = 0;
        }

        if(0 <= score && score < 20){
            text = "まだまだ";
        }else if(20 <= score && score < 40){
            text = "ちょっと足りない";
        }else if(40 <= score && score < 60){
            text = "そこそこ";
        }else if(60 <= score && score < 80){
            text = "いい感じ";
        }else if(80 <= score && score <= 100){
            text = "ばっちり！";
        }

        return new Pair<>(score, text);
    }
}