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
                alert(this.props.token+ " " +this.props.params.id)  ;  
            }
        }


        let checkeatButton=<button type="button" className="btn btn-default" onClick={checkeat}>Check Eat!</button>;
        
        
      
        let commentsHtml = this.props.comments.map(function(u){
        
            return <li key={u.id}> {u.content}</li>;
        });
        let ratingssHtml=this.props.ratings.average ;
        
        
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
        meal: state.meal,
        comments: state.comments,
        ratings: state.ratings
        

    };
};

var mapActionsToProps = function(dispatch) {
    return { actions: bindActionCreators(actions, dispatch) };
}

export default connect(mapStateToProps, mapActionsToProps)(Meal);
