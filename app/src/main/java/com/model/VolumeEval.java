package com.model;

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
    EvalResult returnResult() {
        EvalResult evalResult = new EvalResult();
        double decibelMean = 0;
        final double bestVolume = 60;

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
        evalResult.score = 100 - 100 * Math.abs(bestVolume - decibelMean) / bestVolume;

        if(decibelMean > bestVolume){
            evalResult.evalDirection = evalResult.large;
        }else{
            evalResult.evalDirection = evalResult.small;
        }

        if(0 <= evalResult.score && evalResult.score < 20){
            if(decibelMean > bestVolume){
                evalResult.text = "声が大きすぎる";
            }else{
                evalResult.text = "声が小さすぎる";
            }
        }else if(20 <= evalResult.score && evalResult.score < 40){
            if(decibelMean > bestVolume){
                evalResult.text = "声が大きい";
            }else{
                evalResult.text = "声が小さい";
            }
        }else if(40 <= evalResult.score && evalResult.score < 60){
            if(decibelMean > bestVolume){
                evalResult.text = "ちょっと声が大きい";
            }else{
                evalResult.text = "ちょっと声が小さい";
            }
        }else if(60 <= evalResult.score && evalResult.score < 80){
            evalResult.text = "いい感じ";
        }else if(80 <= evalResult.score && evalResult.score <= 100){
            evalResult.text = "ばっちり！";
        }

        return evalResult;
    }
}