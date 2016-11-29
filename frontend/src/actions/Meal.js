var load = function(token,id){

    return {

        type: 'LOAD_MEAL',
        id: id,
        token
    };
};

export { load };
