import { otherRouter } from '../../router/router';
import { router } from '../../router/index';
import Vue from 'vue'

const app = {
    state: {
        // 菜单数组
        menuList: [],
        routers: [
            otherRouter
        ],
        tabsList: [],
        activeTabIndex: ""
    },
    mutations: {
        // 动态添加主界面路由，需要缓存
        updateAppRouter(state, routes) {
            // state.routers.push(...routes);
            // console.log(router);
            router.addRoutes(routes);
            router.options.routes.push(...routes);
            console.log("Menu加载完成")
        },
        // 动态添加全局路由，不需要缓存
        updateDefaultRouter(state, routes) {
            router.addRoutes(routes);
        },
        updateMenulist(state, routes) {
            state.menuList = routes;
        },
        // Tab 操作
        addTab(state, data) {
            // 注意：这里加上this就需要加.app，不加this就不需要，即 下面两行效果相同
            state.tabsList.push(data);
            // this.state.app.tabsList.push(data);
        },
        deleteTab(state, route) {
            let index = 0;
            for (const tab of state.tabsList) {
                if (tab.route === route) {
                    break;
                }
                index++;
            }
            this.state.app.tabsList.splice(index, 1);
        },
        setActiveTab(state, index) {
            // console.log("setActiveTab value=" + index);
            this.state.app.activeTabIndex = index;
        }
    }
};

export default app;