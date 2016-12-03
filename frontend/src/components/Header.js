
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router';
import * as actions from '../actions/Header';
import { apiUrl } from '../config';
import $ from 'jquery';
import { bindActionCreators } from 'redux';
import './header.css';

var Header = function(props){

    var buildSearchListElement = function(result){
        var baseUrl = document.location.href.split("/")[0];
        if(result.userId!=null && result.userId!=''){ // if search result is meal
            var serverOfMeal={};
            $.ajax({
                url: apiUrl+"/user/"+result.userId,
                dataType: 'json',
                cache: false,
                success: function(data) {
                    var listElement ="<a class='search_list_element' href='"+baseUrl+"/meal/"+result.id+"'>"+result.name+" - "+data.fullName+"</a>";
                    $("#search_results").append(listElement);
                },
                error: function(xhr, status, err) {
                    alert("AJAX ERROR");
                }
            });
        }
        else {
            var direction = "";
            if(result.userType==0){ //regular user
                direction = "user";
            }
            else{
                direction ="foodServer";
            }
            var listElement ="<a class='search_list_element' href='"+baseUrl+"/"+direction+"/"+result.id+"'>"+result.fullName+"</a>";
            $("#search_results").append(listElement);
        }
    }
var fillSearchResults = function(results){
    console.log(results);
    $("#search_results").show();
    $(".search_list_element").remove();
    var i;
    for(i=0;i<results.length;i++){
        buildSearchListElement(results[i]);
    }
}
    
    $("#search_input").keyup(function(event){
        $("#search_results").hide();
        submitSearch();
    })
    $("#search_input").keydown(function(event){
        submitSearch();
    })

    var submitSearch = function(){
        var mealResults;
        var userResults;
        var query = $("#search_input").val();
        if(query == null || query == ''){
            $(".search_list_element").remove();
            return;
        }
        $.ajax({
            url: apiUrl+"/meal/search/"+query,
            dataType: 'json',
            cache: false,
            success: function(data) {
                fillSearchResults(data)
            },
            error: function(xhr, status, err) {
                alert("AJAX ERROR");
            }
        });
        $.ajax({
            url: apiUrl+"/user/search/"+query,
            dataType: 'json',
            cache: false,
            success: function(data) {
                fillSearchResults(data)
            },
            error: function(xhr, status, err) {
                alert("AJAX ERROR");
            }
        });
    }

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
    
    if(props.uid == 0){
        userHeader = (
            <ul className="nav navbar-nav navbar-right">
                { loginLinkTags }
            </ul>
        );
    } else {
        
        if(props.name){
            if(props.userType == 0){ //user
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
                    <li><Link to="">Loading ...</Link></li>
                </ul>
            );
        }
    }

    return (
        <nav className="navbar navbar-inverse">
            <div className="container-fluid">
            <div className="col-sm-1">
                <div className="navbar-header">
                    <Link className="navbar-brand" to="/">
                        <img src="/logo.png" alt="Logo" />
                    </Link>
                </div>
                </div>
                <div className="col-sm-2">
                <ul className="nav navbar-nav navbar">
                    { linkTags }
                </ul>
                </div>
                <div className="col-sm-2">

                <ul className="nav navbar-nav navbar">
                    <li><input type="search" className="form-control" placeholder="Search" id="search_input" /></li>
                    <li><button type = 'button' onClick={submitSearch}>Search</button></li>
                    <li>
                        <div id="search_results">
                        </div>
                    </li>
                </ul>
                </div>

                <div className="col-sm-7">
                <ul className="nav navbar-nav navbar">
                    <li><input type="email" className="form-control" placeholder="E-mail" id="login-email2" /></li>
                    <li><input type="password" className="form-control" placeholder="Password" id="login-pass2"/></li>
                    <li><button className="btn btn-default" type="button" onClick={submitForm1} >Login</button></li>
                </ul>
                
                

                { userHeader }

            </div>
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
        avatar: state.currentUser.avatarUrl,
        searchMeal: state.searchMeal,
        searchUser: state.searchUser,
        searchUserofMeal : state.userById
    };
}

var mapDispatchToProps = function(dispatch){
    return { actions: bindActionCreators(actions, dispatch) };
}

export default connect(mapStateToProps, mapDispatchToProps)(Header);
