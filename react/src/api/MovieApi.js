import { utilFetchWrapper } from "../services/utilFetchWrapper";
const fetchWrapper = utilFetchWrapper();

export function getMovieList(searchString){
    return fetchWrapper.post('/movie/search',searchString);
}
export function getAllMovies(){
    return fetchWrapper.get('/movie/all');
}
export function getMovieDetail(id){
    return fetchWrapper.get(`/movie/detail/${id}`);
}
export function saveMovieDetail(movie){
    return fetchWrapper.put(`/movie/detail/${movie.id}`,movie);
}
