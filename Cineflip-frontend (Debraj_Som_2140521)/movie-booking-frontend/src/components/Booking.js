import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';
import '../Styles/Booking.css'; // Import the CSS file for booking

function Booking() {
  const location = useLocation();
  const navigate = useNavigate();
  const [theaters, setTheaters] = useState([]);
  const [selectedTheater, setSelectedTheater] = useState('');
  const [numberOfTickets, setNumberOfTickets] = useState('');
  const [seatNumber, setSeatNumber] = useState('');
  const [availableTickets, setAvailableTickets] = useState(0);
  const [language, setLanguage] = useState('');
  const [showTimings, setShowTimings] = useState([]);
  const [selectedShowTime, setSelectedShowTime] = useState('');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const { movename, posterUrl } = location.state || {};

  useEffect(() => {
    if (movename) {
      fetchMovieDetails();
    } else {
      setError('Movie name is missing.');
    }
  }, [movename]);

  const fetchMovieDetails = () => {
    axios.get(`http://localhost:8082/fetchAMove/${movename}`)
      .then(response => {
        const movieDetails = response.data;
        setTheaters(movieDetails.theaters || []);
        if (movieDetails.theaters && movieDetails.theaters.length > 0) {
          setSelectedTheater(movieDetails.theaters[0].theaterName);
          setAvailableTickets(movieDetails.theaters[0].noOfTickets);
          setLanguage(movieDetails.theaters[0].languages[0]); // Assuming single language
          setShowTimings(movieDetails.theaters[0].showTimings || []); // Set initial show timings
        }
      })
      .catch(error => {
        console.error('Error fetching movie details:', error);
        setError('Failed to fetch movie details.');
      });
  };

  const handleTheaterChange = (event) => {
    const selected = event.target.value;
    const selectedTheaterDetails = theaters.find(theater => theater.theaterName === selected);
    setSelectedTheater(selected);
    setAvailableTickets(selectedTheaterDetails.noOfTickets);
    setLanguage(selectedTheaterDetails.languages[0]); // Assuming single language
    setShowTimings(selectedTheaterDetails.showTimings || []); // Update show timings based on selected theater
  };

  const handleShowTimeChange = (event) => {
    setSelectedShowTime(event.target.value);
  };

  const handleBooking = () => {
    if (!selectedTheater) {
      setError('Please select a theater.');
      return;
    }

    if (!selectedShowTime) {
      setError('Please select a show time.');
      return;
    }

    const numTickets = parseInt(numberOfTickets, 10);
    if (!numTickets || numTickets <= 0 || numTickets > availableTickets) {
      setError('Please enter a valid number of tickets.');
      return;
    }

    const seats = seatNumber.split(',').map(num => parseInt(num, 10)).filter(num => !isNaN(num));
    if (seats.length === 0) {
      setError('Please enter at least one valid seat number.');
      return;
    }

    const loginId = localStorage.getItem('loginId'); // Retrieve loginId from localStorage

    if (!loginId) {
      setError('User not logged in.');
      return;
    }

    const bookingData = {
      movename,
      theaterName: selectedTheater,
      numberOfTickets: numTickets,
      seatNumber: seats,
      language,
      showTime: selectedShowTime,
      loginId
    };

    axios.post('http://localhost:8082/bookingMoveTickets', bookingData)
      .then((response) => {
        setSuccess('Booking successful!');
        setError('');
        setTimeout(() => {
          navigate('/home', { state: { bookingDetails: bookingData } }); // Redirect to booking details page
        }, 2000);
      })
      .catch(error => {
        console.error('Booking failed:', error);
        setError('Booking failed. Please try again.');
        setSuccess('');
      });
  };

  const handleCancel = () => {
    navigate('/home');
  };

  return (
    <div>
        <header className="flex justify-between mx-16 items-center">
        <h1 className='font-bold text-5xl text-[#E50815]  mt-4'>CINEFLIP</h1>
      </header>
      <div className='mx-auto flex justify-center mt-12'>
      
    <div className=" p-6 px-10 md:px-20  rounded-sm  mx-auto bg-[#121113] w-4/6">
    <h2 className='text-center font-bold text-4xl px-10 text-white mb-5'>Booking for {movename}</h2>
    <div className='flex'>
      <div className="w-1/2 my-auto">
        <img src={posterUrl} alt="Movie Poster" className="h-[400px] w-[300px] " />
      </div>
      <div className="white w-full item-center w-1/2">
        <div className="flex flex-col text-white">
          <label>
            Select Theater:
            </label>
            <select className="bg-transparent hover:bg-black border-[#262626] border text-white" value={selectedTheater} onChange={handleTheaterChange}>
              {theaters.length > 0 ? theaters.map((theater, index) => (
                <option key={index} value={theater.theaterName}>{theater.theaterName}</option>
              )) : <option value="">No theaters available</option>}
            </select>
          
          <label className='mt-3'>
            Select Show Time:
            </label>
            <select className="bg-transparent hover:bg-black border-[#262626] border text-white" value={selectedShowTime} onChange={handleShowTimeChange}>
              {showTimings.length > 0 ? showTimings.map((showTiming, index) => (
                <option key={index} value={showTiming.time}>{showTiming.time}</option>
              )) : <option value="">No show timings available</option>}
            </select>
          
          <label className='mt-3'>
            Select Number of Tickets:
            </label>
            <input className="bg-transparent hover:bg-black border-[#262626] text-white"
              type="number"
              value={numberOfTickets}
              onChange={(e) => setNumberOfTickets(e.target.value)}
              min="1"
              max={availableTickets}
            />
          
          <label>
            Seat Numbers (comma-separated):   
              <input className="bg-transparent hover:bg-black border-[#262626] text-white w-full"
                type="text"
                value={seatNumber}
                onChange={(e) => setSeatNumber(e.target.value)}
                placeholder="e.g., 1,2,3"
              />
          </label>
          <button className='bg-red-600 hover:bg-red-500  font-semibold' onClick={handleBooking}>Book Now</button>
          <button className='bg-white border-2 border-[#262626] hover:bg-red-500 hover:text-white font-semibold text-black' onClick={handleCancel}>Cancel Booking</button>
          {error && <p className="error">{error}</p>}
          {success && <p className="success">{success}</p>}
        </div>
        </div>
      </div>
    </div>
    </div>
    <footer className="footer">
        <p>&copy; 2024 CINEFLIP. All rights reserved.</p>
      </footer>
    </div>
  );
}

export default Booking;
