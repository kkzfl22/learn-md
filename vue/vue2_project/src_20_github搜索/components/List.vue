<template>
  <div class="row" style="">
    <!-- 用户列表展示 -->
    <div
      class="card"
      v-show="users.length"
      v-for="user in users"
      :key="user.login"
    >
      <a :href="user.html_url" target="_blank">
        <img :src="user.avatar_url" style="width: 100px" />
      </a>
      <p class="card-text">{{ user.login }}</p>
    </div>
    <div class="loadcss">
      <!--欢迎词展示-->
      <h1 v-show="isFirst">欢迎使用</h1>
      <!--展示加载中-->
      <h1 v-show="isLoading">加载中</h1>
      <!--展示错误信息-->
      <h1 v-show="errorMsg">{{ errorMsg }}</h1>
    </div>
  </div>
</template>

<script>
export default {
  name: "List",
  data() {
    return {
      isFirst: true,
      isLoading: false,
      errorMsg: "",
      users: [],
    };
  },
  mounted() {
    this.$bus.$on("updateListData", (dataObj) => {
      this.isFirst = dataObj.isFirst;
      this.isLoading = dataObj.isLoading;
      this.errorMsg = dataObj.errorMsg;
      this.users = dataObj.users;
    });
  },
};
</script>

<style scoped>
.album {
  min-height: 50rem; /* Can be removed; just added for demo purposes */
  padding-top: 3rem;
  padding-bottom: 3rem;
  background-color: #f7f7f7;
}

.card {
  float: left;
  width: 33.333%;
  padding: 0.75rem;
  margin-bottom: 2rem;
  border: 1px solid #efefef;
  text-align: center;
}

.card > img {
  margin-bottom: 0.75rem;
  border-radius: 100px;
}

.card-text {
  font-size: 85%;
}

.loadcss {
  margin-left: 25px;
}
</style>