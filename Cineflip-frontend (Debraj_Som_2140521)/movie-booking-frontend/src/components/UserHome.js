import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../Styles/UserHome.css'; // Import the CSS file

function UserHome({ loginId }) {
  const [movies, setMovies] = useState([]);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    fetchMovies();
  }, []);

  const fetchMovies = () => {
    axios.get('http://13.233.40.14:8082/fetchAllMoves')
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
    axios.get(`http://13.233.40.14:8082/fetchAMove/${movename}`)
      .then(response => {
        const { theaters, posterUrl } = response.data;
        navigate('/booking', { state: { movename, theaters, posterUrl } });
      })
      .catch(() => {
        setError('Failed to fetch movie details.');
      });
  };

  const handleLogout = () => {
    navigate('/login');
  };

  return (
    <div className="user-home">
      <header className="flex justify-between mx-16 items-center">
        <h1 className='font-bold text-5xl text-[#E50815]  mt-4'>CINEFLIP</h1>
        <div className='items-center pt-1'>

        <button className="font-semibold bg-red-600 hover:bg-red-500 items-center mt-4" onClick={handleLogout}>Logout</button>
        </div>
      </header>
      <main className="main-content space-y-4">
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
                <div className="flex flex-col py-2 space-y-1">
                  <h3 className='font-bold text-lg'>{movie.movename}</h3>
                  {/* Access the languages correctly */}
                  <p className='text-sm font-normal'>{movie.theaters.length > 0 ? movie.theaters[0].languages.join(', ') : 'Languages not available'}</p>
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
}

export default UserHome;
