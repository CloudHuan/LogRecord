package com.cloudhuan.airbot;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/1.
 */

public class Utils {

    public static String getFolderTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(new Date());
    }

    public static String getFileTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return simpleDateFormat.format(new Date());
    }
}
