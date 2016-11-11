var submit = function(id, answer, history){
    return { type: 'FORGOT_PASSWORD_REQ', id, answer, history };
}

var getUser = function(email){
    return { type: 'GET_USER_BY_EMAIL', email };
}

export { submit, getUser };
