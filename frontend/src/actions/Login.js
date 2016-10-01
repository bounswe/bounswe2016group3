var submit = function(email, pass){
    return { type: 'LOGIN_REQ', email, pass };
};

export { submit };
