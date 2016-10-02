import { apiCall } from '../api';

var apiService = function(store) {
    return function(next) {
        return function(action) {
            var userModel;

            next(action);
            switch(action.type) {
            case 'LOGIN_REQ':
            userModel = { email: action.email, password: action.pass };
            
            apiCall("/session/login", "POST", {}, userModel).success(function(accessToken){
                next({
                    type: 'LOGIN_DONE',
                    token: accessToken.accessToken,
                    id: accessToken.userId
                });
                
                localStorage['token'] = accessToken.accessToken;

                next({
                    type: 'LOGIN_CONFIRM',
                    token: accessToken.accessToken
                });
            }).error(function(error, response){
                next({type: 'LOGIN_FAIL'});
            });
            break;
            
            case 'SIGNUP_REQ':
            userModel = {email: action.email, password: action.pass, fullName: action.name};

            apiCall("/user/", "POST", {}, userModel).success(function(user){
                next({type: 'SIGNUP_DONE'});
            }).error(function(error, response){
                next({type: 'SIGNUP_FAIL'});
            });
            break;

            case 'LOGIN_CONFIRM':
            apiCall("/session/currentUser", "GET", {"Authorization": "Bearer " + action.token}).success(function(user){
                next({type: 'LOGIN_CONFIRMED', user: user});
            }).error(function(error, response){
                next({type: 'LOGIN_FAIL'});
            });
            break;

            default:
            break;
            }              
        }
    }
};

export default apiService;
