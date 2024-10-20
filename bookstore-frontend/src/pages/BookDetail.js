import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import api from '../api/axiosConfig';

const BookDetail = () => {
    // useParams hook is used to extract the 'id' from the URL parameters
    const { id } = useParams();
    
    // State to store the book details fetched from the API
    const [book, setBook] = useState(null);
    
    // useNavigate hook is used to navigate to other pages programmatically
    const navigate = useNavigate();

    // useEffect hook is used to fetch the book details from the API when the component mounts
    useEffect(() => {
        const fetchBook = async () => {
            try {
                // Making a GET request to the API to fetch the book details using the 'id' from the URL
                const response = await api.get(`/books/${id}`);
                
                // Saving the fetched book data to the state
                setBook(response.data);
            } catch (err) {
                console.error(err); // Handling errors, if any
            }
        };

        fetchBook(); // Call the function to fetch the book data
    }, [id]); // Re-run this effect if the 'id' changes (when a new book is selected)

    // Function to handle adding the book to the cart
    const addToCart = async () => {
        if (!book) {
            // If the book is not available, show an alert and stop further execution
            alert("Book is not available.");
            return; // Stop the function execution
        }

        // Check if the user is logged in by looking for the user in localStorage
        const user = JSON.parse(localStorage.getItem('user'));

        // If no user is found, redirect to the login page
        if (!user) {
            alert("You need to log in to add items to the cart.");
            navigate('/login');
            return; // Stop further execution of this function
        }

        try {
            // Get the user from localStorage
            const user = JSON.parse(localStorage.getItem('user'));

            // Create the request body with the book and user
            const orderRequestDto = {
                book: book,
                user: user
            };

            // Send a POST request to add the book to the order (order creation)
            let response = await api.post('/orders/new', orderRequestDto, {
                headers: {
                    'Authorization': 'Basic Ym9va3N0b3JlOnRlc3QxMjM=' // Send the provided in memory user from spring boot to authenticate
                }
            });
   
            if (response.status === 201) {
                // If the order creation was successful, alert the user
                alert("Book added to cart!");
            }

            // After the book is added to the order, add the order to the cart
            response = await api.post('/carts/addOrder', response.data);
        } catch (err) {
            // Handle errors if the request fails
            if (err.response && err.response.status === 401) {
                // If the error is due to unauthorized access (user not logged in), show an alert and redirect to login page
                alert("You need to log in to add items to the cart.");
                navigate('/login'); // Redirect to login page
            } else {
                console.error(err); // Log any other errors
                alert('Error adding book to cart'); // Show a generic error message
            }
        }
    };

    // Check if the book data is loaded before rendering the content
    return (
        <div className='py-40 px-10 bg-gray-100'>
            {book ? (
                <>
                    {/* Display book details if the book data is available */}
                    <h1 className='text-3xl font-bold text-blue-400'>{book.title}</h1>
                    <h3 className='text-1xl text-gray-600 italic py-4'>Author(s): {book.authors}</h3>
                    <div className='flex flex-row gap-8 py-6'>
                        {/* Book thumbnail */}
                        <img src={book.thumbnailUrl} className='w-1/5' alt={book.title}></img>
                        
                        {/* Book description */}
                        <p className='w-2/4'>Description: {book.longDescription}</p>
                    </div>
                    {/* Display the current price of the book */}
                    <p className='py-4'>Current price: {book.price}€</p>
                    
                    {/* Button to add the book to the cart */}
                    <button onClick={addToCart} className='bg-blue-500 text-white font-bold py-2 px-4 rounded'>Add to Cart</button>
                </>
            ) : (
                // Show a loading message while the book details are being fetched
                <p>Loading...</p>
            )}
        </div>
    );
};

export default BookDetail;
