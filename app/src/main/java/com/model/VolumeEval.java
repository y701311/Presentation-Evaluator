package com.model;

import android.util.Pair;

import java.util.ArrayList;

class VolumeEval extends Evaluator {
    private ArrayList<Double> evalValue;

    VolumeEval() {
        this.evalValue = new ArrayList<Double>();
    }

    // audioDataの実効値のデシベルを計算する
    @Override
    void calculation(double[] audioData) {
        double evalValue;
        evalValue = Utility.getDecibel(Utility.getRms(audioData));

        this.evalValue.add(evalValue);
    }

    @Override
    Pair<Double, String> returnResult() {
        double decibelMean = 0;
        final double bestVolume = 50;
        double score = 0;
        String text = "";

        // デシベルの平均値を算出
        for(double value : this.evalValue){
            decibelMean += value;
        }
        decibelMean /= this.evalValue.size();

        // 点数化
        // 大きすぎるなら点数化しやすいように丸める
        if(decibelMean >= bestVolume * 2){
            decibelMean = bestVolume * 2;
        }
        score = 100 - Math.abs(bestVolume - decibelMean);

        if(0 <= score && score < 20){
            if(decibelMean > bestVolume){
                text = "声が大きすぎる";
            }else{
                text = "声が小さすぎる"
            }
        }else if(20 <= score && score < 40){
            if(decibelMean > bestVolume){
                text = "声が大きい";
            }else{
                text = "声が小さい"
            }
        }else if(40 <= score && score < 60){
            if(decibelMean > bestVolume){
                text = "ちょっと声が大きい";
            }else{
                text = "ちょっと声が小さい"
            }
        }else if(60 <= score && score < 80){
            text = "いい感じ";
        }else if(80 <= score && score <= 100){
            text = "ばっちり！";
        }

        return new Pair<>(score, text);
    }
}