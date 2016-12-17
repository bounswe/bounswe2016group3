var mealTag = function(state=[], action) {
    switch(action.type) {
        case 'TAGS_LOADED':
        return action.data;

        default:
        return state;
    }
};


export { mealTag };
