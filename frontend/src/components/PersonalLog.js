import React, { Component } from 'react';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import * as actions from '../actions/PersonalLog';
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
        <div className="col-xm-6">
          <h3>Meals Checkate for one week</h3>
          <ul> {eaten} </ul>
        </div>
        <div className="col-xm-6">
          <h3>Total Nutrients for one week</h3>
          <p>Weight: {this.changeNumber(this.props.personalLog.weight)} g<br></br>
          Calories: {this.changeNumber(this.props.personalLog.calories)} kcal<br></br>
          Total Fat: {this.changeNumber(this.props.personalLog.totalFat)}g<br></br>
          Saturated Fat: {this.changeNumber(this.props.personalLog.saturatedFat)}g<br></br>
          Cholesterol: {this.changeNumber(this.props.personalLog.cholesterol)}mg<br></br>
          Sodium: {this.changeNumber(this.props.personalLog.sodium)}mg<br></br>
          Total Carbohydrate: {this.changeNumber(this.props.personalLog.totalCarbohydrate)}g<br></br>
          Dietary Fiber: {this.changeNumber(this.props.personalLog.dietaryFiber)}g<br></br>
          Sugars: {this.changeNumber(this.props.personalLog.sugars)}g <br></br>
          Protein: {this.changeNumber(this.props.personalLog.protein)}g <br></br>
          Potassium: {this.changeNumber(this.props.personalLog.potassium)}mg <br></br>
          Phosphorus: {this.changeNumber(this.props.personalLog.phosphorus)}mg <br></br> </p>
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
