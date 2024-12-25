# Vue笔记



## 1 .vue简介

### 1.1 官网

```sh
# 英文官网
https://vuejs.org/

# 中文官网
https://cn.vuejs.org/
```

**介绍与描述**

动态构建用户界面的渐进式javascript框架。

作者：尤雨溪

**Vue特点**

遵循MVVM模式

编码简洁，体积小，运行效率高，适合移动/PC端开发。

它本身只关注UI，也可以引入其他第三方库开发项目。



**环境安装**

```sh
Visual Studio Code
需要安装插件live server
# 浏览器安装插件
vue_dev_tools.crx
```



### 1.2 VUE的hello-word

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>初识VUE</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <div id="root">
        <h1>Hello,nullnull</h1>
    </div>
    <script type="text/javascript">
        Vue.config.productionTip = false
    </script>
</body>
</html>
```



打开live server

![image-20241224123728450](.\images\image-20241224123728450.png)



### 1.3 模板语法

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-模板语法</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <div id="root">
        <h1>插件语法</h1>
        <h3>你好: {{name}}</h3>
        <hr/>
        <h1>指令语法</h1>
        <a v-bind:href="employee.url" attribute="你好">点击去{{employee.name}}1</a>
        <a :href="employee.url" :attribute="employee.attribute">点击去{{employee.name}}2</a>
    </div>
    <script type="text/javascript">
        //阻止VUE在启动时生产警告信息
        Vue.config.productionTip = false
        new Vue({
            el:'#root',
            data: {
                name:'nullnull',
                employee: {
                    name: '百度',
                    url: 'https://www.baidu.com',
                    attribute: '属性'
                }
            }
        }
        );
    </script>
</body>
</html>
```

打开live server

打开浏览器显示：

![image-20241224125246351](.\images\image-20241224125246351.png)

查看见面源代码：

```html

<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-模板语法</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <div id="root">
        <h1>插件语法</h1>
        <h3>你好: {{name}}</h3>
        <hr/>
        <h1>指令语法</h1>
        <a v-bind:href="employee.url" attribute="你好">点击去{{employee.name}}1</a>
        <a :href="employee.url" :attribute="employee.attribute">点击去{{employee.name}}2</a>
    </div>
    <script type="text/javascript">
        //阻止VUE在启动时生产警告信息
        Vue.config.productionTip = false
        new Vue({
            el:'#root',
            data: {
                name:'nullnull',
                employee: {
                    name: '百度',
                    url: 'https://www.baidu.com',
                    attribute: '属性'
                }
            }
        }
        );
    </script>
<!-- Code injected by live-server -->
<script>
	// <![CDATA[  <-- For SVG support
	if ('WebSocket' in window) {
		(function () {
			function refreshCSS() {
				var sheets = [].slice.call(document.getElementsByTagName("link"));
				var head = document.getElementsByTagName("head")[0];
				for (var i = 0; i < sheets.length; ++i) {
					var elem = sheets[i];
					var parent = elem.parentElement || head;
					parent.removeChild(elem);
					var rel = elem.rel;
					if (elem.href && typeof rel != "string" || rel.length == 0 || rel.toLowerCase() == "stylesheet") {
						var url = elem.href.replace(/(&|\?)_cacheOverride=\d+/, '');
						elem.href = url + (url.indexOf('?') >= 0 ? '&' : '?') + '_cacheOverride=' + (new Date().valueOf());
					}
					parent.appendChild(elem);
				}
			}
			var protocol = window.location.protocol === 'http:' ? 'ws://' : 'wss://';
			var address = protocol + window.location.host + window.location.pathname + '/ws';
			var socket = new WebSocket(address);
			socket.onmessage = function (msg) {
				if (msg.data == 'reload') window.location.reload();
				else if (msg.data == 'refreshcss') refreshCSS();
			};
			if (sessionStorage && !sessionStorage.getItem('IsThisFirstTime_Log_From_LiveServer')) {
				console.log('Live reload enabled.');
				sessionStorage.setItem('IsThisFirstTime_Log_From_LiveServer', true);
			}
		})();
	}
	else {
		console.error('Upgrade your browser. This Browser is NOT supported WebSocket for Live-Reloading.');
	}
	// ]]>
</script>
</body>
</html>
```

总结：

VUE的模板语法有两大类：

> 1. 插值语法
>
>    功能：用于解析标签体内容。
>
>    写法： {{xxx}} ,xxx是js表达式，且可以直接读取到data中的所有属性
>
> 2. 指令语法
>
>    功能：用于解析标签（包括标签属性、标签内容、绑定事件......）
>
>    例如：v-bind:href="xxx" 或  简写为 :href="xxx"，xxx同样要写js表达式，且可以直接读取到data中的所有属性。				 
>
>    备注：Vue中有很多的指令，且形式都是v-xxx



### 1.4 数据绑定

