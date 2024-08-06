import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import QRCodeImage from '../Images/QR.png'; // Adjust the import path if necessary
import '../Styles/BookingDetails.css'; // Import the CSS file for booking details
import axios from 'axios';

function BookingDetails() {
  const [bookingData, setBookingData] = useState(null);
  const [error, setError] = useState('');
  const navigate = useNavigate();
  
  useEffect(() => {
    const loginId = localStorage.getItem('loginId');
    
    if (!loginId) {
      setError('User not logged in.');
      navigate('/login'); // Redirect to login page if not logged in
      return;
    }

    // Fetch booking details
    axios.get(`http://localhost:8082/fetchBookingDetails/${loginId}`)
      .then(response => {
        console.log('Fetched booking data:', response.data); // Log the fetched data
        setBookingData(response.data);
      })
      .catch(error => {
        console.error('Error fetching booking details:', error);
        setError('Failed to fetch booking details.');
      });
  }, [navigate]);

  if (error) {
    return <div>{error}</div>;
  }

  if (!bookingData) {
    return <div>Loading booking details...</div>;
  }

  const { movename, theaterName, numberOfTickets, seatNumber = [], language, showTime } = bookingData;

  return (
    <div className="booking-details">
      <div className="qr-container">
        <img src={QRCodeImage} alt="QR Code" className="qr-code" />
      </div>
      <div className="booking-info">
        <h2>Booking Details</h2>
        <p><strong>Movie Name:</strong> {movename}</p>
        <p><strong>Theater Name:</strong> {theaterName}</p>
        <p><strong>Number of Tickets:</strong> {numberOfTickets}</p>
        <p><strong>Seat Numbers:</strong> {seatNumber.length ? seatNumber.join(', ') : 'N/A'}</p>
        <p><strong>Language:</strong> {language}</p>
        <p><strong>Show Time:</strong> {showTime}</p>
      </div>
    </div>
  );
}

export default BookingDetails;
