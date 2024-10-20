import React, { useState } from 'react';
import api from '../api/axiosConfig'; // Import the Axios configuration to handle API requests

const Register = () => {
    // Local state to store the input values for username and password
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    // Function to handle the registration process when the form is submitted
    const handleRegister = async (e) => {
        e.preventDefault(); // Prevent the form from refreshing the page

        try {
            // Send a POST request to the '/users/register' endpoint with the username and password
            const response = await api.post('/users/register', {
                username,
                password,
            });

            // If the response is successful (status 201), the registration is complete
            if (response.status === 201) {
                console.log('Registration successful! You can now log in.');
            }
        } catch (err) {
            // Handle errors that occur during the registration process
            console.error('An error occurred during registration:', err);
        }
    };

    return (
        <div className="h-screen flex items-center justify-center bg-gray-100">
            {/* Container for the registration form */}
            <div className="bg-white p-8 rounded-lg shadow-md w-full max-w-sm">
                <h2 className="text-2xl font-bold text-center text-blue-500 mb-6">Register</h2>
                
                {/* Form submission triggers the handleRegister function */}
                <form onSubmit={handleRegister}>
                    {/* Username input field */}
                    <div className="mb-4">
                        <label htmlFor="username" className="block text-sm font-medium text-gray-600">Username</label>
                        <input
                            type="text"
                            id="username"
                            value={username} // Binds the input value to the username state
                            onChange={(e) => setUsername(e.target.value)} // Updates the state when user types
                            className="w-full mt-1 p-2 border border-gray-300 rounded-md"
                            required
                        />
                    </div>
                    
                    {/* Password input field */}
                    <div className="mb-6">
                        <label htmlFor="password" className="block text-sm font-medium text-gray-600">Password</label>
                        <input
                            type="password"
                            id="password"
                            value={password} // Binds the input value to the password state
                            onChange={(e) => setPassword(e.target.value)} // Updates the state when user types
                            className="w-full mt-1 p-2 border border-gray-300 rounded-md"
                            required
                        />
                    </div>
                    
                    {/* Submit button to trigger the registration */}
                    <button type="submit" className="w-full bg-blue-500 text-white py-2 rounded-md hover:bg-blue-600 transition">
                        Register
                    </button>
                </form>

                {/* Link to the login page for users who already have an account */}
                <div className="mt-4 text-center">
                    <p className="text-sm text-gray-600">Already have an account? 
                        <a href="/login" className="text-blue-500"> Login</a>
                    </p>
                </div>
            </div>
        </div>
    );
};

export default Register;
