package com.example.project_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    Context context = MainActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        }, 1000);

    }

//    private void dialog_chung(Integer so , String tb , Context context){
//        switch (so){
//            case 0:
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle("Thông Báo").setMessage(tb);
//                AlertDialog dialog = builder.create();
//                dialog.show();
//                break;
//            case 1:
//                Toast.makeText(context ,tb ,Toast.LENGTH_SHORT).show();
//                break;
//        }
//    }
}