var load = function(token,id){

    return {

        type: 'LOAD_MEAL',
        id: id,
        token
    };
};

var checkeat = function(token,id){

	return{

		type:'CHECKEAT_MEAL',
		id:id,
		token
	};
};

export { load, checkeat};
