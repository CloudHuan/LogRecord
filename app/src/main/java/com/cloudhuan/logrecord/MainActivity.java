package com.cloudhuan.logrecord;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.cloudhuan.logreocrd.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {

    Button button;
    Button button2;
    CheckBox checkBox;
    LogService logService;
    String TAG = MainActivity.class.getName();
    boolean flag0 = false;
    boolean flag1 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)this.findViewById(R.id.button);
        button2 = (Button)this.findViewById(R.id.button2);
        checkBox = (CheckBox)this.findViewById(R.id.checkBox);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.button:{
                button.setText("开始记录log");
                if(flag0){
                    flag0 = false;
                }else{
                    flag0 = true;
                    button.setText("停止记录log");
                    this.bindService(new Intent(this,LogService.class),this,BIND_AUTO_CREATE);
                }
                break;
            }
            case R.id.button2:{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int count = 0;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                button2.setText("停止打印log");
                            }
                        });
                        if(flag1){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    button2.setText("开始打印log");
                                }
                            });
                            flag1 = false;
                        }else {
                            flag1 = true;
                        }
                        while(flag1) {
                            Log.i(TAG, String.format("我是log，我是第%s号",count++));
                            SystemClock.sleep(50);
                        }
                    }
                }).start();
                break;
            }
        }
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
        this.unbindService(this);
        logService.stopRecordLog();
    }
}
