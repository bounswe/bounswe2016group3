import React, { Component } from 'react';

import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import * as actions from '../actions/Meal';
import './meal.css';
import $ from 'jquery';

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
            	document.location.href = document.location.href  ;
            }
        }

        let rate_meal=document.getElementById("rate_meal");
		let rate = () => {
            if(this.props.token!==""){
            	 $("#stars").find("rating5").each(function(){
                     if ($(this).prop('checked')==true){ 
                         
               }
               alert(this.name);
});

            	this.props.actions.rate(this.props.token,this.props.params.id,this.props.currentUser.id,rate_meal.value);
            	//ratingssHtml=this.props.ratings.average ;
            	//document.location.href = document.location.href  ;
            	//rateButton=<button type="button" className="btn btn-default disabled" onClick={""}>Rate</button>;
            }
        }        

        let checkeatButton=<button type="button" className="btn btn-default" onClick={checkeat}>Check Eat!</button>;
       
      
        let commentsHtml = this.props.comments.map(function(u){
        
            return <li key={u.id}> {u.content}</li>;
        });
       let ratingssHtml=this.props.ratings.average ;
        let commentButton=<button type="button" className="btn btn-default" onClick={comment}>Comment</button>;
        let rateButton=<button type="button" className="btn btn-default" onClick={rate}>Rate</button>;
        return  <div className="col-xs-6">
            
            <h2>{this.props.meal.name}</h2>
            {checkeatButton}
            <p>{this.props.meal.description}</p>
            
            <h2>Ingredients</h2>
            <p>{this.props.meal.ingredients}</p>
           
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
             <p>{rateButton}</p> 

             </div>
             <span className="starRating" id="stars">
                  <input id="rating5" type="radio" name="rating" value="5"></input>
                  <label for="rating5">5</label>
                  <input id="rating4" type="radio" name="rating" value="4"></input>
                  <label for="rating4">4</label>
                  <input id="rating3" type="radio" name="rating" value="3"></input>
                  <label for="rating3">3</label>
                  <input id="rating2" type="radio" name="rating" value="2"></input>
                  <label for="rating2">2</label>
                  <input id="rating1" type="radio" name="rating" value="1"></input>
                  <label for="rating1">1</label>
                </span>  
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
        currentUser: state.currentUser

    };
};

var mapActionsToProps = function(dispatch) {
    return { actions: bindActionCreators(actions, dispatch) };
}

export default connect(mapStateToProps, mapActionsToProps)(Meal);
