import React, { Component } from 'react';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';

import * as actions from '../actions/Profile';

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
        let dietTypes=["EGG_DIARY_VEG","GLUTEN_FREE ","NO_MUSHROOM_OR_RED_MEAT ","NO_NUTS" ,"OMNIVORE" ,"PALEO" ,"VEGAN"];
        let dietTypes_chbx= "";

        for (var i = dietTypes.length - 1; i >= 0; i--) {
                                   dietTypes_chbx= dietTypes_chbx+"<input type='checkbox' id='"+i+"' value='"+i+"' />"+ dietTypes[i]
        }
        
         
        if(current.id === profile.id) {
            followButton = <div></div>;
        } else {
            if(this.props.following.some(function(u){ return u.id === current.id})){
                followButton = <button type="button" className="btn btn-default disabled">Follow</button>
            } else {
                followButton = <button type="button" className="btn btn-default" onClick={followUser}>Follow</button>
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
                    </div>
                     <div className="row">
                        <div className="col-xs-6">
                            <h3>Diet Type</h3>
                              {dietTypes[profile.dietType]}
                            <button type="button" className="btn btn-default" onClick={""}>Update </button>
                        <ul> 
                         <li> <input type="checkbox" id="diet_0" value="0" name="diet_chkbx"/>  {dietTypes[0]} </li> 
                          <li> <input type="checkbox" id="diet_1" value="1" name="diet_chkbx" />  {dietTypes[1]} </li> 
                        <li> <input type="checkbox" id="diet_2" value="2" name="diet_chkbx" />  {dietTypes[2]} </li> 
                          <li> <input type="checkbox" id="diet_3" value="3" name="diet_chkbx" />  {dietTypes[3]} </li> 
                          <li> <input type="checkbox" id="diet_4" value="4" name="diet_chkbx"/>  {dietTypes[4]} </li> 
                          <li> <input type="checkbox" id="diet_5" value="5" name="diet_chkbx"/>  {dietTypes[5]} </li> 
                           <li> <input type="checkbox" id="diet_6" value="6" name="diet_chkbx"/>  {dietTypes[6]} </li> 

                        </ul>

                    
                              
                            
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