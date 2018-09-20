<template>
<div>
    main
    <h1>Vuex 测试</h1>
    Clicked: {{getCount}}
    <button @click="increment">+</button>
    <button v-on:click="decrement">-</button>
</div>
</template>

<script>
import {
    mapGetters,
    mapActions
} from 'vuex';
export default {
    computed: {
        // 使用对象展开运算符将 getters 混入 computed 对象中
        ...mapGetters([
            'getCount'
            // ...
        ])
    },
    methods: {
        ...mapActions([
            'increment', // 映射 this.increment() 为 this.$store.dispatch('increment')
            'decrement'
        ])
        //...mapActions({
        //  add: 'increment' // 映射 this.add() 为 this.$store.dispatch('increment')
        //})
    }
}
</script>

<style scoped>

</style>
