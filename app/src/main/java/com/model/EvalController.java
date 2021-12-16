package com.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

public class EvalController {
    // 評価対象のファイルストリーム
    private InputStream audioStream;
    // ファイルの音声データ
    private double[] perUnitAudioData;
    // 音声データの残りのbyte数
    private int restOfDataSize;

    private int channel;
    private int samplingRate;
    private int dataSpeed;
    private int blockSize;
    private int bitPerSample;

    // 渡されたパスのオーディオファイルを評価して、評価値を返す
    public com.presenter.EvaluationValues evalController(Uri audioFilePath, Context context) throws IOException {
        com.presenter.EvaluationValues evaluationValues = new com.presenter.EvaluationValues();

        setAudioStream(audioFilePath, context);
        preProcess();

        int bufferSize;
        double timePerData;
        if (this.samplingRate == 44100) {
            // サンプリングレートが44.1kHzなら0.093秒くらい分のデータ
            bufferSize = 4096 * (bitPerSample / 8);
            timePerData = (double)4096 / this.samplingRate;
        }else{
            bufferSize = (int) (0.1 * samplingRate * (bitPerSample / 8));
            timePerData = 0.1;
        }

        // 各評価項目のインスタンス生成
        AccentsEval accentsEval = new AccentsEval();
        MeanLessWordsEval meanLessWordsEval = new MeanLessWordsEval(timePerData);
        SpeakingIntervalEval speakingIntervalEval = new SpeakingIntervalEval();
        SpeakingSpeedEval speakingSpeedEval = new SpeakingSpeedEval(timePerData);
        VolumeEval volumeEval = new VolumeEval();

        // 評価実行
        while(this.restOfDataSize > 0){
            makePerUnitAudioData(bufferSize);

            accentsEval.calculation(this.perUnitAudioData);
            meanLessWordsEval.calculation(this.perUnitAudioData);
            speakingIntervalEval.calculation(this.perUnitAudioData);
            speakingSpeedEval.calculation(this.perUnitAudioData);
            volumeEval.calculation(this.perUnitAudioData);
        }

        // 評価結果の取得
        EvalResult evalResult;
        int accentsEvalDirection;
        int meanLessWordsEvalDirection;
        int speakingIntervalEvalDirection;
        int speakingSpeedEvalDirection;
        int volumeEvalDirection;

        evalResult = accentsEval.returnResult();
        evaluationValues.accents = evalResult.score;
        evaluationValues.accentsText = evalResult.text;
        accentsEvalDirection = evalResult.evalDirection;
        evalResult = meanLessWordsEval.returnResult();
        evaluationValues.meanLessWords = evalResult.score;
        evaluationValues.meanLessWordsText = evalResult.text;
        meanLessWordsEvalDirection = evalResult.evalDirection;
        evalResult = speakingIntervalEval.returnResult();
        evaluationValues.speakingInterval = evalResult.score;
        evaluationValues.speakingIntervalText = evalResult.text;
        speakingIntervalEvalDirection = evalResult.evalDirection;
        evalResult = speakingSpeedEval.returnResult();
        evaluationValues.speakingSpeed = evalResult.score;
        evaluationValues.speakingSpeedText = evalResult.text;
        speakingSpeedEvalDirection = evalResult.evalDirection;
        evalResult = volumeEval.returnResult();
        evaluationValues.volume = evalResult.score;
        evaluationValues.volumeText = evalResult.text;
        volumeEvalDirection = evalResult.evalDirection;

        // 総合評価を決定
        double total = 0;
        total += evaluationValues.accents / 20;
        total += evaluationValues.meanLessWords / 20;
        total += evaluationValues.speakingInterval / 20;
        total += evaluationValues.speakingSpeed / 20;
        total += evaluationValues.volume / 20;
        evaluationValues.total = total;

        if(evaluationValues.accents <= 60 ||
                (evaluationValues.speakingSpeed <= 60 && speakingSpeedEvalDirection == evalResult.large)){
            evaluationValues.totalText = "聞き取りづらい";
        }else if((evaluationValues.speakingSpeed <= 60 && speakingSpeedEvalDirection == evalResult.small) ||
                evaluationValues.speakingInterval <= 60){
            evaluationValues.totalText = "退屈";
        } else {
            evaluationValues.totalText = "聞きやすい";
        }

        this.audioStream.close();

        System.out.println("accent score : " + String.valueOf(evaluationValues.accents));
        System.out.println(evaluationValues.accentsText);
        System.out.println("meanless score : " + String.valueOf(evaluationValues.meanLessWords));
        System.out.println(evaluationValues.meanLessWordsText);
        System.out.println("interval score : " + String.valueOf(evaluationValues.speakingInterval));
        System.out.println(evaluationValues.speakingIntervalText);
        System.out.println("speed score : " + String.valueOf(evaluationValues.speakingSpeed));
        System.out.println(evaluationValues.speakingSpeedText);
        System.out.println("volume score : " + String.valueOf(evaluationValues.volume));
        System.out.println(evaluationValues.volumeText);
        System.out.println("total score : " + String.valueOf(evaluationValues.total));
        System.out.println(evaluationValues.totalText);

        return evaluationValues;
    }

