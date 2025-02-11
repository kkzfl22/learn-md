<template>
  <div id="root">
    <Test />
    <hr />
    <Test2 />
    <hr />
    <hr />
    <Test3 />
  </div>
</template>

<script>
import Test from "./components/Test.vue";
import Test2 from "./components/Test2.vue";
import Test3 from "./components/Test3.vue";

export default {
  name: "App",
  components: { Test, Test2,Test3 },
};
</script>
