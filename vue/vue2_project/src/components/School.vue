<template>
    <div>
        <h2 @click="showName">学校名称: {{name}}</h2>
        <h2>学校地址: {{address}}</h2>
    </div>
</template>

<script>
//局部混合
import {hunhe,hunhe2} from '../mixin'

export default {
    name: "School",
    data() {
        return {
            name: '上海交大',
            address: '闵行'
        }
    },
    //局部混入
    mixins:[hunhe,hunhe2]
}
</script>