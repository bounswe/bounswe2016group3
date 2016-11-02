import React, { Component } from 'react';

import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';

import * as actions from '../actions/Menu';

class Menu extends Component {
    componentDidMount() {
        this.params.actions.load(this.props.params.id);
    }

    render() {

    }
}

var mapStateToProps = function(state) {

};

var mapActionsToProps = function(dispatch) {
    return { actions: bindActionCreators(actions, dispatch) };
}

export default connect(mapStateToProps, mapActionsToProps)(Menu);
