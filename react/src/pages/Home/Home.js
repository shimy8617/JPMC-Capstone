
import React from 'react';
import { useNavigate } from 'react-router-dom';
import Layout from "../../components/ui/Layout";
import TinderLogo from '../../../src/images/rtinder-logo.png';

import './Home.css';

import { Button, ButtonGroup } from 'react-bootstrap';

const Home = ({ children }) => {
    // defino el objeto navigate
    const navigate = useNavigate();

    const handleClick = () => {
        // redirige al login cuando el boton se clickea
        navigate('/login');
    }

    return (
        <>
            <div className="container-fluid">
                <div className="home-title">   
                <div className="items">        
                    <img src={TinderLogo} alt="Logo" className="tinder-logo" />
                    <h1>RESTAURANT TINDER</h1>
                    <p className='paragraph'>Press the button to login</p>
                    <ButtonGroup>
                        <Button onClick={handleClick} className="login-btn">Login</Button>
                    </ButtonGroup>
                    </div> 
            </div>

            {children}
            </div>
        </>
    );
}
export default Home;