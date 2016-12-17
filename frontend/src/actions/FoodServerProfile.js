var load = function(id){
    return {

        type: 'LOAD_PROFILE_FS',
        id: id
    };
}
var addmeal = function(token,user_id,menu_id,meal_name,meal_description,meal_ingredients,meal_url){

    return {
        type: 'ADD_MEAL',
        userId:user_id,
        menuId:menu_id,
        name: meal_name,
        description: meal_description,
        ingredients: meal_ingredients,
        photoUrl: meal_url,
        token

    };

}
var follow = function(token, followee, follower){
    return {
        type: 'FOLLOW_USER',
        id: followee.id,
        followee,
        follower,
        token
    };
}

var unfollow = function(token, unfollowee) {
  return {
    type: 'UNFOLLOW_USER',
    id: unfollowee.id,
    token
  };
}

var loadMeal = function(id){
    return {
        type: 'LOAD_MEAL',
        id: id
    };
};

export { load, addmeal, follow, unfollow, loadMeal };
