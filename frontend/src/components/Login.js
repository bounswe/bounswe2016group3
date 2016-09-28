import React, { Component } from 'react';

class Login extends Component {
    render() {
        return (
            <div>
                <p>
                    <input type="text" className="form-control" placeholder="Username" ref={(r) => {this.username = r;}}/>
                </p>
                <p>
                    <input type="password" className="form-control" placeholder="Password"  ref={(r) => {this.username = r;}}/>
                </p>

                <p>
                    <button className="btn btn-default" type="button" onClick={this.props.submit}  ref={(r) => {this.username = r;}}>Login</button>
                </p>
            </div>
        );
    }
}

export default Login;