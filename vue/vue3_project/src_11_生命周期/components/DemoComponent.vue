<template>
  <h2>当前求和为：{{sum}}</h2>
  <button @click="sum++">点我加1</button>
</template>

<script>
import {onBeforeMount, onBeforeUpdate, onMounted, onUpdated,onBeforeUnmount,onUnmounted, ref} from 'vue'
export default {
  name: 'DemoComponent',
  setup(){
    console.log('----setup----');
    //数据
    let sum = ref(0);

    //通过组合式API使用生命周期钩子
    onBeforeMount(()=>{
      console.log('----onBeforeMount----');
    });
    onMounted(()=>{
      console.log('----onMounted----');
    });
    onBeforeUpdate(()=>{
      console.log('----onBeforeUpdate----');
    });
    onUpdated(()=>{
      console.log('----onUpdated----');
    });
    onBeforeUnmount(()=>{
      console.log('----onBeforeUnmount----');
    });
    onUnmounted(()=>{
      console.log('----onUnmounted----');
    });

    return {
      sum
    }
  },
  //通过配制形式使用的生命周期钩子
  beforeCreate() {
			console.log('---beforeCreate---')
		},
		created() {
			console.log('---created---')
		},
		beforeMount() {
			console.log('---beforeMount---')
		},
		mounted() {
			console.log('---mounted---')
		},
		beforeUpdate(){
			console.log('---beforeUpdate---')
		},
		updated() {
			console.log('---updated---')
		},
		beforeUnmount() {
			console.log('---beforeUnmount---')
		},
		unmounted() {
			console.log('---unmounted---')
		},
}
</script>

<style>

</style>