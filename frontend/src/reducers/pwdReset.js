var userId = function(state=0,action){
    switch(action.type){
        case 'GOT_USER_BY_EMAIL':
        return action.uid;

        default:
        return state
    }
}

var secretQuestion = function(state="",action){
    switch(action.type){
        case 'GOT_USER_BY_EMAIL':
        return action.question;
        
        default:
        return state
    }
}

export { userId, secretQuestion }