<template>
    <div>
        <h1>{{msg}}</h1>
        <h2>姓名: {{name}}</h2>
        <h2>年龄: {{myAge}}</h2>
        <h2>性别: {{sex}}</h2>
        <button @click="updateMsg">点我修改年龄</button>
    </div>
</template>

<script>
export default {
    name: "Student",
    data() {
        return {
            msg: '这是一个学生的信息',
            myAge: this.age
        }
    },
    methods: {
        updateMsg(){
            console.log('update');
            this.myAge += 1;
        }
    },
    //1. 简单声明接收
    //props: ['name','age','sex']
    //2. 接收的同时限制类型
    // props:{
    //   name: String,
    //   age: Number,
    //   sex: String  
    // }
    //3, 接收的同时对数据类型、默认值、必要性进行限制
    props:{
        name: {
            //指定name的类型为字符串
            type:String,
            //指定name是必须的
            required:true
        },
        age:{
            type:Number,
            //指定默认值为19
            default: 19
        },
        sex:{
            type:String,
            required:true
        }
    }
    
}
</script>