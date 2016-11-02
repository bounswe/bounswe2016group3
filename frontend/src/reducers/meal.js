var meal = function(state={}, action) {
    switch(action.type) {
        case 'MEAL_LOADED':
        return action.data;

        default:
        return state;
    }
};

var meals = function(state=[], action) {
    switch(action.type) {
        case 'MEALS_LOADED':
        return action.data;

        default:
        return state;
    }
};

export { meal, meals };
