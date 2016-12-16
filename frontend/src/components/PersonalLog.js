import React, { Component } from 'react';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import * as actions from '../actions/PersonalLog';

class PersonalLog extends Component {
   
	render() {	
 	  
    if(this.props.token!=""){
    	 this.props.actions.load(this.props.params.id, this.props.token);
    	
    }


		return (

			<div className="container">            	
         
			<div className="col-xm-6">
			<h3>Name of the meal:</h3>
			</div>
			<div className="col-xm-6">
			<h3>Amount of Calories:</h3>
			
			</div>
			

			</div>
		);
	}
}

var mapStateToProps = function(state){
    return {
        token: state.token,
        personalLog:state.personalLog
    };
}
var mapActionsToProps = function(dispatch) {
    return { actions: bindActionCreators(actions, dispatch) };
}

export default connect(mapStateToProps, mapActionsToProps)(PersonalLog);