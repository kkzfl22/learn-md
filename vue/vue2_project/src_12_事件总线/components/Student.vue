<template>
  <div class="demo">
    <h2>学生姓名:{{ name }}</h2>
    <h2>学生性别:{{ sex }}</h2>
    <button @click="sendStudentName">把学生名称传递给School组件</button>
  </div>
</template>

<script>
export default {
  name: "Student",
  data() {
    return {
      name: "nullnull",
      sex: "男",
    };
  },
  methods: {
    sendStudentName() {
      //将当前的学生名称传递给事件总线
      this.$bus.$emit('hello',this.name);
    },
  },
};
</script>

<style scoped>
.demo {
  background-color: pink;
}
</style>
