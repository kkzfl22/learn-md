<template>
  <div class="category">
    <h3>{{ title }}</h3>
    <!-- 通过子组件向父组件App传递内容 -->
    <slot :games="games" msg="hello22">插槽默认值,没有为插槽指定数据时，此将显示</slot>
  </div>
</template>

<script>
export default {
  name: "Category",
  props: ["title"],
  data() {
    return {
      games: ["和平精英", "穿越火线", "魔兽世界", "CS"],
    };
  },
};
</script>

<style scoped>
.category {
  background-color: skyblue;
  width: 200px;
  height: 300px;
}
h3 {
  text-align: center;
  background-color: orange;
}
video {
  width: 100%;
}
img {
  width: 100%;
}
</style>
