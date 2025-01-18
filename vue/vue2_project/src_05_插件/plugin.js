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