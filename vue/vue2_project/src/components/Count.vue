<template>
  <div>
    <h1>{{ $store.state.sum }}</h1>
    <select v-model.number="n">
      <option value="1">1</option>
      <option value="2">2</option>
      <option value="3">3</option>
    </select>
    <button @click="increment">+</button>
    <button @click="decrement">-</button>
    <button @click="incrementOdd">当前求和为奇数再加</button>
    <button @click="incrementWait">等一等再加</button>
  </div>
</template>

<script>
export default {
  name: 'Count',
  data() {
    return {
      //用户选择的数字
      n: 1,
      //求和
      sum: 0
    }
  },
  methods: {
    increment() {
        this.$store.dispatch('increment',this.n);
    },
    decrement() {
      this.$store.commit('DECREMENT',this.n);
    },
    incrementOdd() {
      this.$store.dispatch('incrementOdd',this.n);
    },
    incrementWait() {
      this.$store.dispatch('incrementWait',this.n);
    },
  },
};
</script>

<style lang="css">
button {
  margin-left: 5px;
}
</style>