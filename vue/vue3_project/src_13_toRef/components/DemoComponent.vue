<template>
  <h2>姓名： {{ person.name }}</h2>
  <h2>年龄: {{ person.age }}</h2>
  <h2>薪资: {{ person.job.j1.salary }}K</h2>
  <button @click="person.name += '~'">修改姓名</button>
  <button @click="person.age++">增长年龄</button>
  <button @click="person.job.j1.salary++">涨薪</button>
  <hr />
  <h4>{{ person }}</h4>
  <h2>姓名2：{{ name }}</h2>
  <h2>年龄2: {{ age }}</h2>
  <!-- 使用toRef包装 -->
  <!-- <h2>薪资2: {{ salary }}K</h2> -->
  <h2>薪资2: {{ job.j1.salary }}K</h2>
</template>

<script>
import { reactive,  toRefs } from "vue";
export default {
  name: "DemoWatch",
  setup() {
    //数据
    let person = reactive({
      name: "张三",
      age: 18,
      job: {
        j1: {
          salary: 20,
        },
      },
    });

    const name1 = person.name;
    console.log("%%%", name1);

    //将一个普通数据转换为响应式数据.
    // const nameRef = toRef(person, "name");
    //ObjectRefImpl{_object: Proxy(Object), _key: 'name', _defaultValue: undefined, __v_isRef: true, _value: undefined}
    // console.log("ref:", nameRef);

    //将对象中的属性都包装成响应式数据
    const nameRef = toRefs(person);
    //将每一个都包装为响应式数据
    console.log("refs:", nameRef);

    return {
      person,
      // //单独返回每个对象,并且是响应式
      // name: toRef(person, "name"),
      // age: toRef(person, "age"),
      // salary: toRef(person.job.j1, "salary"),
      ...toRefs(person)
    };
  },
};
</script>

<style></style>
