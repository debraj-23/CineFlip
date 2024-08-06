import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useLocation, useNavigate } from 'react-router-dom';
import '../Styles/MovieUpdate.css'; // Import your CSS file

const MovieUpdate = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { movename, theaters, posterUrl } = location.state || {};
  const [theaterList, setTheaterList] = useState(theaters || []);

  useEffect(() => {
    if (!location.state) {
      navigate('/admin-dashboard'); // Redirect if state is null
    }
  }, [location.state, navigate]);

  const handleUpdateStatus = (theaterName) => {
    axios.put(`http://localhost:8082/updateTicketBookingStatus/${movename}/${theaterName}`)
      .then(() => {
        setTheaterList(prevTheaters => 
          prevTheaters.map(theater => 
            theater.theaterName === theaterName
              ? { ...theater, status: 'BOOK ASAP' }
              : theater
          )
        );
      })
      .catch((error) => {
        console.error('Error updating status:', error);
      });
  };
  const handleLogout = () => {
    navigate('/login');
  };
  const handleHome = () => {
    navigate('/admin-dashboard')
  }

  return (
    <div>
        <header className="flex justify-between mx-16 items-center">
        <h1 className='font-bold text-5xl text-[#E50815]  mt-4'>CINEFLIP</h1>
        <div className='flex space-x-6 mt-4'>
        <button className="font-semibold bg-red-600 hover:bg-red-500 items-center" onClick={handleHome}>Home    </button>
        <button className="font-semibold bg-red-600 hover:bg-red-500 items-center" onClick={handleLogout}>Logout</button>
        </div>
      </header>
      <div className='mx-auto flex justify-center mt-12'>
      
    <div className=" p-6 px-10 md:px-20  rounded-sm  mx-auto bg-[#121113] w-4/6">
    <h2 className='text-center font-bold text-4xl px-10 text-white mb-5'>{movename}</h2>
    <div className='flex'>
      <div className="w-1/2 my-auto">
        <img src={posterUrl} alt="Movie Poster" className="h-[400px] w-[300px] " />
      </div>
      <div className="white w-full w-1/2">
      <h3 className='text-white text-2xl font-bold'>Theaters:</h3>
        <div className="flex flex-col text-white my-4">
        {theaterList.map((theater) => (
            <div key={theater.theaterName} className="">
              <div className='flex space-x-4'>
              <h4>{theater.theaterName}</h4>
              <p className='px-20'>Status: {theater.status}</p>

              </div>
              <button onClick={() => handleUpdateStatus(theater.theaterName)} 
              className='bg-red-600 hover:bg-red-500 font-medium px-6 py-2 mt-10 w-full'>Update</button>
            </div>
          ))}
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
};

export default MovieUpdate;
