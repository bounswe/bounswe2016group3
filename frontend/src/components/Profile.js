import React, { Component } from 'react';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import './profile.css';
import {
  Modal,
  ModalHeader,
  ModalTitle,
  ModalClose,
  ModalBody,
  ModalFooter
} from 'react-modal-bootstrap';
import $ from 'jquery';
import jQuery from 'jquery';
import * as actions from '../actions/Profile';
import PicEdit  from './PicEdit';
import FollowButton  from './FollowButton';


class Profile extends Component {
  constructor(props) {
    super();
    this.follow = this.follow.bind(this);
    this.unfollow = this.unfollow.bind(this);
    this.state = {
         fullname: null,
         bio:null

    }
    this.updateState = this.updateState.bind(this);
    
   // this.profile= this.profile.bind(this);
  }
  updateState(e) {
      this.setState({fullname: e.target.value});
   }
   updateState_bio(e){
     this.setState({bio: e.target.value});
   }

  componentDidMount(){
   
    this.props.actions.load(this.props.params.id);
  }

  state = {
    isOpen: false,
    isOpen_Include: false,
    isOpen_Exclude: false
  };


  isFollower(followers, currentUser, profile) {

    if (jQuery.isEmptyObject(currentUser) || profile.id === currentUser.id) {
      return null;
    }
    if (followers.some((u) => (u.id === currentUser.id))) {
      return true;
    }
    return false;
  }


  follow() {
    if(this.props.token) {
      this.props.actions.follow(this.props.token,
        this.props.profile, this.props.currentUser);
    }
  }

  unfollow() {
    if(this.props.token) {
      this.props.actions.unfollow(this.props.token, this.props.profile);
    }
  }

  openModal = () => {this.setState({isOpen: true});};
  hideModal = () => { this.setState({isOpen: false});};

  openModal_Include = () => { this.setState({ isOpen_Include: true });};
  hideModal_Include = () => { this.setState({ isOpen_Include: false});};

  openModal_Exclude = () => { this.setState({ isOpen_Exclude: true });};
  hideModal_Exclude = () => { this.setState({ isOpen_Exclude: false});};

  openModal_updateProfile = () => { this.setState({ isOpen_updateProfile: true });  this.setState({fullname: this.props.profile.fullName, bio:this.props.profile.bio});};
  hideModal_updateProfile = () => { this.setState({ isOpen_updateProfile: false});};
  
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


    const current = this.props.currentUser;
    const profile = this.props.profile;
    var moreIngredient_include = () =>{
      $("#ingredients_list_include").append('<div class="ingredients_list_element_include" id="ingredients_list_element_include"><div class="col-xs-8"><input type="text" id="name" class="form-control" placeholder="Name"/></div></div>')

    }
    var moreIngredient_exclude = () =>{
      $("#ingredients_list_exclude").append('<div class="ingredients_list_element_exclude" id="ingredients_list_element_exclude"><div class="col-xs-8"><input type="text" id="name" class="form-control" placeholder="Name"/></div></div>')

    }


    var includeNames = () => {
      if(this.props.token !== "") {
        var include_names =[];

        $(".ingredients_list_element_include").each(function(){
          include_names.push($(this).find("#name").val());
        })
        this.props.actions.include(profile.id, this.props.token,include_names);
      }
    }

    var excludeNames = () => {
      if(this.props.token !== "") {
        var exclude_names=[];
        $(".ingredients_list_element_exclude").each(function(){
          exclude_names.push($(this).find("#name").val());
        })
        this.props.actions.exclude(profile.id, this.props.token,exclude_names);
      }
    }

    var check =() =>{
      var dietType=null;
      
    $(".diet_checkboxes").each(function(){
        alert($(this).find("#").val() );
         })
    this.props.actions.update_profile(profile.id,profile.avatarUrl,profile.email,profile.fullName,profile.bio, 2, profile.secretQuestion,profile.userType, this.props.token);
    }

    var update_profile =(fullname_text,bio_text)=> {
      
      var fullname=$(".update_profile_inputs").find("#fullname_text").val();
      var bio=$(".update_profile_inputs").find("#bio_text").val();
      
     
      
      this.props.actions.update_profile(profile.id,profile.avatarUrl,profile.email,fullname,bio, profile.dietType, profile.secretQuestion,profile.userType, this.props.token);

    }

    let includeHtml = this.props.include.map(function(m){

      return <label className="label-preferences"> {m} </label>;


    });
    let excludeHtml = this.props.exclude.map(function(m){

      return <label className="label-preferences"> {m}</label>
      ;
    });
    let dietTypes=["EGG_DIARY_VEG","GLUTEN_FREE ","NO_MUSHROOM_OR_RED_MEAT ","NO_NUTS" ,"OMNIVORE" ,"PALEO" ,"VEGAN"];
    let dietTypes_chbx= "";

    for (var i = dietTypes.length - 1; i >= 0; i--) {
      dietTypes_chbx= dietTypes_chbx+"<input type='checkbox' id='"+i+"' value='"+i+"' />"+ dietTypes[i]
    }
   
