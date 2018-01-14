import * as actionTypes from './actionTypes';
import axios from 'axios';

export const authStart = () => {
    return {
        type: actionTypes.AUTH_START
    };
};

export const authSuccess = (token, userId, role) => {
    return {
        type: actionTypes.AUTH_SUCCESS,
        token: token,
        userId: userId,
        role: role
    };
};

export const authFail = (error) => {
    return {
        type: actionTypes.AUTH_FAIL,
        error: error
    };
};

export const logout = () => {
    return {
        type: actionTypes.AUTH_LOGOUT
    };
};

export const checkAuthTimeout = (expirationTime) => {
    return dispatch => {
        setTimeout(() => {
            dispatch(logout());
        }, expirationTime * 100000);
    };
};

export const setAuthRedirectPath = (path) => {
    return {
        type: actionTypes.SET_AUTH_REDIRECT_PATH,
        path: path
    };
};

export const auth = (email, password) => {
    return dispatch => {
        dispatch(authStart());
        axios({
            method: 'post',
            url: 'http://localhost:8080/pa165/oauth/token',
            headers: {
                'Content-type': 'application/x-www-form-urlencoded'
            },
            params: {
                'grant_type': 'password',
                'client_id': 'web-client',
                'client_secret': '53ac618c-c8d2-44a1-b257-a2bd3816e829',
                'scope': 'read write trust',
                'username': email,
                'password': password
            }
        }).then(function (res) {
            dispatch(authSuccess(res.data.access_token, res.data.id, res.data.role));
            dispatch(checkAuthTimeout(res.data.expires_in));
        })
        .catch(function (err) {
            dispatch(authFail(err));
        });
    };
};
