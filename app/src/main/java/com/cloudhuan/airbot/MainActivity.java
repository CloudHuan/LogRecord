package com.cloudhuan.airbot;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {

    Button button,button2;
    CheckBox checkBox;
    LogService logService;
    String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)this.findViewById(R.id.button);
        button2 = (Button)this.findViewById(R.id.button2);
        checkBox = (CheckBox)this.findViewById(R.id.checkBox);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{ android.Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_LOGS},1);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.button:{
                Log.i(TAG,"clickzzzzzzzzzzzzzzzzzzz");
                startAir();
                this.bindService(new Intent(this,LogService.class),this,BIND_AUTO_CREATE);
            }
            case R.id.button2:{
                startAir();
                /*try {
                    Runtime.getRuntime().exec("xxxxxx").waitFor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            }
        }
    }

    private void startAir(){
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName("com.arashivision.insta360atom","com.arashivision.insta360atom.ConnectControlActivity");
        intent.setComponent(componentName);
        startActivity(intent);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        logService = ((LogService.MyBinder)service).getService();
        logService.startRecordLog();
        if(checkBox.isChecked()){
            logService.setClearBeforeEnable();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        logService.stopRecordLog();
    }
}
