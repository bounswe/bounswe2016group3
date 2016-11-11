var menu = function(state={}, action) {
    switch(action.type) {
        case 'MENU_LOADED':
        return action.data;

        default:
        return state;
    }
};

var menus = function(state=[], action) {
    switch(action.type) {
        case 'MENUS_LOADED':
        return action.data;

        default:
        return state;
    }
};

export { menu, menus };
