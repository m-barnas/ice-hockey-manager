import * as actionTypes from '../actions/actionTypes';

const initialState = {
  managers: []
};

const reducer = (state = initialState, action) => {
  switch (action.type) {
      case actionTypes.DELETE_MANAGER:
        return state;
      default:
        return state;
  }
};

export default reducer;