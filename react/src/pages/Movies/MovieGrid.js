import Layout from "../../components/ui/Layout";
import React, { useState } from 'react';
import Card from 'react-bootstrap/Card';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import { Button } from 'react-bootstrap';
import Form from "react-bootstrap/Form";
import Nav from "react-bootstrap/Nav";
import { LinkContainer } from "react-router-bootstrap";
import { getMovieList } from "../../api/MovieApi";

const MovieGrid = ({children}) => {

  const [movies, setMovies] = useState([]);
  const [searchValue, setSearchValue] = useState("");
  const [numberOfResults, setNumberOfRecords] = useState(0);
  const doSetSearchValue = (event) => {
    setSearchValue(event.target.value);
  };
  const handleSearch = (event) => {
    if (event !== undefined) event.preventDefault();
    const search = async () => {
      const searchCriteria = {title: searchValue};
      getMovieList(searchCriteria).then(data => {
        console.log("Found:" + data.length + " records");
        setMovies(data);
        setNumberOfRecords(data.length);
      }).catch(error=>{        
        alert("did it work??");
        throw new Error("Could not get movies from server");
        
      });
    }
    search();
  };

  return (
    <Layout>
      <Form onSubmit={handleSearch}>
        <Form.Control
          type="search"
          placeholder="Search"
          className="me-2"
          aria-label="Search"
          value={searchValue}
          onChange={doSetSearchValue}
        />
        <Button variant="outline-success" onClick={handleSearch}>Search Movies</Button>
        </Form>
        <Row>
          {(numberOfResults === 0) &&
            <div>No movies found</div>
          }
          {movies.map(movie => (
            <Col key={movie.id}>
              <Card className="my-2"
                style={{
                  width: '18rem'
                }}>
                <Card.Img variant="top" src={movie.posterPath} alt={movie.title} width="10%" />
                <Card.Body>
                  <Card.Title>{movie.title}</Card.Title>
                  <Card.Subtitle className="mb-2 text-muted moveSubtitle"
                    tag="h6">{movie.tagline}</Card.Subtitle>
                  <Card.Text>
                    {movie.overview}
                  </Card.Text>
                  <Card.Link>
                    <LinkContainer to={{ pathname: `/movieDetail/${movie.id}` }} >
                      <Nav.Link>Show Detail</Nav.Link>
                    </LinkContainer></Card.Link>
                </Card.Body>
              </Card>
            </Col>
          ))}
        </Row>      
      {children}
    </Layout>
  );
}
export default MovieGrid;