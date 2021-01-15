# Uni_CrossPlatform
Uni-App实现跨平台项目

UniAppDemo 是H5端项目
UniAppAndroid 是安卓移动端项目
可以根据https://nativesupport.dcloud.net.cn/AppDocs/usesdk/android 打包指南，实现H5端项目前端代码与Android端公用

注意点：

1、要在manifest.json文件中配置AppId和个推ID（不需要该功能则忽略）

2、需要推送功能的也要在App模块配置里面勾选Push和uniPush，具体参考https://ask.dcloud.net.cn/article/35622

3、可以通过Intent实现原生Activity与dcloud代码交互传参，主要代码参考如下：


      Intent intent = new Intent();
      //com.HBuilder.integrate换成你的包名，com.HBuilder.integrate.MainActivity换成你要开启的Activity的全路径
      intent.setClassName("com.tigerhsu.android.uniappandroid", "io.dcloud.PandoraEntry"); 
      Context mContext = this;
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      ComponentName componentName = new ComponentName("com.tigerhsu.android.uniappandroid",
              "io.dcloud.PandoraEntryActivity");
      if (intent != null) {
          intent.setComponent(componentName);
          intent.putExtra("phone", "13987654321");
      }
      mContext.startActivity(intent);

4、 APK覆盖安装后页面不更新问题：
      1) 内置的资源版本名称和版本号要高于上一个版本；
      2) control.xml 中 debug 改成 false  //会出现console.log 日志不输出问题
