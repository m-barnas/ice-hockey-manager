import * as actionTypes from '../actions/actionTypes';
import { updateObject } from '../utility';

const initialState = {
    managers: [],
    loading: false
};

const fetchManagersStart = ( state, action ) => {
    return updateObject( state, { loading: true } );
};

const fetchManagersSuccess = ( state, action ) => {
    return updateObject( state, {
        managers: action.managers,
        loading: false
    } );
};

const fetchManagersFail = ( state, action ) => {
    return updateObject( state, { loading: false } );
};

const removeManagerStart = ( state, action ) => {
    return updateObject( state, { loading: true } );
};

const removeManagerFail = ( state, action ) => {
    return updateObject( state, { loading: false } );
};

const reducer = (state = initialState, action) => {
    switch (action.type) {
        case actionTypes.FETCH_MANAGERS_START:
            return fetchManagersStart( state, action );
        case actionTypes.FETCH_MANAGERS_SUCCESS:
            return fetchManagersSuccess( state, action );
        case actionTypes.FETCH_MANAGERS_FAIL:
            return fetchManagersFail( state, action );
        case actionTypes.REMOVE_MANAGER_START:
            return removeManagerStart( state, action );
        case actionTypes.REMOVE_MANAGER_FAIL:
            return removeManagerFail( state, action );
        default:
          return state;
    }
};

export default reducer;