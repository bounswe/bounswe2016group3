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
var comment = function(token,mealId,userId,comment){
	return{

		type:'COMMENT_MEAL',
		mealId:mealId,
		userId:userId,
		content:comment,
		token
	};


};

var rate = function(token,mealId,rating){
	return{

		type:'RATE_MEAL',
		mealId:mealId,
		rating:rating,
		token
	};


};
export { load, checkeat,comment, rate};
