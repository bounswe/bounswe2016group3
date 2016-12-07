import React, { Component } from 'react';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import {
  Modal,
  ModalHeader,
  ModalTitle,
  ModalClose,
  ModalBody,
  ModalFooter
} from 'react-modal-bootstrap';
import $ from 'jquery';
import * as actions from '../actions/Profile';
import PicEdit from './PicEdit';

class Profile extends Component {
    componentDidMount(){
        this.props.actions.load(this.props.params.id);
        console.log(this.props.profile);
        console.log(this.props.currentUser);
    }
    state = {
    isOpen: false,
    isOpen_Include: false,
    isOpen_Exclude: false
    };

    openModal = () => {this.setState({isOpen: true});};
    hideModal = () => { this.setState({isOpen: false});};

    openModal_Include = () => { this.setState({ isOpen_Include: true });};
    hideModal_Include = () => { this.setState({ isOpen_Include: false});};

    openModal_Exclude = () => { this.setState({ isOpen_Exclude: true });};
    hideModal_Exclude = () => { this.setState({ isOpen_Exclude: false});};

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
        let names=["onion","pepper","tomato","welcome!!!"];
        var includeNames = () => {
          if(this.props.token !== "") {
            this.props.actions.include(profile.id, this.props.token,names);
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
        let updatePreferencesModalButton
        let updateIncludeModalButton
        let updateExcludeModalButton
        if(current.id === profile.id) {
            followButton = <div></div>;
            updatePreferencesModalButton =<button type="button" className="btn btn-success" onClick={this.openModal}>Update</button>
            updateIncludeModalButton =<button type="button" className="btn btn-success" onClick={this.openModal_Include}>Update</button>
            updateExcludeModalButton =<button type="button" className="btn btn-success" onClick={this.openModal_Exclude}>Update</button>
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
                    {
                      current.id === profile.id && <div>
                        <p></p>
                        <PicEdit />
                      </div>
                    }
                </div>
                <div className="col-xs-8">
                    <h1>{profile.fullName}</h1>
                    <p>{profile.bio}</p>

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
                            <h4>Include</h4>
                            {this.props.include}
                            {updateIncludeModalButton}
                            <Modal isOpen={this.state.isOpen_Include} onRequestHide={this.hideModal_Include} id="updateInclude_modal">
                                <ModalHeader>
                                    <ModalClose onClick={this.hideModal_Include}/>
                                    <ModalTitle><h3>Include</h3></ModalTitle>
                                </ModalHeader>
                                <ModalBody>

                               <div className="col-xs-12" id="ingredients_list_include">
                                        <div className="ingredients_list_element" id="ingredients_list_element">
                                            <div className="col-xs-8">
                                                <input type="text" id="name" className="form-control" placeholder="Name"/>
                                            </div>
                                           <div className="col-xs-12">
                                    <button type="button" className="btn-success more_button" onClick={""}>More</button>
                                    </div>
                                        </div>
                                    </div>
                                </ModalBody>
                                <ModalFooter>
                                    <button type="button" className="btn btn-default" onClick={includeNames}>Update </button>
                                    <button className='btn btn-default' onClick={this.hideModal_Include}>Cancel</button>

                                </ModalFooter>
                            </Modal>
                            <h4>Exclude</h4>
                             {updateExcludeModalButton}
                            <Modal isOpen={this.state.isOpen_Exclude} onRequestHide={this.hideModal_Exclude} id="updateExclude_modal">
                                <ModalHeader>
                                    <ModalClose onClick={this.hideModal_Exclude}/>
                                    <ModalTitle><h3>Exclude</h3></ModalTitle>
                                </ModalHeader>
                                <ModalBody>
                                     <div className="col-xs-12" id="ingredients_list_exclude">
                                        <div className="ingredients_list_element" id="ingredients_list_element">
                                            <div className="col-xs-8">
                                                <input type="text" id="name" className="form-control" placeholder="Name"/>
                                            </div>
                                           <div className="col-xs-12">
                                    <button type="button" className="btn-success more_button" onClick={""}>More</button>
                                    </div>
                                        </div>
                                    </div>


                                </ModalBody>
                                <ModalFooter>
                                    <button type="button" className="btn btn-default" onClick={""}>Update </button>
                                    <button className='btn btn-default' onClick={this.hideModal_Exclude}>Cancel</button>

                                </ModalFooter>
                            </Modal>
                        </div>
                    </div>
                     <div className="row">
                        <div className="col-xs-6">
                            <h3>Diet Type</h3>
                            {dietTypes[profile.dietType]}

                             {updatePreferencesModalButton}
                            <Modal isOpen={this.state.isOpen} onRequestHide={this.hideModal} id="updateDietType_modal">
                                <ModalHeader>
                                    <ModalClose onClick={this.hideModal}/>
                                    <ModalTitle><h3>Update Diet Type</h3></ModalTitle>
                                </ModalHeader>
                                <ModalBody>

                                 <ul>
                                 <li> <input type="checkbox" id="diet_0" value="0" name="diet_chkbx"/>  {dietTypes[0]} </li>
                                  <li> <input type="checkbox" id="diet_1" value="1" name="diet_chkbx" />  {dietTypes[1]} </li>
                                 <li> <input type="checkbox" id="diet_2" value="2" name="diet_chkbx" />  {dietTypes[2]} </li>
                                  <li> <input type="checkbox" id="diet_3" value="3" name="diet_chkbx" />  {dietTypes[3]} </li>
                                  <li> <input type="checkbox" id="diet_4" value="4" name="diet_chkbx"/>  {dietTypes[4]} </li>
                                  <li> <input type="checkbox" id="diet_5" value="5" name="diet_chkbx"/>  {dietTypes[5]} </li>
                                   <li> <input type="checkbox" id="diet_6" value="6" name="diet_chkbx"/>  {dietTypes[6]} </li>
                                 </ul>

                                </ModalBody>
                                <ModalFooter>
                                    <button type="button" className="btn btn-default" onClick={""}>Update </button>
                                    <button className='btn btn-default' onClick={this.hideModal}>Cancel</button>

                                </ModalFooter>
                            </Modal>
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
        include: state.include,
        exclude: state.exclude,
        currentUser: state.currentUser
    };
}

var mapDispatchToProps = function(dispatch){
    return { actions: bindActionCreators(actions, dispatch) };
}

export default connect(mapStateToProps, mapDispatchToProps)(Profile);
