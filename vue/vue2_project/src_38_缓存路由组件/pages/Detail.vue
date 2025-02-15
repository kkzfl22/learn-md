<template>
  <ul>
    <!-- <li>消息编号： {{$route.params.id}}</li> -->
    <!-- <li>消息标题： {{$route.params.title}}</li> -->
    <li>消息编号： {{id}}</li>
    <li>消息标题： {{title}}</li>
    <li>其他参数： {{a}},{{b}}</li>
  </ul>
</template>

<script>
export default {
    name: 'Detail',
    props: ['id','title','a','b'],
    		computed: {
			// id(){
			// 	return this.$route.query.id
			// },
			// title(){
			// 	return this.$route.query.title
			// },
		},
		mounted() {
			// console.log(this.$route)
		},
}
</script>