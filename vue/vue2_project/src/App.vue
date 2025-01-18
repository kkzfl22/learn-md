<template>
  <div class="app">
    <h1>{{ msg }},学生是: {{ studentName }}</h1>

    <!-- 方式1：通过父组件组子组件传递函数类型的props实现，子给父传递数据 -->
    <School :getSchoolName="getSchoolName" />

    <hr />
    <!-- 方式2：通过父组件绑定一个自定义事件实现子给父传递数据（使用@或者v-on） -->
    <!-- <Student v-on:userClick="getStudentName" /> -->
    <!-- <Student @userClick="getStudentName" /> -->

    <!-- 方式3：通过父组件组子组件绑定一个自定义事件实现，子给父传递数据（使用ref） -->
    <Student ref="student" />
  </div>
</template>

<script>
import Student from "./components/Student.vue";
import School from "./components/School.vue";

export default {
  name: "App",
  components: {
    School,
    Student,
  },
  data() {
    return {
      msg: "你好啊!",
      studentName: "空空",
    };
  },
  methods: {
    getSchoolName(name) {
      console.log("App收到了学校名：", name);
    },
    getStudentName(name, ...params) {
      console.log("App收到了学生名", name, params);
    },
  },
  mounted() {
    //绑定自定义事件
    this.$refs.student.$on("userClick", this.getStudentName);
    // 绑定自定义事件（一次性）
    // this.$refs.student.$once("userClick", this.getStudentName);
  },
};
</script>

<style>
.app {
  background-color: gray;
}
</style>