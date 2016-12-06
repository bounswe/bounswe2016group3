var users = function(state=[], action){
    switch(action.type){
        case 'USERS_LOADED':
        return action.users;
        
        default:
        return state;
    }
};

var profile = function(state={}, action){
    switch(action.type){
        case 'PROFILE_LOADED':
        return action.user;

        default:
        return state;
    }
}

var followers = function(state=[], action){
    switch(action.type){
        case 'FOLLOWERS_LOADED':
        return action.data;

        case 'FOLLOW_USER':
        return state.concat([action.follower]);

        default:
        return state;
    }
}

var following = function(state=[], action){
    switch(action.type){
        case 'FOLLOWING_LOADED':
        return action.data;

        //case 'FOLLOW_USER':
        //return state.concat([action.followee]);

        default:
        return state;
    }
}
var include = function(state=[], action){
    switch(action.type){
        case 'LOAD_INCLUDE_SUCCESS':
        return action.data;

        default:
        return state;
    }
}
var exclude = function(state=[], action){
    switch(action.type){
        case 'LOAD_EXCLUDE_SUCCESS':
        return action.data;

        default:
        return state;
    }
}


export { users, profile, followers, following, include, exclude };