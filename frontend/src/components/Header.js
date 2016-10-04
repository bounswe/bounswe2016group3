import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router';
import { Navbar } from 'react-bootstrap';
import * as actions from '../actions/Header';
import { bindActionCreators } from 'redux';

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
                    <li>
                        <Link to={`/user/${props.uid}`}>
                            <img src={props.avatar} alt="Avatar for user {props.uid}" className="avatar-sm" />
                            {props.name}
                        </Link>
                    </li>
                    <li>
                        <a href="#" onClick={props.actions.logout()}>Logout</a>
                    </li>
                </ul>
            );
        } else {
            userHeader = (
                <ul className="nav navbar-nav navbar-right">
                    <li><Link to="#">Loading ...</Link></li>
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
        uid: state.currentUser.hasOwnProperty('id')?state.currentUser.id:0, 
        name: state.currentUser.fullName, 
        avatar: state.currentUser.avatarUrl
    };
}

var mapDispatchToProps = function(dispatch){
    return { actions: bindActionCreators(actions, dispatch) };
}

export default connect(mapStateToProps, mapDispatchToProps)(Header);
