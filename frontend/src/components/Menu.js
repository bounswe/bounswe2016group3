import React, { Component } from 'react';

import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';

import * as actions from '../actions/Menu';

class Menu extends Component {
    componentDidMount() {
        this.props.actions.load(this.props.params.id);
    }

    render() {
        let mealsHtml = this.props.meals.map(function(m){
            return <li key={m.id}><a href={`/meal/${m.id}/`}>{m.name}</a></li>;
        });

        return <div className="col-xs-12">
            <h2>{this.props.menu.name}</h2>
            <h2>Meals</h2>
            <ul>{mealsHtml}</ul>
        </div>;
    }
}

var mapStateToProps = function(state) {
    return { 
        menu: state.menu,
        meals: state.meals
    };
};

var mapActionsToProps = function(dispatch) {
    return { actions: bindActionCreators(actions, dispatch) };
}

export default connect(mapStateToProps, mapActionsToProps)(Menu);
