import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router';

var Header = function(props){
    var userHeader = null;

    let links = [
        { text: "Home", path: "/" },
        { text: "All users", path: "/users" }
    ];

    let loginLinks = [
        { text: "Login", path: "/login" },
        { text: "Signup", path: "/signup" }
    ];

    let linkTags = links.map(function(link){
        let curr = link.path === props.location.pathname;
        let classes = curr ? "active" : "";

        return <li key={link.path} className={classes}><Link to={link.path}>{link.text}</Link></li>;
    });

    let loginLinkTags = loginLinks.map(function(link){
        let curr = link.path === props.location.pathname;
        let classes = curr ? "active" : "";

        return <li key={link.path} className={classes}><Link to={link.path}>{link.text}</Link></li>;
    });

    if(props.uid === 0){
        userHeader = (
            <ul className="nav navbar-nav navbar-right">
                { loginLinkTags }
            </ul>
        );
    } else {
        if(props.name){
            userHeader = (
                <ul className="nav navbar-nav navbar-right">
                    <li><Link to="/user/{props.uid}">{props.name}</Link></li>
                </ul>
            );
        } else {
            userHeader = (
                <ul className="nav navbar-nav navbar-right">
                    <li><Link to="/user/{props.uid}">Loading ...</Link></li>
                </ul>
            );
        }
    }

    return (
        <nav className="navbar navbar-default">
            <div className="container-fluid">
                <div className="navbar-header">
                    <Link className="navbar-brand" to="/">Test</Link>
                </div>
                <ul className="nav navbar-nav">
                    { linkTags }
                </ul>
                { userHeader }
            </div>
        </nav>
    );
}

var mapStateToProps = function(state){
  return { 
      token: state.token,
      name: state.fullName,
      uid: state.userId 
    };
}

var mapDispatchToProps = function(dispatch){
    return {};
}

export default connect(mapStateToProps, mapDispatchToProps)(Header);
