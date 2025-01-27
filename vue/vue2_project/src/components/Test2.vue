<template>
  <div>
    <button @click="isShow = !isShow">显示/隐藏</button>
    <transition-group name="nullnull" appear>
      <h1 v-show="isShow" key="1">你好啊</h1>
      <h1 v-show="!isShow" key="2">天黑了，睡觉</h1>
    </transition-group>
  </div>
</template>

<script>
export default {
  name: "Test",
  data() {
    return {
      isShow: true,
    };
  },
};
</script>

<style scoped>
h1 {
  background-color: orange;
}

/* 进入的起点、离开的终点 */
.nullnull-enter,
.nullnull-leave-to {
  transform: translateX(-100%);
}
.nullnull-enter-active,
.nullnull-leave-active {
  transition: 0.5s linear;
}
/* 进入的终点、离开的起点 */
.nullnull-enter-to,
.nullnull-leave {
  transform: translateX(0);
}
</style>