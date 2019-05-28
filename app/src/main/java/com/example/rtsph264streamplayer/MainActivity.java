package com.example.rtsph264streamplayer;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.rtsph264streamplayer.dialog.CustomDialog;

public class MainActivity extends AppCompatActivity {

    EditText edit_ip;
    Button btn_ok, btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomDialog dialog = new CustomDialog(MainActivity.this);
        dialog.show();
    }
}
