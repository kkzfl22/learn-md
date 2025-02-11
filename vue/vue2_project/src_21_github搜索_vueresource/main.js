//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'
// 引入插件
import VueResource from 'vue-resource'
//关闭Vue的生产提示
Vue.config.productionTip = false

//使用插件 
Vue.use(VueResource)

//创建vm
new Vue({
	el:'#app',
	render: h => h(App),
	//创建事件总线
	beforeCreate() {
		Vue.prototype.$bus = this
	}
})