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