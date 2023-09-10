import { utilFetchWrapper } from "../services/utilFetchWrapper";
const fetchWrapper = utilFetchWrapper();

export function getAllRestaurants(){
    return fetchWrapper.get('/restaurant/all');
}

export function getRandomRestaurant(){
    return fetchWrapper.get('/api/restaurant/random');
}

export function getRestaurantById(restaurantId){
     return fetchWrapper.get(`/api/restaurant/detail/${restaurantId}`);
}

export function getUserLikes(){
    return fetchWrapper.get('/api/myLikes');
}