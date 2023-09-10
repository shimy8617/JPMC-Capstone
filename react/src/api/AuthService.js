//import { useFetchWrapper } from "./useFetchWrapper";

const baseUrl = process.env.NODE_ENV + `/api`;


const AuthService = {
  loginUser: (login) => {
    //return useFetchWrapper.post(`${baseUrl}/login`, login);    
    return {};
  },
  registerUser: (user) => {
    // return useFetchWrapper.post(`${baseUrl}/register`, user);
    return {};
  }
}
export default AuthService;

