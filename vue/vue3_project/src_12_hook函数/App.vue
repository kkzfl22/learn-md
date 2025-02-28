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