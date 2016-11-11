var token = function(state="", action){
    switch(action.type){
        case 'LOGIN_DONE':
        case 'LOGIN_CONFIRM':
        return action.token;
        default:
        return state;
    }
};

var userId = function(state=0, action){
    switch(action.type){
        case 'LOGIN_DONE':
        return action.id;
        case 'LOGOUT_REQ':
        return 0;
        default:
        return state;
    }
}

var currentUser = function(state={}, action){
    switch(action.type){
        case 'LOGIN_CONFIRMED':
        return action.user;
        case 'LOGOUT_REQ':
        return {};   
        default:
        return state;
    }
}

export { token, userId, currentUser };