var redirectService = function(store) {
    return function(next) {
        return function(action) {
            next(action);
            switch(action.type){
                case 'REDIRECT_ROOT':
                action.history.pushState(null,"/");
                break;
                default:
                break;
            }
        }
    }
}

export default redirectService;