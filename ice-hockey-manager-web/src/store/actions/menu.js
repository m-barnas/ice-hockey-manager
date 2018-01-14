import * as actionTypes from './actionTypes';

export const setCurrentPage = (page) => {
    return {
        type: actionTypes.SET_CURRENT_PAGE,
        currentPage: page
    };
};