var submit = function(email, pass, name){
    return { type: 'SIGNUP_REQ', email, pass, name };
};

var wrongPassword = function(){
    return { type: 'WRONG_PASSWORD' };
}

export { submit, wrongPassword };
