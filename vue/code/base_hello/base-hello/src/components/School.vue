<template>
  <div class="school-style">
    <h2>学校名称：{{ name }}</h2>
    <h2>学校地址：{{ address }}</h2>
    <button @click="showName">点我提示学校信息</button>
  </div>
</template>

<script>
export default {
  name: "SchoolData",
  data() {
    return {
      name: "nullnull",
      address: "上海"
    };
  },
  methods: {
    showName() {
      alert("学校名称:" + this.name);
    },
  },
};
</script>

<style>
.school-style {
  background-color: blue;
}
</style>



