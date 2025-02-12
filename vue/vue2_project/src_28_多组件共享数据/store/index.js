//该文件创建Vuex中最核心的Store
import Vue from 'vue'
//引入vuex
import Vuex from 'vuex'
//应用Vuex插件
Vue.use(Vuex)

//准备actions-用于响应组件中的动作 
const actions = {
    increment(context, value) {
        console.log('actions中的increment被调用了')
        context.commit('INCREMENT', value);
    },
    incrementOdd(context, value) {
        if (context.state.sum % 2 !== 0) {
            context.commit('INCREMENT', value);
        }
    },
    incrementWait(context, value) {
        setTimeout(() => {
            context.commit('INCREMENT', value);
        }, 500);
    }
}

//准备mutations--用于操作数据 
const mutations = {
    INCREMENT(state, value) {
        console.log('mutations中的INCREMENT被调用了')
        state.sum += value;
    },
    DECREMENT(state, value) {
        state.sum -= value;
    },
    ADD_PERSON(state,value){
        state.personList.unshift(value);
    }
}

//准备State--用于存储数据
const state = {
    //求和
    sum: 0,
    school: '交大',
    subject: '计算机',
    personList:[
        {id:'001',name:'nullnull'}
    ]
}

//准备Getters-用于将state中的数据进行加工
const getters = {
    bigSum(state) {
        return state.sum * 10
    }
}

//创建并暴露store
export default new Vuex.Store({
    actions: actions,
    mutations: mutations,
    state,
    getters
})

