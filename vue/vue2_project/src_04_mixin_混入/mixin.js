export const hunhe = {
    methods: {
        showName(){
            alert(this.name);
        }
    },
    //需要注意的是mounteds这类的生命周期的方法
    mounted() {
        console.log("全局的mounted")
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