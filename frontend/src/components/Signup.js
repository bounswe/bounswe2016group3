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
                props.actions.submit(email.value, password.value, name.value, q.value, a.value, 0);
            } else {
                props.actions.wrongPassword();
            }
            
        }

        e.preventDefault();
        if(props.success){
      
    alert("Sign up is successful.Please Login")
       window.location.href= "./";

        }
       
    }
    var submitFormFoodServer = function(e) {

        let email = document.getElementById("signup-email2");
        let password = document.getElementById("signup-pass2");
        //let address = document.getElementById("signup-address2");
        let confirm = document.getElementById("signup-confirm2");
        let name = document.getElementById("signup-name2");
        let q = document.getElementById("signup-question2");
        let a = document.getElementById("signup-answer2");


        if(email&&password&&confirm&&name){
            if(password.value === confirm.value){

                props.actions.submitFoodServer(email.value, password.value, name.value, q.value, a.value, 1);
                
            } else {
                props.actions.wrongPassword();
            }
            
        }

        e.preventDefault();
        if(props.success){
      
    alert("Sign up is successful.Please Login")
       window.location.href= "./";

        }
    }
     


    

    return (
        
        <div className="container">

        <article className="col-xs-1">
        </article>

          
          <article className="col-xs-4">
           <h1 className="signup_h1"> User</h1> 
		

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
                <button className="btn btn-primary btn-block" type="button" onClick={submitFormUser}>Signup</button>
            </p>
           
            </article>
            

            <article className="col-xs-2">
            </article>


            <article className="col-xs-4">
           <h1 className="signup_h1"> Food Server</h1> 
            <p>
                <input type="text" className="form-control" placeholder="Username" id="signup-email2" />
            </p>
            <p>
                <input type="text" className="form-control" placeholder="Food Service Name" id="signup-name2" />
            </p>
            <p>
                <input type="text" className="form-control" placeholder="Food Service Address" id="signup-address2" />
            </p>

            <p>
                <input type="password" className="form-control" placeholder="Password" id="signup-pass2" />
            </p>
            <p>
                <input type="password" className="form-control" placeholder="Confirm Password" id="signup-confirm2" />
            </p>
            <p>
                <input type="text" className="form-control" placeholder="Secret Question" id="signup-question2" />
            </p>
            <p>
                <input type="text" className="form-control" placeholder="Secret Answer" id="signup-answer2" />
            </p>

            <p>
                <button className="btn btn-primary btn-block" type="button" onClick={submitFormFoodServer}>Signup</button>
            </p>
            </article>

        <article className="col-xs-1">
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
