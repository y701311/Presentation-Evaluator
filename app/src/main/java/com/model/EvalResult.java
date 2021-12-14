package com.model;

class EvalResult {
    // 点数
    double score;
    // 評価テキスト
    String text;
    // 評価の方向性
    // 例えば音量なら、大きくて悪い評価なのか、小さくて悪い評価なのかの指標
    final int small = 1;
    final int large = 2;
    int evalDirection;
}
