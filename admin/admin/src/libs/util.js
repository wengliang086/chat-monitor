import axios from 'axios';
import lazyLoading from './lazyLoading'

let util = {};

//服务器获取路由
util.initRouter = function (vm) {
    // alert('util');
    const constRoutes = [];
    const otherRoutes = [];

    // 404路由需要和动态路由一起注入
    const otherRouter = [{
        path: '/*',
        name: 'error-404',
        meta: {
            title: '404-页面不存在'
        },
        component: 'error-page/404'
    }];

    // let menuData = res.result;
    // util.initRouterNode(constRoutes, menuData);
    // util.initRouterNode(otherRoutes, otherRouter);

    // test
    let testData = {
        path: '/',
        component: "Home",
        name: '动态添加测试',
        iconCls: 'fa fa-bar-chart',
        children: [
            { path: '/form3', component: "dynamicNavTest/Form1", name: 'Form3' },
            { path: '/page6', component: 'dynamicNavTest/Page6', name: 'Page6' }
        ]
    };
    util.initRouterNode(constRoutes, [testData]);

    // 添加主界面路由
    vm.$store.commit('updateAppRouter', constRoutes);
}

// 生成路由节点
util.initRouterNode = function (routers, data) {
    for (var item of data) {
        let menu = Object.assign({}, item);
        // menu.component = import(`@/views/${menu.component}.vue`);
        menu.component = lazyLoading(menu.component);

        if (item.children && item.children.length > 0) {
            menu.children = [];
            util.initRouterNode(menu.children, item.children);
        }

        let meta = {};
        // 给页面添加权限、标题、第三方网页链接
        meta.permTypes = menu.permTypes ? menu.permTypes : null;
        meta.title = menu.title ? menu.title + " - 前后端分离开发平台" : null;
        meta.url = menu.url ? menu.url : null;
        menu.meta = meta;

        routers.push(menu);
    }
};

export default util;