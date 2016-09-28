import React from 'react';
import './App.css';
import Header from './components/Header.js';

var App = function(props) {
    return (
      <div className="container">
        <Header location={props.location}/>
        <div className="row">
            {props.children}
        </div>
      </div>
    );
}


export default App;
