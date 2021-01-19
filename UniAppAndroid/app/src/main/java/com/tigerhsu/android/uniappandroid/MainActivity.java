package com.tigerhsu.android.uniappandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tigerhsu.android.uniappandroid.language.CommSharedUtil;
import com.tigerhsu.android.uniappandroid.language.LanguageType;
import com.tigerhsu.android.uniappandroid.language.MultiLanguageUtil;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initLanguage();
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


    private void initLanguage() {
        MultiLanguageUtil.init(this);
        MultiLanguageUtil.getInstance().updateLanguage(LanguageType.LANGUAGE_EN);
        setConfiguration();
    }

    /**
     * 设置语言
     */
    public void setConfiguration() {
        Locale targetLocale = getLanguageLocale();
        Configuration configuration = this.getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(targetLocale);
        } else {
            configuration.locale = targetLocale;
        }

        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(configuration, dm);//语言更换生效的代码!
    }

    /**
     * 如果不是英文、简体中文、繁体中文，默认返回简体中文
     *
     * @return
     */
    private Locale getLanguageLocale() {
        int languageType = CommSharedUtil.getInstance(this).getInt(MultiLanguageUtil.SAVE_LANGUAGE, 0);
        Log.d("MainActivity", "languageType ==> : " + languageType);
        if (languageType == LanguageType.LANGUAGE_FOLLOW_SYSTEM) {
            Locale sysLocale= getSysLocale();
            return sysLocale;
        } else if (languageType == LanguageType.LANGUAGE_EN) {
            return Locale.ENGLISH;
        } else if (languageType == LanguageType.LANGUAGE_CHINESE_SIMPLIFIED) {
            return Locale.SIMPLIFIED_CHINESE;
        } else if (languageType == LanguageType.LANGUAGE_CHINESE_TRADITIONAL) {
            return Locale.TRADITIONAL_CHINESE;
        }
        getSystemLanguage(getSysLocale());
        Log.e("MainActivity", "getLanguageLocale" + languageType + languageType);
        return Locale.SIMPLIFIED_CHINESE;
    }

    private String getSystemLanguage(Locale locale) {
        return locale.getLanguage() + "_" + locale.getCountry();

    }

    //以上获取方式需要特殊处理一下
    public Locale getSysLocale() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        return locale;
    }
}
