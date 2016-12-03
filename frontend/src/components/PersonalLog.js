import React, { Component } from 'react';







class PersonalLog extends Component {

	constructor() {
		super();
		this.setState  = {
			isOpen: false
		};
		this.changeX = this.changeX.bind(this);
	}

    changeX() {
		this.setState({
			isOpen: !this.state.isOpen
		});
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