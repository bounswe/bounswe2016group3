import React from 'react';
import * as actions from '../actions/Signup';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';

var Signup = function(props) {
    var submitForm = function(e) {

        let email = document.getElementById("signup-email");
        let password = document.getElementById("signup-pass");
        let confirm = document.getElementById("signup-confirm");
        let name = document.getElementById("signup-name");
        let q = document.getElementById("signup-question");
        let a = document.getElementById("signup-answer");


        if(email&&password&&confirm&&name){
            if(password.value === confirm.value){
                props.actions.submit(email.value, password.value, name.value, q.value, a.value, props.userType);
            } else {
                props.actions.wrongPassword();
            }
            
        }

        e.preventDefault();
    }

    if(props.success){
        props.history.pushState(null,"/");
    }

    return (
        <div>
            <p>
                <input type="text" className="form-control" placeholder="Username" id="signup-email" />
            </p>
            <p>
                <input type="text" className="form-control" placeholder="Full Name" id="signup-name" />
            </p>
            <p>
                <input type="password" className="form-control" placeholder="Password" id="signup-pass" />
            </p>
            <p>
                <input type="password" className="form-control" placeholder="Confirm Password" id="signup-confirm" />
            </p>
            <p>
                <input type="text" className="form-control" placeholder="Secret Question" id="signup-question" />
            </p>
            <p>
                <input type="text" className="form-control" placeholder="Secret Answer" id="signup-answer" />
            </p>

            <p>
                <button className="btn btn-default" type="button" onClick={submitForm}>Signup</button>
            </p>
        </div>
    );
}

var mapStateToProps = function(state){
  return { 
        loading: state.signup.loading,
        success: state.signup.success
    };
}

var mapDispatchToProps = function(dispatch){
    return { actions: bindActionCreators(actions, dispatch) };
}


export default connect(mapStateToProps, mapDispatchToProps)(Signup);
