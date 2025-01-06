// WelcomeScreen.js
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './WelcomeScreen.css';
import { useNavigate } from 'react-router-dom';
import carIcon from './images/carIcon.png';

function WelcomeScreen() {
  const [user, setUser] = useState(null);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    axios
      .get('http://localhost:8081/users')
      .then((response) => {
        if (response.data && response.data.length > 0) {
          setUser(response.data[0]);
        } else {
          setError('No users found.');
        }
      })
      .catch((error) => {
        setError('Error fetching user: ' + error.message);
      });
  }, []);

  const handleRegisterCar = () => {
    navigate('/registerCar');
  };

  const handleViewParkingSpots = () => {
    alert('Fetching available parking spots...');
  };

  const handleLogout = () => {
    localStorage.removeItem('currentUser');
    setUser(null);
    navigate('/login');
  };

  return (
    <div className="WelcomeScreen">
      <header className="WelcomeScreen-header">
        <img src={carIcon} className="App-logo" alt="logo" />
        <p>
          {user ? (
            <>
              <strong>Welcome</strong>
              <br />
              To the Parking Lot Allocation Dashboard
            </>
          ) : error ? (
            <span style={{ color: 'red' }}>{error}</span>
          ) : (
            'Loading user data...'
          )}
        </p>
        <div className="button-container">
          <button className="custom-button" onClick={handleRegisterCar}>
            Register a New Car in a Parking Spot
          </button>
          <button className="custom-button" onClick={handleViewParkingSpots}>
            See Parking Spots Available
          </button>
        </div>
        <p className="logout-link" onClick={handleLogout}>
          <span>Log out</span>
        </p>
      </header>
    </div>
  );
}

export default WelcomeScreen;
