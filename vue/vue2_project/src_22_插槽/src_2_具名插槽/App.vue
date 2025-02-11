<template>
  <div  class="container">
    <Category title="美食">
      <img slot="center" src="https://s3.ax1x.com/2021/01/16/srJlq0.jpg" />
      <a slot="footer" href="https://s3.ax1x.com/2021/01/16/srJlq0.jpg">更多美食</a>
    </Category>

    <Category title="游戏">
      <ul slot="center">
        <li v-for="(g, index) in games" :key="index">{{ g }}</li>
      </ul>
      <div class="foot" slot="footer">
          <a href="https://s3.ax1x.com/2021/01/16/srJlq0.jpg">单机游戏</a>
          <a href="https://s3.ax1x.com/2021/01/16/srJlq0.jpg">网络游戏</a>
      </div>
    </Category>

    <Category title="电影">
      <video
        slot="center"
        controls
        src="https://vdept3.bdstatic.com/mda-rbahmp4f4z4sdxc8/cae_h264/1739278704766346239/mda-rbahmp4f4z4sdxc8.mp4?v_from_s=hkapp-haokan-nanjing&auth_key=1739291096-0-0-63c487c7778033283310fe6c25c0e288&bcevod_channel=searchbox_feed&pd=1&cr=0&cd=0&pt=3&logid=1496735763&vid=6626823136837992546&klogid=1496735763&abtest=132219_1"
      ></video>
    <template v-slot:footer>
        <div class="foot">
            <a href="https://s3.ax1x.com/2021/01/16/srJlq0.jpg">经典</a>
            <a href="https://s3.ax1x.com/2021/01/16/srJlq0.jpg">动作</a>
            <a href="https://s3.ax1x.com/2021/01/16/srJlq0.jpg">爱情</a>
        </div>
        <h4>欢迎前来观看</h4>
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
	.container,.foot {
		display: flex;
		justify-content: space-around;
	}
</style>
