package com.model;

import android.util.Pair;

import java.util.ArrayList;

class SpeakingIntervalEval extends Evaluator {
    private ArrayList<Double> evalValue;

    SpeakingIntervalEval() {
        this.evalValue = new ArrayList<Double>();
    }

    // audioDataの実効値のデシベルを計算する
    @Override
    void calculation(double[] audioData) {
        double evalValue;
        evalValue = Utility.getDecibel(Utility.getRms(audioData));

        this.evalValue.add(evalValue);
    }

    // 値が一定値以下なら話していないとして、そのような時間の割合を評価
    @Override
    Pair<Double, String> returnResult() {
        final double base = 30;// ささやき声くらい
        int silentCount = 0;
        // 話していないとした割合
        double silentRate;
        final double bestSilentRate = 0.2;
        double score = 0;
        String text = "";

        for(double value : this.evalValue){
            if(value <= base){
                silentCount++;
            }
        }
        silentRate = (double)silentCount / this.evalValue.size();

        if(silentRate < bestSilentRate){
            score = 100 - ((bestSilentRate - silentRate) / bestSilentRate) * 100;
        }else if(silentRate >= bestSilentRate){
            score = 100 - ((silentRate - bestSilentRate) / (1 - bestSilentRate)) * 100;
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