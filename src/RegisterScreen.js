import React, { useState } from 'react';
import './RegisterScreen.css';
import { useNavigate } from 'react-router-dom';

function RegisterScreen() {
  const [formData, setFormData] = useState({
    username: '',
    password: '',
    confirmPassword: '',
    email: '',
    phone: '',
  });

  const navigate = useNavigate();

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevFormData) => ({
      ...prevFormData,
      [name]: value,
    }));
  };

  const handleRegister = (e) => {
    e.preventDefault();
    // Add registration logic here
    if (formData.password !== formData.confirmPassword) {
      alert('Passwords do not match!');
    } else {
      alert(`Account Registered:\n${JSON.stringify(formData, null, 2)}`);
    }
  };

  const handleBackToLogin = () => {
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
          <button type="submit" className="custom-button">
            Register Account
          </button>
        </form>
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
