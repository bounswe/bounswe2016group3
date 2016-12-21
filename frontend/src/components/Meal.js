import React, { Component } from 'react';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import * as actions from '../actions/Meal';
import './meal.css';
import './rateYo/jquery.rateyo.css';
import './rateYo/jquery.rateyo';
import $ from 'jquery';
import TagButton from './TagButton';

class RateStar extends Component {
  constructor(props) {
    super(props);
    this.changeNumber = this.changeNumber.bind(this);

  }

  changeNumber(rating) {
    const rate = parseFloat(rating);
    return parseFloat(rate.toFixed(1));
  }

  render() {
    var stars = this.refs.rateYo;
   var rating = this.changeNumber(this.props.props.ratings.average);

   var rate = (e) => {
     e.preventDefault();
      if(this.props.props.token!==""){
         this.props.props.actions.rate(this.props.props.token,this.props.props.params.id,this.props.props.currentUser.id,5);

       }
    }

    if(!rating) rating=0;
    $(stars).rateYo({
      rating: rating,
      readOnly: false
    });
    /*
    if (this.props.ratings.current) {
      $(stars).rateYo("option", "readOnly", true);

    }
    else {
      $(stars).rateYo()
      .on("rateyo.set", function (e, data) {
        e.preventDefault();
        $(this).next().text(data.rating);
        rating = data.rating;
      });
    }
    */
    return (

      <div>
        <div ref="rateYo" onClick={(e) => rate(e)}></div>
      <p> Current rating : {rating} </p>
      </div>
    );
  }
}



class Meal extends Component {
  componentDidMount() {
  }


  render() {

    if(this.props.token!="" &&this.props.meal.ingredients===undefined){
      this.props.actions.load(this.props.token,this.props.params.id);

    }
    let checkeat = () => {
      if(this.props.token!==""){

        this.props.actions.checkeat(this.props.token,this.props.params.id);
      }
    }
    let comment_meal=document.getElementById("comment_meal");
    let comment = () => {
      if(this.props.token!==""){

        this.props.actions.comment(this.props.token,this.props.params.id,this.props.currentUser.id,comment_meal.value);

      }
    }


    let checkeatButton=<button type="button" className="btn btn-default" onClick={checkeat}>Check Eat!</button>;


    let commentsHtml = this.props.comments.map(function(u){

      return <li key={u.id}> {u.content}</li>;
    });
    let ratingssHtml=this.props.ratings.average ;
    let commentButton=<button type="button" className="btn btn-default" onClick={comment}>Comment</button>;
   // let rateButton=<button type="button" className="btn btn-default" onClick={""}>Rate</button>;

    return  <div className="col-xs-6">

      <h2>{this.props.meal.name}</h2>
      {checkeatButton}
      <TagButton token={this.props.token}/>
      <p>{this.props.meal.description}</p>

      <h2>Ingredients</h2>
      <p>{this.props.meal.ingredients}</p>
      <h2>Nutritional Info</h2>
      <p>Weight: {this.props.nutritionInfo.weight} g<br></br>
      Calories: {this.props.nutritionInfo.calories} kcal<br></br>
      Total Fat: {this.props.nutritionInfo.totalFat}g<br></br>
      Saturated Fat: {this.props.nutritionInfo.saturatedFat}g<br></br>
      Cholesterol: {this.props.nutritionInfo.cholesterol}mg<br></br>
      Sodium: {this.props.nutritionInfo.sodium}mg<br></br>
      Total Carbohydrate: {this.props.nutritionInfo.totalCarbohydrate}g<br></br>
      Dietary Fiber: {this.props.nutritionInfo.dietaryFiber}g<br></br>
      Sugars: {this.props.nutritionInfo.sugars}g <br></br>
      Protein: {this.props.nutritionInfo.protein}g <br></br>
      Potassium: {this.props.nutritionInfo.potassium}mg <br></br>
      Phosphorus: {this.props.nutritionInfo.phosphorus}mg <br></br> </p>
      <img src={this.props.meal.photoUrl} alt="Avatar for user {props.uid}" className="avatar-m" />

      <div className="row">
        <div className="col-xs-6">
          <h3> Comments </h3>
          <p>{commentsHtml}</p>
          <input type="text" className="form-control" placeholder="Share your thoughts" id="comment_meal" />
          <p>{commentButton}</p>
        </div>
        <div className="col-xs-6">
          <h3> Ratings </h3>
          {ratingssHtml} out of 5
          <input type="text" className="form-control" placeholder="Rate" id="rate_meal" />
          <RateStar props={this.props}/>

        </div>
      </div>

    </div>
  }

}

var mapStateToProps = function(state) {
    return {
        token: state.token,
        profile: state.profile,
        meal: state.meal,
        comments: state.comments,
        ratings: state.ratings,
        rate: state.rate,
        currentUser: state.currentUser,
        nutritionInfo: state.nutritionInfo

    };
};

var mapActionsToProps = function(dispatch) {
    return { actions: bindActionCreators(actions, dispatch) };
}

export default connect(mapStateToProps, mapActionsToProps)(Meal);
