import React from 'react';
import ReactDOM from 'react-dom';

import App from './App';
import Home from './components/Home';
import Login from './components/Login';
import Signup from './components/Signup';

import apiService from './service/apiService';
import redirectService from './service/redirectService';

import { token, currentUser } from './reducers/login';
import { loading, success, error } from './reducers/status';

import { Router, Route, IndexRoute, browserHistory } from 'react-router';
import { applyMiddleware, combineReducers, createStore } from 'redux';
import { Provider } from 'react-redux';

import './index.css';
import './../node_modules/bootstrap/dist/css/bootstrap.min.css';
import './../node_modules/bootstrap/dist/css/bootstrap-theme.min.css';

var middleware = applyMiddleware(apiService, redirectService);

var login = combineReducers({ loading, success, error });
var signup = combineReducers({ loading, success, error }); 
var reducer = combineReducers({ token, currentUser, login, signup });
var store = createStore(reducer, {}, middleware);

ReactDOM.render((
  <Provider store={store}>
    <Router history={browserHistory}>
      <Route path="/" component={App}>
        <IndexRoute component={Home} />
        <Route path="login" component={Login} />
        <Route path="signup" component={Signup} />
      </Route>
    </Router>
  </Provider>
  ),
  document.getElementById("root")
);
