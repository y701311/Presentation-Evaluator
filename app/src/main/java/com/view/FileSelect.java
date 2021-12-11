package com.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.main.MainActivity;
import com.main.R;

public class FileSelect extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //表示ボタンであるButtonオブジェクトを取得
        Button button_select = findViewById(R.id.button_select);
        //リスナクラスのインスタンスを生成
        SelectListener listener = new SelectListener();
        //表示ボタンにリスナを設定
        button_select.setOnClickListener(listener);
    }

    private class SelectListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            Intent intent = new Intent(this, SelectedFile.class);
            startActivity(intent);
        }
    }
}
}
