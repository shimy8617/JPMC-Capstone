import { useEffect, useState } from "react";
import Layout from "../../components/ui/Layout";
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";

import './Recomendation.css';

import { getRandomRestaurant } from "../../api/RestaurantApi";
import { storeUserLikes } from "../../api/UserApi";

const Recomendation = () => {
    const navigate = useNavigate();  
    // const [randomRestaurant, setRandomRestaurant] = useState([]);
    const [name, setName] = useState();
    const [description, setDescription] = useState();
    const [zipCode, setZipCode] = useState();
    const [photo, setPhoto] = useState();
    const [numberOfResults, setNumberOfRecords] = useState(0);
    const [restaurantId, setRestaurantId] = useState(null);


    useEffect(() => {
        const fetchData = async() => {
            try{
                const data = await getRandomRestaurant();
                console.log(data);


                    setName(data.restaurantName);
                    setDescription(data.description);
                    setZipCode(data.zipCode);
                    setPhoto(data.photoPath);
                    setRestaurantId(data.restaurantId);
                    setNumberOfRecords(data.length);


            } catch (error) {
                console.error("Error fetching preferences:", error);
            }
        }
        fetchData();
    },[])

    const handleClick = (e) => {
        e.preventDefault();
        const fetchData = async() => {
            const restaurantLike = await storeUserLikes(restaurantId);
            setRestaurantId(restaurantLike);
            
            window.location.reload();
        }
        fetchData();
    }

    const handleDelete = (e) => {
        e.preventDefault();
        window.location.reload();
    }
 
    return (
        <>
        <Layout>
            <div className="container">
                <div>
            
            {(numberOfResults === 0) &&
                    <div>No restaurant found</div>
            }
            
                <Card style={{ width: '30rem'}}>
                <div className="recom-title">Recommendation for you</div>
                <Card.Img variant="top" src={photo} />
                    <Card.Body className="card-recom">
                        <Card.Title>{name}</Card.Title>
                        <Card.Text className="card-text">
                            <Card.Text className="rest-info">
                                {description}
                            </Card.Text>
                            <Card.Text className="rest-info">
                                <b>Zip Code: </b>{zipCode}
                            </Card.Text>
                            <Link className="link-detail" to={`/Restaurant/Detail/${restaurantId}`}>
                                <b>See more...</b>
                            </Link>
                        </Card.Text>
                        <div className="btn-row">
                            <Button onClick={handleClick} variant="primary" className="btn-like"><i className="bi bi-heart-fill"></i></Button>
                            <Button onClick={handleDelete} variant="primary" className="btn-dislike"><i className="bi bi-x-lg"></i></Button>
                        </div>
                    </Card.Body>
                </Card>
                </div>
            </div>
        </Layout>
        </>
    )
}

export default Recomendation;