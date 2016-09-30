var token = function(state="", action){
    switch(action.type){
        case 'LOGIN_DONE':
        return action.token;
        default:
        return state;
    }
};

var userId = function(state=0, action){
    switch(action.type){
        case 'LOGIN_DONE':
        return action.id;
        default:
        return state;
    }
}

export { token, userId };