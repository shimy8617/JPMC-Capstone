import { useState,useContext, useEffect } from 'react';
import Layout from "../../components/ui/Layout";
import { UserContext } from "../../context/UserContext";
import { registerUser } from '../../api/UserApi';
import { useNavigate } from "react-router-dom";
import Form from 'react-bootstrap/Form';
import InputGroup from 'react-bootstrap/InputGroup';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import { getPreferencesList } from '../../api/PreferenceApi';
import { savePreferences } from '../../api/PreferenceApi';

import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';

import * as formik from 'formik';
import * as yup from 'yup';

import './Register.css';

// defino los valores iniciales
const initialValues = {
  username: '',
  password: '',
  confirmPassword: '',
  fullName: '',
  email: '',
  zipCode: '',
  address: '',
  role: 'User',
  preferencesArray: [],
};

// defino las validaciones de yup schema
const validationSchema = yup.object().shape({
  username: yup.string().required('Username is required'),
  password: yup.string().required('Password is required'),
  confirmPassword: yup.string()
    .oneOf([yup.ref('password'), null], 'Passwords must match')
    .required('Confirm Password is required'),
  fullName: yup.string().required('Full Name is required'),
  email: yup.string().email('Invalid email').required('Email is required'),
  zipCode: yup.string().required('Zip Code is required'),
  address: yup.string().required('Address is required'),
});

