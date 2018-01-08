import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter } from 'react-router-dom';
import { createStore, applyMiddleware } from 'redux';
import reduxThunk from 'redux-thunk';
import { Provider } from 'react-redux';
import './index.css';
import App from './App';
import registerServiceWorker from './registerServiceWorker';
import reducer from "./store/reducers/reducer";
import { composeWithDevTools } from 'redux-devtools-extension';

const store = createStore(reducer, {}, composeWithDevTools(
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
