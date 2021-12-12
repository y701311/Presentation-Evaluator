package com.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.main.R;

public class FileSelect extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fileselect);
        //表示ボタンであるButtonオブジェクトを取得
        Button button_select = findViewById(R.id.button_select);
        //リスナクラスのインスタンスを生成
//        SelectListener listener = new SelectListener();
        //表示ボタンにリスナを設定
//        button_select.setOnClickListener(listener);
        button_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),EvalStart.class);
                startActivity(intent);
            }
        });
    }
}
