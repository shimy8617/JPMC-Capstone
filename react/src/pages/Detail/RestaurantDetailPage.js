import { useParams } from "react-router-dom";
import Layout from '../../components/ui/Layout';
import Detail from "./Detail";


const MovieDetailPage = ({ children }) => {
  let { restaurantId } = useParams();  

  return (
    <Layout>
      <Detail restaurantId={restaurantId}/>
      {children}
    </Layout>
  );
}
export default MovieDetailPage;