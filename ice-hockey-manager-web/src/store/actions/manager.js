import * as actionTypes from './actionTypes';
import axios from '../../axios';

export const fetchManagersSuccess = ( managers ) => {
    return {
        type: actionTypes.FETCH_MANAGERS_SUCCESS,
        managers: managers
    };
};

export const fetchManagersFail = ( error ) => {
    return {
        type: actionTypes.FETCH_MANAGERS_FAIL,
        error: error
    };
};

export const fetchManagersStart = () => {
    return {
        type: actionTypes.FETCH_MANAGERS_START
    };
};

export const removeManagersFail = ( error ) => {
    return {
        type: actionTypes.REMOVE_MANAGER_FAIL,
        error: error
    };
};

export const removeManagersStart = () => {
    return {
        type: actionTypes.REMOVE_MANAGER_START
    };
};

export const removeManager = (managerId, token) => {
    return dispatch => {
        dispatch(removeManagersStart());
        axios({
            method: 'delete',
            url: '/managers/' + managerId,
            headers: {
                'Authorization': 'Bearer ' + token
            }})
            .then( res => {
                dispatch(fetchManagers());
            } )
            .catch( err => {
                dispatch(removeManagersFail(err));
            } );
    };
};

export const fetchManagers = () => {
    return dispatch => {
        dispatch(fetchManagersStart());
        axios.get('/managers/all')
            .then( res => {
                const managers = [];
                for ( let key in res.data ) {
                    managers.push( {
                        ...res.data[key]
                    } );
                }
                dispatch(fetchManagersSuccess(managers));
            } )
            .catch( err => {
                dispatch(fetchManagersFail(err));
            } );
    };
};