//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'
//引入插件
import plugin from './plugin'

//关闭Vue的生产提示
Vue.config.productionTip = false


//应用插件
Vue.use(plugin,1,2,3)


//创建VM
new Vue({
    el: '#app',
	render: h => h(App)
})