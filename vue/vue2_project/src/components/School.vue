<template>
    <div>
        <h2>学校名称: {{name | mySlice}}</h2>
        <h2>学校地址: {{address}}</h2>
        <button @click="onTest">测试一下</button>
    </div>
</template>

<script>
export default {
    name: "School",
    data() {
        return {
            name: '上海交大某某校区',
            address: '闵行'
        }
    },
    methods:{
        onTest(){
            this.testHello();
        }
    }
}
</script>