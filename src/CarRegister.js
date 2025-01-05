import React, { useState } from 'react';
import './CarRegister.css';
import carRegisterData from './carRegister.json';
import parkingLotImage from './images/parkingLot.png';

function CarRegister() {
  const [formData, setFormData] = useState({
    licensePlate: '',
    carBrand: '',
    color: '',
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleRegisterCar = (e) => {
    e.preventDefault(); 
    alert(
      `Car Registered:\nLicense Plate: ${formData.licensePlate}\nCar Brand: ${formData.carBrand}\nColor: ${formData.color}`
    );
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
        <button type="submit" className="custom-button">
          {carRegisterData.buttonText}
        </button>
      </form>

      <div className="parking-lot-image-container">
        <img
          src={parkingLotImage}
          alt="Parking Lot"
          className="parking-lot-image"
        />
      </div>
    </div>
  );
}

export default CarRegister;
