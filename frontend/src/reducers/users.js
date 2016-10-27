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

        default:
        return state;
    }
}

var following = function(state=[], action){
    switch(action.type){
        case 'FOLLOWING_LOADED':
        return action.data;

        default:
        return state;
    }
}

export { users, profile, followers, following };