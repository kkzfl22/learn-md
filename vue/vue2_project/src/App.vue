<template>
  <div id="root">
    <button @click="getStudents">获取学生信息</button>
    <button @click="getCars">获取汽车信息</button>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "App",
  methods:{
    getStudents(){
      //此处代理还是本地的端口
      axios.get('http://localhost:8080/stu/students').then(
        response => {
          console.log('请求成功了:',response);
        },
        error=>{
          console.log('请求失败了:',error.message);
        }
      )
    },
    getCars(){
      axios.get('http://localhost:8080/cs/cars').then(
        response => {
          console.log('请求成功了',response.data);
        },
        error=>{
          console.log('请求失败了:',error.message);
        }
      )
    }
  }
};
</script>
