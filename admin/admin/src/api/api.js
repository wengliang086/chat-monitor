import axios from 'axios';

let base = '/api/admin';
export const requestLoginPost = params => { return axios.post(`${base}/user/loginByAccount`, params).then(res => res.data); };
export const requestLoginPost2 = params => {
    return axios({
        method: 'post',
        mode: 'no-cors',
        headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json',
        },
        withCredentials: true,
        credentials: true,
        url: `${base}/user/loginByAccount`,
        // data: {
        //     'account':'111',
        //     'password':'fdfsf'
        // }
        data: 'account=111&password=p111'
    }).then(res => res.data);
};
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
