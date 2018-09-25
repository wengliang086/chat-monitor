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
        path: '*',
        name: 'error-404',
        hidden: true,
        redirect: { path: '/404' },
        component: "/404"
    }];

    // let menuData = res.result;
    // util.initRouterNode(constRoutes, menuData);
    util.initRouterNode(otherRoutes, otherRouter);

    let staticRouters = [
        {
            path: '/',
            component: "Home",
            name: '统计分析',
            iconCls: 'fa fa-bar-chart',
            children: [
                { path: '/echarts', component: "statistics/echarts", name: 'echarts' }
            ]
        },
        {
            path: '/',
            component: "Home",
            name: '系统管理',
            iconCls: 'fa fa-id-card-o',
            children: [
                { path: '/changePassword', component: "systemManagement/ChangePassword", name: '修改密码' },
                { path: '/logout', component: "systemManagement/Logout", name: '注销' }
            ]
        },
    ];

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
    // 加载菜单
    let baseUrl = process.env.API_BASE_URL;
    axios.get(baseUrl + "/admin/user/getMenuList").then(res => {
        util.initRouterNode(constRoutes, [res[0]]);
        util.initRouterNode(constRoutes, staticRouters);
        util.initRouterNode(constRoutes, [testData]);

        // 添加主界面路由
        vm.$store.commit('updateAppRouter', constRoutes);
        // 添加全局路由
        vm.$store.commit('updateDefaultRouter', otherRoutes);
    }).catch(errMsg => {
        alert("加载菜单失败: " + errMsg);
    });
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