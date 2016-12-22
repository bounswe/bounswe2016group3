var personalLog = function(state={}, action) {

    switch(action.type){
        case 'PERSONALLOG_LOADED':
        return action.data;

        case 'PERSONALLOG_FAILED':
        return state;

        default:
        return state;
    }
};

var lastWeekEaten = function(state=[], action) {
  switch (action.type) {
    case 'MEALS_EATEN_LOADED':
    return action.data;
    break;

    default:
    return state;

  }
}

export { personalLog, lastWeekEaten };
