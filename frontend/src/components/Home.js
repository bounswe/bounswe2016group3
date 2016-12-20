import React from 'react';
import './Home.css';

var Home = function(props){
    return (
    	<div className="row">
        <div className="container">
        <img src="/logo.png" alt="Logo" id="this" />
            <h3><strong>A place to simplify your nutritional needs</strong></h3>
            <div className="col-sm-4">

            </div>

            <div className="col-sm-2">
            <p><a className="btn btn-primary" href="/signup">Signup as a regular user</a></p>
            </div>
            <div className="col-sm-2">

            <p><a className="btn btn-primary"href="/signup">Signup as a food server</a></p>
            </div>
            <div className="col-sm-4">
            </div>
        </div>
        </div>
    );
}

export default Home;