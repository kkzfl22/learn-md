<template>
  <div>
     <h1 v-text="msg" ref="title"></h1>
     <button ref="btn" @click="showDom">点击显示元素</button>
     <School ref="sch"/>
  </div>
</template>

<script>
//引入组件
import School from './componets/School.vue';


export default {
  name: 'App',
  components: {
    School
  },
  data(){
    return {
      msg: '欢迎学习Vue'
    }
  },
  methods: {
    showDom(){
      //通过ref标签查找元素,类似于document.getElementById
      console.log('title',this.$refs.title);
      console.log('btn',this.$refs.btn);
      console.log('btn',this.$refs.sch);
    }
  },
};
</script>

