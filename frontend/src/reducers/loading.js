var loading = function(state=false, action){
    switch(action.type){
        case 'LOGIN_REQ':
        return true;
        case 'LOGIN_DONE':
        case 'LOGIN_FAIL':
        return false;
        default:
        return state;
    }
}

export { loading };