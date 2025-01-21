<template>
  <li>
    <label>
      <input
        type="checkbox"
        :checked="todoItem.done"
        @change="checkHandler(todoItem.id)"
      />
      <!-- 如下代码也能实现功能，但不推荐，违反了原则，因为修改了props中的值 -->
      <!-- <input type="checkbox" :checked="todoItem.done" @change="checkHandler(todoItem.id)" /> -->
      <span v-show="!todoItem.isEdit">{{ todoItem.title }}</span>
      <!-- ref="inputTitle" 引用标识会生成一个引用的ID -->
      <input
        type="text"
        v-show="todoItem.isEdit"
        :value="todoItem.title"
        @blur="handleBlur(todoItem,$event)"
        @keyup.enter="handleBlur(todoItem,$event)"
        ref="inputTitle"
      />
    </label>
    <button class="btn btn-danger" @click="deleteHandler(todoItem.id)">
      删除
    </button>
    <button class="btn btn-edit" @click="editHandler(todoItem)">编辑</button>
  </li>
</template>

<script>
import pubsub from "pubsub-js";
export default {
  name: "NullItem",
  //声明接收传递过来的todoItem对象
  // props: ["todoItem","checkedTodoBox","deleteTodoBox"],
  //使用事件总线，无需传递
  props: ["todoItem"],
  methods: {
    checkHandler(id) {
      //通知组件将对应的done值取反。
      // this.checkedTodoBox(id);
      //使用全局事件总线触发
      this.$bus.$emit("checkedTodoBox", id);
    },
    deleteHandler(id) {
      if (confirm("确定删除数据吗?")) {
        //this.deleteTodoBox(id);
        //使用全局事件总线触发
        // this.$bus.$emit('deleteTodoBox',id);
        //使用pubsub来操作
        pubsub.publish("deleteTodoBox", id);
      }
    },
    // 编辑
    editHandler(todo) {
      //检查是否包含isEdit属性，如果当前包含，设置设置为可以编辑
      if (todo.hasOwnProperty("isEdit")) {
        todo.isEdit = true;
      } else {
        this.$set(todo, "isEdit", true);
      }
      this.$nextTick(function(){
        this.$refs.inputTitle.focus();
      })
    },
    //失去焦点回调，执行真正修改逻辑
    handleBlur(todoItem, event) {
      todoItem.isEdit = false;
      if (!event.target.value.trim()) {
        return alert("输入不能为空");
      }
      this.$bus.$emit('updateTodo',todoItem.id,event.target.value);
    },
  },
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