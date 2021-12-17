package com.model;

import android.util.Log;

import org.jtransforms.fft.DoubleFFT_1D;
import org.jtransforms.fft.FloatFFT_1D;

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
        if (rms / Base <= 1) {
            return 0;
        } else {
            return 20 * Math.log10(rms / Base);
        }
    }

    static double[] getFormant(double[] d, int samplingRate) {
        double[] data = d;
        int a = 0;
        int len = data.length;
        double[] powerSpector = new double[len / 2 + 1];
        double[] ave = new double[len / 20];
        DoubleFFT_1D fft_1D = new DoubleFFT_1D(len);
        int firstFormant = -1, secondFormant = -1;

        fft_1D.realForward(data);
        int tmp;
        if (len % 2 == 0) tmp = 0;
        else {
            tmp = 1;
            powerSpector[0] = data[0];
        }
        for (; tmp < len / 2; tmp++)
            powerSpector[tmp] = Math.sqrt(data[tmp] * data[tmp] + data[tmp + 1] * data[tmp + 1]);

        for (int i = 0; i < len / 20; ++i) {
            for (int j = 0; j < 10; j++) ave[i] += powerSpector[i * 10 + j];
            ave[i] /= 10;
        }

        int allAve = 0;
        for (double t : ave) {
            allAve += t;
        }
        allAve /= ave.length;

        for (int i = 0; i < ave.length; ++i) {
            if (ave[i] > allAve * 2.5) {
                if (firstFormant == -1) firstFormant = (i + 1) * 10 - 5;
                else if (secondFormant == -1) secondFormant = (i + 1) * 10 - 5;
            }
        }
        return new double[]{firstFormant * ((samplingRate / 2.0) / len), secondFormant * ((samplingRate / 2.0) / len)};
    }
}
