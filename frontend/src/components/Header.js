
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
        if(query == null || query == '' || query.length<3){
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

    var openAdvanceSearch = function(){
        $("#advance_search_div").show();
    }

    var addIncluded = function(){
        $("#add_included_input").val('');
        $("#add_included_div").show();
    }
    var addIncludedSubmit = function(){
        var ingredient = $("#add_included_input").val();
        if(ingredient == null || ingredient==""){
            return false;
        }
        $("#advance_search_included_div").append("<div class='add_included_list_element'>"+ingredient+"</div>");
        $("#add_included_div").hide();
    }
    var addExcluded = function(){
        $("#add_excluded_input").val('');
        $("#add_excluded_div").show();
    }
    var addExcludedSubmit = function(){
        var ingredient = $("#add_excluded_input").val();
        if(ingredient == null || ingredient==""){
            return false;
        }
        $("#advance_search_excluded_div").append("<div class='add_excluded_list_element'>"+ingredient+"</div>");
        $("#add_excluded_div").hide();
    }
    var submitAdvanceSearch = function(){

    }
    var closeAdvanceSearch = function (){
        $(".add_excluded_list_element").remove();
        $(".add_included_list_element").remove();
        $("#add_included_input").val('');
        $("#add_excluded_input").val('');
        $("#calorie_min").val('');
        $("#colorie_max").val('');
        $("#cho_min").val('');
        $("#cho_max").val('');
        $("#protein_min").val('');
        $("#protein_max").val('');
        $("#fat_min").val('');
        $("#fat_max").val('');
        $("#advance_search_div").hide();
    }
    var userHeader = null;

    if(props.uid!=0){
        $("#login_form").hide();
        $("#open_advance_search_button").show();
    }
    else{
        $("#login_form").show();
        $("#open_advance_search_button").hide();
    }
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
            <ul className="nav navbar-nav navbar-right" id="login_form">
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
        <div id="advance_search_div">
                <button type = "button" id="close_advance_search_button" onClick={closeAdvanceSearch}>X</button>
                <div>
                <input className="form-control" type='text' placeholder="Keywords"/>
                </div>
                <div>
                    <input type="radio" id="advance_search_preference_button"/> Change my preferences
                </div>
                <div>
                    <div id="advance_search_included_div">
                        <h4>Included Ingredients</h4>
                        <div id="included_list">
                        </div>
                        <button type='button' className="btn btn-success" onClick={addIncluded}>+</button>
                        <div id="add_included_div">
                            <input type="text" placeholder="Ingredient" id="add_included_input"/>
                            <button type="button" className="btn btn-success" onClick={addIncludedSubmit}>Add</button>
                        </div>
                    </div>
                </div>
                <div>
                    <div id="advance_search_excluded_div">
                        <h4>Excluded Ingredients</h4>
                        <div id="excluded_list">
                        </div>
                        <button type='button' className="btn btn-danger" onClick={addExcluded}>+</button>
                        <div id="add_excluded_div">
                            <input type="text" placeholder="Ingredient" id="add_excluded_input"/>
                            <button type="button" className="btn btn-success" onClick={addExcludedSubmit}>Add</button>
                        </div>
                    </div>
                </div>
                <div>
                    <div id="advance_search_calorie_range_div">
                        Calorie Range
                        <input type="text" className="range_input" placeholder="Max" id="calorie_max"/>
                        <input type="text" className="range_input" placeholder="Min" id="calorie_min"/>
                    </div>
                </div>
                <div>
                    <div id="advance_search_cho_range_div">
                        CHO Range
                        <input type="text" className="range_input" placeholder="Max" id="cho_max"/>
                        <input type="text" className="range_input" placeholder="Min" id="cho_min"/>
                    </div>
                </div>
                <div>
                    <div id="advance_search_protein_range_div">
                        Protein Range
                        <input type="text" className="range_input" placeholder="Max" id="protein_max"/>
                        <input type="text" className="range_input" placeholder="Min" id="protein_min"/>
                    </div>
                </div>
                <div>
                    <div id="advance_search_fat_range_div">
                        Fat Range
                        <input type="text" className="range_input" placeholder="Max" id="fat_max"/>
                        <input type="text" className="range_input" placeholder="Min" id="fat_min"/>
                    </div>
                </div>
                <div>
                    <button type="button" id="advance_search_submit_button" className="btn btn-info" onClick={submitAdvanceSearch}>Search</button>
                </div>

        </div>
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
                <div className="col-sm-4">

                <ul className="nav navbar-nav navbar">
                    <li><input type="search" className="form-control" id="search_input" placeholder="Search" id="search_input" /></li>
                    <li><button type = 'button' className="btn btn-info" id="open_advance_search_button" onClick={openAdvanceSearch}>Advance Search</button></li>
                    <li>
                        <div id="search_results">
                        </div>
                    </li>
                </ul>
                </div>

                <div className="col-sm-4">
                <ul className="nav navbar-nav navbar" id="login_form">
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
