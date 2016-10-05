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

export { users, profile };