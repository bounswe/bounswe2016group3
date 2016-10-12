import React from 'react';

var Home = function(props){
    return (
        <div className="jumbotron">
            <h1>Eatalyze</h1>
            <p>A place to simplify your nutritional needs</p>

            <p><a className="btn btn-primary" href="/signup">Signup as a regular user</a></p>
            <p><a className="btn btn-primary"href="/serverSignup">Signup as a food server</a></p>
        </div>
    );
}

export default Home;