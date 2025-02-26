<template>
    <DemoComp @hello="showHelloMsg" msg="你好啊" school="交大">
        <!-- 在Vue3中，建议使用v-slot:qwe的方式指定名称，否则会报错 -->
        <template v-slot:qwe>
            <span>上海</span>
        </template>
        <template v-slot:asd>
            <span>闵行</span>
        </template>
    </DemoComp>
</template>

<script>
import DemoComp from './components/Demo'
export default {
  name: 'App',
  components: {DemoComp},
  beforeCreate(){
    console.log('---beforeCreate---执行了')
  },
  setup(){
      console.log('---setup初始化了...',this)

    function showHelloMsg(value){
      alert(`你好啊,你触发了hello事件，我收到的参数是${value}`)
    }
    
    return {
      showHelloMsg
    }
  }
}
</script>

<style>

</style>