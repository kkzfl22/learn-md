<template>
  <!-- 使用数据集的大小，非0即为true -->
  <div class="todo-footer" v-show="countTotal">
    <label>
      <!-- <input type="checkbox" :checked="isAllChecked" @change="checkAll" /> -->
      <input type="checkbox" v-model="isAllChecked" />
    </label>
    <span>
      <span>已完成{{ countDoneNum }}</span> / 全部{{ countTotal }}
    </span>
    <button class="btn btn-danger" @click="cleanAll">清除已完成任务</button>
  </div>
</template>

<script>
export default {
  name: "NullFooter",
  //此时数据还是需要传递的，但方法无需再传递
  // props: ["todos", "checkAllOrNot","cleanFinish"],
  props: ["todos"],
  computed: {
    countTotal() {
      return this.todos.length;
    },
    countDoneNum: {
      get() {
        let num = 0;
        this.todos.forEach((item) => {
          if (item.done) {
            num++;
          }
        });

        return num;
      },
    },
    isAllChecked: {
      get() {
        return this.countTotal == this.countDoneNum && this.countTotal > 0;
      },
      //set在传递时，值就为true或者false，表示选择与未选择
      set(value) {
        //使用自定义事件调用
        // this.checkAllOrNot(value);
        this.$emit('checkAllOrNot', value);
      },
    },
  },
  methods: {
    // 此使用计算属性来实现更为便捷
    // checkAll(e)
    // {
    //   this.checkAllOrNot(e.target.checked);
    // }
    cleanAll() {
      //使用自定义事件来改写
      // this.cleanFinish();
      this.$emit('cleanFinish');
    },
  },
};
</script>

<style scoped>
/*footer*/
.todo-footer {
  height: 40px;
  line-height: 40px;
  padding-left: 6px;
  margin-top: 5px;
}

.todo-footer label {
  display: inline-block;
  margin-right: 20px;
  cursor: pointer;
}

.todo-footer label input {
  position: relative;
  top: -1px;
  vertical-align: middle;
  margin-right: 5px;
}

.todo-footer button {
  float: right;
  margin-top: 5px;
}
</style>
