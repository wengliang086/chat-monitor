import axios from 'axios';
import router from '../main'
import { getStore, setStore } from '@/libs/storage'

axios.defaults.timeout = 5000
// axios.defaults.baseURL = '/api/admin'

axios.interceptors.request.use(requestConfig => {
    var user = sessionStorage.getItem('user');
    if (user) {
        user = JSON.parse(user);
        requestConfig.headers.accessToken = user.accessToken;
    }
    return requestConfig;
}, err => {
    Promise.reject(err)
})

axios.interceptors.response.use(response => {
    // console.log(response);
    var code = response.data.code;
    if (code === 1) {
        return response.data.value;
    } else if (code === 7) {
        // 7 清除token信息并跳转到登录页面
        sessionStorage.removeItem('user');
        // 只有在当前路由不是登录页面才跳转
        if (router.currentRoute.path !== 'login') {
            // router.push({
            router.replace({
                path: '/login',
                query: { redirect: router.currentRoute.path },
            })
        }
    } else {
        console.log(response);
        return Promise.reject(response.data.msg)
    }
    return response;
}, err => {
    console.log(err);
    // let returnValue = JSON.parse(JSON.stringify(err.response.data));
    // console.log(returnValue);
    // return Promise.reject(returnValue.msg);
    return Promise.reject(err.response.data.msg);
})

// let base = '/api/admin';
let base = process.env.API_BASE_URL + "/admin";
export const requestLoginPost = params => { return axios.post(`${base}/user/loginByAccount`, params) };
export const requestLogin = params => { return axios.get(`${base}/user/loginByAccount`, { params: params }).then(res => res.data); };
/**
 * game相关
 */
export const getGameListPage = params => { return axios.get(`${base}/game/list`, { params: params }); };

export const removeGame = params => { return axios.get(`${base}/game/remove`, { params: params }); };

export const editGame = params => { return axios.get(`${base}/game/update?` + params); };

export const addGame = params => { return axios.get(`${base}/game/add?` + params); };

/**
 * group相关
 */
export const getGroupList = params => { return axios.get(`${base}/group/list`, { params: params }); };

export const addGroup = params => { return axios.get(`${base}/group/add?` + params); };

export const editGroup = params => { return axios.get(`${base}/group/update?` + params); };

/**
 * user相关
 */
export const getUserList = params => { return axios.get(`${base}/user/list`, { params: params }); };

export const addUser = params => { return axios.get(`${base}/user/register?` + params); };

export const editUser = params => { return axios.get(`${base}/user/updateUserInfo?` + params); };

/**
 * suspicious相关
 */
export const getSuspiciousList = params => { return axios.get(`${base}/suspicious/listDetail`, { params: params }); };

export const editSuspicious = params => { return axios.get(`${base}/suspicious/msgSure?` + params); };

//export const editUser = params => { return axios.get(`${base}/user/updateUserInfo?` + params); };





export const getRequest = (url, params) => {
    let accessToken = getStore('accessToken');
    return axios({
        method: 'get',
        url: `${base}${url}`,
        params: params,
        headers: {
            'accessTocken' : accessToken
        }
    });
};

export const postRequest = (url, params) => {
    let accessToken = getStore('accessToken');
    return axios({
        method: 'post',
        url: `${base}${url}`,
        params: params,
        transformRequest: [function (data) {
            let ret = '';
            for (let it in data) {
                ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&';
            }
            return ret;
        }],
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'accessTocken' : accessToken
        }
    });
};

export const putRequest = (url, params) => {
    let accessToken = getStore('accessToken');
    return axios({
        method: 'put',
        url: `${base}${url}`,
        params: params,
        transformRequest: [function (data) {
            let ret = '';
            for (let it in data) {
                ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&';
            }
            return ret;
        }],
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'accessTocken' : accessToken
        }
    });
};

export const deleteRequest = (url, params) => {
    let accessToken = getStore('accessToken');
    return axios({
        method: 'delete',
        url: `${base}${url}`,
        params: params,
        headers: {
            'accessTocken' : accessToken
        }
    });
};

export const uploadFileRequest = (url, params) => {
    let accessToken = getStore('accessToken');
    return axios({
        method: 'post',
        url: `${base}${url}`,
        params: params,
        headers: {
            'accessToken': accessToken
        }
    });
};
