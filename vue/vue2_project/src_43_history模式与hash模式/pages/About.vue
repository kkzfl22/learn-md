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
  },
  //通过路由规则，进入该组件时被调用
  beforeRouteEnter(to,from,next){
    console.log('About--beforeRouteEnter',to,from);
    if(to.meta.isAuth)
    {
      if(localStorage.getItem('school') === 'test')
      {
        next();
      }
      else{
        alert('学校名称不对，无权限查看');
      }
    }
    else{
      next();
    }
  },
  //通过路由规则，离开该组件时被调用
  beforeRouteLeave(to,from,next){
    console.log('About-beforeRouteLeave',to,from)
    next();
  }
};
</script>
