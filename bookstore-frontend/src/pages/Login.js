import React, { useState, useContext } from 'react'; // Import useState and useContext for state and context management
import { useNavigate } from 'react-router-dom'; // Import useNavigate for navigation after login
import api from '../api/axiosConfig'; // Import API configuration for making requests
import { AuthContext } from '../context/AuthContext'; // Import AuthContext to access authentication-related functions

const Login = () => {
    // Local state to store input values for username and password
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate(); // useNavigate hook to programmatically navigate to different routes

    const { login } = useContext(AuthContext); // Destructure the login function from AuthContext

    // Function to handle the login process when the form is submitted
    const handleLogin = async (e) => {
        e.preventDefault(); // Prevent the form from refreshing the page on submit

        try {
            // Send a POST request to the '/users/login' endpoint with username and password
            const response = await api.post('/users/login', { username, password }, { withCredentials: true });

            // Extract the id and username from the response data
            const { id, username: loggedInUsername } = response.data;

            // Store the user details in localStorage for persistence (keeps the user logged in across sessions)
            localStorage.setItem('user', JSON.stringify({ id, username: loggedInUsername }));

            // Call the login function from the AuthContext to update the global authentication state
            login({ id, username: loggedInUsername });

            // Notify the user of successful login and navigate to the homepage
            alert('Login successful!');
            navigate('/'); // Navigate to the homepage after login
        } catch (err) {
            // Handle login errors (e.g., incorrect credentials)
            console.error(err);
            alert('Login failed. Please check your credentials.');
        }
    };

    return (
        <div className="h-screen flex items-center justify-center bg-gray-100">
            {/* Container for the login form */}
            <div className="bg-white p-8 rounded-lg shadow-xl w-full max-w-sm">
                <h2 className="text-2xl font-bold text-center text-blue-500 mb-6">Login</h2>
                
                {/* Form submission triggers the handleLogin function */}
                <form onSubmit={handleLogin}>
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
                    
                    {/* Submit button to trigger the login */}
                    <button type="submit" className="w-full bg-blue-500 text-white py-2 rounded-md hover:bg-blue-600 transition">
                        Login
                    </button>
                </form>

                {/* Link to the registration page for users who don't have an account */}
                <div className="mt-4 text-center">
                    <p className="text-sm text-gray-600">
                        Don't have an account? <a href="/register" className="text-blue-500">Register</a>
                    </p>
                </div>
            </div>
        </div>
    );
};

export default Login;
