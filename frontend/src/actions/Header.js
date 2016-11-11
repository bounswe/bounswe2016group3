var submit = function(email, pass){
     
    return { type: 'LOGIN_REQ', email, pass };
};

var logout = function(token){
    return {
        type: 'LOGOUT_REQ',
        token: token
    };
}

export { submit,logout };