import React, { Component } from 'react';

class FollowButton extends Component {
  constructor(props) {
    super(props);
    this.state = {
      content: "Following",
      className: "btn btn-primary"
    };
    this.following = this.following.bind(this);
    this.unfollowing = this.unfollowing.bind(this);
  }

  unfollowing() {
    this.setState({
      content: "Unfollow",
      className: "btn btn-danger"
    });
  }

  following() {
    this.setState({
      content: "Following",
      className: "btn btn-primary"
    });
  }

  render() {
    if (this.props.isFollow === null)
      return null;
    if (this.props.isFollow === false) {
      return (<button type="button" className="btn btn-default"
        onClick={this.props.follow}>Follow</button>);
    }
    return (<button type="button" className={this.state.className}
      onClick={this.props.unfollow} onMouseEnter={this.unfollowing}
      onMouseLeave={this.following}> {this.state.content} </button>);
  }
}

export default FollowButton;
