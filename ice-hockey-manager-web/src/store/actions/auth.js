import * as actionTypes from './actionTypes';
import axios from '../../axios';

export const authStart = () => {
    return {
        type: actionTypes.AUTH_START
    };
};

export const authSuccess = (authData) => {
    return {
        type: actionTypes.AUTH_SUCCESS,
        authData: authData
    };
};

export const authFail = (error) => {
    return {
        type: actionTypes.AUTH_FAIL,
        error: error
    };
};

export const auth = (email, password) => {
    return dispatch => {
        dispatch(authStart());
        const authData = {
            grant_type: 'password',
            client_id: 'web-client',
            client_secret: '53ac618c-c8d2-44a1-b257-a2bd3816e829',
            scope: 'trust',
            username: email,
            password: password
        };
        axios.post('oauth/token', JSON.stringify(authData))
            .then(response => {
                console.log(response);
                dispatch(authSuccess(response.data));
            })
            .catch(err => {
                console.log(err);
                dispatch(authFail(err));
            })
    };
};

// const data = {
//     grant_type: USER_GRANT_TYPE,
//     client_id: CLIENT_ID,
//     client_secret: CLIENT_SECRET,
//     scope: SCOPE_INT,
//     username: DEMO_EMAIL,
//     password: DEMO_PASSWORD
// };
//
//
//
// axios.post(TOKEN_URL, Querystring.stringify(data))
//     .then(response => {
//         console.log(response.data);
//         USER_TOKEN = response.data.access_token;
//         console.log('userresponse ' + response.data.access_token);
//     })
//     .catch((error) => {
//         console.log('error ' + error);
//     });
//
// const AuthStr = 'Bearer '.concat(USER_TOKEN);
// axios.get(URL, { headers: { Authorization: AuthStr } })
//     .then(response => {
//         // If request is good...
//         console.log(response.data);
//     })
//     .catch((error) => {
//         console.log('error ' + error);
//     });