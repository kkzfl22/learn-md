<template>
  <div id="root">
    <div class="todo-container">
      <div class="todo-wrap">
        <!-- 将一个函数传递给子组件 -->
        <!-- <NullHeader :addTodoItem="addTodoItem" /> -->
        <!-- 使用自定义事件改写 -->
        <NullHeader @addTodoItem="addTodoItem" />
        <NullList
          :todos="todos"
          :checkedTodoBox="checkedTodoBox"
          :deleteTodoBox="deleteTodoBox"
        />
        <!-- <NullFooter
          :todos="todos"
          :checkAllOrNot="checkAllOrNot"
          :cleanFinish="cleanFinish"
        /> -->
        <!-- 使用自定义事件改写  -->
        <NullFooter
          :todos="todos"
          @checkAllOrNot="checkAllOrNot"
          @cleanFinish="cleanFinish"
        />
      </div>
    </div>
  </div>
</template>

<script>
import NullHeader from "./components/NullHeader.vue";
import NullList from "./components/NullList.vue";
import NullFooter from "./components/NullFooter.vue";

export default {
  name: "App",
  components: { NullHeader, NullList, NullFooter },
  data() {
    return {
      //将数据存储至localStorage中
      todos: JSON.parse(localStorage.getItem("todos")) || [],
    };
  },
  methods: {
    addTodoItem(todoItem) {
      this.todos.unshift(todoItem);
    },
    checkedTodoBox(id) {
      console.log("调用了APP中的checkedTodoBox", id);
      this.todos.forEach((item) => {
        if (item.id === id) {
          item.done = !item.done;
        }
      });
    },
    deleteTodoBox(id) {
      this.todos = this.todos.filter((item) => {
        return item.id != id;
      });
    },
    checkAllOrNot(done) {
      this.todos.forEach((item) => {
        item.done = done;
      });
    },
    cleanFinish() {
      this.todos = this.todos.filter((item) => {
        return !item.done;
      });
    },
  },
  watch: {
    // 当检测到数据改变时，将数据进行保存至localStorage操作
    todos: {
      deep: true,
      handler(value) {
        localStorage.setItem("todos", JSON.stringify(value));
      },
    },
  },
};
</script>

<style>
/*base*/
body {
  background: #fff;
}
.btn {
  display: inline-block;
  padding: 4px 12px;
  margin-bottom: 0;
  font-size: 14px;
  line-height: 20px;
  text-align: center;
  vertical-align: middle;
  cursor: pointer;
  box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
  border-radius: 4px;
}
.btn-danger {
  color: #fff;
  background-color: #da4f49;
  border: 1px solid #bd362f;
}
.btn-danger:hover {
  color: #fff;
  background-color: #bd362f;
}
.btn:focus {
  outline: none;
}
.todo-container {
  width: 600px;
  margin: 0 auto;
}
.todo-container .todo-wrap {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
}
</style>
