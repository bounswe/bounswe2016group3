var submit = function(email, pass){
     
    return { type: 'LOGIN_REQ', email, pass };
};

var logout = function(token){
    return {
        type: 'LOGOUT_REQ',
        token: token
    };
}

var searchMeal = function(query){
	return{
		type:'SEARCH_MEAL',
		query:query
	}
}

var searchUser = function(query){
	return{
		type:'SEARCH_USER',
		query:query
	}
}

var getUserById = function(id){
	return{
		type:'GET_USER_BY_ID',
		id:id
	}
}

export { submit,logout,searchMeal,searchUser,getUserById };