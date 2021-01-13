package com.tigerhsu.android.uniappandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("MainActivity", "MainActivity: onCreate");

        Button btn = findViewById(R.id.actionBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goNewActivity();
            }
        });
    }

    private void goNewActivity() {
        Intent intent = new Intent();
        Log.d("PluginTestFunction", "PluginTestFunction: " + intent);
        //com.HBuilder.integrate换成你的包名，com.HBuilder.integrate.MainActivity换成你要开启的Activity的全路径
        intent.setClassName("com.tigerhsu.android.uniappandroid", "io.dcloud.PandoraEntry");
//        Context mContext = getDPluginContext();
        Context mContext = this;

//        Activity mDPluginActivity = getDPluginActivity(); 这个值为null
        Log.d("PluginTestFunction", "mDPluginActivity: " + mContext);
        /**
         * public Context getDPluginContext() {
         return this.mDPluginContext == null ? this.mApplicationContext : this.mDPluginContext;
         }
         用于这里返回的是mApplicationContext ，所以要开启新的任务栈Intent.FLAG_ACTIVITY_NEW_TASK
         */
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        ComponentName componentName = new ComponentName("com.tigerhsu.android.uniappandroid",
                "io.dcloud.PandoraEntryActivity");
        if (intent != null) {
            // 这里跟Activity传递参数一样的嘛，不要担心怎么传递参数，还有接收参数也是跟Activity和Activity传参数一样
            intent.setComponent(componentName);
            intent.putExtra("phone", "13987654321");
        }
        mContext.startActivity(intent);
    }
}
