var ratings = function(state=[], action) {
	
    switch(action.type) {
        case 'RATINGS_LOADED':
        return action.data;

        default:
        return state;
    }
};

export { ratings };