<template>
  <h1>一个人的信息</h1>
  <h2>姓名: {{name}}</h2>
  <h2>年龄: {{age}}</h2>
  <h2>工作种类: {{job.type}}</h2>
  <h2>工作薪水: {{job.salary}}</h2>
  <button @click="changeInfo">修改人的信息</button>
</template>

<script>
import {ref} from 'vue'
export default {
    name: 'App',
    setup(){
       //数据,ref函数，将一般类型转换为响应式对象，类型为RefImpl
       let name=ref('张三')
       let age = ref(18)
       //将对象类型转换为响应式对象，类型为Proxy
       let job = ref({
        type: '前端工程师',
        salary: '21K'
       })

       //方法
       function changeInfo(){
          name.value = '李四'
          age.value = 33
          console.log(job.value)
          job.value.type = '设计师'
          job.value.salary = '55K'
          console.log(name,age)
       }

       //返回一个对象
       return {
          name,
          age,
          job,
          changeInfo
       }
    }
}
</script>

<style>

</style>