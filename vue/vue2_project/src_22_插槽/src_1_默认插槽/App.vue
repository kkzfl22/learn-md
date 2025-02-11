<template>
  <div  class="container">
    <Category title="美食">
      <img src="https://avatars.githubusercontent.com/u/13393021?v=4" />
    </Category>

    <Category title="游戏">
      <ul>
        <li v-for="(g, index) in games" :key="index">{{ g }}</li>
      </ul>
    </Category>

    <Category title="电影">
      <video
        controls
        src="https://vdept3.bdstatic.com/mda-rbahmp4f4z4sdxc8/cae_h264/1739278704766346239/mda-rbahmp4f4z4sdxc8.mp4?v_from_s=hkapp-haokan-nanjing&auth_key=1739291096-0-0-63c487c7778033283310fe6c25c0e288&bcevod_channel=searchbox_feed&pd=1&cr=0&cd=0&pt=3&logid=1496735763&vid=6626823136837992546&klogid=1496735763&abtest=132219_1"
      ></video>
    </Category>
  </div>
</template>

<script>
import axios from "axios";
import Category from "./components/Category";

export default {
  name: "App",
  components: { Category },
  data() {
    return {
      foods: ["火锅", "烧烤", "小龙虾", "牛排"],
      games: ["红色警戒", "穿越火线", "劲舞团", "超级玛丽"],
      films: ["《变形金钢》", "《拆弹专家》", "《战狼》", "《哪吒》"],
    };
  },
};
</script>

<style scoped>
	.container{
		display: flex;
		justify-content: space-around;
	}
</style>
