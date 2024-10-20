import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom'; // Import the useNavigate hook to navigate between pages
import api from '../api/axiosConfig'; // Import the axios instance to make API requests

const Cart = () => {
    const [cart, setCart] = useState(null); // State to store the cart data
    const [cartId, setCartId] = useState(null); // State to store the cart ID
    const [books, setBooks] = useState({}); // State to store the books in the cart
    const [loadingBooks, setLoadingBooks] = useState(true); // State to handle loading books
    const navigate = useNavigate(); // Hook to navigate to different pages

    // Fetch user information from localStorage when the component mounts
    useEffect(() => {
        const user = JSON.parse(localStorage.getItem('user')); // Get the user from localStorage
        if (user && user.id) {
            const cartId = user.id; // Use the user's ID as the cart ID
            setCartId(cartId); // Set the cart ID
            fetchCart(cartId); // Fetch the cart data for the given cart ID
        } else {
            console.error("User or User ID not found in localStorage"); // Handle the case when no user is found
        }
    }, []);

    // Fetch the cart details for the given cart ID
    const fetchCart = async (cartId) => {
        try {
            const response = await api.get(`/carts/${cartId}`); // Make a GET request to fetch the cart

            if (response.status === 200) {
                const fetchedCart = response.data; // Store the fetched cart data
                setCart(fetchedCart); // Set the cart state
                await fetchBooks(fetchedCart.orders); // Fetch the books for the cart orders
            } else {
                console.error('Failed to fetch cart data'); // Handle error if cart fetch fails
            }
        } catch (err) {
            console.error('Error fetching cart:', err); // Catch and log errors while fetching the cart
        }
    };

    // Fetch the book details for each book in the cart orders
    const fetchBooks = async (orders) => {
        const bookRequests = orders.map(order => fetchBook(order.bookId)); // Create an array of requests to fetch book details
        await Promise.all(bookRequests); // Wait for all book details to be fetched
        setLoadingBooks(false); // Set loading state to false once books are fetched
    };

    // Fetch individual book details by bookId
    const fetchBook = async (bookId) => {
        try {
            const response = await api.get(`/books/${bookId}`); // Fetch the book details by its ID
            if (response.status === 200) {
                setBooks(prevBooks => ({
                    ...prevBooks, // Keep previous books
                    [bookId]: response.data // Add the new book to the books state
                }));
            } else {
                console.error('Failed to fetch book data'); // Handle error if book fetch fails
            }
        } catch (err) {
            console.error('Error fetching book:', err); // Catch and log errors while fetching the book
        }
    };

    // Remove an item from the cart by orderId
    const removeFromCart = async (orderId) => {
        try {
            const response = await api.delete(`/carts/${cartId}/removeOrder/${orderId}`); // Make a DELETE request to remove the item from the cart
            setCart(response.data); // Update the cart state with the new data after removal
        } catch (error) {
            console.error("Error removing item from cart", error); // Handle error if removal fails
        }
    };

    // Update the quantity of an item in the cart by orderId
    const updateQuantity = async (orderId, newQuantity) => {
        try {
            const response = await api.put(`/carts/${cartId}/updateOrderQuantity/${orderId}?quantity=${newQuantity}`); // Make a PUT request to update the item quantity
            setCart(response.data); // Update the cart state with the new data after the update
        } catch (error) {
            console.error("Error updating item quantity", error); // Handle error if quantity update fails
        }
    };

    // Handle the checkout process
    const handleCheckout = () => {
        // Calculate the total price of the items in the cart
        const totalPrice = cart.orders.reduce((total, order) => {
            const book = books[order.bookId]; // Get the book details from the books state
            return total + (book ? book.price * order.quantity : 0); // Calculate the total price based on book price and quantity
        }, 0);

        // Navigate to the checkout page and pass the books and totalPrice as state
        navigate('/checkout', {
            state: {
                books,
                totalPrice
            }
        });
    };

    return (
        <div className='py-40 px-10 bg-gray-100 min-h-screen'>
            <h1 className='text-3xl font-bold text-blue-400'>Your current orders</h1>
            {loadingBooks ? (
                <p>Loading books...</p> // Show loading text while books are being fetched
            ) : (
                <>
                    {/* If there are no orders in the cart, display a message */}
                    {cart && cart.orders.length === 0 ? (
                        <p className='text-lg text-gray-500 mt-6'>You have no orders in your cart.</p>
                    ) : (
                        <div className='py-5'>
                            {/* Loop through each order in the cart */}
                            {cart && cart.orders.map((order) => (
                                <div key={order.id} className="flex justify-between items-center bg-white p-4 rounded shadow-md mt-6">
                                    <div>
                                        {/* If the book is loaded, display its details */}
                                        {books[order.bookId] ? (
                                            <>
                                                <p>{books[order.bookId].title} - ${books[order.bookId].price} - Quantity: {order.quantity}</p>
                                            </>
                                        ) : (
                                            <p>Loading book details...</p> // Show loading text if book details are not yet available
                                        )}
                                    </div>
                                    <div className='flex space-x-6'>
                                        {/* Buttons to update quantity or remove item from cart */}
                                        <button onClick={() => updateQuantity(order.id, order.quantity + 1)} className='bg-blue-500 text-white font-bold py-2 px-4 rounded'>
                                            +
                                        </button>
                                        <button onClick={() => updateQuantity(order.id, order.quantity - 1)} disabled={order.quantity === 1} className='bg-blue-500 text-white font-bold py-2 px-4 rounded'>
                                            -
                                        </button>
                                        <button onClick={() => removeFromCart(order.id)} className='bg-red-500 text-white font-bold py-2 px-4 rounded'>
                                            Remove
                                        </button>
                                    </div>
                                </div>
                            ))}
                        </div>
                    )}
                </>
            )}
            {/* Checkout button, disabled if cart is empty */}
            <button onClick={handleCheckout} disabled={!cart || cart.orders.length === 0} className='bg-green-500 text-white font-bold py-2 px-6 rounded mt-6'>
                Checkout
            </button>
        </div>
    );
};

export default Cart;