const Register = ({ children }) => {
    const navigate = useNavigate();    
    const [formFields, setFormFields] = useState(initialValues);
    const { username, password, confirmPassword, fullName, email, zipCode, address, role, preferencesArray } = formFields;
    const { setCurrentUser } = useContext(UserContext);

    const [preferences, setPreferences] = useState([]);
    const [restrictions, setRestrictions] = useState([]);
    const [numberOfResults, setNumberOfRecords] = useState(0);

    const[checkedPreference, setCheckedPreference] = useState([]);
    const[checkedRestriction, setCheckedRestriction] = useState([]);

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const { Formik } = formik;


    useEffect(() => {
      const fetchData = async () => {
          try {
              const data = await getPreferencesList(); // Assuming getPreferencesList is an async function that returns your data

              
              if (data.length > 0) {
                  let rArray = [];
                  let pArray = [];
                  for(var i =0; i < data.length; i++) {
                      if(data[i].typeId >= 0 && data[i].typeId < 4) {
                          rArray.push(data[i]); 
                      } else if (data[i].typeId > 3) {
                          pArray.push(data[i]);
                      }
                  }
                  setRestrictions(rArray);
                  setPreferences(pArray);
                  setNumberOfRecords(data.length);
              }
          } catch (error) {
              console.error("Error fetching preferences:", error);
          }
      }; 
      fetchData();
  }, []); // Corre el useEffect una sola vez cuanto se monta el componente

  // estoy chequeando que queden todos mis datos seleccionados en el nuevo array
  // de manera inmediata y sin retraso como lo hace el console.log
  useEffect(() => {
    console.log("Updated preferencesArray:", formFields.preferencesArray);
  }, [formFields.preferencesArray]);


    const handleSubmit = async (event) => {
        // event.preventDefault();
        if (password !== confirmPassword) {
            alert("password do not match");
            return;
        } 
        if (checkedPreference.length === 0 && checkedRestriction.length === 0) {
          alert("please select your preferences");
          return;
        } try {
            const userRegister = { ...formFields, role: "User" }; 
            await registerUser(userRegister);
            
            navigate("/Login");

            
        } catch(error) {
            console.log('user created encounter an error', error);
        } 

    }

    //  Este handlecheck se utiliza solo para las preferencias y restricciones
    const handleCheck = (event) => {
      const { name, value, checked } = event.target;
      setFormFields({...formFields,[name]:value});

      if(checked) {
        if (parseInt(name) > 3) {
        setCheckedPreference([...checkedPreference, name]);
        console.log("check preference: " + name)
        } else if(parseInt(name) >= 0 && parseInt(name) <= 3) {
          setCheckedRestriction([...checkedRestriction, name]);
          console.log("check restriction: " + name)
        } 
      } else {
        if (parseInt(name) > 3) {
        setCheckedPreference([preferences.filter(prevPref => prevPref !== name)]);
        console.log("unchecked preference: " + name);
        }  else if (parseInt(name) >= 0 && parseInt(name) <= 3) {
          setCheckedRestriction([restrictions.filter(prevRestr => prevRestr !== name)]);
          console.log("unchecked restriction: " + name)
        }
      }
  }

    // Es para guardar las preferencias y restricciones
    const handleSave = (e) => {
      let userPreferences = [];
      if(checkedPreference.length === 0 && checkedRestriction.length === 0) {
        alert("Please choose at least one preference");
      } else {
        if(checkedPreference.length !== 0) {
          // // Uso los puntos suspensivos para agregar las preferencias seleccionadas individualmente
          userPreferences.push(...checkedPreference);
        } 
        if(checkedRestriction.length !== 0) {
          userPreferences.push(...checkedRestriction);
        }
          // seteo las preferencias seleccionadas en el formFields state
          setFormFields({ ...formFields, preferencesArray: userPreferences });

          // Cierro el modal una vez guardado
          handleClose();
        }
        console.log("Updated preferencesArray:", formFields.preferencesArray);
        
      
    }

    
    return (
        <Layout>
          <div className='container'>
            <div className='register-form'>
            <h3>Register</h3>
            <Formik
                initialValues={initialValues}
                validationSchema={validationSchema}
                onSubmit={handleSubmit}
              >
            {({ handleSubmit,handleChange, values, touched, errors  }) => (
            <Form noValidate onSubmit={handleSubmit}>

              {/*  primeros 3 fields*/}

              <Row className="mb-3">
                <Form.Group as={Col} md="4" controlId="validationFormik01">
                <Form.Label className='label'>Username</Form.Label>
                  <Form.Control
                    type="text" 
                    // como estoy usando formik, tuve que setear formfields individualmente
                    // para no mezclar los handlechange
                    onChange={(e) => {
                      handleChange(e);
                      setFormFields({ ...formFields, username: e.target.value });
                    }}
                    // onChange={handleChange}
                    value={values.username} 
                    name="username"
                    placeholder="Username" 
                    required
                    isInvalid={!!errors.username}
                  />
                  <Form.Control.Feedback type="invalid">
                  {errors.username}
                  </Form.Control.Feedback>
                  </Form.Group>

                  <Form.Group as={Col} md="4" controlId="validationFormik02">
                <Form.Label className='label'>Password</Form.Label>
                  <Form.Control
                    aria-label="Default"
                    aria-describedby="inputGroup-sizing-default"
                    type="password" 
                    onChange={(e) => {
                      handleChange(e);
                      setFormFields({ ...formFields, password: e.target.value });
                    }}
                    // onChange={handleChange} 
                    value={values.password} 
                    name="password" 
                    placeholder="Password"
                    isInvalid={!!errors.password}
                  />
                  <Form.Control.Feedback type="invalid">
                  {errors.password}
                </Form.Control.Feedback>
                </Form.Group>
                  

                <Form.Group as={Col} md="4" controlId="validationFormik03">
                <Form.Label className='label'>Confirm Password</Form.Label>
                  <Form.Control
                    aria-label="Default"
                    aria-describedby="inputGroup-sizing-default"
                    type="password" 
                    onChange={(e) => {
                      handleChange(e);
                      setFormFields({ ...formFields, confirmPassword: e.target.value });
                    }}
                    // onChange={handleChange} 
                    value={values.confirmPassword} 
                    name="confirmPassword" 
                    placeholder="confirmPassword"
                    isInvalid={!!errors.confirmPassword}
                  />
                  <Form.Control.Feedback type="invalid">
                  {errors.confirmPassword}
                </Form.Control.Feedback>
                </Form.Group>
                </Row>

                
                  {/*  segundos 3 fields*/}

                <Row className="mb-3">  
                <Form.Group as={Col} md="6" controlId="validationFormik04">
                <Form.Label className='label'>Full name</Form.Label>
                  <Form.Control
                    aria-label="Default"
                    aria-describedby="inputGroup-sizing-default"
                    type="text" 
                    onChange={(e) => {
                      handleChange(e);
                      setFormFields({ ...formFields, fullName: e.target.value });
                    }}
                    // onChange={handleChange} 
                    value={values.fullName} 
                    name="fullName" 
                    placeholder="Full name" 
                    required
                    isInvalid={!!errors.fullName}
                  />
                  <Form.Control.Feedback type="invalid">
                  Please enter a valid full name.
                </Form.Control.Feedback>
                </Form.Group>

                <Form.Group as={Col} md="6" controlId="validationFormik05">
                <Form.Label className='label'>Email</Form.Label>
                  <Form.Control
                    aria-label="Default"
                    aria-describedby="inputGroup-sizing-default"
                    type="text" 
                    onChange={(e) => {
                      handleChange(e);
                      setFormFields({ ...formFields, email: e.target.value });
                    }}
                    // onChange={handleChange} 
                    value={values.email} 
                    name="email" 
                    placeholder="Email" 
                    required
                    isInvalid={!!errors.email}
                  />
                  <Form.Control.Feedback type="invalid">
                    Please enter a valid email.
                  </Form.Control.Feedback>
                </Form.Group>
                </Row>

                <Row className="mb-3">  
                <Form.Group as={Col} md="6" controlId="validationFormik06">
                <Form.Label className='label'>Zip Code</Form.Label>
                  <Form.Control
                    aria-label="Default"
                    aria-describedby="inputGroup-sizing-default"
                    type="number" 
                    onChange={(e) => {
                      handleChange(e);
                      setFormFields({ ...formFields, zipCode: e.target.value });
                    }}
                    // onChange={handleChange} 
                    value={values.zipCode} 
                    name="zipCode" 
                    placeholder="Zip Code" 
                    required
                    isInvalid={!!errors.zipCode}
                  />
                  <Form.Control.Feedback type="invalid">
                    Please enter a valid zip code.
                  </Form.Control.Feedback>
                </Form.Group>

                <Form.Group as={Col} md="6" controlId="validationFormik07">
                <Form.Label className='label'>Address</Form.Label>
                  <Form.Control
                    type="text" 
                    onChange={(e) => {
                      handleChange(e);
                      setFormFields({ ...formFields, address: e.target.value });
                    }}
                    // onChange={handleChange} 
                    value={values.address}  
                    name="address" 
                    placeholder="Address" 
                    required
                    isInvalid={!!errors.address}
                  />
                  <Form.Control.Feedback type="invalid">
                    Please enter a valid address.
                  </Form.Control.Feedback>
                </Form.Group>
                </Row>
                
                  <Row className="mb-3">  
                    <Form.Group as={Col} md="2">
                        <Form.Label className='label'>Assign Role</Form.Label>
                          <Form.Control
                            name="role"
                            type="text"
                            placeholder="User"
                            aria-label="Disabled input example"
                            readOnly
                            disabled 
                          />
                    </Form.Group>
                    </Row>
                    <Row className="mb-3">  
                    <Button variant="warning" onClick={handleShow} className='pref-btn'>
                      Choose your preferences
                    </Button>
                  </Row>

      <Modal show={show} onHide={handleClose} animation={false}>
        <Modal.Header closeButton>
          <Modal.Title>My Preferences</Modal.Title>
        </Modal.Header>
        <Modal.Body>
                <Form /*onSubmit={handleSearch}*/>
                {(numberOfResults === 0) &&
                    <div>No preferences found</div>
                }
                {/*CAMBIAR PREFERENCE POR TIPO DE COMIDA U OTRO*/}
                    <p>Select your preferences: </p>
                    {preferences.map(preference => (
                    <Form.Check // prettier-ignore
                        key={`preference-${preference.typeId}`} // Agrego un id unico
                        type="switch"
                        id={`custom-switch-${preference.typeId}`} // Hago uso del unique id
                        label={preference.categoryName}
                        // le voy asignando el nombre para ir guardandolo en la base de datos
                        name={preference.typeId}
                        value={preferencesArray}
                        onChange={handleCheck}
                    />
                    ))}
                    <p>Select your restrictions: </p>
                    {restrictions.map(restriction => (
                    <Form.Check // prettier-ignore
                    key={`preference-${restriction.typeId}`} // Agrego un id unico
                        type="switch"
                        id={`custom-switch-${restriction.typeId}`} // Hago uso del unique id
                        label={restriction.categoryName}
                        // le voy asignando el nombre para ir guardandolo en la base de datos
                        name={restriction.typeId}
                        value={preferencesArray}
                        onChange={handleCheck}
                    />
                    ))}
                  
                </Form></Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
          <Button variant="primary" onClick={handleSave}>
            Save My Preferences
          </Button>
        </Modal.Footer>
      </Modal>



  <Row className="mb-3">  
    <Button className="btn btn-danger btn-submit" type="submit">
      Submit
    </Button>
  </Row>
</Form>

            )}
        </Formik>
        </div>
        </div>
          {children}
        </Layout>
    )
}

export default Register;