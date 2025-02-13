import axios from 'axios'
import { nanoid } from 'nanoid'

export default {
    //此为必须
    namespaced: true,
    actions: {
        addPersonWang(context, value) {
            if (value.name.indexOf('刘') === 0) {
                context.commit('ADD_PERSON', value);
            }
            else {
                alert('添加的人须姓刘');
            }
        },
        addPersionServer(context) {
            axios.get('https://luckycola.com.cn/tools/randomStr?ColaKey=yKZpIa0Eu5nZp01739458376604gCoBaEkvfo').then(
                response => {
                    console.log(response)
                    context.commit('ADD_PERSON', { id: nanoid(), name: response.data.data.str })
                }, error => {
                    alert(error.message);
                }
            )
        }
    },
    mutations: {
        ADD_PERSON(state, value) {
            state.personList.unshift(value);
        }
    },
    state: {
        personList: [
            { id: '001', name: 'nullnull' }
        ]
    },
    getters: {
        firstPersonName(state) {
            return state.personList[0].name;
        }
    }
}