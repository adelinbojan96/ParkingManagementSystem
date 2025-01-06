// src/index.js

import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App'; // Import App instead of WelcomeScreen
import reportWebVitals from './reportWebVitals';
import { UserProvider } from './UserContext'; // Import UserProvider

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <UserProvider> 
      <App /> 
    </UserProvider>
  </React.StrictMode>
);

reportWebVitals();
