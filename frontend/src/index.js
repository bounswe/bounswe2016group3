import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import Home from './components/Home';
import { tokenReducer, userIdReducer } from './reducers/login';
import { Router, Route, IndexRoute, browserHistory } from 'react-router'
import { combineReducers, createStore, Provider } from 'redux'
import './index.css';
import './../node_modules/bootstrap/dist/css/bootstrap.min.css';
import './../node_modules/bootstrap/dist/css/bootstrap-theme.min.css';

let reducer = combineReducers({tokenReducer, userIdReducer});
let store = createStore(reducer);

ReactDOM.render((
  <Provider store={store}>
    <Router history={browserHistory}>
      <Route path="/" component={App}>
        <IndexRoute component={Home}/>
      </Route>
    </Router>
  </Provider>
  ),
  document.body
);
