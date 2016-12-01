package com.cloudhuan.airbot;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.File;
import java.io.FileFilter;

public class LogService extends Service {

    MyBinder myBinder;
    Thread thread;
    final String TAG = LogService.class.getName();
    RecordStep recordStep;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        myBinder = new MyBinder();
        return myBinder;
    }

    public class MyBinder extends Binder{
        public LogService getService(){
            return LogService.this;
        }
    }

    public void startRecordLog(){
        recordStep = new RecordStep();
        thread = new Thread(new MyThread());
        thread.start();
    }

    public void stopRecordLog(){
        recordStep.stop();
        stopSelf();
    }

    /**
     * log记录线程
     */
    private class MyThread implements Runnable{

        @Override
        public void run() {
            initPath();
            recordStep.start();
        }
    }

    public void setClearBeforeEnable(){
        recordStep.setClearBeforeEnable();
    }

    private void initPath(){
        Log.i(TAG,"init path");
        File f = new File(AirBotConst.LOG_DIR);
        if(!f.exists()){
            boolean b = f.mkdirs();
            Log.i(TAG,"not exist,create it:"+AirBotConst.LOG_DIR+b);
        }
        File[] fs = f.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().trim().contains(Utils.getFolderTime());
            }
        });
        if(fs.length<1){
            new File(AirBotConst.TODAY_DIR).mkdirs();
            Log.i(TAG,"create taday folder::"+Utils.getFolderTime());
        }
    }
}
