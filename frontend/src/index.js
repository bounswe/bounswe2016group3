import React from 'react';
import ReactDOM from 'react-dom';

import App from './App';
import Home from './components/Home';
import Login from './components/Login';
import Signup from './components/Signup';
import AllUsers from './components/AllUsers';
import Profile from './components/Profile';
import ResetPassword from './components/ResetPassword';
import NotFound from './components/NotFound';
import Menu from './components/Menu';
import Meal from './components/Meal';

import apiService from './service/apiService';
import redirectService from './service/redirectService';

import { token, currentUser } from './reducers/login';
import { loading, success, error } from './reducers/status';
import { users, profile, followers, following } from './reducers/users';
import { userId, secretQuestion } from './reducers/pwdReset';
import { menu } from './reducers/menu';
import { meal } from './reducers/meal';

import { Router, Route, IndexRoute, browserHistory } from 'react-router';
import { applyMiddleware, combineReducers, createStore } from 'redux';
import { Provider } from 'react-redux';

 

import './../node_modules/bootstrap/dist/css/bootstrap.min.css';
import './../node_modules/bootstrap/dist/css/bootstrap-theme.min.css';


import './index.css';

var middleware = applyMiddleware(apiService, redirectService);

var login = combineReducers({ loading, success, error });
var signup = combineReducers({ loading, success, error });
var pwdReset = combineReducers({ userId, secretQuestion });
var reducer = combineReducers({ token, currentUser, 
  login, signup, pwdReset,
  users, profile,
  followers, following,
  menu, meal });
var store = createStore(reducer, {}, middleware);

ReactDOM.render((
  <Provider store={store}>
    <Router history={browserHistory}>
      <Route path="/" component={App}>
        <IndexRoute component={Home} />
        <Route path="login" component={Login} />
        <Route path="signup" component={() => <Signup userType="0" />} />
        <Route path="serverSignup" component={() => <Signup userType="1" />} />
        <Route path="user/all" component={AllUsers} />
        <Route path="user/:id" component={Profile} />
        <Route path="resetPassword" component={ResetPassword} />
        <Route path="menu/:id" component={Menu} />
        <Route path="meal/:id" component={Meal} />
        <Route path="*" component={NotFound} />
      </Route>
    </Router>
  </Provider>
  ),
  document.getElementById("root")
);
