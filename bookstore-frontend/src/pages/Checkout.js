import React from 'react';
import { useLocation } from 'react-router-dom';

const Checkout = () => {
    // useLocation hook is used to access the state passed via navigation
    const location = useLocation(); 

    // Extract 'books' and 'totalPrice' from the state passed via location
    const { books, totalPrice } = location.state || {}; 

    // If no books are found in the cart, show a message
    if (!books || Object.keys(books).length === 0) {
        return <p>No books found in the cart</p>;
    }

    return (
        <div className="py-40 px-10 bg-gray-100 min-h-screen">
            <h1 className="text-3xl font-bold text-blue-400">Checkout</h1>
            <div className="mt-6">
                {/* Loop through the books and display each one */}
                {Object.keys(books).map((bookId) => (
                    <div key={bookId} className="flex justify-between items-center bg-white p-4 rounded shadow-md mt-6">
                        <div>
                            {/* Display book title and price */}
                            <p>{books[bookId].title} - Price: ${books[bookId].price}</p>
                        </div>
                    </div>
                ))}
            </div>
            <div className="mt-6">
                {/* Display the total price */}
                <h2 className="text-xl font-bold">Total Price: ${totalPrice.toFixed(2)}</h2>
            </div>
            {/* Add payment information or further checkout steps here */}
        </div>
    );
};

export default Checkout;