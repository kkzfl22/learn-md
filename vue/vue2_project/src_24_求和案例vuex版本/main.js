//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'
//引入store,src/store/index.js,默认文件是这个，名称相同时，便可不写
import store from './store'

//关闭Vue的生产提示
Vue.config.productionTip = false

//创建vm
new Vue({
	el:'#app',
	render: h => h(App),
	store:store,
	//创建事件总线
	beforeCreate() {
		Vue.prototype.$bus = this
	}
})