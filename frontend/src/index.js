import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter } from "../node_modules/react-router-dom/index";
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { createStore } from "redux";
import rootReducer from './modules/rootReducer';
import { Provider } from 'react-redux';
import { composeWithDevTools } from '../node_modules/redux-devtools-extension/index';


const store = createStore(rootReducer, composeWithDevTools());


ReactDOM.render(
    <React.StrictMode>
        <Provider store={store}>
            <BrowserRouter>
                <App />
            </BrowserRouter>
        </Provider>
    </React.StrictMode>,
    document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
