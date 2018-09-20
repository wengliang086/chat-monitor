import { otherRouter } from '@/router/router';
import { router } from '@/router/index';
import Vue from 'vue'

const app = {
    state: {
        // 菜单数组
        menuList: [],
        routers: [
            otherRouter
        ],
    },
    mutations: {
        // 动态添加主界面路由，需要缓存
        updateAppRouter(state, routes) {
            state.routers.push(...routes);
            router.addRoutes(routes);
        },
        // 动态添加全局路由，不需要缓存
        updateDefaultRouter(state, routes) {
            router.addRoutes(routes);
        },
        updateMenulist(state, routes) {
            state.menuList = routes;
        },
    }
};

export default app;