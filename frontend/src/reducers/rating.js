var ratings = function(state=[], action) {
	
    switch(action.type) {
        case 'RATINGS_LOADED':

        return action.data;

        default:
        return state;
    }
}

var rate = function(state=[], event) {
	 switch(event.type) {

		case 'MEAL_RATED':
		alert("hello");
        return event.data;

       default:
        return state;
    }
}

export { ratings,rate };