import React from 'react';
import ReactDOM from 'react-dom';

import App from './App';
import Home from './components/Home';
import Login from './components/Login';
import Signup from './components/Signup';
import apiService from './service/apiService';
import { token, userId } from './reducers/login';
import { loading } from './reducers/loading';

import { Router, Route, IndexRoute, browserHistory } from 'react-router';
import { applyMiddleware, combineReducers, createStore } from 'redux';
import { Provider } from 'react-redux';

import './index.css';
import './../node_modules/bootstrap/dist/css/bootstrap.min.css';
import './../node_modules/bootstrap/dist/css/bootstrap-theme.min.css';

var reducer = combineReducers({token, userId, loading});
var store = createStore(reducer, {}, applyMiddleware(apiService));

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
