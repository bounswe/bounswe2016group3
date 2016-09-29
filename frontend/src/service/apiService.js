import { apiCall } from '../api';

var apiService = function(store) {
    return function(next) {
        return function(action) {
              next(action);
              switch(action.type) {
                case 'LOGIN_REQ':
                let userModel = { email: action.email, password: action.pass };
                
                apiCall("/session/login", "POST", {}, userModel).success(function(accessToken){
                    console.log(accessToken);
                }).error(function(){
                    console.log("nope");
                });
                break;
                default:
                break;
              }              
        }
    }
};

export default apiService;