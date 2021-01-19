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
      
      
第二版加入国际化模块，注意如下

1、 安装：npm install vue-i18n --save

2、 main.js 引入声明 

代码如下：

      import VueI18n from 'vue-i18n'  //引入vue-i18n
      import zh from './lang/cn'
      import en from './lang/en'

      Vue.use(VueI18n);

      /---------基本使用-----------/
      const i18n = new VueI18n({
           locale: 'cn', // 语言标识
           messages : {
           'en': en,
           'cn': zh
           }
      })
      const app = new Vue({
          i18n,
          ...App
      })

3、在lang目录下创建 cn.json , en.json 资源文件

cn.json：

      {
            "message": {
                  "inputPhone": "请输入手机号",
                  "inputCode": "请输入手机验证码",
                  "binding": "绑定",
                  "sendText": "发送验证码"
            }
       }

 

en.json：

      {
            "message": {
                  "inputPhone": "Please input the phone num",
                  "inputCode": "Please input the identifying code",
                  "binding": "binding",
                  "sendText": "send code"
            }
       }

4、调用资源文件

      {{ $t('message.binding') }}

动态切换语言调用：

      switchChange(e){
          if(e) {
              this.$i18n.locale = "cn";        
          } else {                    
              this.$i18n.locale = "en";
          }
      }
      
      
第三次更新：Android原生国际化
 
Android原生国际化支持随系统默认语言选择，相关代码如下：

      public Locale getSysLocale() {
          Locale locale;
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
              locale = LocaleList.getDefault().get(0);
          } else {
              locale = Locale.getDefault();
          }
          return locale;
      }

国际化文本资源需要在res目录下添加，比如添加英文就建立values-en目录，在该目录下加入strings.xml资源文件

strings.xml：

      <resources>
          <string name="app_name">patriarch_tips</string>
          <string name="introduction">Hello, my friend!</string>
      </resources>

语言设置及切换Java代码：

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
