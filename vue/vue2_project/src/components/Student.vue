<template>
  <div class="student">
    <h2>学生姓名:{{ name }}</h2>
    <h2>学生姓别:{{ sex }}</h2>
    <button @click="sendStudentName">把学生名发送给App</button>
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
      this.$emit("userClick", this.name, 111, 222, 333);
    },
  },
};
</script>

<style scoped>
.student {
  background-color: pink;
  padding: 5px;
  margin-top: 30px;
}
</style>