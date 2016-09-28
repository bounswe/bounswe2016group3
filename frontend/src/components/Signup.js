import React, { Component } from 'react';

class Signup extends Component {
    render(){
        return (
            <div>
                <p>
                    <input type="text" className="form-control" placeholder="Username" ref={(r) => {this.username = r;}}/>
                </p>
                <p>
                    <input type="text" className="form-control" placeholder="Full Name" ref={(r) => {this.fullName = r;}}/>
                </p>
                <p>
                    <input type="password" className="form-control" placeholder="Password" ref={(r) => {this.password = r;}}/>
                </p>
                <p>
                    <input type="password" className="form-control" placeholder="Confirm Password" ref={(r) => {this.confirm = r;}}/>
                </p>

                <p>
                    <button className="btn btn-default" type="button" onClick={this.props.submit}>Signup</button>
                </p>
            </div>
        );
    }
}

export default Signup;