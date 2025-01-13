<template>
    <div>
        <h2 @click="showName">姓名: {{name}}</h2>
        <h2>性别: {{sex}}</h2>
    </div>
</template>

<script>
//局部混合
import {hunhe,hunh2} from '../mixin'

export default {
    name: "Student",
    data() {
        return {
            name: 'nullnull',
            sex: '男'
        }
    },
     //局部混入
    mixins:[hunhe,hunhe2]
}
</script>