    let updateProfileButton
    let updatePreferencesModalButton
    let updateIncludeModalButton
    let updateExcludeModalButton
    if(current.id === profile.id) {
      updateProfileButton=<button type="button" className="btn btn-success" onClick={this.openModal_updateProfile}>Update Profile</button>
      updatePreferencesModalButton =<button type="button" className="btn btn-success" onClick={this.openModal}>Update</button>
      updateIncludeModalButton =<button type="button" className="btn btn-success" onClick={this.openModal_Include}>Update</button>
      updateExcludeModalButton =<button type="button" className="btn btn-success" onClick={this.openModal_Exclude}>Update</button>
    }
    
    const isFollow = this.isFollower(this.props.followers,
      this.props.currentUser, this.props.profile);
    
    
      return (
        <div>
          <div className="col-xs-4">
            <img src={profile.avatarUrl} alt="avatar"/>
            {
              current.id === profile.id && <div>
                <PicEdit />
              </div>
            }
          </div>

          <div className="col-xs-8">
            <h1>{profile.fullName}</h1>
            <p>{profile.bio}</p>{updateProfileButton}
             <Modal isOpen={this.state.isOpen_updateProfile} onRequestHide={this.hideModal_updateProfile} id="updateProfile_modal">
                  <ModalHeader>
                    <ModalClose onClick={this.hideModal_updateProfile}/>
                    <ModalTitle>Update Profile</ModalTitle>
                  </ModalHeader>
                  <ModalBody>
                    
              
                    <div className="update_profile_inputs">
                     <input type = "text" id="fullname_text" value = {this.state.fullname} onChange = {this.updateState} />
                    <input type="text"  id="bio_text" value={this.state.bio} onChange = {this.updateState_bio} />
                   
                      <button type="button" className="btn-success more_button" onClick={update_profile}>Update</button>
                    </div>
                  </ModalBody>
                  <ModalFooter>
                  
                    <button className='btn btn-default' onClick={this.hideModal_updateProfile}>Cancel</button>

                  </ModalFooter>
                </Modal>
            <FollowButton isFollow={isFollow} follow={this.follow}
              unfollow={this.unfollow}/>
            <div className="row">
              <div className="col-xs-4">
                <h3>Followers: {this.props.followers.length}</h3>

              </div>
              <div className="col-xs-4">
                <h3>Following: {this.props.following.length}</h3>
              </div>

              <div className="col-xs-4">
                <a href={`/user/${profile.id}/logs`}><h3>Personal Log</h3></a>
              </div>
            </div>
            <hr></hr>
            <div className="row">
              <div className="col-xs-6">
                <h4>Preferences</h4>
                <h4>Include</h4>

                {updateIncludeModalButton}
                <Modal isOpen={this.state.isOpen_Include} onRequestHide={this.hideModal_Include} id="updateInclude_modal">
                  <ModalHeader>
                    <ModalClose onClick={this.hideModal_Include}/>
                    <ModalTitle>Include</ModalTitle>
                  </ModalHeader>
                  <ModalBody>
                    {includeHtml}
                    <div className="col-xs-12" id="ingredients_list_include">
                      <div className="ingredients_list_element_include" id="ingredients_list_element_include">
                        <div className="col-xs-8">
                          <input type="text" id="name" className="form-control" placeholder="Name"/>
                        </div>
                      </div>
                    </div>
                    <div className="col-xs-12">
                      <button type="button" className="btn-success more_button" onClick={moreIngredient_include}>More</button>
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
                    <ModalTitle>Exclude</ModalTitle>
                  </ModalHeader>
                  <ModalBody>
                    {excludeHtml}
                    <div className="col-xs-12" id="ingredients_list_exclude">
                      <div className="ingredients_list_element_exclude" id="ingredients_list_element_exclude">
                        <div className="col-xs-8">
                          <input type="text" id="name" className="form-control" placeholder="Name"/>
                        </div>
                      </div>
                    </div>
                    <div className="col-xs-12">
                      <button type="button" className="btn-success more_button" onClick={moreIngredient_exclude}>More</button>
                    </div>

                  </ModalBody>
                  <ModalFooter>
                    <button type="button" className="btn btn-default" onClick={excludeNames}>Update </button>
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
                    <ModalTitle>Update Diet Type</ModalTitle>
                  </ModalHeader>
                  <ModalBody>

                    <div className="diet_checkboxes">
                      <input type="checkbox" id="diet_0" value="0" name="diet_chkbx"/>  {dietTypes[0]} <br></br>
                      <input type="checkbox" id="diet_1" value="1" name="diet_chkbx" />  {dietTypes[1]} <br></br>
                      <input type="checkbox" id="diet_2" value="2" name="diet_chkbx" />  {dietTypes[2]} <br></br>
                      <input type="checkbox" id="diet_3" value="3" name="diet_chkbx" />  {dietTypes[3]} <br></br>
                      <input type="checkbox" id="diet_4" value="4" name="diet_chkbx"/>  {dietTypes[4]} <br></br>
                      <input type="checkbox" id="diet_5" value="5" name="diet_chkbx"/>  {dietTypes[5]}<br></br>
                      <input type="checkbox" id="diet_6" value="6" name="diet_chkbx"/>  {dietTypes[6]}

                    </div>

                  </ModalBody>
                  <ModalFooter>
                    <button type="button" className="btn btn-default" onClick={check}>Update </button>
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
