package com.model;

import java.util.ArrayList;

class SpeakingSpeedEval extends Evaluator {
    private ArrayList<Boolean> evalValue;
    private double timePerData;
    // 母音の数
    private final int vowelNum = 5;
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

    SpeakingSpeedEval(double timePerData) {
        this.evalValue = new ArrayList<Boolean>();
        this.timePerData = timePerData;
    }

    @Override
    void calculation(double[] audioData) {
        double[] formant = Utility.getFormant(audioData);

        // a,i,u,e,oの5つに対して、1つでもフォルマントが合致したら話していると判定
        int matchVowel = 0;
        for(int i = 0; i < this.vowelNum; i++){
            int matchFormant = 0;
            for(int j = 0; j < this.formantNum; j++){
                if(this.formantMin[i][j] <= formant[j] &&
                    formant[j] <= this.formantMax[i][j]){
                    matchFormant++;
                }
            }
            if(matchFormant == this.formantNum){
                matchVowel++;
            }
        }

        if(matchVowel >= 1){
            this.evalValue.add(true);
        }else{
            this.evalValue.add(false);
        }
    }

    @Override
    EvalResult returnResult() {
        EvalResult evalResult = new EvalResult();
        double charNumPerSec;
        int charNum = 0;
        final double bestCharNumPerSec = 7;

        // 話していると判定された数をカウント
        for(Boolean value : this.evalValue){
            if(value){
                charNum++;
            }
        }
        charNumPerSec = charNum / this.timePerData;

        // 点数化
        evalResult.score = 100 * (1 - Math.abs(1 - charNumPerSec / bestCharNumPerSec));
        if(evalResult.score < 0){
            evalResult.score = 0;
        }
        if(charNumPerSec > bestCharNumPerSec){
            evalResult.evalDirection = evalResult.large;
        }else{
            evalResult.evalDirection = evalResult.small;
        }

        if(0 <= evalResult.score && evalResult.score < 20){
            if(charNumPerSec > bestCharNumPerSec){
                evalResult.text = "話すスピードが速すぎるかも";
            }else{
                evalResult.text = "話すスピードが遅すぎるかも";
            }
        }else if(20 <= evalResult.score && evalResult.score < 40){
            if(charNumPerSec > bestCharNumPerSec){
                evalResult.text = "落ち着いて話してみよう";
            }else{
                evalResult.text = "すらすら話すことを意識しよう";
            }
        }else if(40 <= evalResult.score && evalResult.score < 60){
            if(charNumPerSec > bestCharNumPerSec){
                evalResult.text = "話すスピードがすこし速いかも";
            }else{
                evalResult.text = "話すスピードがすこし遅いかも";
            }
        }else if(60 <= evalResult.score && evalResult.score < 80){
            evalResult.text = "話のテンポを意識しよう";
        }else if(80 <= evalResult.score && evalResult.score <= 100){
            evalResult.text = "完璧！";
        }

        return evalResult;
    }
}