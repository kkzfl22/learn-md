<template>
  <h2>当前求和为：{{sum}}</h2>
  <button @click="sum++">点我加1</button>
  <hr/>
  <h2>当前点击鼠标的坐标为:x:{{point.x}},y:{{point.y}}</h2>
</template>

<script>
import { ref} from 'vue'
import usePoint from '../hooks/usePoint'
export default {
  name: 'DemoComponent',
  setup(){
    console.log('----setup----');
    //数据
    let sum = ref(0);
    let point = usePoint();

    return {
      sum,
      point
    }
  },

}
</script>

<style>

</style>