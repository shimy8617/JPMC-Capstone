import { UserProvider } from './context/UserContext';
import ErrorBoundary from './ErrorBoundary';
import Register from './pages/Register/Register';
import Home from './pages/Home/Home';
import Login from './pages/Login/Login';
import Recomendation from './pages/Recomendation/Recomendation';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './App.css';
import Preferences from './pages/Preferences/Preferences';
import Likes from './pages/Likes/Likes';
import RestaurantDetailPage from './pages/Detail/RestaurantDetailPage';


const MyApp = () => {  

  return (
    
    <div className="wrapper">
      <ErrorBoundary fallback={<p>Something went wrong</p>}>
        <UserProvider>
          <BrowserRouter>
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/Login" element={<Login />} />
              <Route path="/Register" element={<Register />} />
              <Route path="/Recomendation" element={<Recomendation />} />
              <Route path="/Restaurant/Detail/:restaurantId" element={<RestaurantDetailPage />} />
              <Route path="/Preferences" element={<Preferences />} />
              <Route path="/MyLikes" element={<Likes />} />
            </Routes>
          </BrowserRouter>
        </UserProvider>
      </ErrorBoundary>
    </div>
  );
}

export default MyApp;