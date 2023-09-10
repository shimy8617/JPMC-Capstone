import { useState, useEffect } from "react";
import Layout from "../../components/ui/Layout";
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import {useParams, Link} from "react-router-dom";
import Loading from '../../components/ui/Loading';

import { getRestaurantById } from '../../api/RestaurantApi';
import { storeUserLikes } from "../../api/UserApi";
import { useNavigate } from "react-router-dom";

import './Detail.css';

const Detail = () => {
    const navigate = useNavigate();
    let { restaurantId } = useParams();
    const[restaurantDetail, setRestaurantDetail] = useState({});
    const [callingDetail, setCalling] = useState(false);
    const [numberOfResults, setNumberOfRecords] = useState(0);
    const [restId, setRestaurantId] = useState(null);
    
    
    useEffect(() => {
        const getDetail = () => {
        const restaurantDetail = async() => {
            if (!callingDetail) {
                setCalling(true);
                const localRestaurant = await getRestaurantById(restaurantId);
                setCalling(false);
                setRestaurantDetail(localRestaurant);
                setRestaurantId(restaurantId);
                
                console.log(localRestaurant);
                setNumberOfRecords(localRestaurant.length);
            } 
            }
            restaurantDetail();
        }
        getDetail();
    }, [])

    const handleClick = (e) => {
        e.preventDefault();
        const fetchData = async() => {
            const restaurantLike = await storeUserLikes(restId);
            setRestaurantId(restaurantLike);
            
            navigate('/Recomendation');
        }
        fetchData();
    }

    if (callingDetail) return <Loading />;


    const handleDelete = (e) => {
        e.preventDefault();
        navigate('/Recomendation');
    }

    return (
        <>
            <div className="container">
                <div>
            
            {(numberOfResults === 0) &&
                    <div>No restaurant found</div>
            }
            
                <Card style={{ width: '40rem'}}>
                {/* <div className="recom-title">This are the details of the Restaurant</div> */}
                <Card.Img className="card-img-detail" variant="top" src={restaurantDetail?.photoPath} />
                    <Card.Body className="card-recom">
                        <Card.Title>{restaurantDetail?.restaurantName}</Card.Title>
                        <Card.Text className="detail-info">
                            {restaurantDetail?.description}
                        </Card.Text>
                        <Card.Text className="detail-info">
                        <b>Phone Number:</b> {restaurantDetail?.phoneNumber}
                        </Card.Text>
                        <Card.Text className="detail-info">
                        <b>Rating:</b> {restaurantDetail?.rating}
                        </Card.Text>
                        <Card.Text className="detail-info">
                        <b>Review:</b> {restaurantDetail?.review}
                        </Card.Text>
                        <Card.Text className="detail-info">
                        <b>Address:</b> {restaurantDetail?.address}
                        </Card.Text>
                        <Link className="link-detail" to={restaurantDetail?.homePage} target="_blank" rel="noreferrer">
                        <b>Go to the site!</b>
                        </Link>

                        <div className="btn-row">
                            <Button onClick={handleClick} variant="primary" className="btn-like"><i className="bi bi-heart-fill"></i></Button>
                            <Button onClick={handleDelete} variant="primary" className="btn-dislike"><i className="bi bi-x-lg"></i></Button>
                        </div>
                    </Card.Body>
                </Card>
                </div>
            </div>
        </>
    )
}

export default Detail;