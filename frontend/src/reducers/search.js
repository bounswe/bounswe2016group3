var searchMeal = function(prev=[], event) {
	switch(event.type) {
		case 'SEARCHMEAL_LOADED':
			return event.data;
		default:
			return prev;
	}
}
var searchUser = function(prev=[], event) {
	switch(event.type) {
		case 'SEARCHUSER_LOADED':
			return event.data;
		default:
			return prev;
	}
}
var userById = function(state={},action){
    switch(action.type){
        case 'GET_USER_BY_ID_SUCCESS':
        return action.data;

        default:
            
    }
}


export { searchMeal,searchUser,userById };