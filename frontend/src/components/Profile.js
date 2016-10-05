import React, { Component } from 'react';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import { Link } from 'react-router'

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

        let profile = this.props.profile;

        return (
            <div>
                <div className="col-xs-4">
                    <img src = {profile.avatarUrl} />
                </div>
                <div className="col-xs-8">
                    <h1>{profile.fullName}</h1>
                    <p>{profile.bio}</p>
                    <p>{profile.email}</p>
                </div>
            </div>
        );
    }
}

var mapStateToProps = function(state){
    return {
        profile: state.profile
    };
}

var mapDispatchToProps = function(dispatch){
    return { actions: bindActionCreators(actions, dispatch) };
}

export default connect(mapStateToProps, mapDispatchToProps)(Profile);