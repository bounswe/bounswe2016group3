var logout = function(token){
    return {
        type: 'LOGOUT_REQ',
        token: token
    };
}

export { logout };