//该文件创建Vuex中最核心的Store
import Vue from 'vue'
//引入vuex
import Vuex from 'vuex'

import countOptions from './count'
import personOption from './person'

//应用Vuex插件
Vue.use(Vuex)

//创建并暴露store
export default new Vuex.Store({
    modules:{
        countAbout: countOptions,
        personAbout: personOption
    }
})

