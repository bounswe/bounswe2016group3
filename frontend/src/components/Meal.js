import React, { Component } from 'react';

import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import * as actions from '../actions/Meal';

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
            	alert(this.props.currentUser.id);
            	this.props.actions.comment(this.props.token,this.props.params.id,this.props.currentUser.id,this.props.comment);
            }
        }

        let checkeatButton=<button type="button" className="btn btn-default" onClick={checkeat}>Check Eat!</button>;
        
        
      
        let commentsHtml = this.props.comments.map(function(u){
        
            return <li key={u.id}> {u.content}</li>;
        });
        let ratingssHtml=this.props.ratings.average ;
        let commentButton=<button type="button" className="btn btn-default" onClick={comment}>Comment</button>;
        
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
        currentUser: state.currentUser

    };
};

var mapActionsToProps = function(dispatch) {
    return { actions: bindActionCreators(actions, dispatch) };
}

export default connect(mapStateToProps, mapActionsToProps)(Meal);
