<template>
    <div class="todo-header">
        <input type="text" placeholder="请输入你的任务名称，按回车键确认" @keyup.enter="userInput"/>
    </div>
</template>

<script>
import {nanoid} from 'nanoid'

export default {
    name: 'NullHeader',
    //使用自定义事件无需再接收参数
    //props: ["addTodoItem"],
    methods: {
        userInput(event){
            //校验数据不能为空
            if(!event.target.value.trim())
            {
                alert('输入不能为空');
                return;
            }
            //将用户输入的信息包装成一个todo对象
            const todoObj = {id: nanoid(),title: event.target.value,done:false};
            //此时必须使用this，才是vc对象。调用receive方法
            //通过APP组件添加一个数据
            //此时不需要再调用添加方法，触发一个事件即可
            //this.addTodoItem(todoObj);
            this.$emit('addTodoItem',todoObj);
            //清空输入
            event.target.value = '';
        }
    }
}
</script>

<style scoped>
	/*header*/
	.todo-header input {
		width: 560px;
		height: 28px;
		font-size: 14px;
		border: 1px solid #ccc;
		border-radius: 4px;
		padding: 4px 7px;
	}

	.todo-header input:focus {
		outline: none;
		border-color: rgba(82, 168, 236, 0.8);
		box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 8px rgba(82, 168, 236, 0.6);
	}
</style>