import React, { useContext } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import LoginScreen from './LoginScreen';
import RegisterScreen from './RegisterScreen';
import WelcomeScreen from './WelcomeScreen';
import CarRegister from './CarRegister';
import { UserContext } from './UserContext';

function ProtectedRoute({ children }) {
  const { user } = useContext(UserContext);
  if (!user) {
    return <Navigate to="/login" replace />;
  }
  return children;
}

function App() {
  const { user } = useContext(UserContext);

  return (
    <Router>
      <Routes>
        <Route path="/login" element={<LoginScreen />} />
        <Route path="/registerAccount" element={<RegisterScreen />} />
        <Route
          path="/welcome"
          element={
            <ProtectedRoute>
              <WelcomeScreen />
            </ProtectedRoute>
          }
        />
        <Route
          path="/registerCar"
          element={
            <ProtectedRoute>
              <CarRegister />
            </ProtectedRoute>
          }
        />
        <Route
          path="/"
          element={user ? <Navigate to="/welcome" /> : <LoginScreen />}
        />
      </Routes>
    </Router>
  );
}

export default App;
