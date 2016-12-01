package com.cloudhuan.airbot;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button,button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)this.findViewById(R.id.button);
        button2 = (Button)this.findViewById(R.id.button2);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        setFloat();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.button:{
                Intent intent = new Intent();
                ComponentName componentName = new ComponentName("com.arashivision.insta360atom","com.arashivision.insta360atom.ConnectControlActivity");
                intent.setComponent(componentName);
                startActivity(intent);
                //setFloat();
            }
            case R.id.button2:{
                try {
                    Runtime.getRuntime().exec("xxxxxx").waitFor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setFloat(){
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        //设置window type
        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888;
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //调整悬浮窗显示的停靠位置为左侧置顶
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        // 以屏幕左上角为原点，设置x、y初始值，相对于gravity
        wmParams.x = 0;
        wmParams.y = 0;

        //设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        Button button = new Button(this);
        button.setText("我是窗口！");
        WindowManager windowManager = (WindowManager)this.getSystemService(WINDOW_SERVICE);
        windowManager.addView(button,wmParams);
    }
}
