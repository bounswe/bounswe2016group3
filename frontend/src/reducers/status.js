var loading = function(state=false, action){
    switch(action.type){
        case 'LOGIN_REQ':
        case 'SIGNUP_REQ':        
        return true;
        case 'LOGIN_DONE':
        case 'LOGIN_FAIL':
        case 'SIGNUP_DONE':
        case 'SIGNUP_FAIL':
        return false;
        default:
        return state;
    }
}

var success = function(state=false, action){
    switch(action.type){
        case 'LOGIN_DONE':
        case 'SIGNUP_DONE':        
        return true;
        case 'LOGIN_REQ':
        case 'LOGIN_FAIL':
        case 'SIGNUP_REQ':
        case 'SIGNUP_FAIL':
        return false;
        default:
        return state;
    }
}

var error = function(state=false, action){
    switch(action.type){
        case 'LOGIN_FAIL':
        case 'SIGNUP_FAIL':        
        return true;
        case 'LOGIN_REQ':
        case 'LOGIN_DONE':
        case 'SIGNUP_REQ':
        case 'SIGNUP_DONE':
        return false;
        default:
        return state;
    }
}

export { loading, success, error };