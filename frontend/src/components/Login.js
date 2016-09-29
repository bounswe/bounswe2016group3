import React, { Component } from 'react';
import { connect } from 'react-redux';
import * as actions from '../actions/Login'
import { bindActionCreators } from 'redux'

class Login extends Component {
    render() {
        var email, password;

        var submitForm = (e) => {
            let email = document.getElementById("login-email");
            let password = document.getElementById("login-pass");

            console.log(email);
            console.log(password);            

            if(email&&password){
                console.log("yah");
                this.props.actions.submit(email.value , password.value);
            }

            return false;
        };

        return (
            <form onSubmit={submitForm()}>
                <p>
                    <input type="email" className="form-control" placeholder="E-mail" id="login-email" />
                </p>
                <p>
                    <input type="password" className="form-control" placeholder="Password" id="login-pass"/>
                </p>

                <p>
                    <input type="submit" className="btn btn-default" type="button" value="Login" />
                </p>
            </form>
        );
    }
}
var mapStateToProps = function(state){
  return {};
}

var mapDispatchToProps = function(dispatch){
    return { actions: bindActionCreators(actions, dispatch) };
}

export default connect(mapStateToProps, mapDispatchToProps)(Login);