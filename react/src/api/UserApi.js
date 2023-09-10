import { utilFetchWrapper } from "../services/utilFetchWrapper";
const fetchWrapper = utilFetchWrapper();

export function loginUser(user){
    return fetchWrapper.post('/login',user);
}
export function getUser(){
    return fetchWrapper.get('/api/user/current');
}
export function getUserById(id){    
    return fetchWrapper.get(`/user/${id}`);
}
export function saveUserDetail(user){
    return fetchWrapper.post('/user',user);
}
export function logoutUser(){
    return fetchWrapper.get('/userLogout');
}
export function registerUser(user) {
    return fetchWrapper.post('/register',user)
}
export function storeUserLikes(restaurantId) {
    return fetchWrapper.post(`/api/likes/${restaurantId}`)
}