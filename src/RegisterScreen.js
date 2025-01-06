import React, { useState } from 'react';
import './RegisterScreen.css';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function RegisterScreen() {
  const [formData, setFormData] = useState({
    username: '',
    password: '',
    confirmPassword: '',
    email: '',
    phone: '',
  });

  const [feedback, setFeedback] = useState('');
  const [isError, setIsError] = useState(false); 
  const [isLoading, setIsLoading] = useState(false);

  const navigate = useNavigate();

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    console.log(`Input Change - ${name}: ${value}`); 
    setFormData((prevFormData) => ({
      ...prevFormData,
      [name]: value,
    }));
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    console.log('Register button clicked');

    if (formData.password !== formData.confirmPassword) {
      console.warn('Password and Confirm Password do not match'); 
      setFeedback('Passwords do not match!');
      setIsError(true);
      return;
    }

    setIsLoading(true);
    setFeedback('');
    setIsError(false);
    console.log('Sending POST request to backend with data:', formData);

    try {
      const response = await axios.post('http://localhost:8081/users', {
        username: formData.username,
        password: formData.password,
        email: formData.email,
        phone: formData.phone,
      });

      console.log('Received response from backend:', response); 

      if (response.status === 200 || response.status === 201) {
        console.log('Registration successful'); 
        setFeedback('Registration successful! Redirecting to login...');
        setIsError(false);

        setFormData({
          username: '',
          password: '',
          confirmPassword: '',
          email: '',
          phone: '',
        });
   
        setTimeout(() => {
          console.log('Redirecting to login page');
          navigate('/login');
        }, 2000);
      }
    } catch (error) {
      console.error('Registration error:', error); 

      if (error.response) {

        console.warn('Error response data:', error.response.data); 
        console.warn('Error response status:', error.response.status);

        switch (error.response.status) {
          case 400:
            setFeedback(`Registration failed: ${error.response.data.message || 'Bad Request'}`);
            break;
          case 401:
            setFeedback(`Registration failed: Unauthorized. Please check your credentials.`);
            break;
          case 409:
            setFeedback(`Registration failed: Conflict. ${error.response.data.message || 'User already exists.'}`);
            break;
          case 500:
            setFeedback(`Registration failed: Internal Server Error. Please try again later.`);
            break;
          default:
            setFeedback(`Registration failed: ${error.response.data.message || 'An error occurred.'}`);
        }
      } else if (error.request) {

        console.error('No response received:', error.request); 
        setFeedback('Registration failed: No response from server. Please ensure the backend is running.');
      } else {

        console.error('Error setting up request:', error.message);
        setFeedback(`Registration failed: ${error.message}`);
      }
      setIsError(true);
    } finally {
      setIsLoading(false);
      console.log('Registration process completed'); 
    }
  };

  const handleBackToLogin = () => {
    console.log('Back to Login clicked'); 
    navigate('/login');
  };

  return (
    <div className="RegisterScreen">
      <header className="RegisterScreen-header">
        <h1>Register Your Account</h1>
        <form className="register-form" onSubmit={handleRegister}>
          <div className="form-group">
            <label htmlFor="username">Username</label>
            <input
              type="text"
              id="username"
              name="username"
              placeholder="Enter your username"
              value={formData.username}
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
              value={formData.password}
              onChange={handleInputChange}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="confirmPassword">Confirm Password</label>
            <input
              type="password"
              id="confirmPassword"
              name="confirmPassword"
              placeholder="Confirm your password"
              value={formData.confirmPassword}
              onChange={handleInputChange}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="email">Email</label>
            <input
              type="email"
              id="email"
              name="email"
              placeholder="Enter your email"
              value={formData.email}
              onChange={handleInputChange}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="phone">Phone Number</label>
            <input
              type="tel"
              id="phone"
              name="phone"
              placeholder="Enter your phone number"
              value={formData.phone}
              onChange={handleInputChange}
              required
            />
          </div>
          <button type="submit" className="custom-button" disabled={isLoading}>
            {isLoading ? 'Registering...' : 'Register Account'}
          </button>
        </form>
        {feedback && (
          <p className={`feedback-message ${isError ? 'feedback-error' : ''}`}>
            {feedback}
          </p>
        )}
        <div className="login-link-container">
          <p className="login-link" onClick={handleBackToLogin}>
            Already have an account? <span>Back to Login</span>
          </p>
        </div>
      </header>
    </div>
  );
}

export default RegisterScreen;
