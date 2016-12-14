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
import jQuery from 'jquery';
import PicEdit from './PicEdit';
import FollowButton from './FollowButton';
import * as actions from '../actions/FoodServerProfile';

class Profile extends Component {

  constructor() {
    super();
    this.follow = this.follow.bind(this);
    this.unfollow = this.unfollow.bind(this);
  }
  componentDidMount(){
    this.props.actions.load(this.props.params.id);
    this.props.actions.loadMeal(this.props.params.id);
  }

  state = {
    isOpen: false
  };

  openModal = () => {

    this.setState({
      isOpen: true
    });
  };

  hideModal = () => {
    this.setState({
      isOpen: false
    });
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

    var moreIngredient = () =>{
      $("#ingredients_list").append('<div class="ingredients_list_element" id="ingredients_list_element"><div class="col-xs-8"><input type="text" id="name" class="form-control" placeholder="Name"/></div><div class="col-xs-4"><input type="text" id="amount" class="form-control" placeholder="Amount"/></div></div>')

    }

    let meal_name=document.getElementById("meal_name");
    let meal_description=document.getElementById("meal_description");

    var addMeal = () => {
      if(this.props.token!==""){
        var ingredients="";
        $(".ingredients_list_element").each(function(){
          ingredients=ingredients+","+$(this).find("#amount").val()+" grams of"+$(this).find("#name").val()
        })
        ingredients = ingredients.substring(1)
        this.props.actions.addmeal(this.props.token,this.props.profile.id, 1, meal_name.value,meal_description.value,ingredients,"");
        //document.location.href = document.location.href  ;
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

    let mealsHtml = this.props.meals.map(function(m){
      var ingredients=m.ingredients;
      if(ingredients != null && ingredients != undefined && ingredients!=""){
        ingredients=m.ingredients.replace(/]]]/g, ',')
        ingredients=ingredients.replace(/%%%/g,"-")
      }
      return <li key={m.id}><a href={`/meal/${m.id}/`}>{m.name}</a></li>;
    });


    let addmealButton;

    let openAddMealModalButton
    if(current.id === profile.id) {
      addmealButton=<button type="button" className="btn btn-default" onClick={addMeal}>Add Meal</button>;
      openAddMealModalButton =<button type="button" className="btn btn-success" onClick={this.openModal}>Add Meal </button>

    } else {
      addmealButton=<button type="button" className="btn btn-default disabled" onClick={addMeal}>Add Meal</button>
    }

    const isFollow = this.isFollower(this.props.followers,
      this.props.currentUser, this.props.profile);

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
            <p>{profile.bio}Hello from the otherside..</p>
            <FollowButton isFollow={isFollow} follow={this.follow}
              unfollow={this.unfollow}/>

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

                <div className="col-xs-6" id="menus">
                  <h3>Menus</h3>
                  {openAddMealModalButton}
                  {mealsHtml}
                  <Modal isOpen={this.state.isOpen} onRequestHide={this.hideModal} id="addMeal_modal">
                    <ModalHeader>
                      <ModalClose onClick={this.hideModal}/>
                      <ModalTitle><h3>Add Meal</h3></ModalTitle>
                    </ModalHeader>
                    <ModalBody>
                      <input type="text" className="form-control" placeholder="Meal name" id="meal_name" />
                      <input type="text" className="form-control" placeholder="Meal desc" id="meal_description" />
                      <h4>Ingredients</h4>
                      <div className="col-xs-12" id="ingredients_list">
                        <div className="ingredients_list_element" id="ingredients_list_element">
                          <div className="col-xs-8">
                            <input type="text" id="name" className="form-control" placeholder="Name"/>
                          </div>
                          <div className="col-xs-4">
                            <input type="text" id="amount" className="form-control" placeholder="Amount"/>
                          </div>
                        </div>
                      </div>
                      <div className="col-xs-12">
                        <button type="button" className="btn-success more_button" onClick={moreIngredient}>More</button>
                      </div>
                    </ModalBody>
                    <ModalFooter>
                      <button className='btn btn-default' onClick={this.hideModal}>Cancel</button>
                      {addmealButton}
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
        meals: state.meals,
        currentUser: state.currentUser
      };
    }

    var mapDispatchToProps = function(dispatch){
      return { actions: bindActionCreators(actions, dispatch) };
    }

    export default connect(mapStateToProps, mapDispatchToProps)(Profile);
