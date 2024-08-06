import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../Styles/styles.css'; // Import the CSS file
import { Link } from 'react-router-dom';

function Login() {
  const [loginData, setLoginData] = useState({
    loginId: '',
    password: ''
  });
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleChange = (e) => {
    setLoginData({ ...loginData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    axios.post('http://localhost:8082/login', loginData)
      .then(response => {
        const { message, isAdmin, loginId } = response.data;
        setMessage(message);
        setError('');
        if (loginId) {
          localStorage.setItem('loginId', loginId);
          console.log('Login ID stored:', loginId);
        }
        setTimeout(() => {
          // Redirect based on admin status
          if (isAdmin) {
            navigate('/admin-dashboard'); // Redirect to admin dashboard
          } else {
            navigate('/home'); // Redirect to user home page
          }
        }, 2000);
      })
      .catch(error => {
        setError('Login failed. Please try again.');
        setMessage('');
      });
  };

  return (
    <div className="w-full h-full">
      <div className="font-bold text-5xl text-[#E50815] ml-32 mt-4">
        <h1>CINEFLIP</h1>
      </div>
      <div className='mt-6 px-[400px] mt-28'>
      <div className="flex flex-col justify-center p-6 px-10 md:px-20  rounded-sm  mx-auto bg-[#121113]">
        <div className='space-y-6'>

        <h1 className='font-bold text-4xl text-white'>Login</h1>
        <form onSubmit={handleSubmit}>
          <input
            type="text"
            name="loginId"
            placeholder="Login ID"
            value={loginData.loginId}
            onChange={handleChange}
            required
          className='bg-transparent hover:bg-black border-[#262626] text-white'
          />
          <input
            type="password"
            name="password"
            placeholder="Password"
            value={loginData.password}
            onChange={handleChange}
            required
         className='bg-transparent hover:bg-black border-[#262626] text-white'
          />
          <button type="submit" className='bg-red-600 hover:bg-red-500 font-semibold'>Login</button>
        </form>
        {message && <p className="message">{message}</p>}
        {error && <p className="error">{error}</p>}
        <p className='text-white'>
          Don't have an account? <Link to="/register">Register here</Link>
        </p>
      </div>
    </div>
      <div className="mt-6 text-white text-sm">
        <p>&copy; 2024 CINEFLIP. All rights reserved.</p>
      </div>
    </div>
    </div>
  );
}

export default Login;
