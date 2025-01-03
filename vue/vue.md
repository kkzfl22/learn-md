# Vue笔记



## 1 .vue入门

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



#### 1.12 收集表单数据

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
            <textarea v-model="userInfo.other"></textarea><br/><br/>
            <input type="checkbox" v-model="userInfo.agree">
            阅读并接受<a href="http://www.atguigu.com">《用户协议》</a>
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





## 结束

