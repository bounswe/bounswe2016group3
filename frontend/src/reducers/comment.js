var comments = function(state=[], action) {
    switch(action.type) {
        case 'COMMENTS_LOADED':
        return action.data;

        default:
        return state;
    }
};

export { comments };