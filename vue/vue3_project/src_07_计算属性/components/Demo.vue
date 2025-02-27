<template>
  <h1>一个人的信息</h1>
  姓: <input type="text" v-model="person.firstName" />
  <br/>
  名: <input type="text" v-model="person.lastName" />
  <br/>
  <span>全名: {{person.fullName}}</span>
  <br/>
  全名: <input type="text" v-model="person.fullName" />
</template>

<script>
import {reactive,computed} from 'vue'
export default {
  name: 'DemoComputed',
  setup(){
    let person = reactive({
      firstName: '张',
      lastName: '三'
    });

    //计算属性-简写（如果没有值被修改的情况）
    // person.fullName = computed(()=>{
    //   return person.firstName + '-' + person.lastName;
    // })

    //计算属性的完整写法,可以读写
    person.fullName = computed({
      get(){
        return person.firstName + '-' + person.lastName
      },
      set(value){
        const nameArr = value.split('-');
        person.firstName = nameArr[0];
        person.lastName = nameArr[1];
      }
    });

    return {
      person
    }
  }
}
</script>

<style>

</style>