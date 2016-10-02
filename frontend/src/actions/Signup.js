var submit = function(email, pass, name, question, answer){
    return { type: 'SIGNUP_REQ', email, pass, name, question, answer };
};

var wrongPassword = function(){
    return { type: 'WRONG_PASSWORD' };
}

export { submit, wrongPassword };
