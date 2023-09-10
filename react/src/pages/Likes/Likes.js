import { useState, useEffect } from 'react';
import Card from 'react-bootstrap/Card';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Layout from '../../components/ui/Layout';
import { getUserLikes } from '../../api/RestaurantApi';
import Button from 'react-bootstrap/Button';
import { Link } from "react-router-dom";

import './Likes.css';

/////cambiar a matches???
const Likes = () => {
    const [matches, setMatches] = useState([]);
    const [numberOfResults, setNumberOfRecords] = useState(0);
    const [restaurantId, setRestaurantId] = useState(null);

    useEffect(() => {
        const fetchData = async() => {
            try {
                const restaurantMatches = await getUserLikes();
                console.log(restaurantMatches);

                const uniqueRestaurant = [];
                const repeatedRestaurantId = new Set();

                for(const match of restaurantMatches) {
                    if(!repeatedRestaurantId.has(match.restaurantId)) {
                        uniqueRestaurant.push(match);
                        repeatedRestaurantId.add(match.restaurantId)
                    }
                }
                
                setMatches(uniqueRestaurant);
                setRestaurantId(restaurantMatches.restaurantId);
                setNumberOfRecords(uniqueRestaurant.length);
            } catch(error) {
                console.log("Error fetching matches", error);
            }
        }
        fetchData();
    },[])


    return (
        <>
        <Layout>
            
            <div className='container'>
                <div className='matches-card'>
                <h3>My Likes</h3>
                {(numberOfResults === 0) &&
                    <div>No likes</div>
                }
                <Row xs={1} md={2} className="g-4">
                    {matches.map((match, idx) => (
                        <Col key={idx}>
                        <Card className='match-card'>
                            <Card.Img variant="top" src={match.photoPath} />
                            <Card.Body>
                            <Card.Title>{match.restaurantName}</Card.Title>
                            <Card.Text>
                            {match.description}
                            </Card.Text>
                            <Card.Text className="detail-info">
                            <b>Phone Number:</b> {match.phoneNumber}
                            </Card.Text>
                            <Card.Text className="detail-info">
                            <b>Rating:</b> {match.rating}
                            </Card.Text>
                            <Card.Text className="detail-info">
                                <b>Address:</b> {match.address}
                            </Card.Text>
                            
                            <Link className="link-detail" to={`/Restaurant/Detail/${match.restaurantId}`}>
                                <b>See more...</b>
                            </Link>
                            </Card.Body>
                        </Card>
                        </Col>
                    ))}
            </Row>
            </div>
        </div>
    </Layout>
    </>
    )
}

export default Likes;