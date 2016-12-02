var comments = function(state=[], action) {
    switch(action.type) {
        case 'COMMENTS_LOADED':
        alert(action);
        return action.data;

        default:
        return state;
    }
};

export { comments };