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
import { ref, reactive, watch } from "vue";
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

    //情况1：监视ref所定义的一个响应式数据,imediate用于控制初始化是否调用
    //将收到：sum变了 1 0
    // watch(sum,(newValue,oldValue)=>{
    //   console.log('sum变了',newValue,oldValue)
    // },{imediate:true})

    //情况2：监视ref所定义的多个响应式数据
    //收到 sum变了(2)[1, '你好啊'](2)[0, '你好啊']
    // watch([sum,msg],(newValue,oldValue)=>{
    //   console.log('sum变了',newValue,oldValue)
    // },{immediate:true})

    //情况3：监视reactive所定义的一个响应式数据的全部属性
    // 1, 注意：无法正确的获取old的数据
    // 2, 注意：强制开启深度监视(deep配制无效)
    // watch(person,(newValue,oldValue)=>{
    //   console.log('sum变了',newValue,'-1111-',oldValue)
    // })

    //情况4：监视reactive所定义的一个响应式数据中的某个属性
    //得到: person.name 张三~ 张三
    // watch(()=>person.name, (newValue, oldValue) => {
    //   console.log("person.name", newValue, oldValue);
    // });

    //情况5：监视reactive所定义的一个响应式数据中的某些属性
    //得到如下两种
    // person.name和age (2)['张三~', 18] (2)['张三', 18]
    // person.name和age (2)['张三~', 19] (2)['张三~', 18]
    // watch([()=>person.name,()=>person.age], (newValue, oldValue) => {
    //   console.log("person.name和age", newValue, oldValue);
    // });
    
    //特殊情况
    //由于此处监视的reactive定义的对象中的某个属性，deep配制有效
    watch(()=>person.job, (newValue, oldValue) => {
      console.log("person.job", newValue, oldValue);
    },{deep:true});




    return {
      sum,
      msg,
      person,
    };
  },
};
</script>

<style></style>
