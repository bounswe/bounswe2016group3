import React from 'react';
import { connect } from 'react-redux';
import * as actions from '../actions/Login';
import { bindActionCreators } from 'redux';

var Login = function(props) {
    var submitForm = function(e) {
        let email = document.getElementById("login-email");
        let password = document.getElementById("login-pass");

        if(email&&password){
            props.actions.submit(email.value , password.value);
        }

        e.preventDefault();
    }

    if(props.token && props.token !== ""){
        props.actions.redirectRoot(props.history);
    }

    if(props.loading){
        return (
            <div>
                <i className="fa fa-circle-o-notch fa-spin fa-3x fa-fw"></i>
                <span className="sr-only">Loading...</span>
            </div>
        );
    }

    return (
        <div>
            <p>
                <input type="email" className="form-control" placeholder="E-mail" id="login-email" />
            </p>
            <p>
                <input type="password" className="form-control" placeholder="Password" id="login-pass"/>
            </p>

            <p>
                <button className="btn btn-default" type="button" onClick={submitForm}>Login</button>
            </p>
        </div>
    );
}

var mapStateToProps = function(state){
  return { 
        loading: state.login.loading,
        token: state.token
    };
}

var mapDispatchToProps = function(dispatch){
    return { actions: bindActionCreators(actions, dispatch) };
}

export default connect(mapStateToProps, mapDispatchToProps)(Login);
