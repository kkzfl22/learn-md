<template>
  <ul class="todo-main">
    <NullItem
      v-for="todoItem in todos"
      :key="todoItem.id"
      :todoItem="todoItem"
      :checkedTodoBox="checkedTodoBox"
	  :deleteTodoBox="deleteTodoBox"
    />
  </ul>
</template>

<script>
import NullItem from "./NullItem.vue";

export default {
  name: "NullFooter",
  components: { NullItem },
  props: ["todos", "checkedTodoBox","deleteTodoBox"],
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