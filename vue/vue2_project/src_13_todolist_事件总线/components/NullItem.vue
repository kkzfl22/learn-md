<template>
  <li>
    <label>
      <input type="checkbox" :checked="todoItem.done" @change="checkHandler(todoItem.id)" />
	  <!-- 如下代码也能实现功能，但不推荐，违反了原则，因为修改了props中的值 -->
	  <!-- <input type="checkbox" :checked="todoItem.done" @change="checkHandler(todoItem.id)" /> -->
      <span>{{todoItem.title}}</span>
    </label>
    <button class="btn btn-danger" @click="deleteHandler(todoItem.id)">删除</button>
  </li>
</template>

<script>
export default {
  name: "NullItem",
  //声明接收传递过来的todoItem对象
  // props: ["todoItem","checkedTodoBox","deleteTodoBox"],
  //使用事件总线，无需传递
  props: ["todoItem"],
  methods:{
	checkHandler(id){
		//通知组件将对应的done值取反。
		// this.checkedTodoBox(id);
    //使用全局事件总线触发
    this.$bus.$emit('checkedTodoBox',id)
	},
	deleteHandler(id){
		if(confirm('确定删除数据吗?'))
		{
			//this.deleteTodoBox(id);
      //使用全局事件总线触发
      this.$bus.$emit('deleteTodoBox',id);
		}
	}
  }
};
</script>

<style scoped>
/*item*/
li {
  list-style: none;
  height: 36px;
  line-height: 36px;
  padding: 0 5px;
  border-bottom: 1px solid #ddd;
}

li label {
  float: left;
  cursor: pointer;
}

li label li input {
  vertical-align: middle;
  margin-right: 6px;
  position: relative;
  top: -1px;
}

li button {
  float: right;
  display: none;
  margin-top: 3px;
}

li:before {
  content: initial;
}

li:last-child {
  border-bottom: none;
}

li:hover {
  background-color: #ddd;
}

/* 鼠标悬浮时进行显示。 */
li:hover button {
  display: block;
}
</style>