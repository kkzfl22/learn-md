<template>
  <h1>一个人的信息</h1>
  <h2>姓名：{{ person.name }}</h2>
  <h2>年龄: {{ person.age }}</h2>
  <button @click="test">测试触发一个Demo组件的Hello事件</button>
</template>

<script>
import { reactive } from "vue";
export default {
  name: 'DemoComp',
  props: ["msg", "school"],
  //需要声明一下自定义事件
  emits: ["hello"],
  //执行时机，在beforeCreate之前执行一次，this是underfined
  setup(props, context) {
    // 将传递的props变为一个Proxy对象Proxy(Object)格式如：{msg: 你好啊, school: 交大}
    console.log("--setup--props:", props);
    console.log("--setup--context:", context);
    //$attrs用于在props未接收的数据存放位置，捡漏
    console.log("--setup---context.attrs", context.attrs); //相关于vue2中的$attrs
    console.log("--setup---context.emit", context.emit); //相当于自定义事件
    console.log("--setup---context.slots", context.slots); //插槽

    //数据
    let person = reactive({
      name: "张三",
      age: 18,
    });

    //方法
    function test() {
      context.emit("hello", 666);
    }

    //返回一个对象
    return {
      person,
      test,
    };
  },
  mounted(){
    console.log('Demo',this)
  }
};
</script>

<style></style>
