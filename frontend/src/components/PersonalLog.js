import React, { Component } from 'react';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import * as actions from '../actions/PersonalLog';

class PersonalLog extends Component {
    componentDidMount() {
        this.props.actions.load(this.props.params.id);
    }

	

	render() {
		return (
			<div>
			 <button type="button" className="btn btn-default" onClick={this.changeX}> Log </button>
			</div>
		);
	}
}

export default PersonalLog;