<template>
    <div>
        <h1>{{msg}}</h1>
        <h2>姓名: {{name}}</h2>
        <h2>年龄: {{age}}</h2>
        <h2>性别: {{sex}}</h2>
        <button @click="updateMsg">点我修改用户信息</button>
    </div>
</template>

<script>
export default {
    name: "Student",
    data() {
        return {
            msg: '这是一个学生的信息'
        }
    },
    methods: {
        updateMsg(){
            console.log('update');
        }
    }
}
</script>

<style>

</style>