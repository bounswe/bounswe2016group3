var load = function(id){
    return {
        type: 'LOAD_PROFILE',
        id: id
    };
}
var update_profile  = function(id, avatarUrl, email, fullname, bio, dietType,  secretQuestion, userType,token){
    return {
        type:'UPDATE_USER',
         id: id,
         avatarUrl:avatarUrl,
         email:email,
         fullname:fullname,
         bio:bio,
         dietType:dietType,
         secretQuestion:secretQuestion,
        userType:userType,
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


var include =   function(id,token,names){
    return {
    type: 'UPDATE_INCLUDE',
    id:id,
    names:names,
    token
 };
}

var exclude =   function(id,token,names){
    return {
    type: 'UPDATE_EXCLUDE',
    id:id,
    names:names,
    token
 };
}

export { load, follow, unfollow, include,exclude,update_profile };
