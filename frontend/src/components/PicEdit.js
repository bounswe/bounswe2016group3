import React, { Component } from 'react';


class PicEdit extends Component {
  constructor() {
    super();
    this.try = this.try.bind(this);
  }

  render() {
    return (
      <button type="button" className="btn btn-default" onClick={this.try}>
        Edit Picture
      </button>
    );
  }


  try() {
    alert('Hello World!');
  }
}

export default PicEdit;
