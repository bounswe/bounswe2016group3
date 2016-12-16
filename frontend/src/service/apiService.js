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

            case 'LOAD_PERSONALLOG':
            console.log(action);
            apiCall("/home/lastweek", "GET", {"Authorization": "Bearer " + action.token}).success(function(res){
                next({type: 'PERSONALLOG_LOADED', data: res});
            }).error(function(error, response){ 
                next({type: 'PERSONALLOG_FAILED'});
            });
            break;

            case 'SIGNUP_REQ':
            req = {
                email: action.email,
                password: action.pass,
                fullName: action.name,
                secretQuestion: action.question,
                secretAnswer: action.answer,
                userType: action.userType
            };

            apiCall("/user/", "POST", {}, req).success(function(user){
                next({type: 'SIGNUP_DONE'});
            }).error(function(error, response){
                next({type: 'SIGNUP_FAIL'});
            });

            break;

            case 'SIGNUP_REQ_FS':

            req = {
                email: action.email,
                password: action.pass,
                fullName: action.name,
                secretQuestion: action.question,
                secretAnswer: action.answer,
                userType: action.userType
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
            apiCall("/user/"+action.id+"/followers", "GET").success(function(res){
                next({type: 'FOLLOWERS_LOADED', data: res});
            });
            apiCall("/user/"+action.id+"/following", "GET").success(function(res){
                next({type: 'FOLLOWING_LOADED', data: res});
            });
            apiCall("/user/"+action.id+"/menus", "GET").success(function(res){
                next({type: 'MENUS_LOADED', data: res});
            });
            apiCall('/user/'+action.id+"/include/","GET").success(function(res){
                next({type: 'LOAD_INCLUDE_SUCCESS', data:res});
            });
             apiCall('/user/'+action.id+"/exclude/","GET").success(function(res){
                next({type: 'LOAD_EXCLUDE_SUCCESS', data:res});
            });

            break;
            case 'LOAD_PROFILE_FS':

            apiCall("/user/"+action.id+"/", "GET").success(function(res){
                next({type: 'PROFILE_LOADED', user: res});
            });
            apiCall("/user/"+action.id+"/followers", "GET").success(function(res){
                next({type: 'FOLLOWERS_LOADED', data: res});
            });
            apiCall("/user/"+action.id+"/following", "GET").success(function(res){
                next({type: 'FOLLOWING_LOADED', data: res});
            });
            apiCall("/user/"+action.id+"/menus", "GET").success(function(res){
                next({type: 'MENUS_LOADED', data: res});
            });
            apiCall("/user/"+action.id+"/meals", "GET").success(function(res){
                next({type: 'MEALS_LOADED', data: res});
            });

            break;

            case 'GET_USER_BY_EMAIL':
            req = action.email;
            apiCall("/user/byEmail", "POST", {}, req).success(function(res){
                next({type: 'GOT_USER_BY_EMAIL', uid: res.id, question: res.secretQuestion});
            });
            break;

            case 'FORGOT_PASSWORD_REQ':
            req = { id: action.id, answer: action.answer };
            apiCall("/user/resetPassword", "POST", {}, req).success(function(){
                next({type: 'REDIRECT_ROOT', history: action.history})
            });
            break;

            case 'FOLLOW_USER':
            apiCall("/user/"+action.id+"/follow", "POST", {"Authorization": "Bearer " + action.token}).success(function(){
                apiCall("/user/"+action.id+"/following", "GET").success(function(res){
                    next({type: 'FOLLOWING_LOADED', data: res});
                });
            }).error(() => {console.log('Failed!!!')});
            break;

            case 'UNFOLLOW_USER':
            apiCall("/user/"+action.id+"/unfollow", "POST", {"Authorization": "Bearer " + action.token}).success(function(){
              console.log('Success!!!');
              apiCall("/user/"+action.id+"/followers", "GET").success(function(res) {
                next({type: 'FOLLOWERS_LOADED', data: res});
              }).error(() => {console.log('Followers not Loaded!!!')});
            }).error(() => {console.log('Failed!!!')});
            break;

            case 'LOAD_MENU':
            apiCall('/menu/'+action.id+"/", "GET").success(function(res){
                next({type: 'MENU_LOADED', data: res});
            });
            apiCall('/menu/'+action.id+"/meals/", "GET").success(function(res){
                next({type: 'MEALS_LOADED', data: res});
            });
            break;

            case 'LOAD_MEAL':
            apiCall('/meal/'+action.id+"/", "GET").success(function(res){
                next({type: 'MEAL_LOADED', data: res});
            });
            apiCall('/meal/'+action.id+"/comments/", "GET").success(function(res){
                next({type: 'COMMENTS_LOADED', data: res});
            });

            apiCall('/meal/'+action.id+"/ratings/", "GET" ,{"Authorization": "Bearer " + action.token}, req).success(function(res){
                next({type: 'RATINGS_LOADED', data: res});
            });
            break;

            case 'ADD_MEAL':

             req = {
                userId:action.userId,
                menuId:action.menuId,
                name: action.name,
                description: action.description,
                ingredients: action.ingredients,
                photoUrl: action.photoUrl


            };

            apiCall("/meal/", "POST", {"Authorization": "Bearer " + action.token}, req).success(function(res){

                    next({type: 'ADDMEAL_DONE'});

            }).error(function(error, response){
                next({type: 'ADDMEAL_FAILED'});
            });
            break;



            case 'CHECKEAT_MEAL':

             apiCall('/meal/'+action.id+"/checkeat/", "POST" ,{"Authorization": "Bearer " + action.token}).success(function(){
                    next({type: 'CHECKEAT_ADDED'});

                });
             break;

             case 'SEARCH_MEAL':

             apiCall('/meal/search/'+action.query+"/", "GET" ).success(function(res){
                    next({type: 'SEARCHMEAL_LOADED', data:res});

                });
             break;

            case 'SEARCH_USER':

             apiCall('/user/search/'+action.query+"/", "GET" ).success(function(res){
                    next({type: 'SEARCHUSER_LOADED', data:res});

                });
             break;

             case 'COMMENT_MEAL':

             req = {
                mealId:action.mealId,
                userId:action.userId,
                content:action.content
            };
             apiCall('/comment/', "POST" ,{"Authorization": "Bearer " + action.token},req).success(function(){
                  apiCall('/meal/'+action.mealId+"/comments/", "GET").success(function(res){
                next({type: 'COMMENTS_LOADED', data: res});
            });

            });
            break;

            case 'RATE_MEAL':


            apiCall("/meal/"+action.mealId+"/rate/"+action.rating+"/","POST",{"Authorization": "Bearer " + action.token}).success(function(){
                 apiCall('/meal/'+action.mealId+"/ratings/", "GET" ,{"Authorization": "Bearer " + action.token}, req).success(function(res){
                    next({type: 'RATINGS_LOADED', data: res});
                 });
            }).error(function(){console.log('Failed!!!')});


            break;

            case 'GET_USER_BY_ID':

                apiCall('/user/'+action.id+"/","GET").success(function(res){
                      next({type: 'GET_USER_BY_ID_SUCCESS', data:res});
                });

            break;

            case 'UPDATE_INCLUDE':
                req={
                    id:action.id,
                    names:action.names

                };

                apiCall('/user/'+action.id+"/include/","POST", {"Authorization": "Bearer " + action.token}, req.names).success(function(){
                  apiCall("/user/"+action.id+"/include/", "GET", {"Authorization": "Bearer " + action.token}).success(function(res) {
                    next({type: 'LOAD_INCLUDE_SUCCESS', data:res});
                  });
                });
            break;
            case 'UPDATE_EXCLUDE':

               req={
                    id:action.id,
                    names:action.names
                };
                apiCall('/user/'+action.id+"/exclude/","POST", {"Authorization": "Bearer " + action.token}, req.names).success(function(){
                  apiCall("/user/"+action.id+"/exclude/", "GET", {"Authorization": "Bearer " + action.token}).success(function(res) {
                    next({type: 'LOAD_EXCLUDE_SUCCESS', data:res});
                  });
                });
            break;


            default:
            break;
            }
        }
    }
};

export default apiService;
