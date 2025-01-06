// src/screens/CarRegister.js

import React, { useState, useContext } from 'react';
import './CarRegister.css';
import carRegisterData from './carRegister.json';
import parkingLotImage from './images/parkingLot.png';
import axios from 'axios';
import { UserContext } from './UserContext';
import { useNavigate } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function CarRegister() {
  const { user, token } = useContext(UserContext); // Access user and token
  const [formData, setFormData] = useState({
    licensePlate: '',
    carBrand: '',
    color: '',
  });
  const [isSubmitting, setIsSubmitting] = useState(false);
  const navigate = useNavigate();

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleRegisterCar = async (e) => {
    e.preventDefault();
    setIsSubmitting(true);

    if (!user || !user.id_user) {
      toast.error('User is not authenticated.');
      setIsSubmitting(false);
      return;
    }

    const dataToSend = {
      id_user: user.id_user,
      license_plate: formData.licensePlate,
      car_brand: formData.carBrand,
      color: formData.color,
    };

    try {
      await axios.post('http://localhost:8081/vehicles', dataToSend, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`, 
        },
      });
      toast.success('Car registered successfully!');
      setFormData({
        licensePlate: '',
        carBrand: '',
        color: '',
      });
      navigate('/welcome');
    } catch (error) {
      console.error('Registration error:', error);

      if (error.response) {
        const { status, data } = error.response;

        switch (status) {
          case 400:
            toast.error(`Bad Request: ${data.message || 'Invalid input data.'}`);
            break;
          case 401:
            toast.error('Unauthorized: Please log in to register a car.');
            break;
          case 403:
            toast.error('Forbidden: You do not have permission to perform this action.');
            break;
          case 404:
            toast.error('Not Found: The requested resource was not found.');
            break;
          case 409:
            toast.error('Conflict: A car with this license plate already exists.');
            break;
          case 500:
            toast.error('Internal Server Error: Please try again later.');
            break;
          default:
            toast.error(`Error ${status}: ${data.message || 'An unexpected error occurred.'}`);
        }
      } else if (error.request) {
        toast.error('No response from server. Please check your network connection.');
      } else {
        toast.error(`Error: ${error.message}`);
      }
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="CarRegister">
      <h1>{carRegisterData.title}</h1>
      <form className="car-register-form" onSubmit={handleRegisterCar}>
        {carRegisterData.fields.map((field) => (
          <div key={field.name} className="form-group">
            <label htmlFor={field.name}>{field.label}</label>
            <input
              type="text"
              id={field.name}
              name={field.name}
              placeholder={field.placeholder}
              value={formData[field.name]}
              onChange={handleInputChange}
              required
            />
          </div>
        ))}
        <button type="submit" className="custom-button-car" disabled={isSubmitting}>
          {isSubmitting ? 'Registering...' : carRegisterData.buttonText}
        </button>
      </form>
      <div className="parking-lot-image-container">
        <img src={parkingLotImage} alt="Parking Lot" className="parking-lot-image" />
      </div>
      <ToastContainer />
    </div>
  );
}

export default CarRegister;
