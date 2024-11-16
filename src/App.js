import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './App.css';
import carIcon from './images/carIcon.png';

function App() {
  const [user, setUser] = useState(null); // State to hold user data
  const [error, setError] = useState(null); // State to hold errors

  useEffect(() => {
    // Fetch the first user
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

  return (
    <div className="App">
      <header className="App-header">
        <img src={carIcon} className="App-logo" alt="logo" />
        <p>
          {user ? (
            <>
              <strong>First User:</strong>
              <br />
              Username: {user.username}
              <br />
              Email: {user.email}
            </>
          ) : error ? (
            <span style={{ color: 'red' }}>{error}</span>
          ) : (
            'Loading user data...'
          )}
        </p>
      </header>
    </div>
  );
}

export default App;
