package com.cloudhuan.logrecord;

import android.os.Environment;

/**
 * Created by cloudhuan on 2016/12/1.
 */

public class LogRecordConst {

    public static final String LOG_DIR = Environment.getExternalStorageDirectory()+"/AirBotLog";

    public static final String TODAY_DIR = Environment.getExternalStorageDirectory()+"/AirBotLog/"+Utils.getFolderTime();

    public static final String NOW_FILE_NAME = Environment.getExternalStorageDirectory()+"/AirBotLog/"+Utils.getFolderTime()+"/"+Utils.getFileTime()+".log";

    public static final long SPLIT_TIME = 10 * 1000;  //log分片时间
}
