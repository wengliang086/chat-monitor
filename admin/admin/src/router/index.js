import Vue from 'vue'
import VueRouter from 'vue-router'
import routes from './router'

Vue.use(VueRouter)

export const router = new VueRouter({
    mode: 'history',
    base: 'chat-admin',
    routes
})

router.beforeEach((to, from, next) => {
    //NProgress.start();
    // console.log("router from [" + from.path + "] > to [" + to.path + "]");

    if (to.path == '/login') {
        sessionStorage.removeItem('user');
    }
    let user = JSON.parse(sessionStorage.getItem('user'));
    if (!user && to.path != '/login') {
        next({ path: '/login' })
    } else {
        next()
    }
})

//router.afterEach(transition => {
//NProgress.done();
//});

// 写到这里不对，暂时不知道为什么？？？
// export default router