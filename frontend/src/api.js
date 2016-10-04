import { apiUrl } from './config';
var request = require('request');

var apiCall = function(url, method, headers={}, body={}){
    let callUrl = apiUrl + url;

    var succCb = function() {};
    var errCb = function() {};

    headers = Object.assign(headers,{'Content-Type': 'application/json'})

    console.log(body);

    request({
        url: callUrl,
        method: method,
        headers: headers,
        body: JSON.stringify(body)
    }, function(error, response, body){
        if(!error && response.statusCode === 200){
            succCb(JSON.parse(body));
        } else {
            errCb(error, response);
        }
    });

    return { success: function(fn) { succCb = fn; return {error: function(fn) { errCb = fn; }};}}
};

export { apiCall }
