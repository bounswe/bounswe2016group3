var submit = function(email, pass, name, question, answer, userType){
    return { type: 'SIGNUP_REQ', 
    email: email, pass: pass,
    name: name,
    question: question, answer: answer, 
    userType: Number(userType) };
};
var submitFoodServer = function(email, pass, name, question, answer, userType){

    return { type: 'SIGNUP_REQ_FS', 
    email: email, pass: pass,
    name: name,
    question: question, answer: answer, 
    userType: Number(userType) };

};
var wrongPassword = function(){
    return { type: 'WRONG_PASSWORD' };
}

export { submit, wrongPassword, submitFoodServer };
