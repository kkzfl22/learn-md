<template>
  <div class="demo">
    <h2 class="title">学校名称: {{ name }}</h2>
    <h2>{{ address }}</h2>
  </div>
</template>

<script>
export default {
  name: "School",
  data() {
    return {
      name: "交大",
      address: "上海闵行",
    };
  },
};
</script>

<style scoped>
  /* 样式冲突，在不同的组件中使用相同的样式名就会导致样式冲突 */
  .demo {
    background-color: skyblue;
  }
  .title{
    background-color: blue
}
</style>