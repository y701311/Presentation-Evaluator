package com.model;

import org.jtransforms.fft.DoubleFFT_1D;

import java.util.ArrayList;
import java.util.List;

public class Utility {

    // 配列の実効値を返す
    static double getRms(double[] array) {
        double rms = 0;

        // 二乗和
        for (double value : array) {
            rms += value * value;
        }
        // 平均
        rms /= array.length;
        // 平方根
        rms = Math.sqrt(rms);

        return rms;
    }

    static double getDecibel(double rms) {
        final double Base = 0.1;
        if(rms / Base <= 0){
            return 0;
        }else{
            return 20 * Math.log10(rms / Base);
        }
    }

    static double[] getFormant(double[] data) {
        int len = data.length;
        DoubleFFT_1D fft_1D = new DoubleFFT_1D(len);
        List<Integer> formant = new ArrayList<>();
        int firstFormant = -1, secondFormant = -1;
        fft_1D.realForward(data);

        for (int i = 5; i < data.length / 2 - 5; ++i) {
            if (data[i - 1] < data[i] && data[i] > data[i + 1]) {
                formant.add(i);
            }
        }
        for (int i : formant) {
            short checkFormant;

            for (checkFormant = -5; checkFormant <= -2; ++checkFormant) {
                if (data[i + checkFormant] > data[i + checkFormant + i]) break;
            }

            if (i + checkFormant != -2) continue;

            for (checkFormant = 1; checkFormant <= 4; ++checkFormant) {
                if (data[i + checkFormant] < data[i + checkFormant + 1]) break;
            }

            if (i + checkFormant != 4) continue;
            if (firstFormant == -1) firstFormant = i;
            else if (secondFormant == -1) secondFormant = i;
        }
        if(firstFormant == -1 || secondFormant == -1) {
            return new double[]{-1, -1};
        }else{
            return new double[]{data[firstFormant], data[secondFormant]};
        }
    }
}
