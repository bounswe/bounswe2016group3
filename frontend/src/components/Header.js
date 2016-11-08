import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router';
import * as actions from '../actions/Header';

import { bindActionCreators } from 'redux';
import './header.css';

var Header = function(props){
    var userHeader = null;
    var submitForm1 = function(e) {
        let email = document.getElementById("login-email2");
        let password = document.getElementById("login-pass2");

        if(email&&password){
             
            props.actions.submit(email.value , password.value);

       
        }

        e.preventDefault();
    }
if(props.success){
        props.history.pushState(null,"/user/1");
    }
     
    let links = [
        { text: "Home", path: "/" },
        { text: "All users", path: "/user/all" }
    ];

    let loginLinks = [
        //{ text: "Login", path: "/login" },
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
            
            if(props.userType == 1){ //user
                 userHeader = (
                <ul className="nav navbar-nav navbar-right">

                    <li>
                        <Link to={`/user/${props.uid}`}>
                            <img src={props.avatar} alt="Avatar for user {props.uid}" className="avatar-sm" />
                            {props.name}
                        </Link>
                    </li>
                    <li>
                        <a href="#" onClick={() => props.actions.logout(props.token) }>Logout</a>
                    </li>
                </ul>
            );
            } 
             else{ //food server
            alert("hereee");
            userHeader = (
                <ul className="nav navbar-nav navbar-right">

                    <li>
                        <Link to={`/foodServer/${props.uid}`}>
                            <img src={props.avatar} alt="Avatar for user {props.uid}" className="avatar-sm" />

                            {props.name}
                        </Link>
                    </li>
                    <li>
                        <a href="#" onClick={() => props.actions.logout(props.token) }>Logout</a>
                    </li>
                </ul>
            );
        }
        } 

        else {
            userHeader = (
                <ul className="nav navbar-nav navbar-right">
                    <li><Link to="#">Loading ...</Link></li>
                </ul>
            );
        }
    }

    return (
        <nav className="navbar navbar-inverse">
            <div className="container-fluid">
                <div className="navbar-header">
                    <Link className="navbar-brand" to="/">
                        <img src="/logo.png" alt="Logo" />
                    </Link>
                </div>
                <ul className="nav navbar-nav">

              
                    { linkTags }
                      <input type="email" className="form-control" placeholder="E-mail" id="login-email2" />
                <input type="password" className="form-control" placeholder="Password" id="login-pass2"/>
                <button className="btn btn-default" type="button" onClick={submitForm1} >Login</button>
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
        userType: state.currentUser.userType,
        avatar: state.currentUser.avatarUrl
    };
}

var mapDispatchToProps = function(dispatch){
    return { actions: bindActionCreators(actions, dispatch) };
}

export default connect(mapStateToProps, mapDispatchToProps)(Header);
