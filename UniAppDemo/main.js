import Vue from 'vue'
import App from './App'

import VueI18n from 'vue-i18n'  //引入vue-i18n
import zh from './lang/cn'
import en from './lang/en'
 
Vue.use(VueI18n); //通过插件的形式挂载

Vue.config.productionTip = false

App.mpType = 'app'

/*---------基本使用-----------*/
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

app.$mount()
