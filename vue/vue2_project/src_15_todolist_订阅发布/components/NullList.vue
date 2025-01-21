<template>
  <ul class="todo-main">
    <!-- <NullItem
      v-for="todoItem in todos"
      :key="todoItem.id"
      :todoItem="todoItem"
      :checkedTodoBox="checkedTodoBox"
	  :deleteTodoBox="deleteTodoBox"
    /> -->

    <!-- 使用事件总线后，无需传递 -->
    <NullItem
      v-for="todoItem in todos"
      :key="todoItem.id"
      :todoItem="todoItem"
    />
  </ul>
</template>

<script>
import NullItem from "./NullItem.vue";

export default {
  name: "NullFooter",
  components: { NullItem },
  //使用事件总线后，无需通过props传递
  // props: ["todos", "checkedTodoBox","deleteTodoBox"],
  props: ["todos"],
};
</script>

<style scoped>
/*main*/
.todo-main {
  margin-left: 0px;
  border: 1px solid #ddd;
  border-radius: 2px;
  padding: 0px;
}

.todo-empty {
  height: 40px;
  line-height: 40px;
  border: 1px solid #ddd;
  border-radius: 2px;
  padding-left: 5px;
  margin-top: 10px;
}
</style>