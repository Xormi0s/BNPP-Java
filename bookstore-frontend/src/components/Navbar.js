import { Link, useNavigate } from 'react-router-dom'; // Import Link for navigation and useNavigate to programmatically redirect
import { useContext } from 'react'; // Import useContext to access context values
import { AuthContext } from '../context/AuthContext'; // Import AuthContext for authentication status and logout function

const Navbar = () => {
    // Destructure isAuthenticated (to check login status) and logout (to log out the user) from AuthContext
    const { isAuthenticated, logout } = useContext(AuthContext); 
    const navigate = useNavigate(); // Initialize navigate for redirection

    // Handle logout when the user clicks the Logout button
    const handleLogout = async () => {
        try {
            // No server-side logout call here (optional), just call the context's logout function
            logout(); // Use the logout function from the context to update authentication status

            alert('Logged out successfully'); // Display success message
            navigate('/login'); // Redirect the user to the login page
        } catch (err) {
            // Log any errors that occur during the logout process
            console.error('Logout failed', err);
        }
    };

    return (
        <nav className='w-full fixed top-0 z-10 bg-blue-400'>
            <div className='container mx-auto py-4 flex items-center justify-between'>
                <div className='flex items-center'>
                    {/* Logo or brand name */}
                    <span className='text-2xl font-bold text-white'>BookStore</span>
                </div>

                {/* Navigation links */}
                <ul className='flex space-x-6 text-white font-bold uppercase'>
                    <li className='hover:text-blue-600'>
                        <Link to="/">Home</Link> {/* Link to the home page */}
                    </li>

                    {/* Show different options depending on whether the user is authenticated */}
                    {isAuthenticated ? (
                        <>
                            {/* If the user is logged in, show Cart and Logout */}
                            <li className='hover:text-blue-600'>
                                <Link to="/cart">Cart</Link> {/* Link to the Cart page */}
                            </li>
                            <li>
                                <button 
                                    onClick={handleLogout} // Trigger handleLogout when clicked
                                    className='hover:text-blue-600 uppercase'
                                >
                                    Logout
                                </button>
                            </li>
                        </>
                    ) : (
                        <>
                            {/* If the user is not logged in, show Login and Register */}
                            <li className='hover:text-blue-600'>
                                <Link to="/login">Login</Link> {/* Link to the login page */}
                            </li>
                            <li className='hover:text-blue-600'>
                                <Link to="/register">Register</Link> {/* Link to the register page */}
                            </li>
                        </>
                    )}
                </ul>
            </div>
        </nav>
    );
};

export default Navbar;