package com.model;

import android.util.Log;

import org.jtransforms.fft.DoubleFFT_1D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class AccentsEval extends Evaluator {
    List<Integer> maxHertzHistory = new ArrayList<>();

    @Override
    void calculation(double[] audioData) {
        int maxHertz = 0;
        double[] data = Arrays.copyOf(audioData, audioData.length);
        new DoubleFFT_1D(data.length).realForward(data);

        int index = 1;
        for (; index < data.length; ++index) {
            if (data[index] > data[maxHertz]) maxHertz = index;
        }
        maxHertzHistory.add(maxHertz);
    }

    @Override
    EvalResult returnResult() {
        EvalResult evalResult = new EvalResult();
        final double bestDiffRate = 160;
        double diffSum = 0, diffRate;

        // データと、その1つ前のデータとの差の平均値を算出
        for (int i = 1; i < this.maxHertzHistory.size(); i++) {
            diffSum += Math.abs(this.maxHertzHistory.get(i) - this.maxHertzHistory.get(i - 1));
        }
        if (this.maxHertzHistory.size() != 0) {
            diffRate = diffSum / this.maxHertzHistory.size();
        } else {
            diffRate = 0;
        }
        // 点数化
        Log.d("debug", String.valueOf(diffRate));
        evalResult.score = 100 - 6.5 * Math.abs(diffRate - bestDiffRate);
        if (evalResult.score < 0) {
            evalResult.score = 0;
        }

        if (0 <= evalResult.score && evalResult.score < 20) {
            evalResult.text = "大事な言葉を意識しよう";
        } else if (20 <= evalResult.score && evalResult.score < 40) {
            evalResult.text = "メリハリを意識してみよう";
        } else if (40 <= evalResult.score && evalResult.score < 60) {
            evalResult.text = "正しい抑揚を意識してみよう";
        } else if (60 <= evalResult.score && evalResult.score < 80) {
            evalResult.text = "表現を工夫してみよう";
        } else if (80 <= evalResult.score && evalResult.score <= 100) {
            evalResult.text = "完璧！";
        }

        return evalResult;
    }
}