import { apiCall } from '../api';

var apiService = function(store) {
    return function(next) {
        return function(action) {
              next(action);
              switch(action.type) {
                case 'LOGIN_REQ':
                let userModel = { email: action.email, password: action.pass };
                
                apiCall("/session/login", "POST", {}, userModel).success(function(accessToken){
                    next({
                        type: 'LOGIN_DONE',
                        token: accessToken.accessToken,
                        id: accessToken.userId
                    });
                }).error(function(error, response){
                    next({type: 'LOGIN_FAIL'})
                });
                break;
                default:
                break;
              }              
        }
    }
};

export default apiService;