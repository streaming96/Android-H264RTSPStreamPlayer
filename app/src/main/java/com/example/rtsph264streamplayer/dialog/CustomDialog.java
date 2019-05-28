package com.example.rtsph264streamplayer.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.rtsph264streamplayer.R;

import static android.content.Context.MODE_PRIVATE;

public class CustomDialog extends Dialog implements View.OnClickListener {

    public Activity c;
    public Dialog d;
    public EditText edit;
    public Button btn_ok, btn_cancel;

    public CustomDialog(Activity activity){
        super(activity);
        this.c = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_setip);
        edit = (EditText) findViewById(R.id.edit_ip);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_ok.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_ok:
                String ipaddress = edit.getText().toString();
                Log.d("Ipaddress" , ipaddress);
                break;
            case R.id.btn_cancel:
                this.hide();
                break;
        }
    }
}
