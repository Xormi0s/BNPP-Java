import React, { useState, useEffect } from 'react'; // Import useState for managing local state and useEffect for side-effects
import api from '../api/axiosConfig'; // Import API configuration to make HTTP requests
import { useNavigate } from 'react-router-dom'; // Import useNavigate for programmatic navigation

const Home = () => {
    const [books, setBooks] = useState([]); // Initialize state to hold the list of books
    const navigate = useNavigate(); // Initialize navigate function for redirecting to other routes

    // useEffect hook to fetch the list of books when the component mounts
    useEffect(() => {
        const fetchBooks = async () => {
            try {
                // Send a GET request to retrieve books from the API
                const response = await api.get('/books');
                setBooks(response.data); // Store the fetched books in state
            } catch (err) {
                // Log any errors encountered during the API call
                console.error(err);
            }
        };

        fetchBooks(); // Trigger the fetchBooks function
    }, []); // Empty dependency array ensures this only runs once, on component mount

    return (
        <div className='py-40 px-10 bg-gray-100'>
            <h1 className='text-3xl font-bold text-blue-400'>Available books in store</h1>
            
            {/* Grid to display books */}
            <div className='grid grid-cols-3 gap-8 py-7 px-4'>
                {/* Loop through the books array and render each book */}
                {books.map(book => (
                    <div key={book._id} className="w-3/4 rounded-lg shadow-xl p-4 flex flex-col gap-3">
                        {/* Display book title */}
                        <h2 className='text-2xl text-blue-500'>{book.title}</h2>

                        {/* Display book authors */}
                        <h3 className='text-sm text-gray-600 italic'>{book.authors}</h3>

                        {/* Display book thumbnail */}
                        <img src={book.thumbnailUrl} className='w-20 h-21' alt={book.title}></img>

                        {/* Display short description or fallback message */}
                        {book.shortDescription ? (
                            <p className='text-sm text-gray-600'>{book.shortDescription}</p>
                        ) : (
                            <p className='text-sm text-gray-600'>No short description available.</p>
                        )}

                        {/* Button to navigate to the book details page */}
                        <button
                            onClick={() => navigate(`/books/${book.isbn}`)} // When clicked, navigate to the book detail page using its ISBN
                            className="bg-blue-500 text-white font-bold py-2 px-4 rounded mt-auto"
                        >
                            More Details
                        </button>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default Home;
