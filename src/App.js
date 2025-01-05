import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LoginScreen from './LoginScreen';
import RegisterScreen from './RegisterScreen';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<LoginScreen />} />
        <Route path="/registerAccount" element={<RegisterScreen />} />
        <Route path="/" element={<LoginScreen />} /> {/* Default to login */}
      </Routes>
    </Router>
  );
}

export default App;
