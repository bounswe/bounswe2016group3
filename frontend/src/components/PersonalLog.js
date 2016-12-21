import React, { Component } from 'react';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import * as actions from '../actions/PersonalLog';
import jQuery from 'jquery';

class PersonalLog extends Component {

	render() {
    if(!this.props.token) {
      return <h1>You should be a user to show them!</h1>;
    }

    if(jQuery.isEmptyObject(this.props.personalLog)) {
      this.props.actions.load(this.props.params.id, this.props.token);
    }

    const eaten = this.props.lastWeekEaten.map((meal) =>
      <p key={meal.id}>{meal.id}</p>);
    var logs;
    for(var prop in this.props.PersonalLog)
      logs.push(<p key={prop}>{prop}</p>);


		return (

			<div className="container">

			<div className="col-xm-6">
			<h3>Name of the meal:</h3>
      <p>{logs} </p>
			</div>
			<div className="col-xm-6">
			<h3>Amount of Calories:</h3>
      {eaten}
			</div>


			</div>
		);
	}
}

var mapStateToProps = function(state){
    return {
        token: state.token,
        personalLog:state.personalLog,
        lastWeekEaten: state.lastWeekEaten
    };
}
var mapActionsToProps = function(dispatch) {
    return { actions: bindActionCreators(actions, dispatch) };
}

export default connect(mapStateToProps, mapActionsToProps)(PersonalLog);
