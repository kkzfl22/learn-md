<template>
  <div class="container">
    <Category title="游戏">
      <template scope="data">
        <ul>
          <li v-for="(g, index) in data.games" :key="index">{{ g }}</li>
        </ul>
        <h3>{{ data.msg }}</h3>
      </template>
    </Category>

    <Category title="游戏">
      <template scope="{games}">
        <ol>
          <li style="color: red" v-for="(g, index) in games" :key="index">{{ g }}</li>
        </ol>
      </template>
    </Category>

    <Category title="游戏">
      <template slot-scope="{ games }">
        <h4 v-for="(g, index) in games" :key="index">{{ g }}</h4>
      </template>
    </Category>
  </div>
</template>

<script>
import axios from "axios";
import Category from "./components/Category";

export default {
  name: "App",
  components: { Category },
};
</script>

<style scoped>
.container,
.foot {
  display: flex;
  justify-content: space-around;
}
</style>
