var load = function(id){
    return {
        type: 'LOAD_PROFILE',
        id: id
    };
}

var follow = function(token, id){
    return {
        type: 'FOLLOW_USER',
        id: id,
        token: token
    };
}

export { load };