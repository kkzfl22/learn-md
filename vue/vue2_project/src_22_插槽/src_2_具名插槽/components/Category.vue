<template>
<div class="category">
  <h3>{{title}}</h3>
  <!-- 定义一个插槽，等着组件的使用进行填充 -->
  <slot name="center">插槽默认值,没有为插槽指定数据时，此将显示</slot>
  <slot name="footer">插槽默认值,没有为插槽指定数据时，此将显示</slot>
</div>
</template>

<script>
export default {
  name: "Category",
  props:["title"]
};
</script>

<style scoped>
	.category{
		background-color: skyblue;
		width: 200px;
		height: 300px;
	}
	h3{
		text-align: center;
		background-color: orange;
	}
	video{
		width: 100%;
	}
	img{
		width: 100%;
	}
</style>