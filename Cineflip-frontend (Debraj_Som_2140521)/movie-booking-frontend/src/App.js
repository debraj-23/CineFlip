import React from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import Login from './components/Login';
import UserHome from './components/UserHome';
import Register from './components/Register';
import Booking from './components/Booking';
import AdminHome from './components/AdminHome';
import BookingDetails from './components/BookingDetails';
import MovieUpdate from './components/MovieUpdate';

function App() {
  console.log("Login Component: ", Login);
  console.log("UserHome Component: ", UserHome);
  console.log("Register Component: ", Register);
  console.log("Booking Component: ", Booking);
  console.log("AdminHome Component: ", AdminHome);
  console.log("BookingDetails Component: ", BookingDetails);
  console.log("MovieUpdate Component: ", MovieUpdate);
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/" element={<Navigate to="/register" />} />
        <Route path="/register" element={<Register />} />
        <Route path="/home" element={<UserHome />} />
        <Route path="/booking" element={<Booking />} />
        <Route path="/admin-dashboard" element={<AdminHome />} />
        <Route path="/booking-details" element={<BookingDetails />} />
        <Route path="/movieupdate" element={<MovieUpdate />} />
      </Routes>
    </Router>
  );
}

export default App;
