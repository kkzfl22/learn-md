<template>
  <ul>
    <li :style="{ opacity }">欢迎学习Vue</li>
    <li>news001 <input type="text" /></li>
    <li>news002 <input type="text" /></li>
    <li>news003 <input type="text" /></li>
  </ul>
</template>

<script>
export default {
  name: "News",
  data() {
    return {
      opacity: 1,
    };
  },
  /* beforeDestroy(){
    //由于存在组件缓存的关系，导致此在组件被切换时，不能被停止，定时器一直在执行，造成浪费。
    console.log('News组件即将被销毁了');
    clearInterval(this.timer);
  },
  mounted(){
    this.timer = setInterval(()=>{
      console.log('@');
      this.opacity -= 0.01;
      if(this.opacity <= 0){
        this.opacity = 1;
      }
    });
  } */

  //组件被激活时
  activated() {
    this.timer = setInterval(() => {
      console.log("@");
      this.opacity -= 0.01;
      if (this.opacity <= 0) {
        this.opacity = 1;
      }
    });
  },
  //组件被切换时，或者失活时
  deactivated() {
    console.log("News组件失活了");
    clearInterval(this.timer);
  },
};
</script>
