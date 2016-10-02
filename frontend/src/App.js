import React, { Component } from 'react';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import './App.css';
import * as actions from './actions/App';
import Header from './components/Header.js';

class App extends Component {
    componentDidMount(){
      if(localStorage["token"]){
        this.props.actions.checkToken(localStorage["token"]);
      }
    }
    render(){
      return (
        <div className="container">
          <Header location={this.props.location}/>
          <div className="row">
              {this.props.children}
          </div>
        </div>
      );
    }
}


var mapStateToProps = function(state){
  return {};
}

var mapDispatchToProps = function(dispatch){
    return { actions: bindActionCreators(actions, dispatch) };
}

export default connect(mapStateToProps, mapDispatchToProps)(App);
