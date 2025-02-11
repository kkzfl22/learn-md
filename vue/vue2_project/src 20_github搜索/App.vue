<template>
  <div id="root">
      <Search/>
      <hr/>
      <List/>
  </div>
</template>

<script>
import axios from "axios";
import List from "./components/List";
import Search from "./components/Search";

export default {
  name: "App",
  components: { List, Search },
};
</script>
