import { LinkContainer } from 'react-router-bootstrap';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import { UserContext } from "../../context/UserContext";
import { useContext } from "react";
import TinderLogo from '../../../src/images/rtinder-logo.png';

import './Header.css';

const Header = () => {
    const userContext = useContext(UserContext);
    const currentUser = userContext.currentUser;


	return (
		<>
			<header>				
				<Nav>
					<Navbar collapseOnSelect expand="lg" className="bg-body-tertiary">
						<Container>
							<Navbar.Brand href="/">
							<img src={TinderLogo} alt="Logo" className="header-logo" />
							</Navbar.Brand>
							<Navbar.Toggle aria-controls="responsive-navbar-nav" />
							<Navbar.Collapse id="responsive-navbar-nav">
								<Nav className="me-auto">
									{/* <LinkContainer to="/">
										<Nav.Link>Home</Nav.Link>
									</LinkContainer> */}
									{(currentUser) &&
									<>
									<LinkContainer to="/Recomendation">
										<Nav.Link>My Recomendation</Nav.Link>
									</LinkContainer>
									<LinkContainer to="/MyLikes">
									<Nav.Link>My Likes</Nav.Link>
								</LinkContainer>
								
								<LinkContainer to="/Preferences">
									<Nav.Link>My Preferences</Nav.Link>
								</LinkContainer>

								{/*		CUANDO APRETAS EL LINK TE DESLOGUEA DIRECTAMENTE 	*/}
								<LinkContainer to="/Login">
										<Nav.Link>Logout</Nav.Link>
									</LinkContainer>
								</>
									}
									

								</Nav>
							</Navbar.Collapse>
						</Container>
					</Navbar>
				</Nav>
			</header>
		</>
	);
};

export default Header;
