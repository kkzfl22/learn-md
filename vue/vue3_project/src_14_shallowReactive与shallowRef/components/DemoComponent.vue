<template>
  <h4>当前经x,y{{x.y}}</h4>
  <button @click="x={y:888}">点我替换x</button>
  <button @click="x.y++">点我x.y++</button>
  <hr/>
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
import {   reactive,  shallowRef,  toRefs } from 'vue'
// import {ref,reactive,toRef,toRefs,shallowReactive,shallowRef} from 'vue'
export default {
  name: "DemoWatch",
  setup() {
    //只处理对象最外层属性的响应式（浅响应式）。
    // let person = shallowReactive({
    let person = reactive({
      name: "张三",
      age: 18,
      job: {
        j1: {
          salary: 20,
        },
      },
    });

  //只处理基本数据类型的响应式, 不进行对象的响应式处理。
    let x= shallowRef({
      y: 0
    })

    console.log('....',x)


    return {
      x,
      person,
      ...toRefs(person)
    };
  },
};
</script>

<style></style>
