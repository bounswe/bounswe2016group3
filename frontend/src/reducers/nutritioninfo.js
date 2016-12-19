var nutritionInfo = function(state=[], action) {

    switch(action.type) {
        case 'NUTRITION_INFO_LOADED':
        return action.data;

        default:
        return state;
    }
};

export { nutritionInfo };