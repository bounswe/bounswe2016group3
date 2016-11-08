var load = function(id){
    return {
     
        type: 'LOAD_PROFILE_FS',
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

export { load, follow };