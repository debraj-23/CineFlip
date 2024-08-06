import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../Styles/UserHome.css';

const AdminHome = () => {
  const [movies, setMovies] = useState([]);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    fetchMovies();
  }, []);

  const fetchMovies = () => {
    axios.get('http://localhost:8082/fetchAllMoves')
      .then(response => {
        if (Array.isArray(response.data)) {
          setMovies(response.data);
          setError('');
        } else {
          setError('Unexpected data format. Please contact support.');
          setMovies([]);
        }
      })
      .catch(() => {
        setError('Failed to fetch movies.');
      });
  };

  const handleMovieClick = (movename) => {
    axios.get(`http://localhost:8082/fetchAMove/${movename}`)
        .then(response => {
            console.log('Fetched data for movie:', response.data); // Debug log
            if (response.data) {
                const { theaters, posterUrl } = response.data;
                console.log('Navigating to MovieUpdate with:', { movename, theaters, posterUrl });
                navigate('/movieupdate', { state: { movename, theaters, posterUrl } });
            } else {
                setError('Movie details not found.');
            }
        })
        .catch(() => {
            setError('Failed to fetch movie details.');
            console.error('Failed to fetch movie details for:', movename);
        });
};


  const handleLogout = () => {
    navigate('/login');
  };

  const handleGoToUserHome = () => {
    navigate('/home');
  };

  return (
    <div className="admin-home">
      <header className="flex justify-between mx-16 items-center">
        <h1 className='font-bold text-5xl text-[#E50815]  mt-4'>CINEFLIP</h1>
        <div className='items-center pt-1'>

        <button className="font-semibold bg-red-600 hover:bg-red-500 items-center mt-6" onClick={handleLogout}>Logout</button>
        </div>
      </header>
      <main className="main-content">
      <h2 className='text-[#E50815] font-medium'>Streamline Your Movie Experience – Book, Watch, Enjoy!</h2>
        <div className="movies-grid">
          {movies.length > 0 ? (
            movies.map((movie, index) => (
              <div
                key={index}
                className="movie-card"
                onClick={() => handleMovieClick(movie.movename)}
              >
                <img src={movie.posterUrl} alt={movie.movename} className="h-[300px] w-full" />
                <div className="movie-info text-white bg-[#121113] p-2 px-2.5 space-y-2">
                  <h3 className='font-bold text-xl'>{movie.movename}</h3>
                  <p className='text-sm font-semibold text-left'>Booked Tickets:</p>
                  {movie.theaters && movie.theaters.length > 0 ? (
                    movie.theaters.map((theater) => (
                      <div key={theater.theaterName} >
                        <p className='text-xs font-bold  text-left'>{theater.theaterName}:</p>
                        {theater.showTimings && theater.showTimings.length > 0 ? (
                          theater.showTimings.map((show) => (
                            <p className='text-xs text-left' key={show.time}>
                              {show.time}: {theater.noOfTickets - show.noOfTickets} booked, {theater.noOfTickets-(theater.noOfTickets - show.noOfTickets)} available
                            </p>
                          ))
                        ) : (
                          <p>No show timings available.</p>
                        )}
                      </div>
                    ))
                  ) : (
                    <p>No theaters available.</p>
                  )}
                </div>
              </div>
            ))
          ) : (
            <p>No movies found.</p>
          )}
        </div>
        {error && <p className="error">{error}</p>}
      </main>
      <footer className="footer">
        <p>&copy; 2024 CINEFLIP. All rights reserved.</p>
      </footer>
    </div>
  );
};

export default AdminHome;
