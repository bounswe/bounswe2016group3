import { apiUrl } from './config';

var apiCall = function(url, method, headers={}, body={}){
    let callUrl = apiUrl + url;

    var succCb = function() {};
    var errCb = function() {};

    fetch(new Request(callUrl,{mode: 'no-cors', method, body: JSON.stringify(body), headers})).then(function(response){
        if(response.status === 200){
            succCb(response.json());
        } else {
            errCb();
        }
    });

    return { success: function(fn) { succCb = fn; return {error: function(fn) { errCb = fn; }};}}
};

export { apiCall }
