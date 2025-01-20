<template>
  <div class="demo">
    <h2>学校名称: {{ name }}</h2>
    <h2>学校地址：{{ address }}</h2>
  </div>
</template>

<script>
import pubsub from "pubsub-js";
export default {
  name: "School",
  data() {
    return {
      name: "交大",
      address: "上海闵行",
    };
  },
  mounted(){
   this.pubId =  pubsub.subscribe('hello',(msgName,data)=>{
      console.log(this);
      console.log('有人发布了hello事件，执行了subscribe方法',msgName,data);
    })
  },
  beforeDestory(){
    //当组件销毁时，解绑掉注册的事件
    pubsub.unsubscribe(this.pubId);
  }
};
</script>

<style scoped>
.demo {
  background-color: skyblue;
}
</style>
