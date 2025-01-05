import React, { useState } from 'react';
import './LoginScreen.css';
import { useNavigate } from 'react-router-dom';

function LoginScreen() {
  const [credentials, setCredentials] = useState({
    username: '',
    password: '',
  });

  const navigate = useNavigate();

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setCredentials((prevCredentials) => ({
      ...prevCredentials,
      [name]: value,
    }));
  };

  const handleLogin = (e) => {
    e.preventDefault();
    alert(`Logging in with:\nUsername: ${credentials.username}\nPassword: ${credentials.password}`);
    // Add authentication logic here
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
          <button type="submit" className="custom-button">
            Login
          </button>
        </form>
        <div className="register-link-container">
          <p
            className="register-link"
            onClick={handleRegisterNavigation}
          >
            Do not have an account yet? <span>Register here.</span>
          </p>
        </div>
      </header>
    </div>
  );
}

export default LoginScreen;
