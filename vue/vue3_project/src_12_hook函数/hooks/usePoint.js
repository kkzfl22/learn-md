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