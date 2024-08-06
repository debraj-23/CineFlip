import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../Styles/styles.css'; // Import the CSS file
import { Link } from 'react-router-dom';

function Register() {
  const [formData, setFormData] = useState({
    userFirstName: '',
    userLastName: '',
    userEmail: '',
    loginId: '',
    password: '',
    conformPassword: '',
    contactNumber: '',
    admin: false
  });
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (formData.password !== formData.conformPassword) {
      setError("Passwords do not match!");
      setMessage('');
      return;
    }
    axios.post('http://localhost:8082/registration', formData)
      .then(response => {
        setMessage('Registration successful!');
        setError('');
        setTimeout(() => {
          navigate('/login');
        }, 2000);
      })
      .catch(error => {
        setError('Registration failed. Please try again.');
        setMessage('');
      });
  };

  return (
    <div className="w-full ">
      <div className="">
        <h1 className='font-bold text-5xl text-[#E50815] ml-32 mt-4'>CINEFLIP</h1>
      </div>
      <div className='mt-6 px-[400px]'>
      <div className="flex flex-col justify-center p-6 px-10 md:px-20  rounded-sm  mx-auto bg-[#121113]">
        <div className='space-y-6'>

        <h1 className='font-bold text-4xl px-10 text-white'>Register</h1>
        <form onSubmit={handleSubmit} className='px-10'>
          <input
            type="text"
            name="userFirstName"
            placeholder="First Name"
            value={formData.userFirstName}
            onChange={handleChange}
            required
         className='bg-transparent hover:bg-black border-[#262626] text-white'
          />
          <input
            type="text"
            name="userLastName"
            placeholder="Last Name"
            value={formData.userLastName}
            onChange={handleChange}
            required
          className='bg-transparent hover:bg-black border-[#262626] text-white'
          />
          <input
            type="email"
            name="userEmail"
            placeholder="Email"
            value={formData.userEmail}
            onChange={handleChange}
            required
            className='bg-transparent hover:bg-black border-[#262626] text-white'
          />
          <input
            type="text"
            name="loginId"
            placeholder="Login ID"
            value={formData.loginId}
            onChange={handleChange}
            required
            className='bg-transparent hover:bg-black border-[#262626] text-white'
          />
          <input
            type="password"
            name="password"
            placeholder="Password"
            value={formData.password}
            onChange={handleChange}
            required
            className='bg-transparent hover:bg-black border-[#262626] text-white'
          />
          <input
            type="password"
            name="conformPassword"
            placeholder="Confirm Password"
            value={formData.conformPassword}
            onChange={handleChange}
            required
          className='bg-transparent hover:bg-black border-[#262626] text-white'
          />
          <input
            type="text"
            name="contactNumber"
            placeholder="Contact Number"
            value={formData.contactNumber}
            onChange={handleChange}
            required
           className='bg-transparent hover:bg-black border-[#262626] text-white'
          />
          <label className='text-white font-medium text-md items-center'>
            <input
              type="checkbox"
              name="admin"
              checked={formData.admin}
              onChange={() => setFormData({ ...formData, admin: !formData.admin })}
            />
            Admin
          </label>
          <button type="submit" className='bg-red-600 hover:bg-red-500  font-semibold'>Register</button>
        </form>
        </div>
        {message && <p className="message">{message}</p>}
        {error && <p className="error">{error}</p>}
        <p className='text-white'>
          Already have an account? <Link to="/login">Login here</Link>
        </p>
      </div>

      </div>
      <div className="text-white text-sm mt-6">
        <p>&copy; 2024 CINEFLIP. All rights reserved.</p>
      </div>
    </div>
  );
}

export default Register;
