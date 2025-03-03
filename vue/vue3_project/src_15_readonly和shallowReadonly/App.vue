<template>
    <button @click="isShowDemo = !isShowDemo">切换隐藏/显示</button>
    <DemoComponent v-if="isShowDemo"/>
</template>

<script>
import {ref} from 'vue'
import DemoComponent from './components/DemoComponent'
export default {
    name: 'App',
    components: {DemoComponent},
    setup(){
        let isShowDemo = ref(true)
        return {isShowDemo}
    }
}
</script>