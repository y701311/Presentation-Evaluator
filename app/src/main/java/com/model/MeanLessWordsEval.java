package com.model;

import java.util.ArrayList;

class MeanLessWordsEval extends Evaluator {
    private ArrayList<String> evalValue;
    private double timePerData;
    private int samplingRate;
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

    MeanLessWordsEval(double timePerData, int samplingRate) {
        this.evalValue = new ArrayList<String>();
        this.timePerData = timePerData;
        this.samplingRate = samplingRate;
    }

    @Override
    void calculation(double[] audioData) {
        double[] formant = Utility.getFormant(audioData, this.samplingRate);

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
    EvalResult returnResult() {
        EvalResult evalResult = new EvalResult();
        double numOfMeanLessWordsPerMinute;
        int numOfMeanLessWords = 0;

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
        evalResult.score = 100 - (double)(100 / 30) * numOfMeanLessWordsPerMinute;
        if(evalResult.score < 0){
            evalResult.score = 0;
        }

        if(0 <= evalResult.score && evalResult.score < 20){
            evalResult.text = "大事なところを覚えよう";
        }else if(20 <= evalResult.score && evalResult.score < 40){
            evalResult.text = "文をよく読んでみよう";
        }else if(40 <= evalResult.score && evalResult.score < 60){
            evalResult.text = "口癖があるかも";
        }else if(60 <= evalResult.score && evalResult.score < 80){
            evalResult.text = "あと一歩";
        }else if(80 <= evalResult.score && evalResult.score <= 100){
            evalResult.text = "完璧！";
        }

        return evalResult;
    }
}