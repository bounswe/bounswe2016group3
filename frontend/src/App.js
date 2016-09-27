import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import Header from './components/Header.js';

class App extends Component {
  render() {
    return (
      <div className="container">
        <Header />
        <div className="row">
        </div>
      </div>
    );
  }
}

export default App;
