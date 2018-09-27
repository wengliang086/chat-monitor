import babelpolyfill from 'babel-polyfill'
import Vue from 'vue'
import { router } from './router/index'
import store from './store'
import util from './libs/util';
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';
//import './assets/theme/theme-green/index.css'
//import NProgress from 'nprogress'
//import 'nprogress/nprogress.css'
// import Mock from './mock'
// Mock.bootstrap();
import 'font-awesome/css/font-awesome.min.css'
import App from './App'

Vue.use(ElementUI)

//NProgress.configure({ showSpinner: false });

new Vue({
  //el: '#app',
  //template: '<App/>',
  router,
  store,
  //components: { App }
  render: h => h(App),
  data: {
    currentPageName: ''
  },
  mounted() {
    // 初始化菜单
    util.initRouter(this);
    this.currentPageName = this.$route.name;
  }
}).$mount('#app')

