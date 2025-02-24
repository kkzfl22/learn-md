<template>
  <h1>一个人的信息</h1>
  <h2>姓名：{{ name }}</h2>
  <h2>年龄: {{ age }}</h2>
  <h2>姓名: {{ sex }}</h2>
  <h2>a的值是: {{ a2 }}</h2>
  <button @click="sayHello">说话(Vue3所配制的-sayHello)</button>
  <br />
  <br />
  <button @click="sayWelcome">说话2(Vue2所配制的-sayWelcome)</button>
  <br />
  <br />
  <button>测试一下在Vue2的配制中去读取VUE3中的数据、方法</button>
  <br />
  <br />
  <button>测试一下在Vue3的setup配置中去读取Vue2中的数据、方法</button>
</template>

<script>
export default {
  name: "App",
  data() {
    return {
      sex: "男",
      a: 100,
    };
  },
  methods: {
    sayWelcome() {
      alert("欢迎学习Vue3");
    },
    test1() {
      console.log(this.sex);
      console.log(this.name);
      console.log(this.age);
      console.log(this.sayHello);
    },
  },
  //此处仅测试不考滤响应式的问题
  async setup() {
    let name = "张三"
    let age = 18
    let a2 = 200

    //方法
    function sayHello() {
      alert(`我叫${name},我${age}岁了，你好啊`);
    }

    function test2() {
      console.log(name);
      console.log(age);
      console.log(sayHello);
      console.log(this.sex);
      console.log(this.sayWelcome);
    }

    //返回一个对象
    return {
      name,
      age,
      sayHello,
      test2,
      a2
    };
  },
};
</script>

<style></style>
