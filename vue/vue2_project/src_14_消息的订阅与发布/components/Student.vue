<template>
  <div class="demo">
    <h2 class="title">学生姓名:{{ name }}</h2>
    <h2>姓名:{{ sex }}</h2>
    <button @click="sendStudentName">把学生名称交给School组件</button>
  </div>
</template>

<script>
//  npm i pubsub-js 安装
import pubsub from 'pubsub-js'
export default {
  name: "Student",
  data() {
    return {
      name: "nullnull",
      sex: "男",
    };
  },
  methods:{
    sendStudentName(){
      pubsub.publish('hello',123);
    }
  }
};
</script>

<style scoped>
.demo {
  background-color: pink;
}
</style>