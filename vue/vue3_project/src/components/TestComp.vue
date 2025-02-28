<template>
  <h2>我是Test组件</h2>
  <h2>当前点击鼠标的坐标为:x:{{point.x}},y:{{point.y}}</h2>
</template>

<script>
import usePoint from '../hooks/usePoint'
export default {
  name: 'DemoComponent',
  setup(){
    console.log('----test-setup----');
    //数据
    let point = usePoint();

    return {
      point
    }
  },

}
</script>

<style>

</style>