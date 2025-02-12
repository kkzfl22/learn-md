<template>
  <div>
    <h1>当前的数据求和为：{{ sum }}</h1>
    <h1>当前求和放大10倍为:{{ bigSum }}</h1>
    <h3>我在{{ school }},学习{{ subject }}</h3>
    <select v-model.number="n">
      <option value="1">1</option>
      <option value="2">2</option>
      <option value="3">3</option>
    </select>
    <button @click="INCREMENT(n)">+</button>
    <button @click="DECREMENT(n)">-</button>
    <button @click="incrementOdd(n)">当前求和为奇数再加</button>
    <button @click="incrementWait(n)">等一等再加</button>
  </div>
</template>

<script>
import { mapState, mapGetters, mapMutations, mapActions } from "vuex";
export default {
  name: "Count",
  data() {
    return {
      //用户选择的数字
      n: 1,
    };
  },
  //使用计算属性来展示
  computed: {
    /* sum() {
      return this.$store.state.sum;
    },
    school() {
      return this.$store.state.school;
    },
    subject() {
      return this.$store.state.subject;
    }, */

    //借助mapState生成计算属性，从state中读取数据（对象写法）
    // ...mapState({ sum: "sum", school: "school", subject: "subject" }),
    //借助mapState生成计算属性，从state中读取数据（数组写法）
    ...mapState(["sum", "school", "subject"]),

    /*
     bigSum() {
      return this.$store.getters.bigSum;
    }, */
    //借助mapGetters生成计算属性,从getters中读取数据。（对象写法）
    // ...mapGetters({ bigSum: "bigSum" }),
    //借助mapGetters生成计算属性,从getters中读取数据。（数组写法）
    ...mapGetters(["bigSum"]),
  },
  methods: {
    /* increment() {
      this.$store.commit("INCREMENT", this.n);
    },
    decrement() {
      this.$store.commit("DECREMENT", this.n);
     },
     */
    //借助mapMutaions生成对应的方法，方法中会调用commit去联系mutaions(对象写法),注意参数需要传递
    // ...mapMutations({ increment: "INCREMENT", decrement: "DECREMENT" }),
    //借助mapMutaions生成对应的方法，方法中会调用commit去联系mutaions(数组写法),此需要将调用方法改为INCREMENT和DECREMENT
    ...mapMutations(["INCREMENT", "DECREMENT"]),

    /* incrementOdd() {
      this.$store.dispatch("incrementOdd", this.n);
    },
    incrementWait() {
      this.$store.dispatch("incrementWait", this.n);
    }, */
    //借助mapActions生成对应的方法，方法中会调用dispatch去联系actions(对象写法)
    // ...mapActions({incrementOdd:'incrementOdd',incrementWait:'incrementWait'}),
    //借助mapActions生成对应的方法，方法中会调用dispatch去联系actions(数组写法)
    ...mapActions(["incrementOdd", "incrementWait"]),
  },
};
</script>

<style lang="css">
button {
  margin-left: 5px;
}
</style>
