package com.example.rtsph264streamplayer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ResultReceiver;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.rtsph264streamplayer.dialog.CustomDialog;

import org.easydarwin.video.Client;
import org.easydarwin.video.EasyPlayerClient;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity {
    public static final String KEY = "6D75724D7A4A36526D343241725370636F395652792B5A76636D63755A57467A65575268636E64706269356C59584E356347786865575679567778576F50394C2F69426C59584E35";
    public static String TAG = "MainActivity";
    private static EasyPlayerClient client;
    long numpad0Time, numpad1Time ,numpad2Time;
    public Context context;
    CustomDialog dialog;
    final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    private ResultReceiver rr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideNavigationAndStatusbar();

        context = this;

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Toast.makeText(MainActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        setContentView(R.layout.activity_main);
        TextureView textureView = (TextureView) findViewById(R.id.texture_view);
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String url = sp.getString("url",null);
        rr = new ResultReceiver(null) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                Message msg = new Message();
                msg.obj = "Connecting...";
                if(resultData == null){
//                    msg.obj = "connect success!";
//                    handler.sendMessage(msg);
                    return;
                }

                if(resultData.getInt("errorinfo") == 2)
                {
                    msg.obj = "Connect failed!";
//                    sp.edit().putString("url", null).apply();
                }else if(resultData.getInt("errorinfo") == 3)
                {
                    msg.obj = "Connect failed!";
//                    sp.edit().putString("url", null).apply();
                }
                handler.sendMessage(msg);
            }
        };
        client = new EasyPlayerClient(this, KEY, textureView, rr, null);

//        client = new EasyPlayerClient(this, KEY, textureView, null, null);
        client.setAudioEnable(true);

        dialog = new CustomDialog(MainActivity.this);

        if(url == null){
            dialog.show();
        }else{
            String path = sp.getString("url",null);
            playVideo(path);
        }

    }

    public static void playVideo(String path){
        client.play(path);
        if(!client.isAudioEnable()){
            Log.e(TAG, "Audio Not Enable!");
        }
    }

    public static void stopVideo(){
        client.stop();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_1:
                numpad0Time = System.currentTimeMillis();
                break;
            case KeyEvent.KEYCODE_2:
                numpad1Time = System.currentTimeMillis();
                break;
            case KeyEvent.KEYCODE_3:
                numpad2Time = System.currentTimeMillis();
                break;
        }

        checkKeys();

        return true;
    }

    private void checkKeys() {
        Long now = System.currentTimeMillis();
        if(now - numpad0Time < 500 &&
                now - numpad1Time < 500 &&
                now - numpad2Time < 500) {

            dialog.show();
        }
    }

    @Override
    public void onBackPressed() {
        client.stop();
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.edit().putString("url", null).apply();
        dialog.show();
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(flags);
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String url = sp.getString("url",null);
        if(url == null){
            dialog.show();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        stopVideo();
        super.onDestroy();
    }

    public void hideNavigationAndStatusbar(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(flags);
    }
}
