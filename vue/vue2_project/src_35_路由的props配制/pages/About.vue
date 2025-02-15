<template>
  <h2>我是About的内容</h2>
</template>

<script>
export default {
  name: "About",
  beforeDestroy() {
    console.log('About组件即将被销毁了');
  },
  mounted(){
    console.log('Abount组件挂载完毕了',this);
    window.abountRoute = this.$route;
    window.abountRouter = this.$router;
  }
};
</script>
