var load = function(id, token){
    return {
        type: 'LOAD_PERSONALLOG',
        id: id,
        token: token
    };
}



export {load};