import Login from './views/Login.vue'
import NotFound from './views/404.vue'
import Home from './views/Home.vue'
import Main from './views/Main.vue'
import Game from './views/base/Game.vue'
import Group from './views/base/Group.vue'
import User from './views/base/User.vue'
import Suspicious from './views/base/Suspicious.vue'
import ChangePassword from './views/systemManagement/ChangePassword.vue'
import Logout from './views/systemManagement/Logout.vue'
import Page6 from './views/nav3/Page6.vue'
import echarts from './views/statistics/echarts.vue'

let routes = [
    {
        path: '/login',
        component: Login,
        name: '',
        hidden: true
    },
    {
        path: '/404',
        component: NotFound,
        name: '',
        hidden: true
    },
    //{ path: '/main', component: Main },
    {
        path: '/',
        component: Home,
        name: '基础功能',
        iconCls: 'fa el-icon-message',//图标样式class
        children: [
            { path: '/main', component: Main, name: '主页', hidden: true },
            { path: '/game', component: Game, name: '游戏列表' },
            { path: '/group', component: Group, name: '用户组列表' },
            { path: '/user', component: User, name: '用户列表' },
            { path: '/suspicious', component: Suspicious, name: '可疑信息' },
        ],
        redirect: { path: "/game" }
    },
    // {
    //     path: '/',
    //     component: Home,
    //     name: '',
    //     iconCls: 'fa fa-address-card',
    //     leaf: true,//只有一个节点
    //     children: [
    //         { path: '/page6', component: Page6, name: '导航三' }
    //     ]
    // },
    {
        path: '/',
        component: Home,
        name: '统计分析',
        iconCls: 'fa fa-bar-chart',
        children: [
            { path: '/echarts', component: echarts, name: 'echarts' }
        ]
    },
    {
        path: '/',
        component: Home,
        name: '系统管理',
        iconCls: 'fa fa-id-card-o',
        children: [
            { path: '/changePassword', component: ChangePassword, name: '修改密码' },
            { path: '/logout', component: Logout, name: '注销' }
        ]
    },
    {
        path: '*',
        hidden: true,
        redirect: { path: '/404' }
    }
];

export default routes;
