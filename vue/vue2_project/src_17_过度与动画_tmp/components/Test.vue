<template>
  <div>
    <button @click="isShow = !isShow">显示/隐藏</button>
    <transition name="nullnull" appear>
      <h1 v-show="isShow">你好啊</h1>
    </transition>
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
.nullnull-enter-active {
  animation: nullnull 0.5s linear;
}
.nullnull-leave-active {
  animation: nullnull 0.5s linear reverse;
}
@keyframes nullnull {
  from {
    transform: translateX(-100%);
  }
  to {
    transform: translateX(0px);
  }
}
</style>