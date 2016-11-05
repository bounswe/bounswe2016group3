import React from 'react';
import * as actions from '../actions/Signup';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';

import './signup.css';



var Signup = function(props) {
    var submitFormUser = function(e) {

        let email = document.getElementById("signup-email");
        let password = document.getElementById("signup-pass");
        let confirm = document.getElementById("signup-confirm");
        let name = document.getElementById("signup-name");
        let q = document.getElementById("signup-question");
        let a = document.getElementById("signup-answer");


        if(email&&password&&confirm&&name){
            if(password.value === confirm.value){
                props.actions.submit(email.value, password.value, name.value, q.value, a.value, 1);
            } else {
                props.actions.wrongPassword();
            }
            
        }

        e.preventDefault();
    }
    var submitFormFoodServer = function(e) {

        let email = document.getElementById("signup-email");
        let password = document.getElementById("signup-pass");
        let address = document.getElementById("signup-address");
        let confirm = document.getElementById("signup-confirm");
        let name = document.getElementById("signup-name");
        let q = document.getElementById("signup-question");
        let a = document.getElementById("signup-answer");


        if(email&&password&&confirm&&name){
            if(password.value === confirm.value){
                props.actions.submit(email.value, password.value,address.value, name.value, q.value, a.value, 0);
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
          <article className="col-xs-5">
           <h1 className="signup_h1"> User</h1> 
			<button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal">Open Modal</button>
<div id="myModal" class="modal fade" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Modal Header</h4>
      </div>
      <div class="modal-body">
        <p>Some text in the modal.</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>
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
                <button className="btn btn-default" type="button" onClick={submitFormUser}>Signup</button>
            </p>
           
            </article>
            

            <article className="col-xs-2">
            </article>


            <article className="col-xs-5">
           <h1> Food Server </h1> 
            <p>
                <input type="text" className="form-control" placeholder="Username" id="signup-email" />
            </p>
            <p>
                <input type="text" className="form-control" placeholder="Food Service Name" id="signup-name" />
            </p>
            <p>
                <input type="text" className="form-control" placeholder="Food Service Address" id="signup-address" />
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
                <button className="btn btn-default" type="button" onClick={submitFormFoodServer}>Signup</button>
            </p>
            </article>
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
