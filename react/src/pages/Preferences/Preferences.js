import React, { useState, useContext, useEffect } from "react";
import Layout from "../../components/ui/Layout";
import Form from 'react-bootstrap/Form';
import { Button, ButtonGroup } from 'react-bootstrap';
import { preferencesUser } from "../../api/PreferenceApi";
import { UserContext } from "../../context/UserContext";
import { useNavigate } from "react-router-dom";
import { getPreferencesList } from "../../api/PreferenceApi";
//import { getUserPreferences} from "../../api/PreferenceApi";

import './Preferences.css';

const Preferences = () => {
    
    const userContext = useContext(UserContext);
  const { setCurrentUser } = userContext;
  const user = userContext.currentUser;
  const navigate = useNavigate();

  const [preferences, setPreferences] = useState([]);
  const [restrictions, setRestrictions] = useState([]);
  const [numberOfResults, setNumberOfResults] = useState(0);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await getPreferencesList();

        if (data.length > 0) {
          const rArray = data.filter(item => item.typeId >= 0 && item.typeId < 4);
          const pArray = data.filter(item => item.typeId > 3);

          setRestrictions(rArray);
          setPreferences(pArray);
          setNumberOfResults(data.length);
        }
      } catch (error) {
        console.error("Error fetching preferences:", error);
      }
    };

    fetchData();
  }, []);

  const isPreferenceChecked = (preferenceTypeId) => {
    return user.typeOfFood.some(item => item.typeId === preferenceTypeId);
  };

  const togglePreference = (typeId, isPreference) => {
    const updatedUserPreferences = [...user.typeOfFood];
    const itemIndex = updatedUserPreferences.findIndex(item => item.typeId === typeId);

    if (itemIndex !== -1) {
      // Si el item esta, eliminalo
      updatedUserPreferences.splice(itemIndex, 1);
    } else {
      // Si el item no esta, agregalo
      updatedUserPreferences.push({ typeId });
    }

    // Estoy actualizando las preference del user
    setCurrentUser({ ...user, typeOfFood: updatedUserPreferences });
  };

  const buildUniqueUserPrefList = () => {
    const newArray = [];
    const prefList = preferences.filter(preference => isPreferenceChecked(preference.typeId));
        for(let i = 0; i < prefList.length; i++) {
            const prefId = prefList[i].typeId;
            newArray.push(prefId);
        }
    const restrList = restrictions.filter(restriction => isPreferenceChecked(restriction.typeId));
        for(let i = 0; i <restrList.length; i++) {
            const restrId = restrList[i].typeId;
            newArray.push(restrId);
        }
        console.log(newArray);
    return newArray;
    
  }
  
  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      const userPreferences = {
        userId: user.id,
        typeIdList: buildUniqueUserPrefList()
      };

      const preferenceUser = await preferencesUser(userPreferences);

      console.log("Updated preferences:", preferenceUser);
      navigate("/Recomendation");

    } catch (error) {
      console.error('Error updating preferences:', error);
    }
  };

  return (
    <>
      <Layout>
        <div className="container">
          <div className="form-container">
            <h3>My Preferences</h3>
            <Form>
              {(numberOfResults === 0) && <div>No preferences found</div>}
              <h4>Select your preferences: </h4>
              {preferences.map(preference => (
                <Form.Check
                  key={preference.typeId}
                  type="switch"
                  id={`custom-switch-pref-${preference.typeId}`}
                  label={preference.categoryName}
                  name={`pref-${preference.typeId}`}
                  onChange={() => togglePreference(preference.typeId, true)}
                  checked={isPreferenceChecked(preference.typeId)}
                />
              ))}
              <h4>Select your restrictions: </h4>
              {restrictions.map(restriction => (
                <Form.Check
                  key={restriction.typeId}
                  type="switch"
                  id={`custom-switch-res-${restriction.typeId}`}
                  label={restriction.categoryName}
                  name={`res-${restriction.typeId}`}
                  onChange={() => togglePreference(restriction.typeId, false)}
                  checked={isPreferenceChecked(restriction.typeId)}
                />
              ))}
              <ButtonGroup>
                <Button variant="primary" type="submit" onClick={handleSubmit}>
                  Submit
                </Button>
              </ButtonGroup>
            </Form>
          </div>
        </div>
      </Layout>
    </>
  );
}
export default Preferences;