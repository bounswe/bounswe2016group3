import React from 'react';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';

import * as actions from '../actions/ResetPassword';

var ResetPassword = function(props){
    var submitForm = function(e) {
        let answer = document.getElementById("forgot-answer");

        if(answer){
            props.actions.submit(props.uid, answer.value, props.history);
        }

        e.preventDefault();
    }

    var getUser = function(e){
        let email = document.getElementById("forgot-email");

        if(email){
            props.actions.getUser(email.value);
        }

        e.preventDefault();
    }

    return (
        <div>
            <p className="form-inline">
                <input type="email" className="form-control" placeholder="E-mail" id="forgot-email" />
                &nbsp;
                <button className="btn btn-default" type="button" onClick={getUser}>Get Secret Question</button>
            </p>
            <p>
                {props.question}
            </p>
            <p>
                <input type="password" className="form-control" placeholder="Secret Answer" id="forgot-answer"/>
            </p>

            <p>
                <button className="btn btn-default" type="button" onClick={submitForm}>Login</button>
            </p>
        </div>
    );
}

var mapStateToProps = function(state){
    return {
        question: state.pwdReset.secretQuestion,
        uid: state.pwdReset.userId
    };
}

var mapDispatchToProps = function(dispatch){
    return { actions: bindActionCreators(actions, dispatch) };
}

export default connect(mapStateToProps, mapDispatchToProps)(ResetPassword);
