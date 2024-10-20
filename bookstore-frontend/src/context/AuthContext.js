import { createContext, useState, useEffect } from 'react';

// Create the AuthContext to provide authentication data across the app
export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    // State to store the authentication status of the user
    const [isAuthenticated, setIsAuthenticated] = useState(false);

    // useEffect hook to check if the user is already logged in by reading from localStorage
    useEffect(() => {
        const user = JSON.parse(localStorage.getItem('user')); // Get the user data from localStorage
        if (user) {
            // If user data exists in localStorage, set isAuthenticated to true
            setIsAuthenticated(true);
        } else {
            // If no user data is found, set isAuthenticated to false
            setIsAuthenticated(false);
        }
    }, []); // Empty dependency array ensures this effect runs once when the component mounts

    // Login function to authenticate the user
    const login = (user) => {
        localStorage.setItem('user', JSON.stringify(user)); // Store user data in localStorage
        setIsAuthenticated(true); // Update state to reflect that the user is logged in
    };

    // Logout function to clear the user's session
    const logout = () => {
        localStorage.removeItem('user'); // Remove user data from localStorage
        setIsAuthenticated(false); // Update state to reflect that the user is logged out
    };

    // Return the AuthContext.Provider to provide the authentication state and functions to the entire app
    return (
        <AuthContext.Provider value={{ isAuthenticated, login, logout }}>
            {children} {/* Render the child components of the AuthProvider */}
        </AuthContext.Provider>
    );
};
