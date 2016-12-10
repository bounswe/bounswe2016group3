import React, { Component } from 'react';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import * as actions from '../actions/PersonalLog';

class PersonalLog extends Component {
    componentWillReceiveProps(props) {
    	console.log(this);
        props.actions.load(props.params.id, props.token);
    }	

	render() {


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
        token: state.token
    };
}

var mapDispatchToProps = function(dispatch){
    return { actions: bindActionCreators(actions, dispatch) };
}

export default connect(mapStateToProps, mapDispatchToProps)(PersonalLog);