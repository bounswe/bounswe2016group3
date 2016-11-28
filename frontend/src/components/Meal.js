import React, { Component } from 'react';

import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import $ from 'jquery';
import * as actions from '../actions/Meal';

class Meal extends Component {
    componentDidMount() {
        this.props.actions.load(this.props.params.id);
    }

    render() {
        return <div className="col-xs-12">
            <h2>{this.props.meal.name}</h2>
            <p>{this.props.meal.description}</p>
           
           
            <p>{this.props.meal.ingredients}</p>
            
            <img src={this.props.meal.photoUrl} alt="Avatar for user {props.uid}" className="avatar-m" />
        </div>;
    }
}

var mapStateToProps = function(state) {
    return { 
        meal: state.meal,
        comments: state.comments
    };
};

var mapActionsToProps = function(dispatch) {
    return { actions: bindActionCreators(actions, dispatch) };
}

export default connect(mapStateToProps, mapActionsToProps)(Meal);
