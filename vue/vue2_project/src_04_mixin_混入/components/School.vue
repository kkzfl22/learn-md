<template>
    <div>
        <h2 @click="showName">学校名称: {{name}}</h2>
        <h2>学校地址: {{address}}</h2>
    </div>
</template>

<script>
//局部混合
//import {hunhe,hunhe2} from '../mixin'

export default {
    name: "School",
    data() {
        return {
            name: '上海交大',
            //当属性出现了局部与全局混合时，局部优先
            address: '闵行'
        }
    },
    //需要注意的是mounteds这类的生命周期的方法，不管是局部，还是全局都会调用
    mounted() {
        console.log("学校的mounted")
    },
    //局部混合
   // mixins:[hunhe,hunhe2]
}
</script>