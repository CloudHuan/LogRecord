package com.cloudhuan.logrecord;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by cloudhuan on 2016/12/1.
 */

public class RecordStep {

    String TAG = RecordStep.class.getName();
    boolean flag = true;
    boolean nameChange_flag = false;
    InputStream inputStream;
    BufferedReader bufferedReader;
    FileWriter fileWriter;
    boolean clearBefore = false;

    public void start(){
        new writeThread().start();
    }

    class writeThread extends Thread{
        @Override
        public void run() {
            beginRecord();
        }
    }

    public void setClearBeforeEnable(){
        clearBefore = true;
    }

    private void beginRecord() {
        Log.i(TAG,"beging record");
        try {
            if(clearBefore){
                Runtime.getRuntime().exec("logcat -c");
            }
            inputStream = Runtime.getRuntime().exec("logcat -v time").getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            fileWriter = new FileWriter(LogRecordConst.NOW_FILE_NAME);
            long s_time = System.currentTimeMillis();
            String str = null;
            while((str=bufferedReader.readLine())!=null){
                //Log.i(TAG,str);
                if(System.currentTimeMillis() - s_time > LogRecordConst.SPLIT_TIME){
                    fileWriter.flush();
                    fileWriter.close();
                    fileWriter = new FileWriter(LogRecordConst.TODAY_DIR+"/"+Utils.getFileTime()+".log");
                    //Log.i(TAG,"change record file!!!"+Utils.getFileTime());
                    s_time = System.currentTimeMillis();
                }
                fileWriter.write(str);
                fileWriter.write("\n");
            }
            Log.i(TAG,"end?");
        }catch (Exception e){
        }finally {
            if(fileWriter!=null){
                try {
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void stop(){
        flag = false;
    }
}

