// src/screens/LoginScreen.js

import React, { useState, useContext } from 'react';
import './LoginScreen.css';
import { useNavigate } from 'react-router-dom';
import axios from 'axios'; 
import { UserContext } from './UserContext'; 

function LoginScreen() {
  const [credentials, setCredentials] = useState({
    id_user:'',
    username: '',
    password: '',
  });
  const [errorMessage, setErrorMessage] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false); 

  const navigate = useNavigate();
  const { setUser } = useContext(UserContext); 

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setCredentials((prevCredentials) => ({
      ...prevCredentials,
      [name]: value,
    }));
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    setErrorMessage('');
    setIsSubmitting(true); 

    try {
      const response = await axios.get(`http://localhost:8081/users/${credentials.username}`, {
        headers: {
          'Content-Type': 'application/json',
        },
      });

      const user = response.data;

      if (credentials.password === user.password) {
        const mappedUser = {
          id_user: user.id_user,
          username: user.username,
          password: user.password
        };

        setUser(mappedUser);
        localStorage.setItem('currentUser', JSON.stringify(mappedUser));

        navigate('/welcome');
      } else {
        setErrorMessage('Invalid username or password.');
      }
    } catch (error) {
      console.error('Login error:', error);

      if (error.response) {
        if (error.response.status === 404) {
          setErrorMessage('User not found.');
        } else {
          setErrorMessage('Authentication failed.');
        }
      } else if (error.request) {
        setErrorMessage('No response from server. Please try again later.');
      } else {
        setErrorMessage('An unexpected error occurred.');
      }
    } finally {
      setIsSubmitting(false); 
    }
  };

  const handleRegisterNavigation = () => {
    navigate('/registerAccount');
  };

  return (
    <div className="LoginScreen">
      <header className="LoginScreen-header">
        <h1>Login to Your Account</h1>
        <form className="login-form" onSubmit={handleLogin}>
          <div className="form-group">
            <label htmlFor="username">Username</label>
            <input
              type="text"
              id="username"
              name="username"
              placeholder="Enter your username"
              value={credentials.username}
              onChange={handleInputChange}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="password">Password</label>
            <input
              type="password"
              id="password"
              name="password"
              placeholder="Enter your password"
              value={credentials.password}
              onChange={handleInputChange}
              required
            />
          </div>
          {errorMessage && <p className="error-message">{errorMessage}</p>}
          <button type="submit" className="custom-button" disabled={isSubmitting}>
            {isSubmitting ? 'Logging in...' : 'Login'} 
          </button>
        </form>
        <div className="register-link-container">
          <p className="register-link" onClick={handleRegisterNavigation}>
            Do not have an account yet? <span>Register here.</span>
          </p>
        </div>
      </header>
    </div>
  );
}

export default LoginScreen;
