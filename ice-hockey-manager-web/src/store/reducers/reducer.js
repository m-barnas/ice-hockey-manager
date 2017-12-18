import * as actionTypes from '../actions/actionTypes';

const initialState = {
  managers: {
    username: 'admin'
  }
};

const reducer = (state = initialState, action) => {
  switch (action.type) {
      case actionTypes.DELETE_MANAGER:
        return {
            ...state,
            managers: {
                ...state.managers
            }
        };
      default:
        return state;
  }
};

export default reducer;