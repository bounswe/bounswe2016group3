var users = function(state=[], action){
    switch(action.type){
        case 'USERS_LOADED':
        return action.users;

        default:
        return state;
    }
};

export { users }