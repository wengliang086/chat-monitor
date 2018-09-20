import Cookies from 'js-cookie'

const user = {
    state: {},
    mutations: {
        logout(state, vm) {
            Cookies.remove('');
            // 清空打开的页面等数据
            localStorage.clear();
        }
    }
};

export default user;