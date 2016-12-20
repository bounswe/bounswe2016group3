var load = function(id){
    return {
        type: 'LOAD_PROFILE',
        id: id
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

export { load, follow, unfollow, include,exclude };
