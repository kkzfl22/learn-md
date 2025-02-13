export default {
    namespaced:true,
    actions: {
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
    },
    mutations: {
        INCREMENT(state, value) {
            console.log('mutations中的INCREMENT被调用了')
            state.sum += value;
        },
        DECREMENT(state, value) {
            state.sum -= value;
        },
    },
    state: {
        sum: 0,
        school: '交大',
        subject: '计算机',
    },
    getters: {
        bigSum(state) {
            return state.sum * 10
        }
    }
}