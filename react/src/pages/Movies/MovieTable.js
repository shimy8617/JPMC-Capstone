import Table from "../../components/ui/table/Table";
import {useState} from "react";
import Form from 'react-bootstrap/Form';
import {Button} from "react-bootstrap";
import {getMovieList} from "../../api/MovieApi";
import Layout from "../../components/ui/Layout";



 const MovieTable = ({children}) => {

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
        });
      }
      search();
    };
    const columns = [
        { label: "Title", accessor: "title", sortable: true, sortbyOrder: "desc" },
        { label: "Length", accessor: "lengthMinutes", sortable: true },
        { label: "Director", accessor: "directorName", sortable: true },
        { label: "Tag Line", accessor: "tagline", sortable: true }
    ];
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
          
            {(movies && (movies.length === 0)) &&
              <div>No movies found</div>
            }
            {movies && (movies.length > 0) &&
                <>
                <h1>Sortable Movies Table {numberOfResults}</h1>
                <Table
                    caption="Note the column headers are sortable."
                    data={movies}
                    columns={columns}
                />
                </>
            }
        {children}
      </Layout>
    );
  }
  export default MovieTable;
