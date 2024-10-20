import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { useContext } from 'react'; // New: Required to use the context in ProtectedRoute
import { AuthProvider, AuthContext } from './context/AuthContext'; // Import AuthContext and AuthProvider
import Home from './pages/Home';
import BookDetail from './pages/BookDetail';
import Cart from './pages/Cart';
import Checkout from './pages/Checkout';
import Login from './pages/Login';
import Register from './pages/Register';
import Navbar from './components/Navbar';
import './output.css'; // Importing Tailwind CSS output for styling

// Protected route component
// This ensures that only authenticated users can access certain routes like Cart and Checkout
const ProtectedRoute = ({ children }) => {
    const { isAuthenticated } = useContext(AuthContext); // Get authentication status from the context

    // If the user is authenticated, render the children (protected component), otherwise redirect to login
    return isAuthenticated ? children : <Navigate to="/login" />;
};

function App() {
    return (
        // Wrap the entire application inside the AuthProvider
        // This makes the authentication state accessible across all components
        <AuthProvider> 
            <Router>
                {/* Navbar will be visible across all pages */}
                <Navbar />
                
                {/* Define the various routes for the application */}
                <Routes>
                    {/* Public routes */}
                    <Route path="/" element={<Home />} />
                    <Route path="/books/:id" element={<BookDetail />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/register" element={<Register />} />

                    {/* Protected routes: only accessible if the user is authenticated */}
                    <Route path="/cart" element={<ProtectedRoute><Cart /></ProtectedRoute>} />
                    <Route path="/checkout" element={<ProtectedRoute><Checkout /></ProtectedRoute>} />
                </Routes>
            </Router>
        </AuthProvider>
    );
}

export default App;