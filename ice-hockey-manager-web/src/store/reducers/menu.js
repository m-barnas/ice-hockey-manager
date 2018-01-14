import * as actionTypes from '../actions/actionTypes';
import {updateObject} from '../utility';

const initialState = {
    currentPage: '1'
};

const setCurrentPage = (state, action) => {
    return updateObject(state, { currentPage: action.currentPage })
};

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case actionTypes.SET_CURRENT_PAGE:
            return setCurrentPage(state,action);
        default:
            return state;
    }
};

export default reducer;