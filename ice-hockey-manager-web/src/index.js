import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter } from 'react-router-dom';
import { createStore, applyMiddleware, combineReducers } from 'redux';
import reduxThunk from 'redux-thunk';
import { Provider } from 'react-redux';
import './index.css';
import App from './App';
import registerServiceWorker from './registerServiceWorker';
import { composeWithDevTools } from 'redux-devtools-extension';

// reducers
import managerReducer from "./store/reducers/manager";
import authReducer from './store/reducers/auth';
import menuReducer from './store/reducers/menu';

const rootReducer = combineReducers({
    manager: managerReducer,
    auth: authReducer,
    menu: menuReducer
});

const store = createStore(rootReducer, {}, composeWithDevTools(
    applyMiddleware(reduxThunk)),);

const app = (
    <Provider store={store}>
        <BrowserRouter basename="/pa165">
            <App/>
        </BrowserRouter>
    </Provider>
);

ReactDOM.render(app, document.getElementById('root'));
registerServiceWorker();
