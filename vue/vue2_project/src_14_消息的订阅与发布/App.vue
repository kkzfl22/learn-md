<template>
  <div>
    <h1 class="title">你好啊</h1>
    <School/>
    <hr/>
    <Student/>
  </div>
</template>

<script>

// 由引入的顺序，在冲突时使用哪个样式，后引入的覆盖前面的
import Student from './components/Student.vue'
import School from './components/School.vue'

export default {
    name: 'App',
    components:{
        School,
        Student
    }
}
</script>

<style>
    .title{
        color: red
    }
</style>