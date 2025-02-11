<template>
  <section class="jumbotron">
    <h3 class="jumbotron-heading">Search Github Users</h3>
    <div>
      <input
        type="text"
        v-model="keyWord"
        placeholder="enter the name you search"
      />&nbsp;
      <button @click="searchUser">Search</button>
    </div>
  </section>
</template>

<script>
import axios from "axios";
export default {
  name: "Search",
  data() {
    return {
      keyWord: "",
    };
  },
  methods: {
    searchUser() {
        console.log('request key:',this.keyWord);
        this.$bus.$emit('updateListData',{isLoading:true,errorMsg:'',users:[],isFirst:false})
        //进行请求无程数据加载到列表中
        axios.get(`https://api.github.com/search/users?q=${this.keyWord}`).then(
            response => {
                console.log('请求成功了',response.data);
                this.$bus.$emit('updateListData',{isLoading:false,errorMsg:'',users:response.data.items,isFirst:false});
            },
            error=>{
                console.log('请求失败了',error);
                this.$bus.$emit('updateListData',{isLoading:false,errorMsg:error.message,users:[],isFirst:false});
            }
        )
    },
  },
};
</script>

<style>
</style>