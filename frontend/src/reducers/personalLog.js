var personalLog = function(state={}, action) {
    switch(action.type) {
        case 'PERSONALLOG_LOADED':
        return action.data;

        default:
        return state;
    }
};
