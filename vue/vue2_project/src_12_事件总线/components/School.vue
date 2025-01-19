<template>
  <div class="demo">
    <h2>学校名称: {{ name }}</h2>
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
  mounted(){
    this.$bus.$on('hello',(data)=>{
      console.log('我是School组件，收到了数据',data);
    })
  },
  beforeDestroy(){
    //当组件销毁时，自动解绑当前组件所用的事件。
    this.$bug.$off('hello');
  }
};
</script>

<style scoped>
.demo {
  background-color: skyblue;
}
</style>
