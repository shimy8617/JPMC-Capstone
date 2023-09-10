import React, { useState, useEffect } from 'react';
import Card from 'react-bootstrap/Card';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import {getMovieDetail, saveMovieDetail} from "../../api/MovieApi";
import {useParams} from "react-router-dom";
import {ListGroup, ListGroupItem} from "reactstrap";
import Modal from "react-bootstrap/Modal";
import Form from "react-bootstrap/Form";
import {Button, ButtonGroup} from "react-bootstrap";
import Moment from 'moment'
import Loading from '../../components/ui/Loading';
import Layout from '../../components/ui/Layout';


const MovieDetail = () => {
  let { movieId } = useParams();
  const [movieDetail, setMovieDetail] = useState({});
  const [callingDetail, setCalling] = useState(false);

  const [movieOverview, setMovieOverview] = useState("");
  const [showMovieDetailModal, setShowMovieDetailModal] = useState(false);

  const handleCancel = () => {
    setShowMovieDetailModal(false);
  };
  const handleUpdateDetail = (event) => {
    if (event !== undefined) event.preventDefault();
    const updateDetail = async () => {
      movieDetail.overview = movieOverview;
      saveMovieDetail(movieDetail).then(data=>{
        /*TODO - fix this to reset with the updated movie detail
         */
        // setMovieDetail(data);
        setShowMovieDetailModal(false);
      });
    }
    updateDetail();
  };
    const doSetOverview = (event) => {
    setMovieOverview(event.target.value);
  };
  const handleShowModalOn = () => {
    if (movieDetail!==undefined) {
      setMovieOverview(movieDetail.overview);
      setShowMovieDetailModal(true);
    }
  };
  
  useEffect(() => {
    const getDetail = () => {
      const movieDetail = async () => {
        if (!callingDetail) {
          setCalling(true);
          getMovieDetail(movieId).then(data => {
            setCalling(false);
            setMovieDetail(data);
          });
        }
      }
      movieDetail();
    };    
    getDetail();
  }, []);


  // const {
  //   data: movieDetail,
  //   error: movieDetailError,
  //   loading: movieDetailLoading
  // } = useFetch(`movie/detail/${movieId}`);
  // if (movieDetailError) throw movieDetailError;
  // if (movieDetailLoading) return <Loading />;

  // const columns = [
  //   { label: "Name", accessor: "name", sortable: true, sortbyOrder: "asc" }
  // ];
  if (callingDetail) return <Loading />;
  
  return (
  <Layout>
    <Row>
        <Col key="imageCard">
          <Card className="my-2"
                style={{
                  minWidth: '16rem'
                }}>
            <Card.Img variant="top" src={movieDetail?.posterPath} alt={movieDetail?.title} width="10%" />
          </Card>
        </Col>
        <Col key="titleCard">
          <Card className="my-2"
                style={{
                  minWidth: '16rem'
                }}>
            <Card.Body>
              <Card.Title>{movieDetail?.title}</Card.Title>
              <Card.Subtitle className="mb-2 text-muted moveSubtitle"
                             tag="h6">{movieDetail?.tagline}</Card.Subtitle>
              <Card.Text>
                {movieDetail?.overview}
              </Card.Text>
              <Card.Link onClick={handleShowModalOn} href="#">
                Edit Overview
              </Card.Link>
              <Card.Text>
                Release Date: {Moment(movieDetail?.releaseDate).format('YYYY-MM-DD')}
              </Card.Text>
              <Card.Text>
                Popularity: {movieDetail?.popularity}
              </Card.Text>
              <Card.Text>
                Vote Average: {movieDetail?.voteAverage}
              </Card.Text>
              <Card.Text>
                Vote Count: {movieDetail?.voteCount}
              </Card.Text>
            </Card.Body>
          </Card>
      </Col>
        <Col key="actorsCard">
          <Card className="my-2"
                style={{
                  minWidth: '16rem'
                }}>
            <Card.Title>Actors</Card.Title>
            {movieDetail?.actors !== undefined && <ListGroup flush>
              {movieDetail.actors.map(actor => (
              <ListGroupItem key={actor.id}>
                {actor.name}
              </ListGroupItem>
              ))}
            </ListGroup>
              }
            </Card>
        </Col>
    </Row>
    <Modal show={showMovieDetailModal} onHide={handleCancel}>
          <Form onSubmit={handleUpdateDetail}>
            <Modal.Header closeButton>
              <Modal.Title>Edit Movie Overview</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              <Form.Group className="mb-3">
                <Form.Label>Description</Form.Label>
                <Form.Control as="textarea" rows={10}
                              value={movieOverview} onChange={doSetOverview} />
              </Form.Group>
            </Modal.Body>
            <Modal.Footer>
              <ButtonGroup>
                <Button variant="primary" onClick={handleUpdateDetail} type="submit">
                  Save
                </Button>
                <Button variant="secondary" onClick={handleCancel}>
                  Cancel
                </Button>
              </ButtonGroup>
            </Modal.Footer>
          </Form>
        </Modal>
  </Layout>
  );
}
export default MovieDetail;