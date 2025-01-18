<template>
  <div class="demo">
    <h2 class="title">学生姓名:{{ name }}</h2>
    <h2>姓名:{{ sex }}</h2>
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
};
</script>

<style scoped>
/* 样式冲突，在不同的组件中使用相同的样式名就会导致样式冲突,添加scoped */
.demo {
  background-color: pink;
}
</style>