import React, { Component } from 'react';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';

import * as actions from '../actions/FoodServerProfile';

class Profile extends Component {
    componentDidMount(){
        this.props.actions.load(this.props.params.id);
    }

    render(){
        if(!this.props.profile || this.props.profile === {}){
            return (
                <article className="col-xs-12">
                    <p>
                        <i className="fa fa-circle-o-notch fa-spin fa-3x fa-fw"></i>
                        Loading...
                    </p>
                </article>
            );
        }

        const profile = this.props.profile;
        const current = this.props.currentUser;

        var followUser = () => {
            if(this.props.token!==""){
                this.props.actions.follow(this.props.token, profile, this.props.currentUser);
            }
        }
        
        let meal_name=document.getElementById("meal_name");
        let meal_description=document.getElementById("meal_description");

        var addMeal = () => {
            if(this.props.token!==""){
                this.props.actions.addmeal(this.props.token,this.props.profile.id, 1, meal_name.value,meal_description.value,"","");
            }
        }


        let followersHtml = this.props.followers.map(function(u){
            return <li key={u.id}><a href={`/user/${u.id}/`}>{u.fullName}</a></li>;
        });

        let followingHtml = this.props.following.map(function(u){
            return <li key={u.id}><a href={`/user/${u.id}/`}>{u.fullName}</a></li>;
        });

        let menusHtml = this.props.menus.map(function(m){
            return <li key={m.id}><a href={`/menu/${m.id}/`}>{m.name}</a></li>;
        });
        
        let followButton;
        let addmealButton;
        if(current.id === profile.id) {
            followButton = <div></div>;
            addmealButton=<button type="button" className="btn btn-default" onClick={addMeal}>Add Meal</button>;
        } else {
            if(this.props.following.some(function(u){ return u.id === current.id})){
                followButton = <button type="button" className="btn btn-default disabled">Follow</button>
            } else {
                followButton = <button type="button" className="btn btn-default" onClick={followUser}>Follow</button>
                addmealButton=<button type="button" className="btn btn-default disabled" onClick={addMeal}>Add Meal</button>
            }
        }

        return (
            <div>
                <div className="col-xs-4">
                    <img src={profile.avatarUrl} alt="avatar"/>
                </div>
                <div className="col-xs-8">
                    <h1>{profile.fullName}</h1>
                    <p>{profile.bio}Hello from the otherside..</p>
                    
                    <p>{followButton}</p>
                    <div className="row">
                        <div className="col-xs-6">
                            <h3>Followers: {followersHtml.length}</h3>
                          
                        </div>
                        <div className="col-xs-6">
                            <h3>Following: {followingHtml.length}</h3>

                        </div>

                    </div>
                    <hr></hr>
                    <div className="row">
                        <div className="col-xs-6">
                            <h3>Preferences</h3>
                           
                        </div>
                        <div className="col-xs-6">
                            <h3>Menus</h3>
                           {menusHtml}
                            <input type="text" className="form-control" placeholder="Meal name" id="meal_name" />
                             <input type="text" className="form-control" placeholder="Meal desc" id="meal_description" />
                             
                            <p>{addmealButton}</p>
                        </div>
                    </div>
                     <div className="row">
                        <div className="col-xs-6">
                            <h3>Diet Type</h3>
                            <ul></ul>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

var mapStateToProps = function(state){
    return {
        token: state.token,
        profile: state.profile,
        followers: state.followers,
        following: state.following,
        menus: state.menus,
        currentUser: state.currentUser
    };
}

var mapDispatchToProps = function(dispatch){
    return { actions: bindActionCreators(actions, dispatch) };
}

export default connect(mapStateToProps, mapDispatchToProps)(Profile);