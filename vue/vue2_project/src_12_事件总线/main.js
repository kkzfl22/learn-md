//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'


//关闭Vue的生产提示
Vue.config.productionTip = false


//创建VM
new Vue({
    el: '#app',
	render: h => h(App),
    beforeCreate(){
        //注册一个全局的事件总线
        Vue.prototype.$bus = this;
    }
})