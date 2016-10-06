import { apiCall } from '../api';

var apiService = function(store) {
    return function(next) {
        return function(action) {
            var req;

            next(action);
            switch(action.type) {
            case 'LOGIN_REQ':
            req = { email: action.email, password: action.pass };
            
            apiCall("/session/login", "POST", {}, req).success(function(accessToken){
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

                // copy-paste that because we don't recurse when calling next
                // same as the login_confirm bit if we want to refactor that 
                // to another method
                apiCall("/session/currentUser", "GET", {"Authorization": "Bearer " + accessToken.accessToken }).success(function(user){
                    next({type: 'LOGIN_CONFIRMED', user: user});
                }).error(function(error, response){
                    next({type: 'LOGIN_FAIL'});
                });
            }).error(function(error, response){
                next({type: 'LOGIN_FAIL'});
            });

            break;

            case 'LOGIN_CONFIRM':
            apiCall("/session/currentUser", "GET", {"Authorization": "Bearer " + action.token}).success(function(user){
                next({type: 'LOGIN_CONFIRMED', user: user});
            }).error(function(error, response){
                next({type: 'LOGIN_FAIL'});
            });
            break;

            case 'SIGNUP_REQ':
            req = {
                email: action.email, 
                password: action.pass, 
                fullName: action.name,
                secretQuestion: action.question,
                secretAnswer: action.answer
            };

            apiCall("/user/", "POST", {}, req).success(function(user){
                next({type: 'SIGNUP_DONE'});
            }).error(function(error, response){
                next({type: 'SIGNUP_FAIL'});
            });
            break;


            case 'LOGOUT_REQ':
            apiCall("/session/logout", "POST", {"Authorization": "Bearer " + action.token}).success(function(){
            });

            delete localStorage['token'];
            break;

            case 'LOAD_USERS':
            apiCall("/user/", "GET").success(function(res){
                next({type: 'USERS_LOADED', users: res});
            });
            break;

            case 'LOAD_PROFILE':
            apiCall("/user/"+action.id+"/", "GET").success(function(res){
                next({type: 'PROFILE_LOADED', user: res});
            });
            break;

            case 'GET_USER_BY_EMAIL':
            var req = { email: action.email };
            apiCall("/user/byEmail", "POST", {}, req).success(function(res){
                next({type: 'GOT_USER_BY_EMAIL', uid: res.id, question: res.secretQuestion});
            });
            break;

            case 'FORGOT_PASSWORD_REQ':
            var req = { id: action.id, answer: action.answer };
            apiCall("/user/resetPassword", "POST", {}, req).success(function(){
                next({type: 'REDIRECT_ROOT', history: action.history})
            });
            break;

            default:
            break;
            }              
        }
    }
};

export default apiService;
