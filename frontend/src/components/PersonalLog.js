import React, { Component } from 'react';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import * as actions from '../actions/PersonalLog';
import './personalLog.css'
import jQuery from 'jquery';

class ListMeals extends Component {
  constructor(props) {
    super(props);
  }

  render() {

  }
}




class PersonalLog extends Component {

  constructor() {
    super();
    this.changeNumber = this.changeNumber.bind(this);
  }


  changeNumber(rating) {
    const rate = parseFloat(rating);
    return parseFloat(rate.toFixed(1));
  }

	render() {
    if(!this.props.token) {
      return <h1>You should be a user to see them!</h1>;
    }

    if(jQuery.isEmptyObject(this.props.personalLog)) {
      this.props.actions.load(this.props.params.id, this.props.token);
    }
    console.log(this.props.lastWeekEaten);
    const eaten = this.props.lastWeekEaten.map((meal) =>
      <li key={meal.id}>{meal.name}</li>);
		return (

      <div className="container">
        <div className="col-xm-6" className="log_div">
          <h3>Meals Checkate for one week</h3>
          <ul> {eaten} </ul>
        </div>
        <div className="col-xm-6" className="log_div">
          <h3>Total Nutrients for one week</h3>
          <ul className="nutritients">
          <li>Weight:<span> {this.changeNumber(this.props.personalLog.weight)} g</span></li>
          <li>Calories:<span> {this.changeNumber(this.props.personalLog.calories)} kcal</span></li>
          <li>Total Fat:<span> {this.changeNumber(this.props.personalLog.totalFat)} g</span></li>
          <li>Saturated Fat:<span> {this.changeNumber(this.props.personalLog.saturatedFat)} g</span></li>
          <li>Cholesterol:<span> {this.changeNumber(this.props.personalLog.cholesterol)} mg</span></li>
          <li>Sodium:<span> {this.changeNumber(this.props.personalLog.sodium)} mg</span></li>
          <li>Total Carbohydrate:<span> {this.changeNumber(this.props.personalLog.totalCarbohydrate)} g</span></li>
          <li>Dietary Fiber:<span> {this.changeNumber(this.props.personalLog.dietaryFiber)} g</span></li>
          <li>Sugars:<span> {this.changeNumber(this.props.personalLog.sugars)} g</span></li>
          <li>Protein:<span> {this.changeNumber(this.props.personalLog.protein)} g</span></li>
          <li>Potassium:<span> {this.changeNumber(this.props.personalLog.potassium)} mg</span></li>
          <li>Phosphorus:<span> {this.changeNumber(this.props.personalLog.phosphorus)} mg</span></li>
          </ul>
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