源码：

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-数据绑定</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <div id="root">
        <!-- 单向数据绑定 -->
        单向数据绑定:<input type="text" v-bind:value="name"/><br/>
        <!-- 双向数据绑定 -->
        双向数据绑定:<input type="text" v-model:value="name"/>

    </div>
    <script type="text/javascript">
        //阻止VUE在启动时生产警告信息
        Vue.config.productionTip = false
        new Vue({
            el: '#root',
            data:{
                name: 'nullnull'
            }
        });
    </script>
</body>
</html>
```

打开live server

网页: http://127.0.0.1:5500/03-databind/vue-03-databind.html

![image-20241225121605792](.\images\image-20241225121605792.png)

在单个绑定的输入框中输入字符，其他数据并不会随着输入变化，而当在双向绑定的框中输入，VUE中信息会跟着变化，而由于单向绑定的框是读取的数据，也会连锁跟着变化。



![image-20241225121736505](.\images\image-20241225121736505.png)

还可以使用简写形式：

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-数据绑定</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <div id="root">
        <!-- 单向数据绑定 -->
        <!-- 单向数据绑定:<input type="text" v-bind:value="name"/><br/> -->
        <!-- 双向数据绑定 -->
        <!-- 双向数据绑定:<input type="text" v-model:value="name"/>  -->

        <!-- 简写 -->
        <!-- 单向数据绑定 -->
        单向数据绑定:<input type="text" :value="name"/><br/>
        <!-- 双向数据绑定 -->
        双向数据绑定:<input type="text" v-model="name"/>

    </div>
    <script type="text/javascript">
        //阻止VUE在启动时生产警告信息
        Vue.config.productionTip = false
        new Vue({
            el: '#root',
            data:{
                name: 'nullnull'
            }
        });
    </script>
</body>
</html>
```

效果与之前一致。

**总结：**

单向数据绑定：

语法，v-bind:href="xxxx"或者简写:href

特点：数据只能从data流向页面

双向数据绑定：

语法：v-model:value="xxx",简写：v-model="xxx"

特点：数据不仅能从data流向页面，还能从页面流向data.

	备注：
	1.双向绑定一般都应用在表单类元素上（如：input、select等）
	2.v-model:value 可以简写为 v-model，因为v-model默认收集的就是value值。





### 1.4.1 El和Data的两种写法

EL的两种写法，可以理解选择与动态选择。

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-EL和Data的两种写法</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <!-- 容器 -->
    <div id="root">
       你好：{{name}}
    </div>
    <script type="text/javascript">
        //阻止VUE在启动时生产警告信息
        Vue.config.productionTip = false
        // el的第一种写法
        // new Vue({
        //     el: '#root',
        //     data:{
        //         name: 'nullnull'
        //     }
        // });
        // el 第二种写法，动态设置
        const v = new Vue({
            // el: '#root',
            data:{
                name: 'nullnull'
            }
        });
        console.log(v)
        v.$mount('#root')

    </script>
</body>
</html>
```

data的两种写法：

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-EL和Data的两种写法</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <!-- 容器 -->
    <div id="root">
       你好：{{name}}
    </div>
    <script type="text/javascript">
        //阻止VUE在启动时生产警告信息
        Vue.config.productionTip = false
        // data 的第一种写法
        new Vue({
            el: '#root',
            data:{
                name: 'nullnull'
            }
        });
        //data 第二种写法，方法式，但方法的返回值须为一个对象
        const v = new Vue({
            el: '#root',
            data:function(){
                //此处的this是Vue实例对象
                console.log('----',this) 
                return {
                    name: "nullnull"
                }
            }
        });

    </script>
</body>
</html>
```

总结：

el有两种写法：

1. new Vue时候配制el属性
2. 先创建Vue实例，随后再通过vm.$mount('#root')，指定el的值

data也有两种写法：

1. 对象式。
2. 函数式。

此处两种写法都可以。但到组件时，data必须使用函数式。

重要的原则：

由Vue管理的函数，一定不要写箭头函数，一旦写了箭头函数就不再是Vue实例了，而变成了windows了。





### 1.5 MVVM模型

1. M: 模型（Model）对应 data中的数据
2. V：视图（view）模板
3. VM：视图模型（ViewModel):Vue实例对象.

![image-20241225122542893](.\images\image-20241225122542893.png)

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-MVVM模型</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <!-- 容器 -->
    <div id="root">
       <h1>直接写的属性:{{name}}</h1>
       <h2>Vue的自身属性:{{$createElement}}</h2>
       <h3>原型对象属性:{{$nextTick}}</h3>
    </div>
    <script type="text/javascript">
        //阻止VUE在启动时生产警告信息
        Vue.config.productionTip = false
        // data 的第一种写法
        const vm = new Vue({
            el: '#root',
            data:{
                name: 'nullnull'
            }
        });
        console.log(vm);
    </script>
</body>
</html>
```

![image-20241225124905441](.\images\image-20241225124905441.png)

结论：

data中的所有属性，最后都同现在vmt身上

vm身上所有的属性 及 Vue原型上所有属性，在Vue模板中都可以直接使用。



