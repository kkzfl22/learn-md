<template>
  <h2>当前的求和信息为: {{ sum }}</h2>
  <button @click="sum++">点我加1</button>
  <hr />

  <h2>当前的信息为: {{ msg }}</h2>
  <button @click="msg += '!'">修改信息</button>
  <hr />

  <h2>姓名： {{ person.name }}</h2>
  <h2>年龄: {{ person.age }}</h2>
  <h2>薪资: {{ person.job.j1.salary }}K</h2>
  <button @click="person.name += '~'">修改姓名</button>
  <button @click="person.age++">增长年龄</button>
  <button @click="person.job.j1.salary++">涨薪</button>
</template>

<script>
import { reactive, ref, watchEffect } from "vue";
export default {
  name: "DemoWatch",
  setup() {
    //数据
    let sum = ref(0);
    let msg = ref("你好啊");
    let person = reactive({
      name: "张三",
      age: 18,
      job: {
        j1: {
          salary: 20,
        },
      },
    });

    // //将收到：sum变了 1 0
    // watch(sum,(newValue,oldValue)=>{
    //   console.log('sum变了',newValue,oldValue)
    // })

    //自动监视使用到的属性，如果被修改了，则收到watch
    watchEffect(() => {
      const x1 = sum.value;
      const x2 = person.job.j1.salary;
      console.log("watchEffect所指定的回调执行了x1:", x1, ",x2:", x2);
    });

    return {
      sum,
      msg,
      person,
    };
  },
};
</script>

<style></style>
