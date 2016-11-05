var submit = function(email, pass){
     alert("12345");
    return { type: 'LOGIN_REQ', email, pass };
};

var redirectRoot = function(history){
    return { type: 'REDIRECT_ROOT', history };
}

export { submit, redirectRoot };
