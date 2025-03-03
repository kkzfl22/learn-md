<template>
  <h4>当前求和为: {{ sum }}</h4>
  <button @click="sum++">点我++</button>
  <hr />
  <h4>{{ person }}</h4>
  <h2>姓名 {{ name }}</h2>
  <h2>年龄 {{ age }}</h2>
  <!-- 使用toRef包装 -->
  <!-- <h2>薪资2: {{ salary }}K</h2> -->
  <h2>薪资2: {{ job.j1.salary }}K</h2>
  <button @click="name += '~'">修改姓名</button>
  <button @click="age++">增长年龄</button>
  <button @click="job.j1.salary++">涨薪</button>
  <hr />
</template>

<script>
import { ref, reactive, toRefs, shallowReadonly } from "vue";
// import {ref,reactive,toRef,toRefs,shallowReactive,shallowRef} from 'vue'
export default {
  name: "DemoWatch",
  setup() {
    //只处理对象最外层属性的响应式（浅响应式）。
    // let person = shallowReactive({
    let sum = ref(0);
    let person = reactive({
      name: "张三",
      age: 18,
      job: {
        j1: {
          salary: 20,
        },
      },
    });

    //让响应式对象变成只读,让一个响应式数据变为只读的（深只读）。
    // person = readonly(person);
    //让一个响应式数据变为只读的（浅只读）。涨薪还可以改
    // person = shallowReadonly(person);
    // sum = readonly(sum)
    // sum = shallowReadonly(sum);

    return {
      sum,
      person,
      ...toRefs(person),
    };
  },
};
</script>

<style></style>