    // 自クラスにファイルストリームをセットする
    private void setAudioStream(Uri audioFilePath, Context context) throws FileNotFoundException {
        ContentResolver resolver = context.getApplicationContext().getContentResolver();
        this.audioStream = resolver.openInputStream(audioFilePath);
    }

    // ファイルからデータを取り出す際の前処理
    private void preProcess() throws IOException {
        final int readHeaderSize = 36;
        byte[] header = new byte[readHeaderSize];

        // ヘッダ情報の取得
        this.audioStream.read(header, 0, readHeaderSize);

        byte[] channelBytes = {(byte)0, (byte)0, header[23], header[22]};
        this.channel = ByteBuffer.wrap(channelBytes).getInt();
        byte[] samplingRateBytes = {header[27], header[26], header[25], header[24]};
        this.samplingRate = ByteBuffer.wrap(samplingRateBytes).getInt();
        byte[] dataSpeedBytes = {header[31], header[30], header[29], header[28]};
        this.dataSpeed = ByteBuffer.wrap(dataSpeedBytes).getInt();
        byte[] blockSizeBytes = {(byte)0, (byte)0, header[33], header[32]};
        this.blockSize = ByteBuffer.wrap(blockSizeBytes).getInt();
        byte[] bitPerSampleBytes = {(byte)0, (byte)0, header[35], header[34]};
        this.bitPerSample = ByteBuffer.wrap(bitPerSampleBytes).getInt();

        // データ領域までストリームを飛ばす
        byte[] bytes = new byte[4];
        // "data"まで飛ばす
        while((bytes[3] = (byte) this.audioStream.read()) != -1){
            if(bytes[0] == 0x64 && bytes[1] == 0x61
                    && bytes[2] == 0x74 && bytes[3] == 0x61){
                break;
            }else{
                bytes[0] = bytes[1];
                bytes[1] = bytes[2];
                bytes[2] = bytes[3];
            }
        }
        // データサイズを取得してセット
        for(int i = 3; i >= 0; i--) {
            bytes[i] = (byte)this.audioStream.read();
        }
        this.restOfDataSize = ByteBuffer.wrap(bytes).getInt();

    }
    // bufferSizeで指定されたバイト数のデータを読んでセットする
    private void makePerUnitAudioData(int bufferSize) throws IOException {
        byte[] buffer = new byte[bufferSize];
        int readSize;
        int audioDataSize = bufferSize / (this.bitPerSample / 8);
        double[] audioData = new double[audioDataSize];

        // 読むbyte数の決定
        if(this.restOfDataSize >= bufferSize){
            readSize = bufferSize;
            this.restOfDataSize -= bufferSize;
        }else{
            readSize = this.restOfDataSize;
            this.restOfDataSize = 0;
        }

        // ファイルの読み込み
        this.audioStream.read(buffer, 0, readSize);

        // byteから1サンプルのデータへ変換
        // モノラルのみ
        if(this.channel == 1) {
            byte[] bytes = new byte[4];
            // リトルエンディアンで並んでいるデータを整数にし、正の最大値で割って-1~1の範囲にする
            if (this.bitPerSample == 8) {
                for(int i = 0; i < audioDataSize; i++){
                    audioData[i] = (double)(buffer[i] / (Math.pow(2, 8) - 1));
                }
            } else if(this.bitPerSample == 16) {
                for(int i = 0; i < audioDataSize; i++){
                    bytes[0] = 0;
                    bytes[1] = 0;
                    bytes[2] = buffer[i*2 + 1];
                    bytes[3] = buffer[i*2];
                    audioData[i] = (double)(ByteBuffer.wrap(bytes).getInt() / (Math.pow(2, 16) - 1));
                }
            } else if(this.bitPerSample == 24){
                for(int i = 0; i < audioDataSize; i++){
                    bytes[0] = 0;
                    bytes[1] = buffer[i*3 + 2];
                    bytes[2] = buffer[i*3 + 1];
                    bytes[3] = buffer[i*3];
                    audioData[i] = (double)(ByteBuffer.wrap(bytes).getInt() / (Math.pow(2, 24) - 1));
                }
            } else if(this.bitPerSample == 32){
                for(int i = 0; i < audioDataSize; i++){
                    bytes[0] = buffer[i*4 + 3];
                    bytes[1] = buffer[i*4 + 2];
                    bytes[2] = buffer[i*4 + 1];
                    bytes[3] = buffer[i*4];
                    audioData[i] = (double)(ByteBuffer.wrap(bytes).getInt() / (Math.pow(2, 32) - 1));
                }
            }
        }

        this.perUnitAudioData = audioData;
    }
}
