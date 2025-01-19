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
    <!-- <Student ref="student"/> -->
    <!-- 告诉Vue使用原生的click事件,此时事件就被绑定到了Student最外层的div元素上 -->
    <Student ref="student" @click.native="show" />
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
      studentName: "",
    };
  },
  methods: {
    getSchoolName(name) {
      console.log("App收到了学校名：", name);
    },
    getStudentName(name, ...params) {
      console.log("App收到了学生名", name, params);
      this.studentName = name;
    },
    show() {
      alert("show invoke");
    },
  },
  mounted() {
    //绑定自定义事件
    //this.$refs.student.$on("userClick", this.getStudentName);
    // 绑定自定义事件（一次性）
    // this.$refs.student.$once("userClick", this.getStudentName);

    //使用此方式绑定自定义事件
    //如果采用此方式将不能对studentName属性值进行设置。
    //此时在APP中的This并不是App的VC，此时的VC的对象是Student，
    //用VUE的解释就是谁触发了事件，此是的this就是谁，由于是Student触发了事件，所以此时的VC就是Student。
    // this.$refs.student.$on("userClick", function (name, ...params) {
    //   console.log("App收到了学生名", name, params);
    //   this.studentName = name;
    // });

    //如果使用是箭头函数，由于箭头函数没有this，就会向外找，此时的this就是App对象的VC。
    this.$refs.student.$on("userClick", (name, ...params) => {
      console.log("App收到了学生名2", name, params);
      this.studentName = name;
    });

    //绑定自定义事件,推荐使用此方式，不用考滤箭头函数还是普通 函数的问题
    //如果方法写在了methods ，并且还是普通函数，那此getStudentName的实例对象一定是App的实例对象。
    //this.$refs.student.$on("userClick", this.getStudentName);
  },
};
</script>

<style>
.app {
  background-color: gray;
}
</style>
