<template>
  <h1>一个人的信息</h1>
  <h2>姓名: {{person.name}}</h2>
  <h2>年龄: {{person.age}}</h2>
  <h2>工作种类: {{person.job.type}}</h2>
  <h2>工作薪水: {{person.job.salary}}</h2>
  <h3>爱好: {{person.hobby}}</h3>
  <h3>测试的数据: {{person.job.a.b.c}}</h3>
  <button @click="changeInfo">修改人的信息</button>
</template>

<script>
import {reactive} from 'vue'
export default {
    name: 'App',
    setup(){
      let person = reactive({
        name: '张三',
        arg: 18,
        job: {
            type: '前端工程师',
            salary: '30K',
            a: {
              b: {
                c: 666
              }
            }
        },
        hobby: ['上网','喝酒','睡觉']
      })

      console.log('reactive',person)


      function changeInfo(){
        person.name = '李四'
        person.age = 32
        person.job.type = '工程师'
        person.job.salary = '55K'
        person.job.a.b.c = 322
        person.hobby[0]='学VUE'
      }

      return {
        person,
        changeInfo
      }

    }
}
</script>

<style>

</style>